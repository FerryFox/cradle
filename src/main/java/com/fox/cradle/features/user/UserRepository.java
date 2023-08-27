package com.fox.cradle.features.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByUsername(String username);
    //the UserRepository interface extends JpaRepository,
    //which provides the basic CRUD operations
}
