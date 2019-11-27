package ch.abbaye11.adsbservice.infrastructure.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.ip.tcp.TcpReceivingChannelAdapter;
import org.springframework.integration.ip.tcp.connection.TcpNetClientConnectionFactory;

@Configuration
@Slf4j
public class TcpInboundAdapterConfiguration {

    @Value("${adsb.tcp.endpoint.host}")
    private String tcpEndpointHost;

    @Value("${adsb.tcp.endpoint.port}")
    private Integer tcpEndpointport;
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
        inbound.setOutputChannelName(ChannelsConfiguration.TCP_OUT_CHANNEL_NAME);

        return inbound;
    }

    @Bean
    public TcpNetClientConnectionFactory connectionFactory() {
        return new TcpNetClientConnectionFactory(tcpEndpointHost,tcpEndpointport);
    }
}
