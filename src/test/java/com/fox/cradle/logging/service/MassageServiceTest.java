package com.fox.cradle.logging.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MassageServiceTest
{
    @Autowired
    private MessageService massageService;

    @Test
    public void readAllMassages()
    {
        massageService.getAllMassages().forEach(System.out::println);
    }

}
