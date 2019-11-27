package ch.abbaye11.adsbservice.infrastructure.integration;

import ch.abbaye11.adsbservice.domaine.sbs1.SBS1Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageHandler;

import java.io.File;

import static ch.abbaye11.adsbservice.infrastructure.integration.ChannelsConfiguration.*;

@Configuration
@Slf4j
public class ServicesActivatorConfiguration {

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
}
