package com.fox.cradle.features.stamp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.service.AppUserService;
import com.fox.cradle.features.picture.service.PictureService;
import com.fox.cradle.features.stamp.model.*;
import com.fox.cradle.features.stamp.service.TemplateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class TemplateController
{
    private final JwtService _jwtService;
    private final TemplateService _TemplateService;
    private final PictureService _pictureService;
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
        Optional<AppUser> AppUse =  _appUserService.
                findUserByEmail(_jwtService.extractUsernameFromRequest(httpServletRequest));

        if (AppUse.isEmpty()) return ResponseEntity.badRequest().build();
        else
        {
            List<TemplateResponse> response = _TemplateService.getMyTemplates(AppUse.get());
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTemplate(@PathVariable Long id)
    {
        _TemplateService.deleteTemplate(id);
    }

    @PutMapping()
    public ResponseEntity<TemplateResponse> updateTemplate(@RequestBody TemplateEdit request, HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> AppUse =  _appUserService.
                findUserByEmail(_jwtService.extractUsernameFromRequest(httpServletRequest));

        if (AppUse.isEmpty()) return ResponseEntity.badRequest().build();
        if (AppUse.get().getAppUserEmail().equals(request.getCreatedBy()))
        {
            TemplateResponse response = _TemplateService.updateTemplate(request);
            return ResponseEntity.ok(response);
        }
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<StampCardCategory[]> getTemplatesByCategory()
    {
        return ResponseEntity.ok(StampCardCategory.values());
    }

    @GetMapping("/security")
    public ResponseEntity<StampCardSecurity[]> getTemplatesBySecurity()
    {
        return ResponseEntity.ok(StampCardSecurity.values());
    }

    @GetMapping("/status")
    public ResponseEntity<StampCardStatus[]> getTemplatesByStatus()
    {
        return ResponseEntity.ok(StampCardStatus.values());
    }

    @PostMapping("/new-template")
    public ResponseEntity<TemplateResponse> createTemplate(@RequestBody NewTemplate request, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        Optional<AppUser> AppUse =  _appUserService.
                findUserByEmail(_jwtService.extractUsernameFromRequest(httpServletRequest));

        if (AppUse.isEmpty()) return ResponseEntity.badRequest().build();

        var savedTemplate = _TemplateService.createTemplate(request, AppUse.get());
        return ResponseEntity.ok(savedTemplate);
    }
}


