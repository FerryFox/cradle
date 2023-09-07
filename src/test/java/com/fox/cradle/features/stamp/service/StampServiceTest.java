package com.fox.cradle.features.stamp.service;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.stamp.model.Stamp;
import com.fox.cradle.features.stamp.model.StampCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class StampServiceTest
{
    @Autowired
    private IStampService stampService;

    @Autowired
    private StampRepository stampRepository;

    @Test
    public void stampACardSavesAReferenceToTheStampTest() {
        // Setup
        StampCard card = stampService.getStampCardById(1L);
        Stamp stamp = new Stamp();
        stamp.setName("Stamp 1");
        stamp.setAppUser(new AppUser());
        Stamp savedStamp = stampRepository.save(stamp);

        // Action
        StampCard stampedCard = stampService.stampACard(card, savedStamp);


        // Verification
        List<Stamp> stampsOnCard = stampedCard.getStamps();
        Assertions.assertNotNull(stampsOnCard, "Stamps list on card should not be null.");
        Assertions.assertTrue(stampsOnCard.contains(savedStamp), "Stamped card should contain the saved stamp.");
        Assertions.assertEquals(1, stampsOnCard.size(), "Stamped card should have only one stamp.");
    }
}
