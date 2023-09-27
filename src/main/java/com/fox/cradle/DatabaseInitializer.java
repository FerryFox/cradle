package com.fox.cradle;

import com.fox.cradle.configuration.security.user.User;
import com.fox.cradle.configuration.security.user.UserRepository;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.service.AppUserService;
import com.fox.cradle.features.picture.model.Picture;
import com.fox.cradle.features.picture.service.PictureService;
import com.fox.cradle.features.stamp.model.*;
import com.fox.cradle.features.stamp.service.*;
import com.fox.cradle.features.news.model.News;
import com.fox.cradle.features.news.model.NewsCategory;
import com.fox.cradle.features.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

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
    private final NewsService newsService;

    @Override
    public void run(String... args) throws Exception {

        //initPictureMongoDb();
        //initNewsMongoDb();


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
        template_001.setImage("650d3bf63b4fbc0412f4e822");
        template_001.setDefaultCount(10);
        template_001.setStampCardCategory(StampCardCategory.FOOD);
        template_001.setStampCardSecurity(StampCardSecurity.TRUSTUSER);
        template_001.setStampCardStatus(StampCardStatus.PUBLIC);
        template_001.setDescription("Buy 10 ice creams and get one for free");
        template_001.setCreatedBy(appUserIceCompany.getAppUserEmail());
        template_001.setCreatedDate(java.time.Instant.now());
        template_001.setAppUser(appUserIceCompany);
        templateService.save(template_001);

        Template template_002 = new Template();
        template_002.setName("Coffee Card");
        template_002.setImage("650d3bf63b4fbc0412f4e823");
        template_002.setDefaultCount(10);
        template_002.setStampCardCategory(StampCardCategory.DRINK);
        template_002.setStampCardSecurity(StampCardSecurity.TRUSTUSER);
        template_002.setStampCardStatus(StampCardStatus.PUBLIC);
        template_002.setDescription("Buy 10 coffees and get one for free");
        template_002.setCreatedBy(appUserIceCompany.getAppUserEmail());
        template_002.setCreatedDate(java.time.Instant.now());
        template_002.setAppUser(appUserIceCompany);
        templateService.save(template_002);

        Template template_003 = new Template();
        template_003.setName("Cinema Card");

        template_003.setImage("650d3bf63b4fbc0412f4e824");
        template_003.setDefaultCount(5);
        template_003.setStampCardCategory(StampCardCategory.ENTERTAINMENT);
        template_003.setStampCardSecurity(StampCardSecurity.TRUSTUSER);
        template_003.setStampCardStatus(StampCardStatus.PUBLIC);
        template_003.setDescription("Visit the cinema 5 times to get a free ticket");
        template_003.setCreatedBy(appUserCinema.getAppUserEmail());
        template_003.setCreatedDate(java.time.Instant.now());
        template_003.setAppUser(appUserCinema);
        templateService.save(template_003);


        System.out.println("database initialized");
    }

    private void initPictureMongoDb() throws Exception {
        Picture ice = pictureService.loadPictureFromFile("ice");
        pictureService.savePicture(ice);

        Picture coffee = pictureService.loadPictureFromFile("coffee");
        pictureService.savePicture(coffee);

        Picture cinema = pictureService.loadPictureFromFile("cinema");
        pictureService.savePicture(cinema);
    }
    private void initNewsMongoDb()
    {
        News news1 = News.builder()
                .title("Collect & Earn")
                .description("Unlock endless possibilities! Collect stamp cards from companies and friends to earn exclusive rewards!")
                .newsCategory(NewsCategory.USER)
                .imageUrl("https://images.nightcafe.studio/jobs/fJuhrZ8z80u4L7jFFTQ1/fJuhrZ8z80u4L7jFFTQ1--1--gbizv.jpg?tr=w-1600,c-at_max")
                .build();

        News news2 = News.builder()
                .title("Create & Share")
                .description("Spread the joy by creating your unique stamp cards and sharing them with friends. Motivate and be motivated!")
                .newsCategory(NewsCategory.USER)
                .imageUrl("https://images.nightcafe.studio/jobs/Nfypghj3m4ie5ZFitkHN/Nfypghj3m4ie5ZFitkHN--1--fxlo3.jpg?tr=w-1600,c-at_max")
                .build();

        News news3 = News.builder()
                .title("Reward & Connect")
                .description("Connect with friends, share stamps, and celebrate every reward. Make every interaction rewarding!")
                .newsCategory(NewsCategory.USER)
                .imageUrl("https://images.nightcafe.studio/jobs/tRSREJlm1oX2klXRWfMc/tRSREJlm1oX2klXRWfMc--1--uahmv.jpg?tr=w-1600,c-at_max")
                .build();

        News news4 = News.builder()
                .title("Personal Motivator")
                .description("Set your goals and mark your achievements with customizable stamp cards. Make every step count!")
                .newsCategory(NewsCategory.USER)
                .imageUrl("https://images.nightcafe.studio/jobs/yZu3VYKdRYTPcv5ffuh4/yZu3VYKdRYTPcv5ffuh4--1--xb0zp.jpg?tr=w-1600,c-at_max")
                .build();

        News news5 = News.builder()
                .title("Joy of Giving")
                .description("Send a token of love! Gift stamp cards to your friends and let them unwrap the joy of earning rewards!")
                .newsCategory(NewsCategory.USER)
                .imageUrl("https://images.nightcafe.studio/jobs/WQ9LJwGtdgmFjNrAIvon/WQ9LJwGtdgmFjNrAIvon--1--wn5vy.jpg?tr=w-1600,c-at_max")
                .build();

        News news6 = News.builder()
                .title("Discover & Save")
                .description("Discover new stamp cards daily from your favorite brands and save big on exciting rewards!")
                .newsCategory(NewsCategory.USER)
                .imageUrl("https://images.nightcafe.studio/jobs/wzDBg3M1YEEEn9TFWkeT/wzDBg3M1YEEEn9TFWkeT--1--kzq0q.jpg?tr=w-1600,c-at_max")
                .build();

        News news7 = News.builder()
                .title("Interactive Collecting")
                .description("Experience the thrill of collecting! Gather stamps interactively and see what rewards await!")
                .newsCategory(NewsCategory.USER)
                .imageUrl("https://images.nightcafe.studio/jobs/X78UrtOc1DgWX15CDU4S/X78UrtOc1DgWX15CDU4S--1--lcy5i.jpg?tr=w-1600,c-at_maxx")
                .build();


        News news8 = News.builder()
                .title("Customized Experience")
                .description("Personalize your journey! Create and collect stamp cards that reflect your taste and preferences!")
                .newsCategory(NewsCategory.USER)
                .imageUrl("https://images.nightcafe.studio/jobs/FK1nqcMODdS2NTv0hmWx/FK1nqcMODdS2NTv0hmWx--1--mnbdg.jpg?tr=w-1600,c-at_max")
                .build();

        List<News> newsList = List.of(news1, news2, news3, news4, news5, news6, news7, news8);
        newsList.forEach(newsService::saveNews);
    }
}
