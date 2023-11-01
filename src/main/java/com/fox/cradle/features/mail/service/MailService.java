package com.fox.cradle.features.mail.service;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.mail.MailMapperService;
import com.fox.cradle.features.mail.model.Mail;
import com.fox.cradle.features.mail.model.MailDTO;
import com.fox.cradle.features.mail.model.NewMail;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailService
{
    private final MailReposetory mailReposetory;
    private final MailMapperService mailMapperService;


    @Transactional
    public List<MailDTO> getMails(AppUser appUser)
    {
        List<Mail> mails = appUser.getMails();
        return mailMapperService.mapMailsToDTOs(mails);
    }

    @Transactional
    public void saveMail(NewMail newMail, Long senderId, AppUser receiver)
    {
        Mail mail = Mail.builder()
                .text(newMail.getText())
                .isRead(false)
                .owner(receiver)
                .templateId(newMail.getTemplateId())
                .senderId(senderId)
                .build();

        receiver.getMails().add(mail);
        mailReposetory.save(mail);
    }
}
