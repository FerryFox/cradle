package com.fox.cradle.logging.service;

import com.fox.cradle.logging.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MessageRepository extends MongoRepository<Message, String>
{
}
