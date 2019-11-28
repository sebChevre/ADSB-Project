package ch.abbaye11.adsbservice.infrastructure.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class ChannelsConfiguration {

    public static final String SBS_1_RAW_OK_CHANNEL_NAME = "sbs1RawOkChannel";
    public static final String SBS_1_RAW_KO_CHANNEL_NAME = "sbs1RawKoChannel";
    public static final String SBS_1_CHANNEL_NAME = "sbs1Channel";
    public static final String MSG_CHANNEL_NAME = "msgChannel";
    public static final String NON_MSG_CHANNEL_NAME = "nonMsgChannel";
    public static final String NON_MSG_JSON_CHANNEL_NAME = "nonMsgJsonChannel";
    public static final String MSG_JSON_CHANNEL_NAME = "msgJsonChannel";
    public final static String TCP_OUT_CHANNEL_NAME = "sbs1RawChannel";

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
    public MessageChannel sbs1RawOkChannel() {
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

}
