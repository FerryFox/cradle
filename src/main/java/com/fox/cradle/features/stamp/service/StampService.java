package com.fox.cradle.features.stamp.service;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.stamp.model.Stamp;
import com.fox.cradle.features.stamp.model.StampCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StampService implements IStampService
{
    private final StampCardRepository stampCardRepository;
    private final StampRepository stampRepository;

    public StampCard stampACard(StampCard stampCard, AppUser appUser, Stamp stamp)
    {
        stamp.setAppUser(appUser);
        stampCard.getStamps().add(stamp);
        return stampCardRepository.save(stampCard);
    }


    public Stamp createStamp(Stamp stamp, AppUser appUser)
    {
        stamp.setAppUser(appUser);
        return stampRepository.save(stamp);
    }


    public StampCard getStampCardById(Long id)
    {
        return stampCardRepository.findById(id).orElse(null);
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
