package com.fox.cradle.features.stampsystem.controller;

import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.service.AppUserService;
import com.fox.cradle.features.stampsystem.model.stamp.StampFieldResponse;
import com.fox.cradle.features.stampsystem.model.stamp.StampThisResponse;
import com.fox.cradle.features.stampsystem.model.stampcard.StampCardResponse;
import com.fox.cradle.features.stampsystem.service.stamp.StampService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/stamp")
@RequiredArgsConstructor
public class StampController
{
    private final StampService stampService;
    private final AppUserService appUserService;
    private final JwtService jwtService;

    @PostMapping("/stampThisCard")
    public ResponseEntity<StampThisResponse> attemptToStamp(@RequestBody StampFieldResponse stampFieldResponse, HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUser =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if(appUser.isEmpty()) return ResponseEntity.badRequest().build();
        if(stampFieldResponse.isStamped()) return ResponseEntity.badRequest().build();

        StampThisResponse stamping = stampService.stampThisCard(stampFieldResponse);
        return ResponseEntity.ok(stamping);
    }

    @PostMapping("/markStampCardAsComplete")
    public ResponseEntity<StampCardResponse> attemptToComplete(@RequestBody long id, HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUser =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (appUser.isEmpty()) return ResponseEntity.badRequest().build();

        StampCardResponse result = stampService.setCompleteForThisCard(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/markStampCardAsRedeemed")
    public ResponseEntity<StampCardResponse> attemptToRedeem(@RequestBody long id, HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUser =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (appUser.isEmpty()) return ResponseEntity.badRequest().build();

        StampCardResponse result = stampService.setRedeemedForThisCard(id);
        return ResponseEntity.ok(result);
    }
}
