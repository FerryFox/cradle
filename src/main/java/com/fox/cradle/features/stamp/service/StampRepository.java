package com.fox.cradle.features.stamp.service;

import com.fox.cradle.features.stamp.model.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampRepository extends JpaRepository<Stamp, Long>
{
}
