package com.ciandt.summit.bootcamp2022.controllers;

import com.ciandt.summit.bootcamp2022.dto.UserDto;
import com.ciandt.summit.bootcamp2022.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService service;

    @GetMapping(value="/{userId}")
    public ResponseEntity<UserDto> findUserById(@PathVariable("userId") String userId){
        log.info("Searching user list, {}", UserController.class);
        var user = service.findUserById(userId);
        log.info("Returning o user list, {}", UserController.class);
        return ResponseEntity.ok(user);
    }
}
