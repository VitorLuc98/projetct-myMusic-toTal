package com.ciandt.summit.bootcamp2022.controllers;

import com.ciandt.summit.bootcamp2022.controllers.exception.ResourceExceptionHandler;
import com.ciandt.summit.bootcamp2022.dto.ArtistDto;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.services.MusicService;
import com.ciandt.summit.bootcamp2022.services.exceptions.NameLenghtException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MusicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MusicService service;

    @Autowired
    private MusicController musicController;

    private ArtistDto artist1;
    private MusicDto music1;
    private ArtistDto artist2;
    private MusicDto music2;
    private ArtistDto artist3;
    private MusicDto music3;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(musicController).setControllerAdvice(new ResourceExceptionHandler()).build();

        artist1 = new ArtistDto("1", "Post Malone");
        music1 = new MusicDto("1", "Take What You Want", artist1);

        artist2 = new ArtistDto("2", "The Weeknd");
        music2 = new MusicDto("2", "Save Your Tears", artist2);

        artist3 = new ArtistDto("3", "Katy Perry");
        music3 = new MusicDto("3", "Thinking of You", artist3);
    }

    @Test
    public void findByNameOrMusicShouldReturnListMusicsWhenExistFilter() throws Exception {
        var musics = List.of(music1, music2, music3);
        var filtro = "You";

        when(service.findByMusicOrArtist(filtro)).thenReturn(musics);

        var response = musicController.findByNameOrMusic(filtro);

        assertNotNull(response);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(musics.size(),response.getBody().size());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(musics.get(0).getId(),response.getBody().get(0).getId());

    }

//    @Test
//    public void findByNameOrMusicShouldReturnListMusicsWhenExistFilter() throws Exception {
//        var musics = List.of(music1, music2, music3);
//        var filtro = "You";
//
//        when(service.findByMusicOrArtist(filtro)).thenReturn(musics);
//
//        ResultActions result =
//                mockMvc.perform(get("/api/musicas")
//                        .param("filter", filtro)
//                        .accept(MediaType.APPLICATION_JSON));
//
//        result.andExpect(status().isOk());
//        result.andExpect(jsonPath("$").isNotEmpty());
//        result.andExpect(jsonPath("$", hasSize(3)));
//    }

    @Test
    public void findByNameOrMusicShouldReturnListMusicsWhenExistFilterIgnoreCase() throws Exception {
        var musics = List.of(music3);
        var filtro = "kAtY";

        when(service.findByMusicOrArtist(filtro)).thenReturn(musics);

        var response = musicController.findByNameOrMusic(filtro);

        assertNotNull(response);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(musics.size(),response.getBody().size());
        assertEquals(ResponseEntity.class, response.getClass());

    }

//    @Test
//    public void findByNameOrMusicShouldReturnListMusicsWhenExistFilterIgnoreCase() throws Exception {
//        var musics = List.of(music3);
//        var filtro = "kAtY";
//
//        when(service.findByMusicOrArtist(filtro)).thenReturn(musics);
//
//        ResultActions result =
//                mockMvc.perform(get("/api/musicas")
//                        .param("filter", filtro)
//                        .accept(MediaType.APPLICATION_JSON));
//
//        result.andExpect(status().isOk());
//        result.andExpect(jsonPath("$").isNotEmpty());
//        result.andExpect(jsonPath("$", hasSize(1)));
//    }

    @Test
    public void findByNameOrMusicShouldReturnErrorWhenFilterIsLessThanTwoCharacters() throws Exception {
        var filtro = "x";

        when(service.findByMusicOrArtist(filtro)).thenThrow(new NameLenghtException());

        var response = musicController.findByNameOrMusic(filtro);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());

        System.out.println(">>>>>>>>>>>>>>>> " + response);


    }

//    @Test
//    public void findByNameOrMusicShouldReturnErrorWhenFilterIsLessThanTwoCharacters() throws Exception {
//        var filtro = "x";
//
//        when(service.findByMusicOrArtist(filtro)).thenThrow(new NameLenghtException());
//
//        ResultActions result =
//                mockMvc.perform(get("/api/musicas")
//                        .param("filter", filtro)
//                        .accept(MediaType.APPLICATION_JSON));
//
//        result.andExpect(status().isBadRequest());
//        result.andExpect(jsonPath("$").isNotEmpty());
//        result.andExpect(jsonPath("$.status", is(400)));
//        result.andExpect(jsonPath("$.error", hasItem("The name should have more than 2 characters")));
//    }

    @Test
    public void findByNameOrMusicShouldReturnNoContentWhenListIsEmpty() throws Exception {
        var filtro = "3xw4d3";

        when(service.findByMusicOrArtist(filtro)).thenThrow(new NameLenghtException());

        ResultActions result =
                mockMvc.perform(get("/api/musicas")
                        .param("filter", filtro)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNoContent());
    }

}
