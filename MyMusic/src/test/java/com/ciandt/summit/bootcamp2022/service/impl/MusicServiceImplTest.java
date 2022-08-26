package com.ciandt.summit.bootcamp2022.service.impl;

import com.ciandt.summit.bootcamp2022.dto.ArtistDto;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.model.Artist;
import com.ciandt.summit.bootcamp2022.model.Music;
import com.ciandt.summit.bootcamp2022.repository.MusicRepository;
import com.ciandt.summit.bootcamp2022.service.exceptions.ListIsEmptyException;
import com.ciandt.summit.bootcamp2022.service.exceptions.NameLenghtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class MusicServiceImplTest {

    @Autowired
    MusicServiceImpl service;

    @Mock
    ModelMapper modelMapper;

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
    void whenFindByMusicOrArtistParameterIsEmpty(){
        when(repository.findAll()).thenReturn(List.of(music));

        var musicResponse = service.findByMusicOrArtist(null);

        assertNotNull(musicResponse);
        assertEquals(1,musicResponse.size());
        assertEquals(MusicDto.class,musicResponse.get(0).getClass());

    }

    @Test
    void whenFindByMusicOrArtistThrowNameLenghtException(){
        var exception = assertThrows(
                NameLenghtException.class, () -> service.findByMusicOrArtist("B"),
                "Exception not found");

        assertEquals("The name should have more than 2 characters",exception.getMessage());
        assertEquals(NameLenghtException.class,exception.getClass());
    }

    @Test
    void whenFindByMusicOrArtistThrowListIsEmptyException(){
        var exception = assertThrows(
                ListIsEmptyException.class,() -> service.findByMusicOrArtist("JBK42"),
                "Exception not found");

        assertEquals("Couldn't find any artist or song with the given name",exception.getMessage());
        assertEquals(ListIsEmptyException.class,exception.getClass());

    }

}