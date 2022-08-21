package com.as.booklibraryapp.service;

import com.as.booklibraryapp.domain.Users;
import com.as.booklibraryapp.repository.JpaUserRepository;
import com.as.booklibraryapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JpaUserRepository jpaUserRepository;


    public UserService(UserRepository userRepository, JpaUserRepository jpaUserRepository) {
        this.userRepository = userRepository;
        this.jpaUserRepository = jpaUserRepository;
    }

    public Optional<Users> save(Users user){
        return userRepository.save(user);
    }

    public Set<Users> findByEnteredQueryData(Users user){
        return jpaUserRepository.findAllByNameContainingIgnoreCase(user.getName());
    }

    public Set<Users> findAllUsers(){
        return userRepository.findAll();
    }
}
