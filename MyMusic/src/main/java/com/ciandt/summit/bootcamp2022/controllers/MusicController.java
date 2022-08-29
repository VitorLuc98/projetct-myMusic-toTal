package com.ciandt.summit.bootcamp2022.controllers;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.services.MusicService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Returns a list of music by filter")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the musics list"),
            @ApiResponse(code = 204, message = "There are no songs to return"),
            @ApiResponse(code = 401, message = "Do not have permission to access this resource"),
            @ApiResponse(code = 500, message = "Unexpected Exception")
    })
    @GetMapping
    public ResponseEntity<List<MusicDto>> findByNameOrMusic(@RequestParam(required = false) String filtro){
        return ResponseEntity.ok(service.findByMusicOrArtist(filtro));
    }


}
