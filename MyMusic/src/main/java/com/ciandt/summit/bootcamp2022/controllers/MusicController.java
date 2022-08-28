package com.ciandt.summit.bootcamp2022.controllers;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.services.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/musicas")
@RequiredArgsConstructor
public class MusicController {
    private final MusicService service;

    @GetMapping
    public ResponseEntity<List<MusicDto>> findByNameOrMusic(@RequestParam(required = false) String filtro){
        return ResponseEntity.ok(service.findByMusicOrArtist(filtro));
    }
}
