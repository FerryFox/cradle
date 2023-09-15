package com.fox.cradle.logging.model;

import jakarta.persistence.GeneratedValue;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Document
@Data
public class Message
{
    @MongoId
    @GeneratedValue
    private String id;

    private Instant timestamp = Instant.now();

    private String message;

    @Override
    public String toString()
    {
        LocalDateTime localDateTime = timestamp.atZone(ZoneId.systemDefault()).toLocalDateTime();
        return "Massage{" +
                "id='" + id + '\'' +
                ", timestamp=" + localDateTime +
                ", message='" + message + '\'' +
                '}';
    }
}
