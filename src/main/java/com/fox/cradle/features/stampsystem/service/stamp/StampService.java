package com.fox.cradle.features.stampsystem.service.stamp;

import com.fox.cradle.features.stampsystem.model.stamp.*;
import com.fox.cradle.features.stampsystem.model.stampcard.StampCard;
import com.fox.cradle.features.stampsystem.model.stampcard.StampCardResponse;
import com.fox.cradle.features.stampsystem.model.template.NewTemplate;
import com.fox.cradle.features.stampsystem.model.template.Template;
import com.fox.cradle.features.stampsystem.service.MapService;
import com.fox.cradle.features.stampsystem.service.card.StampCardRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class StampService
{
    private final StampCardRepository stampCardRepository;
    private final StampRepository stampRepository;
    private final StampFieldRepository stampFieldRepository;
    private final MapService mapService;

    private static final String MESSAGE = "Stamping successful";

    public TimeGateSecurity createTimeGate(NewTemplate newTemplate, Template template)
    {
        Duration duration = Duration.parse(newTemplate.getSecurityTimeGateDuration());

        return TimeGateSecurity.builder()
                .timeGateDuration(duration)
                .template(template)
                .build();
    }

    public StampThisResponse stampThisCard(StampFieldResponse stampFieldResponse)
    {
        Long stampCardId = stampFieldResponse.getStampCardId();
        Long stampFieldId = stampFieldResponse.getId();

        StampCard stampCard = stampCardRepository.findById(stampCardId).orElseThrow();
        StampField stampField = stampFieldRepository.findById(stampFieldId).orElseThrow();

        return switch (stampCard
                .getTemplate()
                .getStampCardSecurity())
        {
            case TRUSTUSER -> trustUser(stampCard, stampField);
            case TIMEGATE -> timeGate(stampCard, stampField);
            case LOCATIONGATE -> locationGate(stampCard, stampField);
        };
    }

    private StampThisResponse trustUser(StampCard stampCard, StampField stampField)
    {
        finishStamping(stampField, stampCard);
        return  new StampThisResponse(true, MESSAGE);
    }

    private StampThisResponse timeGate(StampCard stampCard , StampField stampField)
    {
        Instant time = stampCard.getLastStampDate();
        Duration duration = stampCard.getTemplate().getTimeGateSecurity().getTimeGateDuration();

        if(time == null)
        {
            finishStamping(stampField, stampCard);
            return new StampThisResponse(true, MESSAGE);
        }
        else
        {
            Instant now  = Instant.now();
            Duration timeSinceLastStamp = Duration.between(time, now);

            if(timeSinceLastStamp.compareTo(duration) > 0)
            {
                finishStamping(stampField, stampCard);
                return new StampThisResponse(true, MESSAGE);
            }
        }
        return new StampThisResponse(false, "Time gate not passed yet");
    }

    private StampThisResponse locationGate(StampCard stampCard, StampField stampField)
    {
        if( stampCard!= null && stampField != null)
            return new StampThisResponse(false, "Location gate not implemented yet");
        else
            return new StampThisResponse(false, "Something went wrong");
    }

    private void finishStamping(StampField stampField, StampCard stampCard)
    {
        stampField.setStamped(true);
        stampFieldRepository.save(stampField);

        stampCard.setLastStampDate(Instant.now());
        stampCardRepository.save(stampCard);

        Stamp stamp = Stamp.builder()
                .createdDate(Instant.now())
                .stampField(stampField)
                .issuer(stampCard.getOwner())
                .build();

        stampRepository.save(stamp);
    }

    public StampCardResponse setCompleteForThisCard(long id)
    {
        StampCard stampCard = stampCardRepository.findById(id).orElseThrow();

        if(stampCard.getStampFields().stream().allMatch(StampField::isStamped))
        {
            stampCard.setCompleted(true);
            stampCardRepository.save(stampCard);

            return mapService.mapStampCardToResponse(stampCard);
        }

        return mapService.mapStampCardToResponse(stampCard);
    }
}
