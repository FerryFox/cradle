package com.fox.cradle.features.stamp.service;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.stamp.model.Stamp;
import com.fox.cradle.features.stamp.model.StampCard;

public interface IStampService
{

    //Crud methods Stamp
    Stamp getStampById(Long id);
    Stamp saveStamp(Stamp stamp);
    void deleteStamp(Stamp stamp);
    Stamp updateStamp(Stamp stamp);

}
