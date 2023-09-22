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
    public void run(String... args) throws Exception {
        // Use this lines to insert some pictures into the mongo db
        // Pictures are loaded from the static folder

        Picture ice = pictureService.loadPictureFromFile("ice");
        pictureService.savePicture(ice);

        Picture coffee = pictureService.loadPictureFromFile("coffee");
        pictureService.savePicture(coffee);

        Picture cinema = pictureService.loadPictureFromFile("cinema");
        pictureService.savePicture(cinema);

//create some users
        //User 1 with AppUser 1
        User userIce = new User();
        userIce.setEmail("w@w");
        userIce.setPassword("1234");
        userIce.setReceiveNews(true);
        userIce.setFirstname("Ice Cream Company");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userIce.setPassword(passwordEncoder.encode(userIce.getPassword()));
        userRepository.save(userIce);

        AppUser appUserIceCompany = new AppUser();
        appUserIceCompany.setAppUserName("Ice cream man");
        appUserIceCompany.setAppUserEmail("w@w");
        appUserIceCompany.setReceiveNews(true);
        appUserService.saveAppUser(appUserIceCompany);

        //User 2 with AppUser 2
        User userBob = new User();
        userBob.setEmail("q@q");
        userBob.setPassword("1234");
        userBob.setReceiveNews(true);
        userBob.setFirstname("Bob");
        userBob.setPassword(passwordEncoder.encode(userBob.getPassword()));
        userRepository.save(userBob);

        AppUser appUserBob = new AppUser();
        appUserBob.setAppUserName("Bob");
        appUserBob.setAppUserEmail("q@q");
        appUserBob.setReceiveNews(true);
        appUserService.saveAppUser(appUserBob);

        // User 3 with AppUser 3
        User userCinema = new User();
        userCinema.setEmail("e@e");
        userCinema.setPassword("1234");
        userCinema.setReceiveNews(true);
        userCinema.setFirstname("Cinema");
        userCinema.setPassword(passwordEncoder.encode(userCinema.getPassword()));
        userRepository.save(userCinema);

        AppUser appUserCinema = new AppUser();
        appUserCinema.setAppUserName("Cinema");
        appUserCinema.setAppUserEmail("e@e");
        appUserCinema.setReceiveNews(true);
        appUserService.saveAppUser(appUserCinema);

        //User 2 with AppUser 2
        User userAnna = new User();
        userAnna.setEmail("r@r");
        userAnna.setPassword("1234");
        userAnna.setReceiveNews(false);
        userAnna.setFirstname("Anna");
        userAnna.setPassword(passwordEncoder.encode(userAnna.getPassword()));
        userRepository.save(userAnna);

        AppUser appUserAnna = new AppUser();
        appUserAnna.setAppUserName("Anna");
        appUserAnna.setAppUserEmail("r@r");
        appUserAnna.setReceiveNews(false);
        appUserService.saveAppUser(appUserAnna);

//Create some stamp card templates
        Template template_001 = new Template();
        template_001.setName("Ice Cream Card");
        if(ice != null)
            template_001.setImage(ice.getId());
        else
            template_001.setImage("650d3bf63b4fbc0412f4e822");
        template_001.setDefaultCount(10);
        template_001.setStampCardCategory(StampCardCategory.FOOD);
        template_001.setStampCardSecurity(StampCardSecurity.TRUSTUSER);
        template_001.setStampCardStatus(StampCardStatus.PUBLIC);
        template_001.setDescription("Buy 10 ice creams and get one for free");
        template_001.setCreatedBy(appUserIceCompany.getAppUserEmail());
        template_001.setAppUser(appUserIceCompany);
        templateService.save(template_001);

        Template template_002 = new Template();
        template_002.setName("Coffee Card");
        if(coffee != null)
            template_002.setImage(coffee.getId());
        else
            template_002.setImage("650d3bf63b4fbc0412f4e823");
        template_002.setDefaultCount(10);
        template_002.setStampCardCategory(StampCardCategory.DRINK);
        template_002.setStampCardSecurity(StampCardSecurity.TRUSTUSER);
        template_002.setStampCardStatus(StampCardStatus.PUBLIC);
        template_002.setDescription("Buy 10 coffees and get one for free");
        template_002.setCreatedBy(appUserIceCompany.getAppUserEmail());
        template_002.setAppUser(appUserIceCompany);
        templateService.save(template_002);

        Template template_003 = new Template();
        template_003.setName("Cinema Card");
        if(cinema != null)
            template_003.setImage(cinema.getId());
        else
            template_003.setImage("650d3bf63b4fbc0412f4e824");
        template_003.setDefaultCount(5);
        template_003.setStampCardCategory(StampCardCategory.ENTERTAINMENT);
        template_003.setStampCardSecurity(StampCardSecurity.TRUSTUSER);
        template_003.setStampCardStatus(StampCardStatus.PUBLIC);
        template_003.setDescription("Visit the cinema 5 times to get a free ticket");
        template_003.setCreatedBy(appUserCinema.getAppUserEmail());
        template_003.setAppUser(appUserCinema);
        templateService.save(template_003);

        System.out.println("database initialized");

    }
}
