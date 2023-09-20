package com.fox.cradle.features.picture.service;

import com.fox.cradle.features.picture.model.Picture;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends MongoRepository<Picture, String>
{

}
