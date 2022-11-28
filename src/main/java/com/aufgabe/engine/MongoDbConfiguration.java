package com.aufgabe.engine;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Configuration
@EnableTransactionManagement
@EnableMongoRepositories(basePackages = {"com.aufgabe.engine.repository"})
public class MongoDbConfiguration extends AbstractMongoClientConfiguration {

    private final String dbConnectionString;
    private final String defaultDatabaseName;

    public MongoDbConfiguration(
            @Value("${infrastructure.services.mongodb.cluster.url}") String dbConnectionString,
            @Value("${infrastructure.services.mongodb.database.name}") String defaultDatabaseName
    ) {
        this.dbConnectionString = dbConnectionString;
        this.defaultDatabaseName = defaultDatabaseName;
    }

    @Override
    @Bean
    @Scope(SCOPE_SINGLETON)
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(this.dbConnectionString);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public MongoDatabase mongoDatabase(@Autowired MongoClient mongoClient) {
        return mongoClient.getDatabase(defaultDatabaseName);
    }

    @Bean
    public MongoTemplate mongoTemplate(@Autowired MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, defaultDatabaseName);
    }

    @Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Override
    protected String getDatabaseName() {
        return this.defaultDatabaseName;
    }
}