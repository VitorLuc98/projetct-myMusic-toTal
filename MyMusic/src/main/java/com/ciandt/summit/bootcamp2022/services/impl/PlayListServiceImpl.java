package com.ciandt.summit.bootcamp2022.services.impl;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;
import com.ciandt.summit.bootcamp2022.model.Music;
import com.ciandt.summit.bootcamp2022.model.Playlist;
import com.ciandt.summit.bootcamp2022.repositories.PlayListRepository;
import com.ciandt.summit.bootcamp2022.services.PlayListService;
import com.ciandt.summit.bootcamp2022.services.exceptions.EntityNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
@RequiredArgsConstructor
public class PlayListServiceImpl implements PlayListService {

    private PlayListRepository playListRepository;
    private MusicServiceImpl musicService;
    private ModelMapper modelMapper;



    public PlaylistDto getPlaylistById(String id) {

        var playlistEntity = playListRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("PlayList Not Found"));

        return modelMapper.map(playlistEntity, PlaylistDto.class);
    }

    @Override
    public void saveMusicInPlaylist(MusicDto musicDto, String playlistId) {

        Music music = modelMapper.map(musicService.getMusicById(musicDto.getId()), Music.class);
        Playlist playlist = modelMapper.map(getPlaylistById(playlistId), Playlist.class);

        playlist.getMusics().add(music);

        playListRepository.save(playlist);
    }


}