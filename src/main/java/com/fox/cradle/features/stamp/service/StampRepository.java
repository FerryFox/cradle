package com.fox.cradle.features.stamp.service;

import com.fox.cradle.features.stamp.model.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StampRepository extends JpaRepository<Stamp, Long>
{
}
