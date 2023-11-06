package com.fox.cradle.features.picture.service;

import com.fox.cradle.exceptions.WrongPictureTypeException;
import com.fox.cradle.features.picture.model.Picture;
import org.bson.types.Binary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Base64;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class PictureServiceUnitTest {

    @Mock
    PictureRepository pictureRepository;

    @InjectMocks
    PictureService pictureService;

    @Test
    void savePictureUnitTestHttpImage()
    {
        //GIVEN
        String name = "testName";

        Picture expectedPicture = new Picture();
        expectedPicture.setName(name);
        expectedPicture.setType(PictureType.URL_LINK);
        expectedPicture.setUrl("http://test.com");
        expectedPicture.setImageBinary(null);

        when(pictureRepository.save(Mockito.<Picture>any())).thenReturn(expectedPicture);

        //WHEN
        Picture result = pictureService.savePicture("http://test.com", name);

        //THEN
        assertNotNull(result);
    }
    @Test
    void testSavePictureThrowsException() {
        //GIVEN
        String wrongTypePicture = "unsupported_type";
        String name = "testName";

        //WHEN

        assertThrows(WrongPictureTypeException.class, () -> pictureService.savePicture(wrongTypePicture, "testName"));
        verify(pictureRepository, never()).save(any());
    }

    @Test
    void getPictureStringTestWithWrongID() {

        //WHEN
        var result = pictureService.getPictureString("wrongID");
        Assertions.assertNull(result);
    }

    @Test
    void whenPictureTypeIsUrlLinkThenReturnsUrlType() {
        //GIVEN
        String fakeId = "fakePictureId";

        String expectedUrl = "http://example.com/image.jpg";
        Picture pictureWithUrl = new Picture();
        pictureWithUrl.setId(fakeId);
        pictureWithUrl.setType(PictureType.URL_LINK);
        pictureWithUrl.setUrl(expectedUrl);

        when(pictureRepository.findById(fakeId)).thenReturn(Optional.of(pictureWithUrl));

        //WHEN
        String result = pictureService.getPictureString(fakeId);

        //THEN
        assertEquals(expectedUrl, result);
    }

    @Test
    void whenBase64StringIsInvalid_thenShouldThrowException() {
        // Arrange
        String invalidBase64 = "not_valid_base64";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
           pictureService.base64ToBinary(invalidBase64); // Replace with actual way to get an instance
        });
    }

    @Test
    void whenBase64StringIsValidAndDoesNotHaveDataImagePrefix_thenShouldConvertToBinary() {
        // Arrange
        String validBase64WithoutPrefix = "iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==";
        byte[] expectedBytes = Base64.getDecoder().decode(validBase64WithoutPrefix);

        // Act
        Binary result = pictureService.base64ToBinary(validBase64WithoutPrefix); // Replace with actual way to get an instance

        // Assert
        assertArrayEquals(expectedBytes, result.getData());
    }

    @Test
    void whenPictureIdIsInvalid_thenThrowsException() {
        // Arrange
        String invalidId = "invalidPictureId";
        when(pictureRepository.findById(invalidId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            pictureService.getPictureById(invalidId);
        });

        assertEquals("Picture not found", exception.getMessage());
    }

    @Test
    void whenPictureHasNoPrefix_thenReturnBase64String(){
        //GIVEN
        String base64 = "iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==";

        //WHEN
        String result = pictureService.removePrefixFrom64(base64);

        //THEN
        Assertions.assertNotNull(result);
        Assertions.assertEquals(base64, result);
    }
}