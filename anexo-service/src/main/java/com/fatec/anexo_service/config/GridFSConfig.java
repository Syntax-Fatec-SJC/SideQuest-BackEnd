package com.fatec.anexo_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;

@Configuration
public class GridFSConfig {

    private final MongoClient mongoClient;

    public GridFSConfig(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Bean
    public GridFSBucket gridFSBucket() {
        // Assume que o nome do banco de dados é "sidequest_anexo"
        MongoDatabase database = mongoClient.getDatabase("sidequest_anexo");
        // Cria e retorna o GridFSBucket, que lida com o armazenamento de arquivos grandes
        return GridFSBuckets.create(database);
    }
}
