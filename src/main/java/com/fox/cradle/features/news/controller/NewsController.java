package com.fox.cradle.features.news.controller;

import com.fox.cradle.features.news.model.News;
import com.fox.cradle.features.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController
{
    private final NewsService newsService;

    @GetMapping("/home")
    public ResponseEntity<List<News>> getAllNews()
    {
        return ResponseEntity.ok(newsService.getAllNews());
    }
}
