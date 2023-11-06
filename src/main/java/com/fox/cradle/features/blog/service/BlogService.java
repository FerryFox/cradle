package com.fox.cradle.features.blog.service;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.blog.BlogMapping;
import com.fox.cradle.features.blog.model.BlogEntry;
import com.fox.cradle.features.blog.model.BlogEntryDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final BlogMapping blogMapping;

    @Transactional
    public BlogEntryDTO saveBlog(BlogEntryDTO blogEntryDTO, AppUser appUser)
    {
        try {
            BlogEntry blogEntry = blogMapping.mapBlogDTOToEntity(blogEntryDTO, appUser);

            blogRepository.save(blogEntry);

            return blogMapping.mapToDTO(blogEntry);
        }
        catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Content is too long");
        }
    }

    @Transactional
    public List<BlogEntryDTO> getLatestBlogEntries()
    {
        List<BlogEntry> blogEntries = blogRepository.findTop30ByOrderByCreatedDateDesc();

        return blogMapping.mapToDTOList(blogEntries);
    }
}
