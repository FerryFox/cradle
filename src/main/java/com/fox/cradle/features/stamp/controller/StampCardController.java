package com.fox.cradle.features.stamp.controller;

import com.fox.cradle.features.stamp.service.StampCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stamp-card")
@RequiredArgsConstructor
public class StampCardController
{
    private final StampCardService stampCardService;


}
