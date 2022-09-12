package com.ciandt.summit.bootcamp2022.controllers;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.UserTypeDto;
import com.ciandt.summit.bootcamp2022.services.PlayListService;
import com.ciandt.summit.bootcamp2022.services.UserTypeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/playlists")
@RequiredArgsConstructor
public class UserTypeController {

    private static final Logger log = LoggerFactory.getLogger(UserTypeController.class);

    private final UserTypeService service;

    @GetMapping(value="/{userId}")
    public ResponseEntity<Object> findUserById(@RequestParam(required = false) String id){
        log.info("Searching user list, {}", MusicController.class);
        var user = service.findUserById(id);
        log.info("Returning o user list, {}", UserTypeController.class);
        return ResponseEntity.ok(user);
    }
}
