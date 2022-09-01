package com.ciandt.summit.bootcamp2022.services;


import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PlayListService {
    public void saveMusicInPlaylist(MusicDto musicDto, String playlistId);
}

