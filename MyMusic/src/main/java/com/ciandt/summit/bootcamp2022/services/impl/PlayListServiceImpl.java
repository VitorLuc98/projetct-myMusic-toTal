package com.ciandt.summit.bootcamp2022.services.impl;

import com.ciandt.summit.bootcamp2022.controllers.PlaylistController;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;
import com.ciandt.summit.bootcamp2022.model.Music;
import com.ciandt.summit.bootcamp2022.model.Playlist;
import com.ciandt.summit.bootcamp2022.repositories.PlaylistRepository;
import com.ciandt.summit.bootcamp2022.services.MusicService;
import com.ciandt.summit.bootcamp2022.services.PlayListService;
import com.ciandt.summit.bootcamp2022.services.exceptions.MusicExistInPlaylistException;
import com.ciandt.summit.bootcamp2022.services.exceptions.MusicNotExistInPlaylistException;
import com.ciandt.summit.bootcamp2022.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayListServiceImpl implements PlayListService {

    private final PlaylistRepository playListRepository;
    private final MusicService musicService;
    private final ModelMapper modelMapper;
    private static final Logger log = LoggerFactory.getLogger(PlayListServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public PlaylistDto getPlaylistById(String id) {
        log.info("searching playlist by id = {}", id);
        var playlistEntity = playListRepository.findById(id);
        if (playlistEntity.isEmpty()){
            log.warn("Playlist of id {}, not found!", id);
            throw new ResourceNotFoundException("PlayList Not Found!");
        }
        return modelMapper.map(playlistEntity, PlaylistDto.class);
    }

    @Override
    public PlaylistDto addMusicToPlaylist(String playlistId, MusicDto musicDto) {
        Music music = modelMapper.map(musicService.getMusicById(musicDto.getId()), Music.class);
        Playlist playlist = modelMapper.map(getPlaylistById(playlistId), Playlist.class);

        log.info("checking if the song already exists in the playlist");
        checksMusicExistsInPlaylist(playlist, music);

        log.info("Adding the music in the playlist of id = {}", playlistId);
        playlist.getMusics().add(music);

        log.info("saving the playlist");
        playlist = playListRepository.save(playlist);

        return modelMapper.map(playlist, PlaylistDto.class);
    }

    @Override
    public PlaylistDto removeMusicFromPlaylist(String playlistId, String musicId) {
        Music music = modelMapper.map(musicService.getMusicById(musicId), Music.class);
        Playlist playlist = modelMapper.map(getPlaylistById(playlistId), Playlist.class);

        if (playlist.getMusics().stream().noneMatch(m -> m.getId().equals(music.getId()))){
            throw new MusicNotExistInPlaylistException("Music does not exist in the playlist!");
        }

        playlist.getMusics().removeIf(m -> m.getId().equals(musicId));

        playlist = playListRepository.save(playlist);

        return modelMapper.map(playlist, PlaylistDto.class);
    }

    private void checksMusicExistsInPlaylist(Playlist playlist, Music music){
        var isMusicExist = playlist.getMusics().stream()
                .anyMatch(m -> m.getId().equals(music.getId()) &&
                        m.getArtist().getId().equals(music.getArtist().getId()));

        if (isMusicExist){
            log.warn("The music already exists in the playlist");
            throw new MusicExistInPlaylistException("Music already exists in the playlist!");
        }
    }

}