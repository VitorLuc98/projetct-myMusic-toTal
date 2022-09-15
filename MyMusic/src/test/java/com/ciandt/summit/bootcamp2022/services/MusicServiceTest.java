package com.ciandt.summit.bootcamp2022.services;

import com.ciandt.summit.bootcamp2022.dto.ArtistDto;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.model.Artist;
import com.ciandt.summit.bootcamp2022.model.Music;
import com.ciandt.summit.bootcamp2022.repositories.MusicRepository;
import com.ciandt.summit.bootcamp2022.services.exceptions.ListIsEmptyException;
import com.ciandt.summit.bootcamp2022.services.exceptions.NameLenghtException;
import com.ciandt.summit.bootcamp2022.services.exceptions.ResourceNotFoundException;
import com.ciandt.summit.bootcamp2022.services.impl.MusicServiceImpl;
import org.hibernate.service.spi.InjectService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class MusicServiceTest {

    @Autowired
    MusicServiceImpl service;

    @Autowired
    ModelMapper modelMapper;

    @MockBean
    MusicRepository repository;

    private Music music;
    private MusicDto musicDTO;
    private Optional<Music> optionalMusic;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        music = new Music("1", "Animus", new Artist("2", "Monuments"));
        musicDTO = new MusicDto("1", "Animus", new ArtistDto("2", "Monuments"));
        optionalMusic = Optional.of(music);

        repository = mock(MusicRepository.class);
        service = new MusicServiceImpl(repository,modelMapper);
    }


    @Test
    void findByMusicOrArtistWhenReturnEmptyList(){
        Mockito.clearAllCaches();
        when(repository.findAll()).thenReturn(Collections.emptyList());

        var musicResponse = service.findByMusicOrArtist(null);

        System.out.println(">>>>>>>>> " + musicResponse.size());

        assertNotNull(musicResponse);
        assertEquals(0,musicResponse.size());
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

        var response = service.findByMusicOrArtist(filtro);

        assertNotNull(response);
        assertEquals(1,response.size());
        assertEquals(MusicDto.class,response.get(0).getClass());
    }

    @Test
    void findByMusicOrArtistShouldThrowListIsEmptyException(){
        var exception = assertThrows(
                ListIsEmptyException.class,() -> service.findByMusicOrArtist("JBK42"),
                "Exception not found");

        assertEquals("Couldn't find any artist or song with the given name",exception.getMessage());
        assertEquals(ListIsEmptyException.class,exception.getClass());
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
    void findByMusicOrArtistWhenNameLengthEqualsTo2AndMusicRepositoryMockReturnOneRecordThenReturnMusic(){
        when(repository.findAllByNameMusicOrNameArtist("JB")).thenReturn(List.of(music));

        var response = service.findByMusicOrArtist("JB");

        assertNotNull(response);
        assertEquals(1,response.size());
        assertEquals(MusicDto.class,response.get(0).getClass());
    }

    @Test
    void getMusicByIdShouldReturnAMusicDto(){
        when(repository.findById(anyString())).thenReturn(optionalMusic);

        var response = service.getMusicById("1");

        assertNotNull(response);
        assertEquals(optionalMusic.get().getId(),response.getId());
        assertEquals(MusicDto.class,response.getClass());
    }

    @Test
    void getMusicByIdShouldReturnResourceNotFoundException(){
        var exception = assertThrows(
                ResourceNotFoundException.class,() -> service.getMusicById(null),
                "Exception not found");

        assertEquals("Music not found!",exception.getMessage());
        assertEquals(ResourceNotFoundException.class,exception.getClass());
    }
}
