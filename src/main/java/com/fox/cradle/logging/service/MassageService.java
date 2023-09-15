package com.fox.cradle.logging.service;

import com.fox.cradle.logging.model.Massage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MassageService
{
    private final MassageReposetory massageReposetory;

    public MassageService(MassageReposetory massageReposetory)
    {
        this.massageReposetory = massageReposetory;
        Massage massage = new Massage();
        massage.setMessage("application started");
        saveMassage(massage);
    }

    public Massage saveMassage(Massage massage)
    {
        return massageReposetory.save(massage);
    }

    public List<Massage> getAllMassages()
    {
        return massageReposetory.findAll();
    }
}
