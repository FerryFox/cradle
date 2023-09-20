package com.fox.cradle.features.picture.service;

import com.fox.cradle.features.picture.model.Picture;
import org.bson.types.Binary;
import org.springframework.core.io.ClassPathResource;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PictureService
{
    private final PictureRepository pictureRepository;

    public Picture loadPictureFromFile(String imageName) throws Exception
    {
        ClassPathResource resource = new ClassPathResource("images/" + imageName + ".jpg");
        byte[] imageData = Files.readAllBytes(resource.getFile().toPath());
        Picture picture = new Picture();
        picture.setImageData(new Binary(imageData));
        picture.setName(imageName);
        return picture;
    }

    public List<Picture> getAllPictures()
    {
        return pictureRepository.findAll();
    }

    public Picture savePicture(String base64Image, String name)
    {
        Picture picture = new Picture();
        picture.setName(name);
        picture.setImageData(base64ToBinary(base64Image));
        return pictureRepository.save(picture);
    }

    public void deletePictureById(String id)
    {
        pictureRepository.deleteById(id);
    }

    public Picture savePicture(Picture picture)
    {
        return pictureRepository.save(picture);
    }

    public String getPictureByIdBase64Encoded(String id)
    {
         Optional<Picture> picture =  pictureRepository.findById(id);
         if(picture.isEmpty()) return "";
         byte[] imageBytes = picture.get().getImageData().getData();
         return Base64.getEncoder().encodeToString(imageBytes);
    }

    public Binary base64ToBinary(String base64)
    {
        if(base64.startsWith("data:image"))
        {
            base64 = base64.split(",")[1];
        }
        byte[] bytes = Base64.getDecoder().decode(base64);
        return new Binary(bytes);
    }
}
