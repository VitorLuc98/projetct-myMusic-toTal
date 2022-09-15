package com.ciandt.summit.bootcamp2022.controllers;

import com.ciandt.summit.bootcamp2022.dto.UserDto;
import com.ciandt.summit.bootcamp2022.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService service;

    @ApiOperation(value = "Search user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "returns user by id and his playlist with his songs"),
            @ApiResponse(code = 400, message = "User does not exist in database,"),
            @ApiResponse(code = 401, message = "Do not have permission to access this resource"),
    })
    @GetMapping(value="/{userId}")
    public ResponseEntity<UserDto> findUserById(@PathVariable("userId") String userId){
        log.info("Searching user list, {}", UserController.class);
        var user = service.findUserById(userId);
        log.info("Returning o user list, {}", UserController.class);
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a user"),
            @ApiResponse(code = 401, message = "Do not have permission to access this resource"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserDto userDto){
        log.info("saving the user, {}", UserController.class);
        var user = service.saveUser(userDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(userDto.getId()).toUri();
        log.info("Returning the user already saved, {}", UserController.class);
        return ResponseEntity.created(uri).body(user);
    }
}
