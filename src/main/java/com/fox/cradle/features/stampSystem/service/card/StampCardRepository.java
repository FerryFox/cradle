package com.fox.cradle.features.stampSystem.service.card;

import com.fox.cradle.features.stampSystem.model.stampcard.StampCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StampCardRepository extends JpaRepository<StampCard, Long> {
    // Custom query methods if needed.
}