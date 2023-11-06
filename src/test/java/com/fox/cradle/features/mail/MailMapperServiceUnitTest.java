package com.fox.cradle.features.mail;

import com.fox.cradle.features.appuser.UserMapService;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.blog.BlogMapping;
import com.fox.cradle.features.mail.model.Mail;
import com.fox.cradle.features.mail.model.MailDTO;
import com.fox.cradle.features.mail.model.MailMessage;
import com.fox.cradle.features.picture.service.PictureService;
import com.fox.cradle.features.stampsystem.model.template.TemplateResponse;
import com.fox.cradle.features.stampsystem.service.template.TemplateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class MailMapperServiceUnitTest
{

    @Mock
    private TemplateService templateService;

    @InjectMocks
    private MailMapperService mailMapperService;

    @Test
    void mapMailToDTOTest()
    {
        //GIVEN
        List<MailMessage> mailMessages = new ArrayList<>();
        mailMessages.add(MailMessage.builder()
                .id(1L)
                .text("one")
                .build());

        mailMessages.add(MailMessage.builder()
                .id(2L)
                .text("two")
                .build());

        AppUser sender = AppUser.builder()
                .id(1L)
                .build();

        AppUser receiver = AppUser.builder()
                .id(2L)
                .build();

        Mail mail = Mail.builder()
                .id(1L)
                .text(mailMessages)
                .templateId(1L)
                .isRead(false)
                .owner(receiver)
                .sender(sender)
                .build();

        //WHEN
        TemplateResponse templateResponse = TemplateResponse.builder()
                .id(1L)
                .build();

        when(templateService.getTemplateById(mail.getTemplateId())).thenReturn(templateResponse);

        MailDTO result = mailMapperService.mapMailToDTO(mail);

        //THEN
        Assertions.assertEquals(mail.getId(), result.getId());
        Assertions.assertEquals(mail.getText(), result.getConversation());
        Assertions.assertEquals(mail.getTemplateId(), result.getTemplateResponse().getId());
        Assertions.assertEquals(mail.isRead(), result.isRead());
        Assertions.assertEquals(mail.getOwner().getId(), result.getReceiver().getId());
        Assertions.assertEquals(mail.getSender().getId(), result.getSender().getId());
    }

    @Test
    void mapMailsToDTOs()
    {
        //GIVEN
        List<MailMessage> mailMessages = new ArrayList<>();
        mailMessages.add(MailMessage.builder()
                .id(1L)
                .text("one")
                .build());

        mailMessages.add(MailMessage.builder()
                .id(2L)
                .text("two")
                .build());

        AppUser sender = AppUser.builder()
                .id(1L)
                .build();

        AppUser receiver = AppUser.builder()
                .id(2L)
                .build();

        Mail mail = Mail.builder()
                .id(1L)
                .text(mailMessages)
                .templateId(1L)
                .isRead(false)
                .owner(receiver)
                .sender(sender)
                .build();

        List<Mail> mails = new ArrayList<>();
        mails.add(mail);

        //WHEN
        List<MailDTO> result = mailMapperService.mapMailsToDTOs(mails);
        Assertions.assertTrue(result.size() == 1);
    }
}