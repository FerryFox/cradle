package com.fox.cradle.features.picture.service;

import com.fox.cradle.features.picture.model.Picture;
import org.bson.types.Binary;
import org.springframework.core.io.ClassPathResource;
import java.nio.file.Files;
import java.util.Base64;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PictureService
{
    private final PictureRepository pictureRepository;

    public byte[] loadImageAsBytesFromFile(String imageName) throws Exception
    {
        ClassPathResource resource = new ClassPathResource("images/" + imageName + ".jpg");
        return Files.readAllBytes(resource.getFile().toPath());
    }

    public Picture loadPictureFromFile(String imageName) throws Exception
    {
        ClassPathResource resource = new ClassPathResource("images/" + imageName + ".jpg");
        byte[] imageData = Files.readAllBytes(resource.getFile().toPath());
        Picture picture = new Picture();
        picture.setImageData(new Binary(imageData));
        picture.setName(imageName);
        return picture;
    }

    public Picture savePicture(Picture picture)
    {
        try
        {
            return pictureRepository.findById(picture.getId()).get();
        }
        catch (Exception e)
        {
            return pictureRepository.save(picture);
        }
    }

    public Picture savePicture(byte [] imageData, String name)
    {
        Picture picture = byteToPicture(imageData);
        picture.setName(name);
        return savePicture(picture);
    }

    public Picture savePicture(byte [] imageData)
    {
        Picture picture = byteToPicture(imageData);
        return savePicture(picture);
    }

    public Picture savePicture(String base64Image)
    {

        Picture picture = new Picture();
        picture.setName("no name");
        picture.setImageData(base64ToBinary(base64Image));

        return pictureRepository.save(picture);
    }


    public String getPictureId(String id)
    {
         Picture picture =  pictureRepository.findById(id).get();
         byte[] imageBytes = picture.getImageData().getData();
         String encodedString = Base64.getEncoder().encodeToString(imageBytes);
         return encodedString;
    }

    private Picture byteToPicture( byte[] imageData )
    {
        Binary binaryData = new Binary(imageData);
        Picture picture = new Picture();
        picture.setImageData(binaryData);
        return picture;
    }

    private Binary base64ToBinary(String base64)
    {
        if(base64.startsWith("data:image")) {
            base64 = base64.split(",")[1];
        }

        byte[] bytes = Base64.getDecoder().decode(base64);
        return new Binary(bytes);
    }

}
