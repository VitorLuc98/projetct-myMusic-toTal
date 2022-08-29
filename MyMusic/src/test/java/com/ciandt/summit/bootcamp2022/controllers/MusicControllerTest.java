package com.ciandt.summit.bootcamp2022.controllers;

import com.ciandt.summit.bootcamp2022.dto.ArtistDto;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.services.exceptions.ListIsEmptyException;
import com.ciandt.summit.bootcamp2022.services.exceptions.NameLenghtException;
import com.ciandt.summit.bootcamp2022.services.impl.MusicServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MusicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MusicServiceImpl service;

    @Autowired
    private MusicController controller;

    private ArtistDto artist1;
    private MusicDto music1;
    private ArtistDto artist2;
    private MusicDto music2;
    private ArtistDto artist3;
    private MusicDto music3;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        artist1 = new ArtistDto("1", "Post Malone");
        music1 = new MusicDto("1", "Take What You Want", artist1);

        artist2 = new ArtistDto("2", "The Weeknd");
        music2 = new MusicDto("2", "Save Your Tears", artist2);

        artist3 = new ArtistDto("3", "Katy Perry");
        music3 = new MusicDto("3", "Thinking of You", artist3);
    }

    @Test
    public void findByNameOrMusicShouldReturnListMusicsWhenExistFilter() throws Exception {
        List<MusicDto> musics = new ArrayList<>(Arrays.asList(music1, music2, music3));
        var filter = "You";

        when(service.findByMusicOrArtist(filter)).thenReturn(musics);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/musicas")
                        .param("filtro", filter)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void findByNameOrMusicShouldReturnListMusicsWhenExistFilterIgnoreCase() throws Exception {
        var musics = List.of(music3);
        var filter = "kAtY";

        when(service.findByMusicOrArtist(filter)).thenReturn(musics);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/musicas")
                        .param("filtro", filter)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void findByNameOrMusicShouldReturnErrorWhenFilterIsLessThanTwoCharacters() throws Exception {
        var filter = "x";

        when(service.findByMusicOrArtist(filter)).thenThrow(new NameLenghtException());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/musicas")
                        .param("filtro", filter)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("The name should have more than 2 characters")))
                .andExpect(jsonPath("$.path", is("/api/musicas")));
    }

    @Test
    public void findByNameOrMusicShouldReturnNoContentWhenListIsEmpty() throws Exception {
        var filter = "3xw4d3";

        when(service.findByMusicOrArtist(filter)).thenThrow(new ListIsEmptyException());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/musicas")
                        .param("filtro", filter)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
