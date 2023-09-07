package com.fox.cradle;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.service.AppUserRepository;
import com.fox.cradle.features.stamp.model.StampCard;
import com.fox.cradle.features.stamp.model.StampCardCategory;
import com.fox.cradle.features.stamp.model.StampCardSecurity;
import com.fox.cradle.features.stamp.service.StampCardRepository;
import com.fox.cradle.features.stamp.service.StampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner
{
    private final StampCardRepository stampCardRepository;
    private final StampRepository stampRepository;
    private final AppUserRepository appUserRepository;


    @Override
    public void run(String... args) throws Exception
    {
        // First, save the AppUser
        AppUser appUser1 = new AppUser();
        // Assuming you have an AppUserRepository
        appUserRepository.save(appUser1);

        // Now, create and save each StampCard

        StampCard stampCard_001 = new StampCard();
        stampCard_001.setName("Stamp Card Ice Cream");
        //... other settings
        stampCard_001.setAppUser(appUser1);
        stampCardRepository.save(stampCard_001);

        StampCard stampCard_002 = new StampCard();
        stampCard_002.setName("Coffee Card");
        //... other settings
        stampCard_002.setAppUser(appUser1);
        stampCardRepository.save(stampCard_002);

        StampCard stampCard_003 = new StampCard();
        stampCard_003.setName("Car Wash Card");
        //... other settings
        stampCard_003.setAppUser(appUser1);
        stampCardRepository.save(stampCard_003);

        appUser1.getMyStampCards().add(stampCard_001);
        appUser1.getMyStampCards().add(stampCard_002);
        appUser1.getMyStampCards().add(stampCard_003);

        // If you modify appUser1 after saving,
        // make sure to save the changes again
        appUserRepository.save(appUser1);
        }


}
