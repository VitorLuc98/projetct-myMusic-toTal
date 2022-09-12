package com.ciandt.summit.bootcamp2022.services;

import com.ciandt.summit.bootcamp2022.dto.PlaylistDto;
import com.ciandt.summit.bootcamp2022.dto.UserDto;
import com.ciandt.summit.bootcamp2022.model.*;
import com.ciandt.summit.bootcamp2022.repositories.UserRepository;
import com.ciandt.summit.bootcamp2022.services.exceptions.ResourceNotFoundException;
import com.ciandt.summit.bootcamp2022.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserServiceImpl service;

    @MockBean
    UserRepository repository;

    private User user;
    private Playlist playlist;
    private Music music;
    private Artist artist;
    private UserType userType;

    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        artist = new Artist("1", "Katy Perry");
        music = new Music("1", "The One That Got Away", artist);
        playlist = new Playlist("1", List.of(music));
        userType = new UserType("1", "Comum");
        user = new User("1", "Vitor", playlist, userType);
        optionalUser = Optional.of(user);
    }

    @Test
    void findUserByIdShouldReturnUserDtoWhenUserExist() {
        var id = "1";
        when(repository.findById(id)).thenReturn(optionalUser);

        var userResponse = service.findUserById(id);

        assertNotNull(userResponse);
        assertEquals(optionalUser.get().getId(), userResponse.getId());
        assertEquals(UserDto.class,userResponse.getClass());
    }

    @Test
    void findUserByIdShouldReturnResourceNotFoundExceptionWhenUserDoesExist() {
        var id = "1000";
        var exception = assertThrows(ResourceNotFoundException.class, () -> service.findUserById(id));

        assertEquals("User Not Found!", exception.getMessage());
    }

}
