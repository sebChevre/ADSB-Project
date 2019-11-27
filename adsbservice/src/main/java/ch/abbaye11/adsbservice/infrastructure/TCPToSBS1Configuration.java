package ch.abbaye11.adsbservice.infrastructure;

import ch.abbaye11.adsbservice.domaine.sbs1.Dump1090TrameUtil;
import ch.abbaye11.adsbservice.domaine.sbs1.SBS1Message;
import ch.abbaye11.adsbservice.domaine.sbs1.SBS1MessageType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.ip.tcp.TcpReceivingChannelAdapter;
import org.springframework.integration.ip.tcp.connection.TcpNetClientConnectionFactory;
import org.springframework.integration.mongodb.config.MongoDbInboundChannelAdapterParser;
import org.springframework.integration.mongodb.outbound.MongoDbStoringMessageHandler;
import org.springframework.messaging.*;
import org.springframework.messaging.handler.annotation.Header;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Arrays;
import java.util.Optional;

import static ch.abbaye11.adsbservice.domaine.sbs1.SBS1MessageType.MSG;

@Configuration
@EnableIntegration
@Slf4j
public class TCPToSBS1Configuration {

    public static final String SBS_1_RAW_OK_CHANNEL_NAME = "sbs1RawOkChannel";
    public static final String SBS_1_RAW_KO_CHANNEL_NAME = "sbs1RawKoChannel";
    public static final String SBS_1_CHANNEL_NAME = "sbs1Channel";
    public static final String MSG_CHANNEL_NAME = "msgChannel";
    public static final String NON_MSG_CHANNEL_NAME = "nonMsgChannel";
    public static final String NON_MSG_JSON_CHANNEL_NAME = "nonMsgJsonChannel";
    private static final String MSG_JSON_CHANNEL_NAME = "msgJsonChannel";
    private final String TCP_HOST = "localhost";//""Raspberrypi.local";
    private final int TCP_PORT = 1234;//30003;

    private final static String TCP_OUT_CHANNEL_NAME = "sbs1RawChannel";


    @Autowired
    ObjectMapper objectMapper;

    /**
     * Adapteur point d'entrée pour les trames tcp
     * @return l'adapteur tcp
     */
    @Bean
    public TcpReceivingChannelAdapter tcpDump1090InboundAdapter() {
        log.info("Creating inbound dump1090 tcp adapter");

        TcpReceivingChannelAdapter inbound = new TcpReceivingChannelAdapter();
        inbound.setClientMode(Boolean.TRUE);
        inbound.setConnectionFactory(connectionFactory());
        inbound.setOutputChannelName(TCP_OUT_CHANNEL_NAME);

        return inbound;
    }


    @Router(inputChannel = "sbs1RawChannel")
    public String routeExcludeInvalidTrame (Message message) throws JsonProcessingException {
        assert message.getPayload() instanceof byte[];
        log.info("[router][toRawKo, toRawOk][payload=byte[]]");
        logHeaders(message.getHeaders());
        logPayload(message.getPayload());

        String trameTcp = new String((byte[]) message.getPayload());

        if(Dump1090TrameUtil.validatDump1090TCPTrame(trameTcp)){
            return SBS_1_RAW_OK_CHANNEL_NAME;
        }else{
            return SBS_1_RAW_KO_CHANNEL_NAME;
        }
    }

    @Profile("file")
    @Bean
    @ServiceActivator(inputChannel= "sbs1RawKoChannel")
    public MessageHandler writeRawKoToFile() {

        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("out"));
        handler.setFileNameGenerator(message -> {
            return "tcp-ko.txt";
        });
        handler.setAppendNewLine(Boolean.TRUE);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setExpectReply(false);
        return handler;
    }

    /**
     * Service Activator retourne une instance de message SBBS1
     * @param rawSBS1 la chaine sbs séparé par des virgules
     * @return une instance de message sbs1
     */
    @ServiceActivator(inputChannel = SBS_1_RAW_OK_CHANNEL_NAME, outputChannel = SBS_1_CHANNEL_NAME)
    public SBS1Message mapToSBS1Message(String rawSBS1){
        log.debug("[sbs1Raw]: {}",rawSBS1);
        SBS1Message sbs1Message = SBS1Message.getInstanceFromRawMessage(rawSBS1);
        log.info("before [sbs1]: {}",sbs1Message);
        return sbs1Message;
    }



    @Router(inputChannel = SBS_1_CHANNEL_NAME)
    public String routeByMessageType (SBS1Message message){

        if(message.getMessageType().equals(MSG)){
            return MSG_CHANNEL_NAME;
        }else{
            return NON_MSG_CHANNEL_NAME;
        }
    }


    @Transformer(inputChannel = NON_MSG_CHANNEL_NAME,outputChannel = NON_MSG_JSON_CHANNEL_NAME)
    public String convertToJsonString(Message message) throws JsonProcessingException {
        assert message.getPayload() instanceof SBS1Message;
        log.info("[transformer][nonMsg, nonMsgJson][payload=SMS1Message]");

        logHeaders(message.getHeaders());
        logPayload(message.getPayload());

        return objectMapper.writeValueAsString(message.getPayload());
    }



    @Profile("file")
    @Bean
    @ServiceActivator(inputChannel= NON_MSG_JSON_CHANNEL_NAME)
    public MessageHandler writeNonMsgTypeToFile() {

        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("out"));
        handler.setFileNameGenerator(message -> {
            return "sbs1-non-msg.txt";
        });
        handler.setAppendNewLine(Boolean.TRUE);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setExpectReply(false);
        return handler;
    }


    @Transformer(inputChannel = MSG_CHANNEL_NAME,outputChannel = MSG_JSON_CHANNEL_NAME)
    public String convertJsonToString(Message message) throws JsonProcessingException {
        assert message.getPayload() instanceof SBS1Message;
        log.info("[transformer][msg, msgJson][payload=SMS1Message]");

        logHeaders(message.getHeaders());
        logPayload(message.getPayload());

        return objectMapper.writeValueAsString(message.getPayload());
    }




    @Profile("file")
    @Bean
    @ServiceActivator(inputChannel= MSG_JSON_CHANNEL_NAME)
    public MessageHandler fileWritingHandler() {


        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("out"));
        handler.setFileNameGenerator(msg -> {
            return "sbs1-msg.txt";
        });

        handler.setAppendNewLine(Boolean.TRUE);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setExpectReply(false);
        return handler;
    }



        @Bean
        public MongoDbFactory mongoDbFactory() throws Exception {
            return new SimpleMongoDbFactory(new MongoClient(), "adsb");
        }

        @Bean
        @ServiceActivator(inputChannel = MSG_CHANNEL_NAME)
        public MessageHandler mongodbAdapter() throws Exception {
            MongoDbStoringMessageHandler adapter = new MongoDbStoringMessageHandler(mongoDbFactory());
            adapter.setCollectionNameExpression(new LiteralExpression("message"));


            return adapter;
        }






    @Bean
    public TcpNetClientConnectionFactory connectionFactory() {
        return new TcpNetClientConnectionFactory(TCP_HOST,TCP_PORT);
    }

    /**
     ************************* CHANNEL **********************
     */
    @Bean
    public MessageChannel msgChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public MessageChannel msgJsonChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public MessageChannel nonMsgChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public MessageChannel nonMsgJsonChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public MessageChannel sbs1RawChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public MessageChannel sbs1RawKoChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public MessageChannel sbs1Channel() {
        return new PublishSubscribeChannel();
    }

    @PostConstruct
    public void run() throws Exception {


        ((PublishSubscribeChannel)sbs1RawChannel()).subscribe(new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                log.debug("[sbs1Raw][client.handler] {}: ",message.getPayload());
            }
        });

        ((PublishSubscribeChannel)sbs1Channel()).subscribe(new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                log.info("[sbs1][client.handler] {}: ", message.getPayload());
            }
        });





    }


    private final void logHeaders(MessageHeaders headers){
        headers.keySet().forEach(key -> {
            log.debug("[logHeader] key:{}, value: {}",key,headers.get(key));
        });
    }

    private final void logPayload(Object payload) throws JsonProcessingException {
        log.debug("[logBody] {}", objectMapper.writeValueAsString(payload));
    }

}
