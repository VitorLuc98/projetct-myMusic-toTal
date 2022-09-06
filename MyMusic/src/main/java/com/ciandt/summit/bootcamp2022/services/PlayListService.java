package com.ciandt.summit.bootcamp2022.services;


import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;

public interface PlayListService {

    PlaylistDto getPlaylistById(String id);
     PlaylistDto addMusicToPlaylist(String playlistId, MusicDto musicDto);
     void removeMusicFromPlaylist(String playlistId, String musicaId);
}

