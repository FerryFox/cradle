package com.fox.cradle;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractMongoDBIntegrationTest {

    @Container
    protected static final MongoDBContainer mongoDBContainer = new MongoDBContainer();

    @BeforeAll
    static void setup() {
        mongoDBContainer.start();
    }

    @AfterAll
    static void cleanup() {
        mongoDBContainer.stop();
    }

    @DynamicPropertySource
    static void setUrlDynamically(DynamicPropertyRegistry registry) {
        System.out.println("MONGO_URL=" + mongoDBContainer.getReplicaSetUrl());
        registry.add("MONGO_URL", () -> mongoDBContainer.getReplicaSetUrl());
    }
}
