package com.fox.cradle.features.stampSystem.service.stamp;

import com.fox.cradle.features.stampSystem.model.stamp.Stamp;

public interface IStampService
{

    //Crud methods Stamp
    Stamp getStampById(Long id);
    Stamp saveStamp(Stamp stamp);
    void deleteStamp(Stamp stamp);
    Stamp updateStamp(Stamp stamp);

}
