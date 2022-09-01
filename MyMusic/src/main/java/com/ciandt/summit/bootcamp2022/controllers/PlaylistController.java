package com.ciandt.summit.bootcamp2022.controllers;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;
import com.ciandt.summit.bootcamp2022.services.PlayListService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private Logger log = LoggerFactory.getLogger(PlaylistController.class);
    private final PlayListService service;

    @ApiOperation(value = "Return playlist by playlistId")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Returns the list"),
            @ApiResponse(code = 400, message = "Music does not exist in the database"),
            @ApiResponse(code = 400, message = "Playlist does not exist in the database"),
            @ApiResponse(code = 400, message = "Payload body does not conform to documentation"),
            @ApiResponse(code = 500, message = "Unexpected Exception")
    })
    @PostMapping("{playlistId}/musicas")
    public ResponseEntity<PlaylistDto> addMusicInPlaylist(@PathVariable("playlistId") String playlistId, @RequestBody MusicDto music){
        log.info("Adding Music to Playlist");
        var playlist = service.addMusicToPlaylist(playlistId, music);
        log.info("the song has been added to the playlist");
        return new ResponseEntity<>(playlist, HttpStatus.CREATED);
    }
}
