package com.fox.cradle.features.news.service;

import com.fox.cradle.features.news.model.News;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsReposetory extends MongoRepository<News, String>
{
}
