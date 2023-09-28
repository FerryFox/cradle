package com.fox.cradle.features.stamp.service;

import com.fox.cradle.features.stamp.service.stamp.IStampService;
import com.fox.cradle.features.stamp.service.stamp.StampRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StampServiceTest
{
    @Autowired
    private IStampService stampService;

    @Autowired
    private StampRepository stampRepository;


}
