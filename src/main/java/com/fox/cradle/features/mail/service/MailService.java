package com.fox.cradle.features.mail.service;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.mail.MailMapperService;
import com.fox.cradle.features.mail.model.*;
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
    public List<MailDTO> getAllUserMails(AppUser appUser)
    {
        List<Mail> mails = appUser.getMails();
        return mailMapperService.mapMailsToDTOs(mails);
    }

    @Transactional
    public void saveNewMail(NewMail newMail, AppUser sender, AppUser receiver)
    {
        MailMessage mailMessage = MailMessage.builder()
                .text(newMail.getText())
                .senderMassage(true)
                .build();

        Mail mail = Mail.builder()
                .text(List.of(mailMessage))
                .isRead(false)
                .owner(receiver)
                .templateId(newMail.getTemplateId())
                .sender(sender)
                .build();

        receiver.getMails().add(mail);
        mailReposetory.save(mail);
    }

    public Integer getMailCount(AppUser appUser)
    {
        return appUser.getMails().stream().filter(mail -> !mail.isRead()).toList().size();
    }

    public void markMailAsRead(AppUser appUser, Long mailId)
    {
        Mail mail = appUser.getMails().stream().filter(mail1 -> mail1.getId().equals(mailId)).findFirst().orElseThrow();
        mail.setRead(true);
        mailReposetory.save(mail);
    }

    public void deleteMail(AppUser appUser, Long mailId)
    {
        Mail mail = appUser.getMails().stream().filter(mail1 -> mail1.getId().equals(mailId)).findFirst().orElseThrow();
        appUser.getMails().remove(mail);
        mailReposetory.delete(mail);
    }

    public void deleteSenderMail(AppUser appUser, Long mailId)
    {
        Mail mail = appUser.getSendMails().stream().filter(mail1 -> mail1.getId().equals(mailId)).findFirst().orElseThrow();
        appUser.getSendMails().remove(mail);
        mailReposetory.delete(mail);
    }

    public List<MailDTO> getYourSendMails(AppUser appUser)
    {
        List<Mail> mails = appUser.getSendMails();
        return mailMapperService.mapMailsToDTOs(mails);
    }

    public List<MailMessage> respondToMail(AppUser appUser, Long mailId, MessageDTO message)
    {
        Mail mail;
        if(message.isOriginalSender())
        {
            mail = appUser.getSendMails().stream()
                    .filter(mail1 -> mail1.getId().equals(mailId))
                    .findFirst()
                    .orElseThrow();
        }
        else
        {
            mail = appUser.getMails().stream()
                    .filter(mail1 -> mail1.getId().equals(mailId))
                    .findFirst()
                    .orElseThrow();
        }

        MailMessage mailMessage = MailMessage.builder()
                .text(message.getText())
                .senderMassage(message.isOriginalSender())
                .build();

        mail.getText().add(mailMessage);
        mailReposetory.save(mail);

        return mail.getText();
    }


}
