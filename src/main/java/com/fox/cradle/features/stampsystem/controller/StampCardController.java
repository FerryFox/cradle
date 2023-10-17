package com.fox.cradle.features.stampsystem.controller;

import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.service.AppUserService;
import com.fox.cradle.features.stampsystem.model.stamp.StampFieldResponse;
import com.fox.cradle.features.stampsystem.model.stampcard.StampCardResponse;
import com.fox.cradle.features.stampsystem.service.card.StampCardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stampcard")
@RequiredArgsConstructor
public class StampCardController
{
    private final StampCardService _stampCardService;
    private final JwtService _jwtService;
    private final AppUserService _appUserService;

    @PostMapping("/create")
    public ResponseEntity<StampCardResponse> createStampCard(@RequestBody String templateId, HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> AppUse =  _appUserService.
                findUserByEmail(_jwtService.extractUsernameFromRequest(httpServletRequest));

        if (AppUse.isEmpty()) return ResponseEntity.badRequest().build();

        StampCardResponse result = _stampCardService.createStampCard(templateId , AppUse.get());

        return ResponseEntity.created(null).body(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StampCardResponse>> getAllStampCardsNoFields(HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> AppUse =  _appUserService.
                findUserByEmail(_jwtService.extractUsernameFromRequest(httpServletRequest));

        if (AppUse.isEmpty()) return ResponseEntity.badRequest().build();

        List<StampCardResponse> results = _stampCardService.getAllStampCardsNoFields(AppUse.get());

        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StampCardResponse> getStampCard(@PathVariable Long id)
    {
        StampCardResponse result = _stampCardService.getStampCard(id);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/fields/{stampCardId}")
    public ResponseEntity<List<StampFieldResponse>> getAllStampFields(@PathVariable Long stampCardId)
    {
        List<StampFieldResponse> results = _stampCardService.getStampFields(stampCardId);

        return ResponseEntity.ok(results);
    }

}
