package com.fox.cradle.features.blog.service;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.blog.BlogMapping;
import com.fox.cradle.features.blog.model.BlogEntry;
import com.fox.cradle.features.blog.model.BlogEntryDTO;
import com.fox.cradle.features.picture.service.PictureService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final BlogMapping blogMapping;

    @Transactional
    public BlogEntryDTO saveBlog(BlogEntryDTO blogEntryDTO, AppUser appUser)
    {
        BlogEntry blogEntry = blogMapping.mapBlogDTOToEntity(blogEntryDTO, appUser);
        blogRepository.save(blogEntry);
        return blogMapping.mapToDTO(blogEntry);
    }

    public BlogEntry getBlog(Long id) {
        return blogRepository.findById(id).orElseThrow();
    }

    @Transactional
    public List<BlogEntryDTO> getLatestBlogEntries()
    {
        List<BlogEntry> blogEntries = blogRepository.findTop30ByOrderByCreatedDateDesc();

        return blogMapping.mapToDTOList(blogEntries);
    }
}
