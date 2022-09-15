package com.ciandt.summit.bootcamp2022.services.impl.integration;

import com.ciandt.summit.bootcamp2022.dto.ArtistDto;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;
import com.ciandt.summit.bootcamp2022.repositories.PlaylistRepository;
import com.ciandt.summit.bootcamp2022.services.exceptions.MusicNotExistInPlaylistException;
import com.ciandt.summit.bootcamp2022.services.exceptions.ResourceNotFoundException;
import com.ciandt.summit.bootcamp2022.services.impl.PlayListServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SuppressWarnings("TestFailedLine")
@SpringBootTest
@Transactional
public class PlayListServiceImplIT {
    @Autowired
    private PlayListServiceImpl playListService;
    @Autowired
    private PlaylistRepository playlistRepository;
    private MusicDto musicDto;

    private String existingPlayListId="0ca61061-1fa8-418e-8e17-8234fb38c7a8";
    private String existingMusicId="031da0c6-40d8-4b5c-aecf-e3a8b769be64";


    @BeforeEach
    void setup(){
        musicDto= new MusicDto("0f72f95a-0ae9-4230-924c-4483c8688107","Flying Home",
                new ArtistDto("d1a992dd-34fd-4415-9fde-e39df325b29e","Chuck Berry"));
    }
    @Test
    public void deleteshouldDeleteResourceWhenIdExists() {
     playListService.removeMusicFromPlaylist(existingPlayListId,existingMusicId);
        assertFalse(playlistRepository.findById(existingPlayListId).get().getMusics().contains(existingMusicId));
    }
    @Test
    public void deleteshouldThrowResourceNotFoundExceptionWhenIdDoesntExist()  {
      Assertions.assertThrows(ResourceNotFoundException.class, ()->  {
          playListService.removeMusicFromPlaylist("1","1");
      });
    }
    @Test
    public void getShouldReturnPlayListWhenIdExists(){
       PlaylistDto playlist= playListService.getPlaylistById(existingPlayListId);
       assertEquals(existingPlayListId,playlist.getId());
    }
    @Test
    public void getShouldReturnThrowMusicNotExistinInPlayListWhenIdDoesExists(){
       Assertions.assertThrows(ResourceNotFoundException.class, ()-> {
           playListService.getPlaylistById("1");
        });
    }
    @Test
    public void addMusicInPlayListAndShouldReturnPlayListDto(){
        var saved = playListService.addMusicToPlaylist(existingPlayListId,musicDto);
        assertEquals(existingPlayListId,saved.getId());
        assertTrue(saved.getMusics().stream().
                anyMatch( music-> music.getId() == "0f72f95a-0ae9-4230-924c-4483c8688107"));
   }
    @Test
    public void addMusicInPlayListAndShouldReturnThrowNotFoundPlayListDto(){
         playListService.addMusicToPlaylist(existingPlayListId,musicDto);
        Assertions.assertThrows(ResourceNotFoundException.class, ()->  {
            playListService.addMusicToPlaylist("1",musicDto);
          //  playListService.addMusicToPlaylist(existingPlayListId,)

        });


    }


}