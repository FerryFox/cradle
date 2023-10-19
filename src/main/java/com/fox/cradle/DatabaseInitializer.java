package com.fox.cradle;

import com.fox.cradle.configuration.security.user.User;
import com.fox.cradle.configuration.security.user.UserRepository;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.service.AppUserService;
import com.fox.cradle.features.picture.model.Picture;
import com.fox.cradle.features.picture.service.PictureService;
import com.fox.cradle.features.stampsystem.model.enums.StampCardCategory;
import com.fox.cradle.features.stampsystem.model.enums.StampCardSecurity;
import com.fox.cradle.features.stampsystem.model.enums.StampCardStatus;
import com.fox.cradle.features.stampsystem.model.stamp.TimeGateSecurity;
import com.fox.cradle.features.stampsystem.model.template.Template;
import com.fox.cradle.features.news.model.News;
import com.fox.cradle.features.news.model.NewsCategory;
import com.fox.cradle.features.news.service.NewsService;
import com.fox.cradle.features.stampsystem.service.card.StampCardService;
import com.fox.cradle.features.stampsystem.service.stamp.StampService;
import com.fox.cradle.features.stampsystem.service.stamp.TimeGateRepository;
import com.fox.cradle.features.stampsystem.service.template.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Profile({"test", "dev"})
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner
{
    private final MongoTemplate mongoTemplate;

    private final AppUserService appUserService;
    private final UserRepository userRepository;
    private final TemplateService templateService;
    private final StampCardService stampCardService;
    private final StampService stampService;
    private final PictureService pictureService;
    private final NewsService newsService;
    private final TimeGateRepository timeGateRepository;

    @Override
    public void run(String... args) throws Exception {
    //MongoDb
        String collectionNamePicture = "pictures";
        String collectionNameNews = "news";
        if (mongoTemplate.collectionExists(collectionNamePicture))
        {
            mongoTemplate.dropCollection(collectionNamePicture);
        }
        if (mongoTemplate.collectionExists(collectionNameNews))
        {
            mongoTemplate.dropCollection(collectionNameNews);
        }

        List<Picture> pictures = initPictureMongoDb();
        List<News> news = initNewsMongoDb();

    //Create Some AppUsers
        User userIce = new User();
        userIce.setEmail("w@w");
        userIce.setPassword("1234");
        userIce.setReceiveNews(true);
        userIce.setFirstname("Ice Cream Company");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userIce.setPassword(passwordEncoder.encode(userIce.getPassword()));
        userRepository.save(userIce);

        AppUser appUserIceCompany = new AppUser();
        appUserIceCompany.setAppUserName(userIce.getFirstname());
        appUserIceCompany.setAppUserEmail("w@w");
        appUserIceCompany.setReceiveNews(userIce.isReceiveNews());
        appUserIceCompany.setNameIdentifier(UUID.randomUUID().toString());
        appUserService.saveAppUser(appUserIceCompany);

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
        appUserBob.setNameIdentifier(UUID.randomUUID().toString());
        appUserService.saveAppUser(appUserBob);

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
        appUserCinema.setNameIdentifier(UUID.randomUUID().toString());
        appUserService.saveAppUser(appUserCinema);

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
        appUserAnna.setNameIdentifier(UUID.randomUUID().toString());
        appUserService.saveAppUser(appUserAnna);


//Create some stamp card templates
        Instant time = java.time.Instant.now();

        Template template_001 = new Template();
        template_001.setName("Ice Cream");
        template_001.setImage(pictures.get(0).getId());
        template_001.setExpirationDate("2024-10-11T06:39:11.609Z");
        template_001.setPromise("Free Ice Cream");
        template_001.setDefaultCount(10);
        template_001.setStampCardCategory(StampCardCategory.FOOD);
        template_001.setStampCardSecurity(StampCardSecurity.TIMEGATE);

        TimeGateSecurity timeGateSecurity = new TimeGateSecurity();
        timeGateSecurity.setTimeGateDuration(java.time.Duration.ofSeconds(30));
        template_001.setTimeGateSecurity(timeGateSecurity);

        template_001.setStampCardStatus(StampCardStatus.PUBLIC);
        template_001.setDescription("Buy 10 ice creams and get one for free");
        template_001.setCreatedBy(appUserIceCompany.getAppUserName() + "#" + appUserIceCompany.getNameIdentifier());
        template_001.setCreatedDate(time);
        template_001.setAppUser(appUserIceCompany);
        template_001.setLastModifiedDate(time);
        templateService.save(template_001);

        Template template_002 = new Template();
        template_002.setName("Coffee");
        template_002.setImage(pictures.get(1).getId());
        template_002.setExpirationDate("2024-10-11T06:39:11.609Z");
        template_002.setPromise("Free Coffee");
        template_002.setDefaultCount(10);
        template_002.setStampCardCategory(StampCardCategory.DRINK);
        template_002.setStampCardSecurity(StampCardSecurity.TRUSTUSER);
        template_002.setStampCardStatus(StampCardStatus.PUBLIC);
        template_002.setDescription("Buy 10 coffees and get one for free");
        template_002.setCreatedBy(appUserIceCompany.getAppUserName() + "#" + appUserIceCompany.getNameIdentifier());
        template_002.setCreatedDate(time);
        template_002.setLastModifiedDate(time);
        template_002.setAppUser(appUserIceCompany);
        templateService.save(template_002);

        Template template_003 = new Template();
        template_003.setName("Cinema");
        template_003.setImage(pictures.get(2).getId());
        template_003.setExpirationDate("2024-10-11T06:39:11.609Z");
        template_003.setPromise("Free Ticket");
        template_003.setDefaultCount(5);
        template_003.setStampCardCategory(StampCardCategory.ENTERTAINMENT);
        template_003.setStampCardSecurity(StampCardSecurity.TRUSTUSER);
        template_003.setStampCardStatus(StampCardStatus.PUBLIC);
        template_003.setDescription("Visit the cinema x times to get a free ticket");
        template_003.setCreatedBy(appUserCinema.getAppUserName() + "#" + appUserCinema.getNameIdentifier());
        template_003.setCreatedDate(time);
        template_003.setLastModifiedDate(time);
        template_003.setAppUser(appUserCinema);
        templateService.save(template_003);

        Template template_004 = new Template();
        template_004.setName("Kebab");
        template_004.setImage(pictures.get(3).getId());
        template_004.setExpirationDate("2024-10-11T06:39:11.609Z");
        template_004.setPromise("Free Kebab");
        template_004.setDefaultCount(10);
        template_004.setStampCardCategory(StampCardCategory.FOOD);
        template_004.setStampCardSecurity(StampCardSecurity.TRUSTUSER);
        template_004.setStampCardStatus(StampCardStatus.PUBLIC);
        template_004.setDescription("Buy 10 kebabs and get one for free");
        template_004.setCreatedBy(appUserIceCompany.getAppUserName() + "#" + appUserIceCompany.getNameIdentifier());
        template_004.setCreatedDate(time);
        template_004.setLastModifiedDate(time);
        template_004.setAppUser(appUserIceCompany);
        templateService.save(template_004);

        Template template_005 = new Template();
        template_005.setName("Sushi");
        template_005.setImage(pictures.get(4).getId());
        template_005.setExpirationDate("2024-10-11T06:39:11.609Z");
        template_005.setPromise("Free Sushi");
        template_005.setDefaultCount(8);
        template_005.setStampCardCategory(StampCardCategory.FOOD);
        template_005.setStampCardSecurity(StampCardSecurity.TRUSTUSER);
        template_005.setStampCardStatus(StampCardStatus.PUBLIC);
        template_005.setDescription("Buy 70 sushi and get one for free");
        template_005.setCreatedBy(appUserIceCompany.getAppUserName() + "#" + appUserIceCompany.getNameIdentifier());
        template_005.setCreatedDate(time);
        template_005.setLastModifiedDate(time);
        template_005.setAppUser(appUserIceCompany);
        templateService.save(template_005);

        Template template_006 = new Template();
        template_006.setName("Roller Coaster");
        template_006.setImage(pictures.get(5).getId());
        template_006.setExpirationDate("2024-10-11T06:39:11.609Z");
        template_006.setPromise("Free Ticket");
        template_006.setDefaultCount(3);
        template_006.setStampCardCategory(StampCardCategory.FOOD);
        template_006.setStampCardSecurity(StampCardSecurity.TRUSTUSER);
        template_006.setStampCardStatus(StampCardStatus.PUBLIC);
        template_006.setDescription("Visit the roller coaster 3 times and get a free ticket");
        template_006.setCreatedBy(appUserIceCompany.getAppUserName() + "#" + appUserIceCompany.getNameIdentifier());
        template_006.setCreatedDate(time);
        template_006.setLastModifiedDate(time);
        template_006.setAppUser(appUserIceCompany);
        templateService.save(template_006);

        Template template_007 = new Template();
        template_007.setName("Vegetables");
        template_007.setImage(pictures.get(7).getId());
        template_007.setExpirationDate("2024-10-11T06:39:11.609Z");
        template_007.setPromise("Free Vegetables");
        template_007.setDefaultCount(6);
        template_007.setStampCardCategory(StampCardCategory.FOOD);
        template_007.setStampCardSecurity(StampCardSecurity.TRUSTUSER);
        template_007.setStampCardStatus(StampCardStatus.PUBLIC);
        template_007.setDescription("Buy 6 vegetables and get one for free");
        template_007.setCreatedBy(appUserAnna.getAppUserName() + "#" + appUserAnna.getNameIdentifier());
        template_007.setCreatedDate(time);
        template_007.setLastModifiedDate(time);
        template_007.setAppUser(appUserAnna);
        templateService.save(template_007);

        Template template_008 = new Template();
        template_008.setName("Skate");
        template_008.setImage(pictures.get(6).getId());
        template_008.setExpirationDate("2024-10-11T06:39:11.609Z");
        template_008.setPromise("Free Ticket");
        template_008.setDefaultCount(9);
        template_008.setStampCardCategory(StampCardCategory.ENTERTAINMENT);
        template_008.setStampCardSecurity(StampCardSecurity.TRUSTUSER);
        template_008.setStampCardStatus(StampCardStatus.PRIVATE);
        template_008.setDescription("Visit the skate park 9 times and get a free ticket");
        template_007.setCreatedBy(appUserAnna.getAppUserName() + "#" + appUserAnna.getNameIdentifier());
        template_008.setCreatedDate(time);
        template_008.setLastModifiedDate(time);
        template_008.setAppUser(appUserAnna);
        templateService.save(template_008);

        System.out.println("database initialized");
    }

    private List<Picture> initPictureMongoDb() throws Exception
    {
        List<Picture> pictures = new ArrayList<>();

        Picture ice = pictureService.loadPictureFromFile("ice");
        pictures.add(pictureService.savePicture(ice));

        Picture coffee = pictureService.loadPictureFromFile("coffee");
        pictures.add(pictureService.savePicture(coffee));

        Picture cinema = pictureService.loadPictureFromFile("cinema");
        pictures.add(pictureService.savePicture(cinema));

        Picture kebab = pictureService.loadPictureFromFile("kebab");
        pictures.add(pictureService.savePicture(kebab));

        Picture sushi = pictureService.loadPictureFromFile("sushi");
        pictures.add(pictureService.savePicture(sushi));

        Picture roller  = pictureService.loadPictureFromFile("roller");
        pictures.add(pictureService.savePicture(roller));

        Picture skate = pictureService.loadPictureFromFile("skate");
        pictures.add(pictureService.savePicture(skate));

        Picture vegetables = pictureService.loadPictureFromFile("vegetables");
        pictures.add(pictureService.savePicture(vegetables));

        return pictures;
    }
    private List<News> initNewsMongoDb()
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
        return newsList;
    }
}
