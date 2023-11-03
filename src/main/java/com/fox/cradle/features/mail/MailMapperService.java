package com.fox.cradle.features.mail;

import com.fox.cradle.features.appuser.model.AppUserDTO;
import com.fox.cradle.features.mail.model.Mail;
import com.fox.cradle.features.mail.model.MailDTO;
import com.fox.cradle.features.stampsystem.model.template.TemplateResponse;
import com.fox.cradle.features.stampsystem.service.template.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MailMapperService
{
    private final TemplateService templateService;

    //returns MailDTO with unreal sender
    public MailDTO mapMailToDTO(Mail mail)
    {
        AppUserDTO fakeSender = AppUserDTO.builder()
                .id(mail.getSender().getId())
                .build();

        AppUserDTO receiver = AppUserDTO.builder()
                .id(mail.getOwner().getId())
                .build();

        TemplateResponse templateResponse = null;
        if(mail.getTemplateId() != null){
        templateResponse = templateService.getTemplateById(mail.getTemplateId());
        }

        return MailDTO.builder()
                .id(mail.getId())
                .conversation(mail.getText())
                .sender(fakeSender)
                .receiver(receiver)
                .templateResponse(templateResponse)
                .isRead(mail.isRead())
                .build();
    }

    public List<MailDTO> mapMailsToDTOs(List<Mail> mails)
    {
        return mails.stream()
                .map(this::mapMailToDTO)
                .collect(Collectors.toList());
    }

}
