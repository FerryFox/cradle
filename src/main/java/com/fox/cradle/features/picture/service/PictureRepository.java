package com.fox.cradle.features.picture.service;

import com.fox.cradle.features.picture.model.Picture;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PictureRepository extends MongoRepository<Picture, String>
{

}
