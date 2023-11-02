package com.fox.cradle.features.mail.controller;

import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.model.AppUserDTO;
import com.fox.cradle.features.appuser.service.AppUserService;
import com.fox.cradle.features.mail.model.MailDTO;
import com.fox.cradle.features.mail.service.MailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/mails")
@RequiredArgsConstructor
public class MailController
{
    private final AppUserService appUserService;
    private final JwtService jwtService;
    private final MailService mailService;

    @RequestMapping("/all")
    public ResponseEntity<List<MailDTO>> getAllMails(HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUse =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (appUse.isEmpty()) return ResponseEntity.badRequest().build();

        List<MailDTO> result = mailService.getMails(appUse.get());

        for (MailDTO mailDTO : result) {
            AppUserDTO sender = appUserService.getPlainUserWithAddInfo(mailDTO.getSender().getId());
            mailDTO.setSender(sender);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("count")
    public ResponseEntity<Integer> getMailCount(HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUse =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (appUse.isEmpty()) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(mailService.getMailCount(appUse.get()));
    }

    @PostMapping("mark-as-read/{mailId}")
    public ResponseEntity<Void> markMailAsRead(@PathVariable Long mailId, HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUse =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (appUse.isEmpty()) return ResponseEntity.badRequest().build();

        mailService.markMailAsRead(appUse.get(), mailId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete/{mailId}")
    public ResponseEntity<Void> deleteMail(@PathVariable Long mailId, HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUse =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (appUse.isEmpty()) return ResponseEntity.badRequest().build();

        mailService.deleteMail(appUse.get(), mailId);

        return ResponseEntity.ok().build();
    }

}
