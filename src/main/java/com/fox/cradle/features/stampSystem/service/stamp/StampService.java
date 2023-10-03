package com.fox.cradle.features.stampSystem.service.stamp;

import com.fox.cradle.features.stampSystem.model.enums.StampCardSecurity;
import com.fox.cradle.features.stampSystem.model.stampcard.StampCardResponse;
import com.fox.cradle.features.stampSystem.service.card.StampCardRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StampService
{
    private final StampCardRepository stampCardRepository;
    private final StampRepository stampRepository;

    public boolean stampThisCard(StampCardResponse stampCardResponse)
    {
        return switch (stampCardResponse
                .getTemplateModel()
                .getStampCardSecurity()) {

            case TRUSTUSER -> trustUser(stampCardResponse);
            case TIMEGATE -> timeGate(stampCardResponse);
            case LOCATIONGATE -> locationGate(stampCardResponse);
            default -> false;
        };
    }

    private boolean trustUser(StampCardResponse stampCardResponse)
    {
        return true;
    }

    private boolean timeGate(StampCardResponse stampCardResponse)
    {
        return false;
    }

    private boolean locationGate(StampCardResponse stampCardResponse)
    {
        return false;
    }
}
