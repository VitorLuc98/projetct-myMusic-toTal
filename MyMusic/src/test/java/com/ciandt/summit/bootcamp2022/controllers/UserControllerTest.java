package com.ciandt.summit.bootcamp2022.controllers;

import com.ciandt.summit.bootcamp2022.config.interceptor.TokenInterceptor;
import com.ciandt.summit.bootcamp2022.dto.*;
import com.ciandt.summit.bootcamp2022.model.*;
import com.ciandt.summit.bootcamp2022.services.exceptions.ResourceNotFoundException;
import com.ciandt.summit.bootcamp2022.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController controller;

    @MockBean
    private UserServiceImpl service;

    @MockBean
    private TokenInterceptor tokenInterceptor;

    private UserDto user;
    private PlaylistDto playlist;
    private MusicDto music;
    private ArtistDto artist;
    private UserTypeDto userType;
    private String uri;

    @BeforeEach
    void setUp() throws Exception {
        given(tokenInterceptor.preHandle(any(), any(), any())).willReturn(true);
        MockitoAnnotations.openMocks(this);

        uri = "/api/users";

        artist = new ArtistDto("1", "Katy Perry");
        music = new MusicDto("1", "The One That Got Away", artist);
        playlist = new PlaylistDto("1", List.of(music));
        userType = new UserTypeDto("1", "Comum");
        user = new UserDto("1", "Vitor", playlist, userType);
    }

    @Test
    public void findUserByIdShouldReturnUserWhenIdExist() throws Exception {
        var id = "1";

        when(service.findUserById(id)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(uri+"/"+id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", is(user.getName())));

    }

    @Test
    public void findUserByIdShouldReturnResourceNotFoundWhenDoesIdExist() throws Exception {
        var id = "10000";
        var message = "User Not Found!";
        when(service.findUserById(id)).thenThrow(new ResourceNotFoundException(message));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(uri+"/"+id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is(message)));
    }
}
