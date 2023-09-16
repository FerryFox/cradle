package com.fox.cradle.features.picture.model;

import jakarta.persistence.GeneratedValue;
import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "pictures")
@Data
public class Picture
{
    @MongoId
    @GeneratedValue
    private String id;

    private String name;

    private Binary imageData;
}
