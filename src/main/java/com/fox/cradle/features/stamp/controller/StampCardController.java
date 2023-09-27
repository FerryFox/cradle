package com.fox.cradle.features.stamp.controller;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.service.AppUserService;
import com.fox.cradle.features.stamp.model.StampCard;
import com.fox.cradle.features.stamp.service.StampCardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stamp-card")
@RequiredArgsConstructor
public class StampCardController
{
    private final StampCardService stampCardService;
    private final JwtService _jwtService;
}
