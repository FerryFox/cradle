package com.fox.cradle.features.stamp.controller;

import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.features.stamp.model.TemplateRequest;
import com.fox.cradle.features.stamp.model.TemplateResponse;
import com.fox.cradle.features.stamp.service.StampCardTemplateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class StampCardTemplateController
{
    private final JwtService _jwtService;
    private final StampCardTemplateService _stampCardTemplateService;

    @GetMapping("/all")
    public ResponseEntity<List<TemplateResponse>> getAllTemplates()
    {
        List<TemplateResponse> response = _stampCardTemplateService.getAllTemplates();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<TemplateResponse> register(@RequestBody TemplateRequest request, HttpServletRequest httpServletRequest)
    {
        String name = _jwtService.extractUsernameFromRequest(httpServletRequest);
        request.setCreatedBy(name);
        TemplateResponse response = _stampCardTemplateService.createTemplate(request);
        //not jet created
        return ResponseEntity.ok(response);
    }
}
