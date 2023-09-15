package com.fox.cradle.logging.service;

import com.fox.cradle.logging.model.Massage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface MassageReposetory extends MongoRepository<Massage, String>
{
}
