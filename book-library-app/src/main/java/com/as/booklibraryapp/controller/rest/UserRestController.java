package com.as.booklibraryapp.controller.rest;

import com.as.booklibraryapp.domain.Users;
import com.as.booklibraryapp.repository.JpaUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@Validated // request parameter if it is valid
@RestController
@RequestMapping("api/user")
public class UserRestController {

    private final JpaUserRepository jpaUserRepository;

    public UserRestController(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @GetMapping
    public Iterable<Users> findAll(){
        return jpaUserRepository.findAll();
    }

    @GetMapping("{id}")
    public Users findOne(@PathVariable Long id){
        return jpaUserRepository.findById(id)
                // if it is found, it will return value (User), if it isn't it will return exception
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User was not found")
                );
    }

    // it tells client that api successfully processed data that was sent, and user was created successfully
    @ResponseStatus(HttpStatus.CREATED) // 201 status
    // end point excepts only json, we tell api not to let anything that is not json
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Users save(@Valid @RequestBody Users user){
        // if user has id
        if(user.getId() != null){
            // 400 status, BAD REQUEST tells clients that something was wrong with the data that was sent, and they need correct
            // data it's self is not valid
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID must be left empty when creating a user");
        }

        if(jpaUserRepository.existsUsersByUsername(user.getUsername()))
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Can't insert user with same or already existed data");
        }


        return jpaUserRepository.save(user);
    }

    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT) // status 204, everything OK
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        // existsById is defined in interface that jpa user repository extend
        // if user doesn't exists by this id
        if(!jpaUserRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with provided id doesn't exists");
        }

        jpaUserRepository.deleteById(id);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("{id}/update-email")
    public Users updateEmail(@PathVariable Long id, @RequestParam @Email String email){
        final Users user = jpaUserRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "User with provided id doesn't exists")
                        );
        user.setEmail(email);
        return jpaUserRepository.save(user);
    }
}
