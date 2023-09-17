package com.fox.cradle.features.stamp.service;

import com.fox.cradle.features.stamp.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Long>
{
}
