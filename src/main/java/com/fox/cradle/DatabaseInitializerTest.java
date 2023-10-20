package com.fox.cradle;

import com.fox.cradle.configuration.security.auth.AuthenticationService;
import com.fox.cradle.configuration.security.auth.RegisterRequest;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.service.AppUserService;
import com.fox.cradle.features.news.model.News;
import com.fox.cradle.features.news.model.NewsCategory;
import com.fox.cradle.features.news.service.NewsService;
import com.fox.cradle.features.stampsystem.model.enums.StampCardCategory;
import com.fox.cradle.features.stampsystem.model.enums.StampCardSecurity;
import com.fox.cradle.features.stampsystem.model.enums.StampCardStatus;
import com.fox.cradle.features.stampsystem.model.template.NewTemplate;
import com.fox.cradle.features.stampsystem.service.template.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile({"test"})
@RequiredArgsConstructor
public class DatabaseInitializerTest implements CommandLineRunner
{
    private final MongoTemplate mongoTemplate;
    private final AuthenticationService authService;
    private final AppUserService appUserService;
    private final TemplateService templateService;
    private final NewsService newsService;

    static final String PASSWORD = "startrek";
    static final String EXP = "2024-10-11T06:39:11.609Z";

    static final String PIC = "data:image/jpeg;base64,SGVsbG8sIFdvcmxkIQ==";

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

        String ice = PIC;
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
        String coffee = PIC;
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
                .securityTimeGateDuration("PT30S")
                .stampCardStatus(StampCardStatus.PUBLIC)
                .build();
        templateService.createTemplate(coffeTemplate, appUserIceCompany);


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

        String cinema = PIC;

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
    }

    private List<News> initNewsMongoDb()
    {
        News news1 = News.builder()
                .title("Collect & Earn")
                .description("Unlock endless possibilities! Collect stamp cards from companies and friends to earn exclusive rewards!")
                .newsCategory(NewsCategory.USER)
                .imageUrl("1")
                .build();

        News news2 = News.builder()
                .title("Create & Share")
                .description("Spread the joy by creating your unique stamp cards and sharing them with friends. Motivate and be motivated!")
                .newsCategory(NewsCategory.USER)
                .imageUrl("2")
                .build();

        List<News> newsList = List.of(news1, news2);
        newsList.forEach(newsService::saveNews);
        return newsList;
    }
}