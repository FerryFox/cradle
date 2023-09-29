package com.fox.cradle.features.stampSystem.service.stamp;

import com.fox.cradle.features.stampSystem.model.stamp.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StampRepository extends JpaRepository<Stamp, Long>
{
}
