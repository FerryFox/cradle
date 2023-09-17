package com.fox.cradle.features.stamp.controller;

import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.service.AppUserService;
import com.fox.cradle.features.stamp.model.NewTemplate;
import com.fox.cradle.features.stamp.model.TemplateResponse;
import com.fox.cradle.features.stamp.service.TemplateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class TemplateController
{
    private final JwtService _jwtService;
    private final TemplateService _TemplateService;
    private final AppUserService _appUserService;

    @GetMapping("/all")
    public ResponseEntity<List<TemplateResponse>> getAllTemplates()
    {
        List<TemplateResponse> response = _TemplateService.getAllTemplates();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    public ResponseEntity<List<TemplateResponse>> getMyTemplates(HttpServletRequest httpServletRequest)
    {
        String email = _jwtService.extractUsernameFromRequest(httpServletRequest);
        AppUser appUser =  _appUserService.findUserByEmail(email).orElseThrow(
                () -> new RuntimeException("User not found"));

        List<TemplateResponse> response = _TemplateService.getMyTemplates(appUser);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<TemplateResponse> register(@RequestBody NewTemplate request, HttpServletRequest httpServletRequest)
    {
        String email = _jwtService.extractUsernameFromRequest(httpServletRequest);
        request.setCreatedBy(email);

        TemplateResponse response = _TemplateService.createTemplate(request);
        //not jet created
        return ResponseEntity.ok(response);
    }
}
