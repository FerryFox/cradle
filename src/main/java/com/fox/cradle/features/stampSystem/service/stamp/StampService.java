package com.fox.cradle.features.stampSystem.service.stamp;

import com.fox.cradle.features.stampSystem.model.stamp.Stamp;
import com.fox.cradle.features.stampSystem.model.stampcard.StampCard;
import com.fox.cradle.features.stampSystem.service.card.StampCardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StampService
{
    private final StampCardRepository stampCardRepository;
    private final StampRepository stampRepository;
}
