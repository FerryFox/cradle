package com.fox.cradle.features.stampsystem.service.stamp;

import com.fox.cradle.features.stampsystem.model.stamp.StampField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StampFieldRepository extends JpaRepository<StampField, Long>
{
    List<StampField> findByStampCardId(Long stampCardId);
}
