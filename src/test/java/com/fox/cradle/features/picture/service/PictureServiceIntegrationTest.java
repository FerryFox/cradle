package com.fox.cradle.features.picture.service;

import com.fox.cradle.AbstractMongoDBIntegrationTest;
import com.fox.cradle.features.picture.model.Picture;
import org.bson.types.Binary;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PictureServiceIntegrationTest extends AbstractMongoDBIntegrationTest
{
    @Autowired
    private PictureService pictureService;

    public static final String DEFAULT_PICTURE = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMTEhUTExMWFhUXGBgYGBgYGBgYGBgYGBgYGBgYGBgYHSggGBolGxg";

    @Test
    void WalkThroughPictureService() throws Exception {
        Picture loadedPicture = pictureService.loadPictureFromFile("ice");
        Assertions.assertNotNull(loadedPicture);

        Picture savePicture = pictureService.savePicture(loadedPicture);
        Assertions.assertNotNull(savePicture);


        String base64EncodedPic = pictureService.getPictureString(savePicture.getId());
        Assertions.assertNotNull(base64EncodedPic);

        List<Picture> allPictures = pictureService.getAllPictures();
        Assertions.assertNotNull(allPictures);

        pictureService.deletePictureById(savePicture.getId());
    }

    @Test
    void loadPictureFromFileTest() throws Exception
    {
        //When
        Picture loadedPicture = pictureService.loadPictureFromFile("ice");

        //Then
        Assertions.assertNotNull(loadedPicture);
        Assertions.assertNotNull(loadedPicture.getImageBinary());
        Assertions.assertNotNull(loadedPicture.getName());
        Assertions.assertNotNull(loadedPicture.getType());
    }

    @Test
    void getAllPicturesTest() throws Exception
    {
        //Given
        List<Picture> test = pictureService.getAllPictures();
        Assertions.assertNotNull(test);

        Picture loadedPicture = pictureService.loadPictureFromFile("ice");
        Assertions.assertNotNull(loadedPicture);

        Picture savedPicture = pictureService.savePicture(loadedPicture);
        Assertions.assertNotNull(savedPicture);

        List<Picture> allPictures2 = pictureService.getAllPictures();
        Assertions.assertNotNull(allPictures2);
        Assertions.assertEquals(1, allPictures2.size());

        //Cleanup
        pictureService.deletePictureById(savedPicture.getId());
    }

    @Test
    void savePictureAndDelete() throws Exception
    {
        //Given
        Picture loadedPicture = pictureService.loadPictureFromFile("ice");
        Assertions.assertNotNull(loadedPicture);

        Picture savedPicture = pictureService.savePicture(loadedPicture);
        Assertions.assertNotNull(savedPicture);

        //Cleanup
        pictureService.deletePictureById(savedPicture.getId());
    }

    @Test
    void savePictureWithBaseString()
    {
        //Given
        Picture picture = new Picture();
        picture.setName("test");
        picture.setType(PictureType.BASE64);
        picture.setImageBinary(new Binary(DEFAULT_PICTURE.getBytes()));

        //When
        Picture savedPicture = pictureService.savePicture(picture);

        //Then
        Assertions.assertNotNull(savedPicture);
        Assertions.assertNotNull(savedPicture.getId());
        Assertions.assertEquals(picture.getName(), savedPicture.getName());
        Assertions.assertEquals(picture.getType(), savedPicture.getType());
        Assertions.assertEquals(picture.getImageBinary(), savedPicture.getImageBinary());

        //Cleanup
        pictureService.deletePictureById(savedPicture.getId());
    }

    @Test
    void updatePicutreTest()
    {
        //Given
        Picture picture = new Picture();
        picture.setName("test");
        picture.setType(PictureType.BASE64);
        picture.setImageBinary(new Binary(DEFAULT_PICTURE.getBytes()));

        //When
        Picture savedPicture = pictureService.savePicture(picture);
        Picture updatedPicture = pictureService.updatePicutre(savedPicture.getId(),
                "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkdsddsdsdshUXGBgYGBgYGBgYGBgYGBgYGBgYGBgYHSggGBolGxg");

        //Then
        Assertions.assertNotNull(updatedPicture);
        Assertions.assertEquals(savedPicture.getId(), updatedPicture.getId());
        Assertions.assertEquals(savedPicture.getName(), updatedPicture.getName());
        Assertions.assertEquals(savedPicture.getType(), updatedPicture.getType());
        Assertions.assertNotEquals(savedPicture.getImageBinary(), updatedPicture.getImageBinary());

        //Cleanup
        pictureService.deletePictureById(savedPicture.getId());
    }

    @Test
    void getPictureString()
    {
        //Given
        Picture picture = new Picture();
        picture.setName("test");
        picture.setType(PictureType.BASE64);
        picture.setImageBinary(new Binary(DEFAULT_PICTURE.getBytes()));
        Picture savedPicture = pictureService.savePicture(picture);

        //When
        String pictureString = pictureService.getPictureString(savedPicture.getId());

        //Then
        Assertions.assertNotNull(pictureString);
        //Since the picture is saved as a binary, the string should not be the same as the default picture
        //Since the start of string is removed, the string should not start with "data:image/jpeg;base64,"
        Assertions.assertNotEquals(DEFAULT_PICTURE, pictureString);
        Assertions.assertTrue(pictureString.startsWith("ZGF0YTppbWFnZS9qcGVnO2"));
    }

    @Test
    void base64ToBinary()
    {
        //Given
        String base64 = DEFAULT_PICTURE;

        //When
        Binary binary = pictureService.base64ToBinary(base64);

        //Then
        Assertions.assertNotNull(binary);
        Assertions.assertTrue(binary.getData().length > 0);
    }


    @Test
    void removePrefixFrom64()
    {
        //Given
        String base64 = DEFAULT_PICTURE;

        //When
        String result = pictureService.removePrefixFrom64(base64);

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertNotEquals(base64, result);
        Assertions.assertFalse(result.startsWith("data:image/jpeg;base64,"));
    }
}