package com.fatec.anexo_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

@Configuration
public class GridFSConfig {

    @Autowired
    private MongoDatabaseFactory mongoDatabaseFactory;

    @Autowired
    private MappingMongoConverter mappingMongoConverter;

    @Bean
    public GridFsTemplate gridFsTemplate() {
        return new GridFsTemplate(mongoDatabaseFactory, mappingMongoConverter);
    }

    @Bean
    public MongoDatabase mongoDatabase(MongoClient mongoClient) {
        return mongoClient.getDatabase(mongoDatabaseFactory.getMongoDatabase().getName());
    }
}
