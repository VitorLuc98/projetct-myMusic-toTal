package com.ciandt.summit.bootcamp2022.services;

import com.ciandt.summit.bootcamp2022.dto.ArtistDto;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.model.Artist;
import com.ciandt.summit.bootcamp2022.model.Music;
import com.ciandt.summit.bootcamp2022.repositories.MusicRepository;
import com.ciandt.summit.bootcamp2022.services.exceptions.ListIsEmptyException;
import com.ciandt.summit.bootcamp2022.services.exceptions.NameLenghtException;
import com.ciandt.summit.bootcamp2022.services.impl.MusicServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class MusicServiceTest {

    @Autowired
    MusicServiceImpl service;

    @MockBean
    MusicRepository repository;

    Music music;
    MusicDto musicDTO;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        music = new Music("1","Animus", new Artist("2","Monuments"));

        musicDTO = new MusicDto("1","Animus", new ArtistDto("2","Monuments"));

    }

    @Test
    void findByMusicOrArtistShouldReturnAllMusicsWhenParameterIsNull(){
        when(repository.findAll()).thenReturn(List.of(music));

        var musicResponse = service.findByMusicOrArtist(null);

        assertNotNull(musicResponse);
        assertEquals(1,musicResponse.size());
        assertEquals(MusicDto.class,musicResponse.get(0).getClass());

    }

    @Test
    void findByMusicOrArtistShouldReturnMusicsWhenParameterIsValid(){
        var filtro = "Animus";
        when(repository.findAllByNameMusicOrNameArtist(filtro)).thenReturn(List.of(music));

        var musicResponse = service.findByMusicOrArtist(filtro);

        assertNotNull(musicResponse);
        assertEquals(1,musicResponse.size());
        assertEquals(MusicDto.class,musicResponse.get(0).getClass());

    }

    @Test
    void findByMusicOrArtistShouldThrowNameLenghtException(){
        var exception = assertThrows(
                NameLenghtException.class, () -> service.findByMusicOrArtist("B"),
                "Exception not found");

        assertEquals("The name should have more than 2 characters",exception.getMessage());
        assertEquals(NameLenghtException.class,exception.getClass());
    }

    @Test
    void findByMusicOrArtistShouldThrowListIsEmptyException(){
        var exception = assertThrows(
                ListIsEmptyException.class,() -> service.findByMusicOrArtist("JBK42"),
                "Exception not found");

        assertEquals("Couldn't find any artist or song with the given name",exception.getMessage());
        assertEquals(ListIsEmptyException.class,exception.getClass());

    }

}
