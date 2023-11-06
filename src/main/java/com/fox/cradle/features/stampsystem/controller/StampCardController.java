package com.fox.cradle.features.stampsystem.controller;

import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.model.AppUserDTO;
import com.fox.cradle.features.appuser.service.AppUserService;
import com.fox.cradle.features.stampsystem.model.stampcard.StampCardResponse;
import com.fox.cradle.features.stampsystem.service.card.StampCardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stampcard")
@RequiredArgsConstructor
public class StampCardController
{
    private final StampCardService stampCardService;
    private final JwtService jwtService;
    private final AppUserService appUserService;

    @PostMapping("/create")
    public ResponseEntity<StampCardResponse> createStampCard(@RequestBody String templateId, HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUse =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (appUse.isEmpty()) return ResponseEntity.badRequest().build();

        StampCardResponse result = stampCardService.createStampCard(templateId , appUse.get());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();

        return ResponseEntity.created(location).body(result);
    }

    @GetMapping("/allactive")
    public ResponseEntity<List<StampCardResponse>> getAllActiveStampCardsNoFields(HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUse =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (appUse.isEmpty()) return ResponseEntity.badRequest().build();

        List<StampCardResponse> results = stampCardService.getAllActiveStampCardsNoFields(appUse.get());

        return ResponseEntity.ok(results);
    }

    @GetMapping("/archived")
    public ResponseEntity<List<StampCardResponse>> getAllStampCardsArchived(HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUse =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (appUse.isEmpty()) return ResponseEntity.badRequest().build();

        List<StampCardResponse> results = stampCardService.getAllStampCardsArchived(appUse.get());

        return ResponseEntity.ok(results);
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<StampCardResponse> getStampCardWithCreator(@PathVariable Long id)
    {
        StampCardResponse result = stampCardService.getStampCard(id);

        Long appUserID = result.getTemplateModel().getUserId();
        AppUserDTO appUserDTO = appUserService.getUserDTO(appUserID.toString());
        result.getTemplateModel().setCreator(appUserDTO);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStampCard(@PathVariable Long id , HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUse =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (appUse.isEmpty()) return ResponseEntity.badRequest().build();

        stampCardService.deleteStampCard(id, appUse.get().getId());

        return ResponseEntity.noContent().build();
    }

}
