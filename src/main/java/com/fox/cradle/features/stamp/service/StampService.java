package com.fox.cradle.features.stamp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StampService implements IStampService
{
    private final StampCardRepository stampCardRepository;
    private final StampRepository stampRepository;
}
