package com.fox.cradle.features.stamp.service.stamp;

import com.fox.cradle.features.stamp.model.stamp.Stamp;

public interface IStampService
{

    //Crud methods Stamp
    Stamp getStampById(Long id);
    Stamp saveStamp(Stamp stamp);
    void deleteStamp(Stamp stamp);
    Stamp updateStamp(Stamp stamp);

}
