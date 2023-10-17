package com.fox.cradle.features.stampsystem.service.stamp;

import com.fox.cradle.features.stampsystem.model.stamp.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StampRepository extends JpaRepository<Stamp, Long>
{
}
