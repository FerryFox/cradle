package com.fox.cradle.features.appuser.service;

import com.fox.cradle.features.appuser.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long>
{
    @Query("SELECT u FROM AppUser u WHERE u.appUserEmail = ?1")
    Optional<AppUser> findByEmail(String email);
}
