package com.ciandt.summit.bootcamp2022.controllers;

import com.ciandt.summit.bootcamp2022.config.interceptor.TokenInterceptor;
import com.ciandt.summit.bootcamp2022.dto.ArtistDto;
import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;
import com.ciandt.summit.bootcamp2022.services.impl.PlayListServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PlaylistController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlaylistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayListServiceImpl service;

    @Autowired
    private PlaylistController controller;

    @MockBean
    TokenInterceptor tokenInterceptor;

    private MusicDto music1;
    private ArtistDto artist1;
    private PlaylistDto playlist;

    @BeforeEach
    void setUp() throws Exception {
        given(tokenInterceptor.preHandle(any(), any(), any())).willReturn(true);
        MockitoAnnotations.openMocks(this);

        artist1 = new ArtistDto("1", "Led zeppelin ");
        music1 = new MusicDto("1", "When the levee breaks", artist1);
        playlist = new PlaylistDto("1", Arrays.asList(music1));
    }

    @Test
    void addMusicInPlaylistShouldAddMusicToThePlaylist() throws Exception {
        when(service.addMusicToPlaylist("1",music1)).thenReturn(playlist);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/playlists/1/musicas")
                        .content(asJsonString(music1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void addMusicInPlaylistIDDontexist() throws Exception {
        when(service.addMusicToPlaylist("1",music1)).thenReturn(playlist);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/playlists/1/musicas")
                        .content(asJsonString(music1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}