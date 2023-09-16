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

    public Picture savePicture(Picture picture)
    {
        return pictureRepository.save(picture);
    }

    public Picture savePicture(byte [] imageData)
    {
        Picture picture = byteToPicture(imageData);
        return savePicture(picture);
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

}
