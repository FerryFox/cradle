package com.fox.cradle.features.mail;

import com.fox.cradle.features.mail.model.Mail;
import com.fox.cradle.features.mail.model.MailDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailMapperService
{
    public MailDTO mapMailToDTO(Mail mail)
    {
        return MailDTO.builder()
                .id(mail.getId())
                .senderId(mail.getSenderId())
                .ownerId(mail.getOwner().getId().toString())
                .subject(mail.getSubject())
                .templateId(mail.getTemplateId())
                .build();
    }

    public List<MailDTO> mapMailsToDTOs(List<Mail> mails)
    {
        return mails.stream()
                .map(this::mapMailToDTO)
                .collect(Collectors.toList());
    }

    public Mail mapDTOToMail(MailDTO mailDTO)
    {
        return Mail.builder()
                .id(mailDTO.getId())
                .senderId(mailDTO.getSenderId())
                .subject(mailDTO.getSubject())
                .templateId(mailDTO.getTemplateId())
                .build();
    }
}
