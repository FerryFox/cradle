package com.fox.cradle.features.stampSystem.service.template;

import com.fox.cradle.features.stampSystem.model.template.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long>
{
}
