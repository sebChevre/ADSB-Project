package ch.abbaye11.adsbservice.infrastructure.integration;

import ch.abbaye11.adsbservice.domaine.sbs1.SBS1Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import static ch.abbaye11.adsbservice.infrastructure.integration.ChannelsConfiguration.*;

@Configuration
@Slf4j
public class TransformersConfiguration {

    @Autowired
    ObjectMapper objectMapper;

    @Transformer(inputChannel = NON_MSG_CHANNEL_NAME,outputChannel = NON_MSG_JSON_CHANNEL_NAME)
    public String convertToJsonString(Message message) throws JsonProcessingException {
        assert message.getPayload() instanceof SBS1Message;
        log.info("[transformer][nonMsg, nonMsgJson][payload=SMS1Message]");

        logHeaders(message.getHeaders());
        logPayload(message.getPayload());

        return objectMapper.writeValueAsString(message.getPayload());
    }

    @Transformer(inputChannel = MSG_CHANNEL_NAME,outputChannel = MSG_JSON_CHANNEL_NAME)
    public String convertJsonToString(Message message) throws JsonProcessingException {
        assert message.getPayload() instanceof SBS1Message;
        log.info("[transformer][msg, msgJson][payload=SMS1Message]");

        logHeaders(message.getHeaders());
        logPayload(message.getPayload());

        return objectMapper.writeValueAsString(message.getPayload());
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
