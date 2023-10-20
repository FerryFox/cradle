package com.fox.cradle;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Testcontainers
public abstract class AbstractMongoDBIntegrationTest {

    @Container
    protected static final MongoDBContainer mongoDBContainer = new MongoDBContainer()
            .withStartupTimeout(Duration.ofMinutes(3));


    @DynamicPropertySource
    static void setUrlDynamically(DynamicPropertyRegistry registry) {
        System.out.println("MONGO_URL=" + mongoDBContainer.getReplicaSetUrl());
        registry.add("MONGO_URL", () -> mongoDBContainer.getReplicaSetUrl());
    }
}
