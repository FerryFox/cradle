package com.fox.cradle.features.news.service;

import com.fox.cradle.features.news.model.News;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService
{
    private final NewsReposetory newsReposetory;

    public News getNewsById(String id)
    {
        return newsReposetory.findById(id).get();
    }

    public List<News> getAllNews()
    {
        return newsReposetory.findAll();
    }

    public News saveNews(News news)
    {
        return newsReposetory.save(news);
    }

    public void deleteNewsById(String id)
    {
        newsReposetory.deleteById(id);
    }

    public News updateNews(News news)
    {
        return newsReposetory.save(news);
    }
}
