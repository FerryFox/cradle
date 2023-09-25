package com.fox.cradle.features.picture.service;

import com.fox.cradle.features.picture.model.Picture;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.List;

@SpringBootTest
class PictureServiceIntegrationTest
{
    @Autowired
    private PictureService pictureService;

    @Container
    static final MongoDBContainer mongoDBContainer = new MongoDBContainer();

    @BeforeAll
    static void setup()
    {
        mongoDBContainer.start();
    }

    @AfterAll
    static void cleanup()
    {
        mongoDBContainer.stop(); // stop the MongoDB container
    }

    @DynamicPropertySource
    static void setUrlDynamically(DynamicPropertyRegistry registry)
    {
        System.out.println( "MONGO_URL=" + mongoDBContainer.getReplicaSetUrl());
        registry.add("MONGO_URL", () -> mongoDBContainer.getReplicaSetUrl());
    }

    @Test
    void WalkThroughPictureService() throws Exception {
        Picture loadedPicture = pictureService.loadPictureFromFile("ice");
        assert loadedPicture != null;

        Picture savePicture = pictureService.savePicture(loadedPicture);
        assert savePicture != null;

        String base64EncodedPic = pictureService.getPictureByIdBase64Encoded(savePicture.getId());
        assert base64EncodedPic != null;

        List<Picture> allPictures = pictureService.getAllPictures();
        assert allPictures != null;
        assert allPictures.size() == 1;

        pictureService.deletePictureById(savePicture.getId());
    }

    @Test
    void loadPictureFromFileTest() throws Exception
    {
        Picture loadedPicture = pictureService.loadPictureFromFile("ice");
        assert loadedPicture != null;
        assert loadedPicture.getName().equals("ice");
    }

    @Test
    void getAllPicturesTest() throws Exception
    {
        List<Picture> test = pictureService.getAllPictures();
        assert test != null;

        Picture loadedPicture = pictureService.loadPictureFromFile("ice");
        assert loadedPicture != null;

        Picture savedPicture = pictureService.savePicture(loadedPicture);
        assert savedPicture != null;

        List<Picture> allPictures2 = pictureService.getAllPictures();
        assert allPictures2 != null;
        assert allPictures2.size() == 1;

        pictureService.deletePictureById(savedPicture.getId());
    }

    @Test
    void savePictureAndDelete() throws Exception
    {
        Picture loadedPicture = pictureService.loadPictureFromFile("ice");
        assert loadedPicture != null;

        Picture savedPicture = pictureService.savePicture(loadedPicture);
        assert savedPicture != null;

        pictureService.deletePictureById(savedPicture.getId());
    }
}