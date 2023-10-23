package com.fox.cradle.features.picture.service;

import com.fox.cradle.features.picture.model.Picture;
import org.bson.types.Binary;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class PictureServiceIntegrationTest
{
    @Autowired
    private PictureService pictureService;

    public static final String DEFAULT_PICTURE = "data:image/jpeg;base64,/9j/4AAQgGBolGxg";


    @Test
    void loadPictureFromFileTest() throws Exception
    {
        //GIVEN
        String imageName = "ice";

        //WHEN
        String picture = pictureService.loadPictureFromFile(imageName);

        //THEN
        Assertions.assertNotNull(picture);
        Assertions.assertTrue(picture.startsWith("data:image/jpeg;base64,/9j/"));
    }

    @Test
    void getAllPicturesTest()
    {
        //WHEN
        List<Picture> pictures = pictureService.getAllPictures();

        //THEN
        Assertions.assertNotNull(pictures);
    }

    @Test
    void savePictureAndDelete()
    {
        //GIVEN
        String name = "test";

        //WHEN
        Picture savedPicture = pictureService.savePicture(DEFAULT_PICTURE, name);

        //THEN
        Assertions.assertNotNull(savedPicture);
        Assertions.assertNotNull(savedPicture.getId());
        Assertions.assertEquals(name, savedPicture.getName());
        Assertions.assertEquals(PictureType.BASE64, savedPicture.getType());
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
                "data:image/jpeg;base64,/9j/4AAQSkZYHSggGBolGxg");

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

        //When
    Binary binary = pictureService.base64ToBinary(DEFAULT_PICTURE);

        //Then
        Assertions.assertNotNull(binary);
        Assertions.assertTrue(binary.getData().length > 0);
    }

    @Test
    void getPictureByIdTest() throws IOException {

        //GIVEN
        String picAsString = pictureService.loadPictureFromFile("ice");
        String id = pictureService.savePicture(picAsString, "iceTest").getId();
        //WHEN
        Picture pic = pictureService.getPictureById(id);

        //THEN
        Assertions.assertNotNull(pic);
        Assertions.assertEquals("iceTest", pic.getName());
        Assertions.assertEquals(PictureType.BASE64, pic.getType());
        Assertions.assertNotNull(pic.getImageBinary());
        Assertions.assertEquals(id , pic.getId());
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