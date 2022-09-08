package com.ciandt.summit.bootcamp2022.services.impl;

import com.ciandt.summit.bootcamp2022.dto.ArtistDto;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;
import com.ciandt.summit.bootcamp2022.model.Artist;
import com.ciandt.summit.bootcamp2022.model.Music;
import com.ciandt.summit.bootcamp2022.model.Playlist;
import com.ciandt.summit.bootcamp2022.repositories.MusicRepository;
import com.ciandt.summit.bootcamp2022.repositories.PlaylistRepository;
import com.ciandt.summit.bootcamp2022.services.exceptions.ListIsEmptyException;
import com.ciandt.summit.bootcamp2022.services.exceptions.MusicExistInPlaylistException;
import com.ciandt.summit.bootcamp2022.services.exceptions.MusicNotExistInPlaylistException;
import com.ciandt.summit.bootcamp2022.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PlayListServiceImplTest {

    @Autowired
    PlayListServiceImpl service;

    @MockBean
    MusicServiceImpl musicService;

    @MockBean
    PlaylistRepository repository;

    private PlaylistDto playlistDto;
    private Playlist emptyPlaylist;
    private Playlist playlist;
    private MusicDto musicDto;
    private Music music;
    private Optional<Playlist> optionalPlaylist;
    private Optional<Playlist> emptyOptionalPlaylist;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        music = new Music("1","Animus", new Artist("2","Monuments"));
        musicDto = new MusicDto("1","Animus", new ArtistDto("2","Monuments"));
        emptyPlaylist = new Playlist("1",List.of());
        playlist = new Playlist("1",List.of(music));
        playlistDto = new PlaylistDto("1", List.of(musicDto));
        optionalPlaylist = Optional.of(playlist);
        emptyOptionalPlaylist = Optional.of(emptyPlaylist);
    }

    @Test
    void getPlaylistByIdShouldReturnResourceNotFoundException() {
        var exception = assertThrows(
                ResourceNotFoundException.class, () -> service.getPlaylistById(null),
                "Exception not found");

        assertEquals("PlayList Not Found!",exception.getMessage());
        assertEquals(ResourceNotFoundException.class,exception.getClass());
    }

    @Test
    void getPlaylistByIdShouldReturnPlaylistDto() {
        when(repository.findById(anyString())).thenReturn(optionalPlaylist);

        var playslistResponse = service.getPlaylistById("1");

        assertNotNull(playslistResponse);
        assertEquals(PlaylistDto.class,playslistResponse.getClass());
    }

    @Test
    void addMusicToPlaylistWhenPlaylistIsEmpty() {
        when(musicService.getMusicById(anyString())).thenReturn(musicDto);
        when(repository.findById(anyString())).thenReturn(emptyOptionalPlaylist);
        when(repository.save(any())).thenReturn(playlist);

        var playlistResponse = service.addMusicToPlaylist("1",musicDto);

        assertNotNull(playlistResponse);
        assertEquals("1",playlistResponse.getId());
        assertEquals(playlistDto.getMusics().get(0).getClass(),playlistResponse.getMusics().get(0).getClass());
        assertEquals(playlistDto.getMusics().size(),playlistResponse.getMusics().size());
    }

    @Test
    void addMusicToPlaylistShouldReturnMusicExistInPlaylistException() {
        when(musicService.getMusicById(anyString())).thenReturn(musicDto);
        when(repository.findById(anyString())).thenReturn(optionalPlaylist);

        when(repository.save(any())).thenReturn(playlist);

        var exception = assertThrows(
                MusicExistInPlaylistException.class,() -> service.addMusicToPlaylist("1",musicDto),
                "Exception not found");

        assertEquals("Music already exists in the playlist!",exception.getMessage());
        assertEquals(MusicExistInPlaylistException.class,exception.getClass());
    }

    @Test
    void removeMusicFromPlaylist() {
        when(musicService.getMusicById(anyString())).thenReturn(musicDto);
        when(repository.findById(anyString())).thenReturn(optionalPlaylist);

        service.removeMusicFromPlaylist("1","1");

        verify(repository,times(1)).save(Mockito.any(Playlist.class));
    }

    @Test
    void removeMusicFromPlaylistShouldReturnMusicNotExistInPlaylistException(){
        when(musicService.getMusicById(anyString())).thenReturn(musicDto);
        when(repository.findById(anyString())).thenReturn(emptyOptionalPlaylist);

        var exception = assertThrows(
                MusicNotExistInPlaylistException.class,() -> service.removeMusicFromPlaylist("1","1"),
                "Exception not found");

        assertEquals("Music does not exist in the playlist!",exception.getMessage());
        assertEquals(MusicNotExistInPlaylistException.class,exception.getClass());
    }
}