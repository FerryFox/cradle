package com.fox.cradle.features.stampsystem.controller;

import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.model.AppUserDTO;
import com.fox.cradle.features.appuser.service.AppUserService;
import com.fox.cradle.features.mail.model.NewMail;
import com.fox.cradle.features.mail.service.MailService;
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
    private final MailService mailService;

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
        AppUserDTO creator = appUserService.getUserDTO(result.getTemplateModel().getUserId().toString());
        result.getTemplateModel().setCreator(creator);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/markStampCardAsRedeemed")
    public ResponseEntity<StampCardResponse> attemptToRedeem(@RequestBody long id, HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUser =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (appUser.isEmpty()) return ResponseEntity.badRequest().build();

        StampCardResponse result = stampService.setRedeemedForThisCard(id);
        AppUserDTO creator = appUserService.getUserDTO(result.getTemplateModel().getUserId().toString());
        result.getTemplateModel().setCreator(creator);

        //send mail to template owner
        if(result.isRedeemed()){

            String news = "Greetings i redeemed your stamp card, sincerely " + appUser.get().getAppUserName();

            NewMail mail = NewMail.builder()
                    .receiverId(result.getTemplateModel().getCreator().getId())
                    .redeemedTemplate(true)
                    .templateId(result.getTemplateModel().getId())
                    .text(news)
                    .build();

            AppUser receiver = appUserService.getUserById(result.getTemplateModel().getCreator().getId());

            mailService.saveNewMail(mail, appUser.get(), receiver);
        }


        return ResponseEntity.ok(result);
    }
}
