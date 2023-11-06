package com.fox.cradle.features.mail.controller;

import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.model.AppUserDTO;
import com.fox.cradle.features.appuser.service.AppUserService;
import com.fox.cradle.features.mail.model.MailDTO;
import com.fox.cradle.features.mail.model.MailMessage;
import com.fox.cradle.features.mail.model.MessageDTO;
import com.fox.cradle.features.mail.model.NewMail;
import com.fox.cradle.features.mail.service.MailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/all-my-mails")
    public ResponseEntity<List<MailDTO>> getAllUserMails(HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUse =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (appUse.isEmpty()) return ResponseEntity.badRequest().build();

        List<MailDTO> result = mailService.getAllUserMails(appUse.get());

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

    @PostMapping("/mark-as-read/{mailId}")
    public ResponseEntity<Void> markMailAsRead(@PathVariable Long mailId, HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUse =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (appUse.isEmpty()) return ResponseEntity.badRequest().build();

        mailService.markMailAsRead(appUse.get(), mailId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{mailId}")
    public ResponseEntity<Void> deleteMail(@PathVariable Long mailId, HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUse =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (appUse.isEmpty()) return ResponseEntity.badRequest().build();

        mailService.deleteMail(appUse.get(), mailId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-originator/{mailId}")
    public ResponseEntity<Void> deleteSenderMail(@PathVariable Long mailId, HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUse =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (appUse.isEmpty()) return ResponseEntity.badRequest().build();

        mailService.deleteSenderMail(appUse.get(), mailId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("your-send-mails")
    public ResponseEntity<List<MailDTO>> getYourSendMails(HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUse =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (appUse.isEmpty()) return ResponseEntity.badRequest().build();

        List<MailDTO> result = mailService.getYourSendMails(appUse.get());

        for (MailDTO mailDTO : result)
        {
            AppUserDTO receiver = appUserService.getPlainUserWithAddInfo(mailDTO.getReceiver().getId());
            mailDTO.setReceiver(receiver);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("respond/{mailId}")
    public ResponseEntity<List<MailMessage>> respondToMail(@PathVariable Long mailId, @RequestBody MessageDTO message, HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUse =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (appUse.isEmpty()) return ResponseEntity.badRequest().build();

        List<MailMessage> messages = mailService.respondToMail(appUse.get(), mailId, message);

        return ResponseEntity.ok(messages);
    }

    @PostMapping("send")
    public ResponseEntity<Void> sendMail(@RequestBody NewMail newMail, HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> sender =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (sender.isEmpty()) return ResponseEntity.badRequest().build();

        AppUser reciver = appUserService.getUserById(newMail.getReceiverId());

        mailService.saveNewMail(newMail, sender.get(), reciver);

        return ResponseEntity.ok().build();
    }
}
