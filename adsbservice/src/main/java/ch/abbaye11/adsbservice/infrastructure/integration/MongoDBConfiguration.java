package ch.abbaye11.adsbservice.infrastructure.integration;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mongodb.outbound.MongoDbStoringMessageHandler;
import org.springframework.messaging.MessageHandler;

@Configuration
public class MongoDBConfiguration {

    @Value("${mongodb.host}")
    private String mongodbHost;

    @Value("${mongodb.port}")
    private Integer mongodbPort;

    @Value("${mongodb.database}")
    private String mongodbDatabase;

    @Value("${mongodb.collection}")
    private String mongodbCollection;

    @Bean
    @ServiceActivator(inputChannel = ChannelsConfiguration.MSG_CHANNEL_NAME)
    public MessageHandler mongodbAdapter() throws Exception {
        MongoDbStoringMessageHandler adapter = new MongoDbStoringMessageHandler(mongoDbFactory());
        adapter.setCollectionNameExpression(new LiteralExpression(mongodbCollection));


        return adapter;
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(new MongoClient(mongodbHost,mongodbPort), mongodbDatabase);
    }
}
