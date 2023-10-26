package com.fox.cradle.features.picture.service;

import com.fox.cradle.exceptions.PictureFileMissingException;
import com.fox.cradle.exceptions.WrongPictureTypeException;
import com.fox.cradle.features.picture.model.Picture;
import org.bson.types.Binary;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
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
    private static final String DEFAULT_PICTURE = "data:image";

    public String loadPictureFromFile(String imageName) throws PictureFileMissingException, IOException
    {
        ClassPathResource resource = new ClassPathResource("images/" + imageName + ".jpg");
        byte[] imageData = Files.readAllBytes(resource.getFile().toPath());

        // Convert the byte array to a Base64-encoded string
        String base64Image = Base64.getEncoder().encodeToString(imageData);

        // Return the data URI for a JPEG image
        return "data:image/jpeg;base64," + base64Image;
    }

    public List<Picture> getAllPictures()
    {
        return pictureRepository.findAll();
    }

    public Picture savePicture(String possiblePicture, String name)
    {
        Picture picture = new Picture();
        picture.setName(name);

        if( possiblePicture.startsWith(DEFAULT_PICTURE))
        {
            picture.setType(PictureType.BASE64);
            picture.setImageBinary(base64ToBinary(possiblePicture));
        }
        else if (possiblePicture.startsWith("http"))
        {
            picture.setType(PictureType.URL_LINK);
            picture.setUrl(possiblePicture);
        }
        else
        {
            throw new WrongPictureTypeException("Picture type not supported");
        }
        return pictureRepository.save(picture);
    }

    public Picture savePicture(Picture picture)
    {
        return pictureRepository.save(picture);
    }

    public void deletePictureById(String id)
    {
        pictureRepository.deleteById(id);
    }

    public Picture updatePicutre(String id, String base64)
    {
        Picture picture = getPictureById(id);
        picture.setImageBinary(base64ToBinary(base64));
        return savePicture(picture);
    }


    public String getPictureString(String id)
    {
         Optional<Picture> picture =  pictureRepository.findById(id);
         if(picture.isEmpty()) return null;

         if(picture.get().getType() == PictureType.URL_LINK)
             return picture.get().getUrl();

         else{
             byte[] imageBytes = picture.get().getImageBinary().getData();
             return "data:image/jpg;base64," + Base64.getEncoder().encodeToString(imageBytes);
         }
    }

    public Binary base64ToBinary(String base64)
    {
        if(base64.startsWith(DEFAULT_PICTURE))
        {
            base64 = base64.split(",")[1];
        }
        byte[] bytes = Base64.getDecoder().decode(base64);
        return new Binary(bytes);
    }


    public Picture getPictureById(String id)
    {
        return pictureRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Picture not found"));
    }

    ///used to compare picture-strings
    public String removePrefixFrom64(String base64)
    {
        if (base64.startsWith(DEFAULT_PICTURE))
        {
            base64 = base64.split(",")[1];
        }
        return base64;
    }

}
