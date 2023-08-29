package com.fox.cradle.features.user.services;

import com.fox.cradle.features.user.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long>
{
    Optional<Role> findByName(String name);
    //the UserRepository interface extends JpaRepository,
    //which provides the basic CRUD operations
}
