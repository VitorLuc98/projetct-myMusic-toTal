package com.ciandt.summit.bootcamp2022.controllers;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.services.MusicService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
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
    private Logger log = LoggerFactory.getLogger(MusicController.class);
    private final MusicService service;

    @ApiOperation(value = "Returns a list of music by filter")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the musics list"),
            @ApiResponse(code = 204, message = "There are no songs to return"),
            @ApiResponse(code = 401, message = "Do not have permission to access this resource"),
            @ApiResponse(code = 500, message = "Unexpected Exception")
    })
    @GetMapping
    @Cacheable("findByNameOrMusic")
    public ResponseEntity<List<MusicDto>> findByNameOrMusic(@RequestParam(required = false) String filtro){
        log.info("Searching musics list, {}", MusicController.class);
        var musics = service.findByMusicOrArtist(filtro);
        log.info("Returning the musics list, {}", MusicController.class);
        System.out.println("teste");
        return ResponseEntity.ok(musics);
    }
}
