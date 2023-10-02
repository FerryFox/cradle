package com.fox.cradle.features.stampSystem.service.stamp;

import com.fox.cradle.features.stampSystem.model.stamp.StampField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StampFieldRepository extends JpaRepository<StampField, Long>
{
    List<StampField> findByStampCardId(Long stampCardId);
}
