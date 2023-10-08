package com.fox.cradle.features.stampSystem.controller;

import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.service.AppUserService;
import com.fox.cradle.features.stampSystem.model.stamp.StampFieldResponse;
import com.fox.cradle.features.stampSystem.model.stamp.StampThisResponse;
import com.fox.cradle.features.stampSystem.model.stampcard.StampCardResponse;
import com.fox.cradle.features.stampSystem.service.stamp.StampService;

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
    private final AppUserService _appUserService;
    private final JwtService _jwtService;

    @PostMapping("/stampThisCard")
    public ResponseEntity<StampThisResponse> attemptToStamp(@RequestBody StampFieldResponse stampFieldResponse, HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> AppUse =  _appUserService.
                findUserByEmail(_jwtService.extractUsernameFromRequest(httpServletRequest));

        if(AppUse.isEmpty()) return ResponseEntity.badRequest().build();
        if(stampFieldResponse.isStamped()) return ResponseEntity.badRequest().build();

        StampThisResponse stamping = stampService.stampThisCard(stampFieldResponse);
        return ResponseEntity.ok(stamping);
    }

    //security is missing
    @PostMapping("/markStampCardAsComplete")
    public ResponseEntity<StampCardResponse> attemptToComplete(@RequestBody long id, HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> AppUse =  _appUserService.
                findUserByEmail(_jwtService.extractUsernameFromRequest(httpServletRequest));

        if (AppUse.isEmpty()) return ResponseEntity.badRequest().build();

        StampCardResponse result = stampService.setCompleteForThisCard(id);
        if(result.isCompleted()) return ResponseEntity.ok(result);
        else return ResponseEntity.badRequest().build();
    }

}
