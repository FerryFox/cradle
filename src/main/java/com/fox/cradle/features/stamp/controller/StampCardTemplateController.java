package com.fox.cradle.features.stamp.controller;

import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.features.stamp.model.TemplateRequestDTO;
import com.fox.cradle.features.stamp.model.TemplateResponseDTO;
import com.fox.cradle.features.stamp.service.StampCardTemplateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/stamp-card-templates")
@RequiredArgsConstructor
public class StampCardTemplateController
{
    private final JwtService _jwtService;
    private final StampCardTemplateService _stampCardTemplateService;
    @GetMapping
    public ResponseEntity<List<TemplateResponseDTO>> getAllTemplates()
    {
        //not real return atm
        List<TemplateResponseDTO> response = new ArrayList<>();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<TemplateResponseDTO> register(@RequestBody TemplateRequestDTO request, HttpServletRequest httpServletRequest)
    {
        String name = _jwtService.extractUsernameFromRequest(httpServletRequest);
        request.setCreatedBy(name);
        TemplateResponseDTO response = _stampCardTemplateService.createTemplate(request);
        //not jet created
        return ResponseEntity.ok(response);
    }
}
