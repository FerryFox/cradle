package com.fox.cradle;

import com.fox.cradle.configuration.security.auth.AuthenticationService;
import com.fox.cradle.configuration.security.auth.RegisterRequest;
import com.fox.cradle.features.appuser.model.AddInfoDTO;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.service.AppUserService;
import com.fox.cradle.features.news.model.News;
import com.fox.cradle.features.news.model.NewsCategory;
import com.fox.cradle.features.news.service.NewsService;
import com.fox.cradle.features.picture.service.PictureService;
import com.fox.cradle.features.stampsystem.model.enums.StampCardCategory;
import com.fox.cradle.features.stampsystem.model.enums.StampCardSecurity;
import com.fox.cradle.features.stampsystem.model.enums.StampCardStatus;
import com.fox.cradle.features.stampsystem.model.template.NewSecurityTimeGate;
import com.fox.cradle.features.stampsystem.model.template.NewTemplate;
import com.fox.cradle.features.stampsystem.service.template.TemplateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile({"dev" , "test"})
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner
{

    private final MongoTemplate mongoTemplate;
    private final AuthenticationService authService;
    private final AppUserService appUserService;
    private final TemplateService templateService;
    private final PictureService pictureService;
    private final NewsService newsService;

    static final String PASSWORD = "startrek";
    static final String EXP = "2024-10-11T06:39:11.609Z";

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("DatabaseInitializer is running...");
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
        initNewsMongoDb();

//Create Some AppUsers
    //Create User 1
        RegisterRequest registerRequestIce = RegisterRequest.builder()
                .firstname("Ice Cream Company")
                .email("icecream@gmail.com")
                .password(PASSWORD)
                .receiveNews(true)
                .build();

        //Event triggers the creation of an appUser
        authService.register(registerRequestIce);
        AppUser appUserIceCompany = appUserService.findUserByEmail(registerRequestIce.getEmail()).orElseThrow();

        String icep = pictureService.loadPictureFromFile("icep");
        AddInfoDTO info = AddInfoDTO.builder()
                .name("Ice Cream Company")
                .bio("We are a small ice cream company from Berlin. We love ice cream and we want to share our passion with you. We are looking forward to your visit!")
                .picture(icep)
                .status("We are open!")
                .build();
        appUserService.updateAdditionalInfo(appUserIceCompany, info);


        String ice = pictureService.loadPictureFromFile("ice");

        NewTemplate icreamTemplate = NewTemplate.builder()
                .name("Ice Cream")
                .promise("Free Ice Cream")
                .description("Buy ice creams and get one for free")
                .image(ice)
                .fileName("ice")
                .defaultCount(10)
                .expirationDate(EXP)
                .stampCardCategory(StampCardCategory.FOOD)
                .stampCardSecurity(StampCardSecurity.TRUSTUSER)
                .stampCardStatus(StampCardStatus.PUBLIC)
                .appUser(appUserIceCompany)
                .build();
        templateService.createTemplate(icreamTemplate, appUserIceCompany);


    //Time Gate Example
        String coffee = pictureService.loadPictureFromFile("coffee");
        NewTemplate coffeTemplate = NewTemplate.builder()
                .name("Coffee")
                .promise("Free Coffee")
                .description("Buy coffees and get one for free")
                .image(coffee)
                .fileName("coffee")
                .defaultCount(5)
                .expirationDate(EXP)
                .stampCardCategory(StampCardCategory.DRINK)
                .stampCardSecurity(StampCardSecurity.TIMEGATE)
                .stampCardStatus(StampCardStatus.PUBLIC)
                .securityTimeGate( NewSecurityTimeGate.builder()
                        .timeGateNumber(1)
                        .build())
                .build();
        templateService.createTemplate(coffeTemplate, appUserIceCompany);


        String kebab = pictureService.loadPictureFromFile("kebab");
        NewTemplate kebabTemplate = NewTemplate.builder()
                .name("Kebab")
                .promise("Free Kebab")
                .description("Buy kebabs and get one for free")
                .image(kebab)
                .fileName("kebab")
                .defaultCount(10)
                .expirationDate(EXP)
                .stampCardCategory(StampCardCategory.FOOD)
                .stampCardSecurity(StampCardSecurity.TRUSTUSER)
                .stampCardStatus(StampCardStatus.PRIVATE)
                .build();
       templateService.createTemplate(kebabTemplate, appUserIceCompany);

        //Create User 2
        RegisterRequest registerRequestCinema = RegisterRequest.builder()
                .firstname("Cinema")
                .email("thalia@web.de")
                .password(PASSWORD)
                .receiveNews(true)
                .build();

        //Event triggers the creation of an appUser
        authService.register(registerRequestCinema);
        AppUser appUserCinema = appUserService.findUserByEmail(registerRequestCinema.getEmail()).orElseThrow();

        String cinemaP = pictureService.loadPictureFromFile("cinemap");
        AddInfoDTO infoCinema = AddInfoDTO.builder()
                .name("Cinema")
                .bio("We are a small cinema from Berlin. We love movies and we want to share our passion with you. We are looking forward to your visit!")
                .picture(cinemaP)
                .status("Take a look")
                .build();

        appUserService.updateAdditionalInfo(appUserCinema, infoCinema);


        String cinema = pictureService.loadPictureFromFile("cinema");

        NewTemplate cinemaTemplate = NewTemplate.builder()
                .name("Cinema")
                .promise("Free Ticket")
                .description("Visit the cinema x times to get a free ticket")
                .image(cinema)
                .fileName("cinema")
                .defaultCount(5)
                .expirationDate(EXP)
                .stampCardCategory(StampCardCategory.ENTERTAINMENT)
                .stampCardSecurity(StampCardSecurity.TRUSTUSER)
                .stampCardStatus(StampCardStatus.PUBLIC)
                .appUser(appUserCinema)
                .build();

        templateService.createTemplate(cinemaTemplate, appUserCinema);


        //Create User 3
        RegisterRequest registerRequestFood = RegisterRequest.builder()
                .firstname("Local Food Store")
                .email("food@fake.com")
                .password(PASSWORD)
                .receiveNews(false)
                .build();

        //Event triggers the creation of an appUser
        authService.register(registerRequestFood);
        AppUser appUserFood = appUserService.findUserByEmail(registerRequestFood.getEmail()).orElseThrow();

        String foodp = pictureService.loadPictureFromFile("foodp");
        AddInfoDTO infoFood = AddInfoDTO.builder()
                .name("Local Food Store")
                .bio("We are a small food store from Berlin. We love food and we want to share our passion with you. We are looking forward to your visit!")
                .picture(foodp)
                .status("Eat more!")
                .build();

        appUserService.updateAdditionalInfo(appUserFood, infoFood);

        String sushi = pictureService.loadPictureFromFile("sushi");
        NewTemplate sushiTemplate = NewTemplate.builder()
                .name("Sushi")
                .promise("Free Sushi")
                .description("Buy sushi and get one for free")
                .image(sushi)
                .fileName("sushi")
                .defaultCount(8)
                .expirationDate(EXP)
                .stampCardCategory(StampCardCategory.FOOD)
                .stampCardSecurity(StampCardSecurity.TRUSTUSER)
                .stampCardStatus(StampCardStatus.PUBLIC)
                .appUser(appUserFood)
                .build();
        templateService.createTemplate(sushiTemplate, appUserFood);

        String vegetables = pictureService.loadPictureFromFile("vegetables");
        NewTemplate vegetablesTemplate = NewTemplate.builder()
                .name("Vegetables")
                .promise("Free Vegetables")
                .description("Buy vegetables and get one for free")
                .image(vegetables)
                .fileName("vegetables")
                .defaultCount(8)
                .expirationDate(EXP)
                .stampCardCategory(StampCardCategory.FOOD)
                .stampCardSecurity(StampCardSecurity.TRUSTUSER)
                .stampCardStatus(StampCardStatus.PUBLIC)
                .appUser(appUserFood)
                .build();
        templateService.createTemplate(vegetablesTemplate, appUserFood);

//Create User 4
        RegisterRequest registerRequestPark = RegisterRequest.builder()
                .firstname("Theme Park")
                .email("park@gmail.com")
                .password(PASSWORD)
                .receiveNews(true)
                .build();

        //Event triggers the creation of an appUser
        authService.register(registerRequestPark);
        AppUser appUserClothes = appUserService.findUserByEmail(registerRequestPark.getEmail()).orElseThrow();

        String parkp = pictureService.loadPictureFromFile("parkp");
        AddInfoDTO infoPark = AddInfoDTO.builder()
                .name("Theme Park")
                .bio("We are a small theme park from Berlin. We love theme parks and we want to share our passion with you. We are looking forward to your visit!")
                .picture(parkp)
                .status("We are open!")
                .build();

        appUserService.updateAdditionalInfo(appUserClothes, infoPark);

        String roller = pictureService.loadPictureFromFile("roller");
        NewTemplate rollerTemplate = NewTemplate.builder()
                .name("Roller Coaster")
                .promise("Free Ride")
                .description("Visit the park x times to get a free ride")
                .image(roller)
                .fileName("roller")
                .defaultCount(5)
                .expirationDate(EXP)
                .stampCardCategory(StampCardCategory.ENTERTAINMENT)
                .stampCardSecurity(StampCardSecurity.TRUSTUSER)
                .stampCardStatus(StampCardStatus.PUBLIC)
                .appUser(appUserClothes)
                .build();
        templateService.createTemplate(rollerTemplate, appUserClothes);

        String skate = pictureService.loadPictureFromFile("skate");
        NewTemplate skateTemplate = NewTemplate.builder()
                .name("Skate Park")
                .promise("Free Skate")
                .description("Visit the park x times to get a free skate")
                .image(skate)
                .fileName("skate")
                .defaultCount(5)
                .expirationDate(EXP)
                .stampCardCategory(StampCardCategory.ENTERTAINMENT)
                .stampCardSecurity(StampCardSecurity.TRUSTUSER)
                .stampCardStatus(StampCardStatus.PUBLIC)
                .appUser(appUserClothes)
                .build();
        templateService.createTemplate(skateTemplate, appUserClothes);
        System.out.println("DatabaseInitializer finished...");

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
