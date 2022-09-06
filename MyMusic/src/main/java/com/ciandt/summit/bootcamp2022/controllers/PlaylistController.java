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

import javax.validation.Valid;

@RestController
@RequestMapping("api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private Logger log = LoggerFactory.getLogger(PlaylistController.class);
    private final PlayListService service;

    @ApiOperation(value = "Add a new music to the playlist")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Return to playlist with added music"),
            @ApiResponse(code = 400, message = "Music or Playlist does not exist in the database, or Music already exists in the playlist, " +
                    "or Request body not populating correctly"),
            @ApiResponse(code = 401, message = "No permission to access this feature")
    })
    @PostMapping("{playlistId}/musicas")
    public ResponseEntity<PlaylistDto> addMusicInPlaylist(@PathVariable("playlistId") String playlistId, @Valid @RequestBody MusicDto music) {
        log.info("Adding Music to Playlist");
        var playlist = service.addMusicToPlaylist(playlistId, music);
        log.info("the song has been added to the playlist");
        return new ResponseEntity<>(playlist, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Remove a music from the playlist")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Music successfully removed"),
            @ApiResponse(code = 400, message = "Music or Playlist does not exist in database, or Music does not exist in playlist"),
            @ApiResponse(code = 401, message = "No permission to access this feature")
    })
    @PutMapping("{playlistId}/musicas/{musicId}")
    public ResponseEntity<Void> removeMusicInPlaylist(@PathVariable("playlistId") String playlistId, @PathVariable("musicId") String musicId) {
        service.removeMusicFromPlaylist(playlistId, musicId);
        return ResponseEntity.noContent().build();
    }

}
