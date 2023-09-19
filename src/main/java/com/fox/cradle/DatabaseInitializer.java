package com.fox.cradle;

import com.fox.cradle.configuration.security.user.User;
import com.fox.cradle.configuration.security.user.UserRepository;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.service.AppUserService;
import com.fox.cradle.features.picture.model.Picture;
import com.fox.cradle.features.picture.service.PictureService;
import com.fox.cradle.features.stamp.model.*;
import com.fox.cradle.features.stamp.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner
{
    private final AppUserService appUserService;
    private final UserRepository userRepository;
    private final TemplateService templateService;
    private final StampCardService stampCardService;
    private final StampService stampService;
    private final PictureService pictureService;

    @Override
    public void run(String... args) throws Exception
    {
        //fill mongo db with some pictures
/*
        Picture ice = pictureService.loadPictureFromFile("ice");
        pictureService.savePicture(ice);

        Picture coffee = pictureService.loadPictureFromFile("coffee");
        pictureService.savePicture(coffee);

        Picture cinema = pictureService.loadPictureFromFile("cinema");
        pictureService.savePicture(cinema);

 */


//create some users
    //User 1 with AppUser 1
        User user1 = new User();
        user1.setEmail("w@w");
        user1.setPassword("1234");
        user1.setReceiveNews(true);
        user1.setFirstname("Ice cream man");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user1.setPassword(passwordEncoder.encode(user1.getPassword()));
        userRepository.save(user1);

        AppUser appUser1 = new AppUser();
        appUser1.setAppUserName("Ice cream man");
        appUser1.setAppUserEmail("w@w");
        appUser1.setReceiveNews(true);
        appUserService.saveAppUser(appUser1);

    //User 2 with AppUser 2
        User user2 = new User();
        user2.setEmail("q@q");
        user2.setPassword("123");
        user2.setReceiveNews(true);
        user2.setFirstname("Bob");
        user2.setPassword(passwordEncoder.encode(user2.getPassword()));
        userRepository.save(user2);

        AppUser appUser2 = new AppUser();
        appUser2.setAppUserName("Bob");
        appUser2.setAppUserEmail("q@q");
        appUser2.setReceiveNews(true);
        appUserService.saveAppUser(appUser2);

//Create some stamp card templates
        Template template_001 = new Template();
        template_001.setName("Ice Cream Card");
        template_001.setImage("6509f026a96a17398d0e87ae");
        template_001.setDefaultCount(10);
        template_001.setStampCardCategory(StampCardCategory.FOOD);
        template_001.setStampCardSecurity(StampCardSecurity.TRUSTUSER);
        template_001.setStampCardStatus(StampCardStatus.PUBLIC);
        template_001.setDescription("Buy 10 ice creams and get one for free");
        template_001.setCreatedBy(appUser1.getAppUserEmail());
        template_001.setAppUser(appUser1);
        templateService.save(template_001);

        Template template_002 = new Template();
        template_002.setName("Coffee Card");
        template_002.setImage("6509f026a96a17398d0e87af");
        template_002.setDefaultCount(10);
        template_002.setStampCardCategory(StampCardCategory.DRINK);
        template_002.setStampCardSecurity(StampCardSecurity.TRUSTUSER);
        template_002.setStampCardStatus(StampCardStatus.PUBLIC);
        template_002.setDescription("Buy 10 coffees and get one for free");
        template_002.setCreatedBy(appUser2.getAppUserEmail());
        template_002.setAppUser(appUser2);
        templateService.save(template_002);

        Template template_003 = new Template();
        template_003.setName("Cinema Card");
        template_003.setImage("6509f026a96a17398d0e87b0");
        template_003.setDefaultCount(5);
        template_003.setStampCardCategory(StampCardCategory.ENTERTAINMENT);
        template_003.setStampCardSecurity(StampCardSecurity.TRUSTUSER);
        template_003.setStampCardStatus(StampCardStatus.PUBLIC);
        template_003.setDescription("Visit the cinema 5 times to get a free ticket");
        template_003.setCreatedBy(appUser2.getAppUserEmail());
        template_003.setAppUser(appUser2);
        templateService.save(template_003);


        AppUser bob = appUser2;
        stampCardService.createStampCard(template_001, bob);



//load bob new and check
        AppUser unknown = appUserService.getAppUserById(bob.getId());
        StampCard stampCard = unknown.getMyStampCards().get(0);
        stampService.stampACard(stampCard);

        System.out.println("done database init");


        }
}
