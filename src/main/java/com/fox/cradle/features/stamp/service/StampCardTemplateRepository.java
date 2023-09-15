package com.fox.cradle.features.stamp.service;

import com.fox.cradle.features.stamp.model.StampCardTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampCardTemplateRepository extends JpaRepository<StampCardTemplate, Long>
{
}
