package ch.abbaye11.adsbservice.infrastructure.integration;

import ch.abbaye11.adsbservice.domaine.sbs1.Dump1090TrameUtil;
import ch.abbaye11.adsbservice.domaine.sbs1.SBS1Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Router;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import static ch.abbaye11.adsbservice.domaine.sbs1.SBS1MessageType.MSG;
import static ch.abbaye11.adsbservice.infrastructure.integration.ChannelsConfiguration.*;

@Configuration
@Slf4j
public class RoutersConfiguration {

    @Autowired
    ObjectMapper objectMapper;

    @Router(inputChannel = "sbs1RawChannel")
    public String routeExcludeInvalidTrame (Message message) throws JsonProcessingException {
        assert message.getPayload() instanceof byte[];
        log.info("[router][toRawKo, toRawOk][payload=byte[]]");
        logHeaders(message.getHeaders());
        logPayload(message.getPayload());

        String trameTcp = new String((byte[]) message.getPayload());

        if(Dump1090TrameUtil.validatDump1090TCPTrame(trameTcp)){
            return ChannelsConfiguration.SBS_1_RAW_OK_CHANNEL_NAME;
        }else{
            return ChannelsConfiguration.SBS_1_RAW_KO_CHANNEL_NAME;
        }
    }

    @Router(inputChannel = SBS_1_CHANNEL_NAME)
    public String routeByMessageType (SBS1Message message){

        if(message.getMessageType().equals(MSG)){
            return MSG_CHANNEL_NAME;
        }else{
            return NON_MSG_CHANNEL_NAME;
        }
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
