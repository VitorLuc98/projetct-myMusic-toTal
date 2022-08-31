package com.ciandt.summit.bootcamp2022.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    @GetMapping
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("deu");
    }
}
