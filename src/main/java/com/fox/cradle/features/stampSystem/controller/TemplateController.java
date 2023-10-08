package com.fox.cradle.features.stampSystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.service.AppUserService;
import com.fox.cradle.features.stampSystem.model.enums.StampCardCategory;
import com.fox.cradle.features.stampSystem.model.enums.StampCardSecurity;
import com.fox.cradle.features.stampSystem.model.enums.StampCardStatus;
import com.fox.cradle.features.stampSystem.model.template.NewTemplate;
import com.fox.cradle.features.stampSystem.model.template.TemplateEdit;
import com.fox.cradle.features.stampSystem.model.template.TemplateResponse;
import com.fox.cradle.features.stampSystem.service.template.TemplateService;
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
    private final TemplateService _templateService;
    private final AppUserService _appUserService;

    @GetMapping("/all")
    public ResponseEntity<List<TemplateResponse>> getAllTemplates()
    {
        List<TemplateResponse> response = _templateService.getAllTemplates();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/public")
    public ResponseEntity<List<TemplateResponse>> getAllPublicTemplates()
    {
        List<TemplateResponse> response = _templateService.getAllPublic();
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
            List<TemplateResponse> response = _templateService.getMyTemplates(AppUse.get());
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTemplate(@PathVariable Long id)
    {
        _templateService.deleteTemplate(id);
    }

    @PutMapping()
    public ResponseEntity<TemplateResponse> updateTemplate(@RequestBody TemplateEdit request, HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> AppUse =  _appUserService.
                findUserByEmail(_jwtService.extractUsernameFromRequest(httpServletRequest));

        if (AppUse.isEmpty()) return ResponseEntity.badRequest().build();
        if (AppUse.get().getAppUserEmail().equals(request.getCreatedBy()))
        {
            TemplateResponse response = _templateService.updateTemplate(request);
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

        TemplateResponse savedTemplate = _templateService.createTemplate(request, AppUse.get());

        return ResponseEntity.ok(savedTemplate);
    }
}


