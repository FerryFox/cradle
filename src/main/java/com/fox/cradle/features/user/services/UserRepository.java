package com.fox.cradle.features.user.services;

import com.fox.cradle.features.user.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long>
{
    //the UserRepository interface extends JpaRepository,
    //which provides the basic CRUD operations
    Optional<AppUser> findByUsername(String username);

}
