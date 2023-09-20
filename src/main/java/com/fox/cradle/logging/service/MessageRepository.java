package com.fox.cradle.logging.service;

import com.fox.cradle.logging.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageRepository extends MongoRepository<Message, String>
{
}
