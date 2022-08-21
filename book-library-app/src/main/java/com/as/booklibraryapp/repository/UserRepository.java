package com.as.booklibraryapp.repository;

import com.as.booklibraryapp.domain.Users;

import java.util.Optional;
import java.util.Set;

public interface UserRepository {

    Set<Users> findAll();

    Optional<Users> findById(Long id);

    Optional<Users> findByUsername(String username);

    Optional<Users> save(Users user);

    void deleteById(Long id);
}
