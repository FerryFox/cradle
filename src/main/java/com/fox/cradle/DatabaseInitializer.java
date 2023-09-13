package com.fox.cradle;

import com.fox.cradle.configuration.security.user.User;
import com.fox.cradle.configuration.security.user.UserRepository;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.service.AppUserRepository;
import com.fox.cradle.features.stamp.model.StampCard;
import com.fox.cradle.features.stamp.model.StampCardCategory;
import com.fox.cradle.features.stamp.model.StampCardSecurity;
import com.fox.cradle.features.stamp.service.StampCardRepository;
import com.fox.cradle.features.stamp.service.StampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner
{
    private final StampCardRepository stampCardRepository;
    private final AppUserRepository appUserRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception
    {
        User user1 = new User();
        user1.setEmail("w@w");
        user1.setPassword("1234");
        user1.setReceiveNews(true);
        user1.setFirstname("John Doe");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user1.setPassword(passwordEncoder.encode(user1.getPassword()));
        userRepository.save(user1);

        // First, save the AppUser
        AppUser appUser1 = new AppUser();
        appUser1.setAppUserName("John Doe");
        appUser1.setReceiveNews(true);
        // Assuming you have an AppUserRepository
        appUserRepository.save(appUser1);

        User user2 = new User();
        user2.setEmail("q@q");
        user2.setPassword("123");
        user2.setReceiveNews(true);
        user2.setFirstname("Bob");
        user2.setPassword(passwordEncoder.encode(user2.getPassword()));
        userRepository.save(user2);

        // First, save the AppUser
        AppUser appUser2 = new AppUser();
        appUser2.setAppUserName("Bob");
        appUser2.setReceiveNews(true);
        // Assuming you have an AppUserRepository
        appUserRepository.save(appUser2);

        // Now, create and save each StampCard

        StampCard stampCard_001 = new StampCard();
        stampCard_001.setName("Stamp Card Ice Cream");
        stampCard_001.setStampCardCategory(StampCardCategory.FOOD);
        stampCard_001.setStampCardSecurity(StampCardSecurity.TRUSTUSER);
        stampCard_001.setDescription("Buy 10 ice creams and get one for free");
        //... other settings
        stampCard_001.setAppUser(appUser1);
        stampCardRepository.save(stampCard_001);

        StampCard stampCard_002 = new StampCard();
        stampCard_002.setName("Coffee Card");
        stampCard_002.setStampCardCategory(StampCardCategory.DRINK);
        stampCard_002.setStampCardSecurity(StampCardSecurity.TRUSTUSER);
        stampCard_002.setDescription("Buy 10 cups of coffee and get one for free");
        //... other settings
        stampCard_002.setAppUser(appUser1);
        stampCardRepository.save(stampCard_002);

        StampCard stampCard_003 = new StampCard();
        stampCard_003.setName("Car Wash Card");
        stampCard_003.setStampCardCategory(StampCardCategory.OTHER);
        stampCard_003.setStampCardSecurity(StampCardSecurity.TRUSTUSER);
        stampCard_003.setDescription("Buy 10 car washes and get one for free");
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
