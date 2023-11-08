package com.fox.cradle.features.blog.service;

import com.fox.cradle.features.blog.model.BlogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntry, Long>
{
    @Query(value = "SELECT * FROM blog_entry ORDER BY created_date DESC LIMIT 30", nativeQuery = true)
    List<BlogEntry> findTop30ByOrderByCreatedDateDesc();

    BlogEntry findTopByOrderByCreatedDateDesc();
}
