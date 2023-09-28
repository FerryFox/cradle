package com.fox.cradle.features.stamp.service.stamp;

import com.fox.cradle.features.stamp.model.stamp.Stamp;
import com.fox.cradle.features.stamp.model.stampcard.StampCard;
import com.fox.cradle.features.stamp.service.card.StampCardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StampService implements IStampService
{
    private final StampCardRepository stampCardRepository;
    private final StampRepository stampRepository;


    @Transactional
    public StampCard stampACard(StampCard stampCard)
    {
        Stamp stamp = new Stamp();
        stamp.setStampCard(stampCard);
        stamp.setIssuer(stampCard.getOwner());
        stampCard.getStamps().add(stamp);

        return stampCardRepository.save(stampCard);
    }

    //Crud methods
    public Stamp getStampById(Long id)
    {
        return stampRepository.findById(id).orElse(null);
    }

    public Stamp saveStamp(Stamp stamp)
    {
        return stampRepository.save(stamp);
    }

    public void deleteStamp(Stamp stamp)
    {
        stampRepository.delete(stamp);
    }

    public Stamp updateStamp(Stamp stamp)
    {
        return stampRepository.save(stamp);
    }
}
