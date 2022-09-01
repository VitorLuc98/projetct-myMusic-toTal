package com.ciandt.summit.bootcamp2022.services;


import com.ciandt.summit.bootcamp2022.dto.MusicDto;

public interface PlayListService {
    public void saveMusicInPlaylist(MusicDto musicDto, String playlistId);
}

