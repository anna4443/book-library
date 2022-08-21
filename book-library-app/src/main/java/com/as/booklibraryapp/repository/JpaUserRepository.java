package com.as.booklibraryapp.repository;

import com.as.booklibraryapp.domain.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface JpaUserRepository extends CrudRepository<Users, Long> {

    Set<Users> findAll();

    // findAll is operation, Name is column, Containing represents keyword like equal, or containing
    Set<Users> findAllByNameContainingIgnoreCase(String name);

    boolean existsUsersByUsername(String username);
}
