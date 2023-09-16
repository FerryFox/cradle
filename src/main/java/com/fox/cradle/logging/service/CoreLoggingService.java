package com.fox.cradle.logging.service;

import com.fox.cradle.logging.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoreLoggingService
{
    private final MessageService messageService;

    public void logMassage(String massage)
    {
        Message message = new Message();
        message.setMessage(massage);
        messageService.saveMassage(message);
    }
}
