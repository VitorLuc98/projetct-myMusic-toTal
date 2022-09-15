package com.ciandt.summit.bootcamp2022.services;

import com.ciandt.summit.bootcamp2022.dto.*;
import com.ciandt.summit.bootcamp2022.model.Artist;
import com.ciandt.summit.bootcamp2022.model.Music;
import com.ciandt.summit.bootcamp2022.model.Playlist;
import com.ciandt.summit.bootcamp2022.repositories.PlaylistRepository;
import com.ciandt.summit.bootcamp2022.services.exceptions.*;
import com.ciandt.summit.bootcamp2022.services.impl.MusicServiceImpl;
import com.ciandt.summit.bootcamp2022.services.impl.PlayListServiceImpl;
import com.ciandt.summit.bootcamp2022.services.impl.UserServiceImpl;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class PlayListServiceTest {

    @Autowired
    PlayListServiceImpl service;

    @MockBean
    MusicServiceImpl musicService;

    @MockBean
    UserServiceImpl userService;

    @MockBean
    PlaylistRepository repository;

    private PlaylistDto playlistDto;
    private PlaylistDto playlistDto2;
    private Playlist playlist2;
    private Playlist emptyPlaylist;
    private Playlist playlist;
    private MusicDto musicDto;
    private UserDto userComumDto, userPremiumDto;
    private Music music;
    private Optional<Playlist> optionalPlaylist;
    private Optional<Playlist> emptyOptionalPlaylist;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        music = new Music("1", "Animus", new Artist("2", "Monuments"));
        musicDto = new MusicDto("1", "Animus", new ArtistDto("2", "Monuments"));
        emptyPlaylist = new Playlist("2", List.of());
        playlist = new Playlist("1", List.of(music));
        playlist2 = new Playlist("2", List.of(music));
        playlistDto = new PlaylistDto("1", List.of(musicDto));
        playlistDto2 = new PlaylistDto("2", List.of(musicDto, musicDto, musicDto, musicDto, musicDto));
        userComumDto = new UserDto("1", "Emerson", playlistDto2, new UserTypeDto("1", "Comum"));
        userPremiumDto = new UserDto("2", "Vitor", playlistDto2, new UserTypeDto("2", "Premium"));
        optionalPlaylist = Optional.of(playlist);
        emptyOptionalPlaylist = Optional.of(emptyPlaylist);
    }

    @Test
    void getPlaylistByIdShouldReturnResourceNotFoundException() {
        var exception = assertThrows(
                ResourceNotFoundException.class, () -> service.getPlaylistById(null),
                "Exception not found");

        assertEquals("PlayList Not Found!", exception.getMessage());
        assertEquals(ResourceNotFoundException.class, exception.getClass());
    }

    @Test
    void getPlaylistByIdShouldReturnPlaylistDto() {
        when(repository.findById(anyString())).thenReturn(optionalPlaylist);

        var playslistResponse = service.getPlaylistById("1");

        assertNotNull(playslistResponse);
        assertEquals(PlaylistDto.class, playslistResponse.getClass());
    }

    @Test
    void addMusicToPlaylistWhenPlaylistIsEmpty() {
        when(musicService.getMusicById(anyString())).thenReturn(musicDto);
        when(repository.findById(anyString())).thenReturn(emptyOptionalPlaylist);
        when(repository.save(any())).thenReturn(playlist);

        var playlistResponse = service.addMusicToPlaylist("1", musicDto);

        assertNotNull(playlistResponse);
        assertEquals("1", playlistResponse.getId());
        assertEquals(playlistDto.getMusics().get(0).getClass(), playlistResponse.getMusics().get(0).getClass());
        assertEquals(playlistDto.getMusics().size(), playlistResponse.getMusics().size());
    }

    @Test
    void addMusicToPlaylistShouldReturnMusicExistInPlaylistException() {
        when(musicService.getMusicById(anyString())).thenReturn(musicDto);
        when(repository.findById(anyString())).thenReturn(optionalPlaylist);

        when(repository.save(any())).thenReturn(playlist);

        var exception = assertThrows(
                MusicExistInPlaylistException.class, () -> service.addMusicToPlaylist("1", musicDto),
                "Exception not found");

        assertEquals("Music already exists in the playlist!", exception.getMessage());
        assertEquals(MusicExistInPlaylistException.class, exception.getClass());
    }

    @Test
    void removeMusicFromPlaylist() {
        when(musicService.getMusicById(anyString())).thenReturn(musicDto);
        when(repository.findById(anyString())).thenReturn(optionalPlaylist);

        service.removeMusicFromPlaylist("1", "1");

        verify(repository, times(1)).save(Mockito.any(Playlist.class));
    }

    @Test
    void removeMusicFromPlaylistShouldReturnMusicNotExistInPlaylistException() {
        when(musicService.getMusicById(anyString())).thenReturn(musicDto);
        when(repository.findById(anyString())).thenReturn(emptyOptionalPlaylist);

        var exception = assertThrows(
                MusicNotExistInPlaylistException.class, () -> service.removeMusicFromPlaylist("1", "1"),
                "Exception not found");

        assertEquals("Music does not exist in the playlist!", exception.getMessage());
        assertEquals(MusicNotExistInPlaylistException.class, exception.getClass());
    }

    @Test
    void userAddMusicToPlaylistWhenPlaylistIsEmpty() {
        when(musicService.getMusicById(anyString())).thenReturn(musicDto);
        when(userService.findUserById(anyString())).thenReturn(userPremiumDto);
        when(repository.findById(anyString())).thenReturn(emptyOptionalPlaylist);
        when(repository.save(any())).thenReturn(playlist2);

        var playlistResponse = service.userAddMusicToPlaylist("2", "2", musicDto);

        assertNotNull(playlistResponse);
        assertEquals("2", playlistResponse.getId());
        assertEquals(playlistDto.getMusics().get(0).getClass(), playlistResponse.getMusics().get(0).getClass());
        assertEquals(playlistDto.getMusics().size(), playlistResponse.getMusics().size());
    }

    @Test
    void userAddMusicToPlaylistShouldThrowsPlaylistIsNotTheUser() {
        when(musicService.getMusicById(anyString())).thenReturn(musicDto);
        when(userService.findUserById(anyString())).thenReturn(userPremiumDto);
        when(repository.findById(anyString())).thenReturn(Optional.of(playlist));

        var exception = assertThrows(
                PlaylistIsNotTheUserException.class, () -> service.userAddMusicToPlaylist("1", "2", musicDto),
                "Exception not found");

        assertEquals("Playlist does not belong to this user", exception.getMessage());
        assertEquals(PlaylistIsNotTheUserException.class, exception.getClass());
    }

    @Test
    void userAddMusicToPlaylistShouldThrowsMusicExistInPlaylistException() {
        when(musicService.getMusicById(anyString())).thenReturn(musicDto);
        when(userService.findUserById(anyString())).thenReturn(userPremiumDto);
        when(repository.findById(anyString())).thenReturn(Optional.of(playlist2));

        var exception = assertThrows(
                MusicExistInPlaylistException.class, () -> service.userAddMusicToPlaylist("2", "2", musicDto),
                "Exception not found");

        assertEquals("Music already exists in the playlist!", exception.getMessage());
        assertEquals(MusicExistInPlaylistException.class, exception.getClass());
    }

    @Test
    void userAddMusicToPlaylistShouldThrowsMusicLimitAchievedException() {
        MusicDto newMusicDto = new MusicDto("2", "Regenerate", new ArtistDto("1", "Monuments"));

        when(musicService.getMusicById(anyString())).thenReturn(newMusicDto);
        when(userService.findUserById(anyString())).thenReturn(userComumDto);
        when(repository.findById(anyString())).thenReturn(Optional.of(playlist2));

        var exception = assertThrows(
                MusicLimitAchievedException.class, () -> service.userAddMusicToPlaylist("2", "1", newMusicDto),
                "Exception not found");

        assertEquals("You have reached the maximum number of songs in your playlist. " +
                "To add more songs, purchase the premium plan", exception.getMessage());
        assertEquals(MusicLimitAchievedException.class, exception.getClass());
    }

}
