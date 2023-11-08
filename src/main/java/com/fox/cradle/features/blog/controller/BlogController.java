package com.fox.cradle.features.blog.controller;

import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.service.AppUserService;
import com.fox.cradle.features.blog.model.BlogEntryDTO;
import com.fox.cradle.features.blog.service.BlogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blog/")
@RequiredArgsConstructor
public class BlogController
{
    private final AppUserService appUserService;
    private final BlogService blogService;
    private final JwtService jwtService;

    @PostMapping("create")
    public ResponseEntity<BlogEntryDTO> createBlogEntry(@RequestBody BlogEntryDTO blogEntryDTO , HttpServletRequest httpServletRequest)
    {

        Optional<AppUser> appUser =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (appUser.isEmpty()) return ResponseEntity.badRequest().build();

        BlogEntryDTO result =  blogService.saveBlog(blogEntryDTO, appUser.get());
        return ResponseEntity.ok(result);
    }

    @GetMapping("get-latest")
    public ResponseEntity<List<BlogEntryDTO>> getLatestBlogEntries()
    {
        List<BlogEntryDTO> result = blogService.getLatestBlogEntries();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/last")
    public ResponseEntity<List<BlogEntryDTO>> getLastBlogEntry()
    {
        BlogEntryDTO result = blogService.getLastBlogEntry();
        List<BlogEntryDTO> list = List.of(result);
        return ResponseEntity.ok(list);
    }
}
