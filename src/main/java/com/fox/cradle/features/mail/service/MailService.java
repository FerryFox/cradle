package com.fox.cradle.features.mail.service;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.mail.MailMapperService;
import com.fox.cradle.features.mail.model.Mail;
import com.fox.cradle.features.mail.model.MailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailService
{
    private final MailReposetory mailReposetory;
    private final MailMapperService mailMapperService;

    public List<MailDTO> getMails(AppUser appUser)
    {
        List<Mail>  mails = mailReposetory.findAllByAppUser(appUser);
        return mailMapperService.mapMailsToDTOs(mails);
    }

    public MailDTO sendMail(MailDTO mailDTO, AppUser sender, Long receiverId)
    {
        Mail mail = mailMapperService.mapDTOToMail(mailDTO);


    }

}
