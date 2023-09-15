package com.fox.cradle.logging.service;

import com.fox.cradle.logging.model.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService
{
    private final MessageRepository massageRepository;

    public MessageService(MessageRepository massageRepository)
    {
        this.massageRepository = massageRepository;
        Message massage = new Message();
        massage.setMessage("application started");
        saveMassage(massage);
    }

    public Message saveMassage(Message massage)
    {
        return massageRepository.save(massage);
    }

    public List<Message> getAllMassages()
    {
        return massageRepository.findAll();
    }
}
