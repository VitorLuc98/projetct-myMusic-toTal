package com.ciandt.summit.bootcamp2022.services.impl;

import com.ciandt.summit.bootcamp2022.dto.UserDto;
import com.ciandt.summit.bootcamp2022.model.Playlist;
import com.ciandt.summit.bootcamp2022.model.User;
import com.ciandt.summit.bootcamp2022.repositories.UserRepository;
import com.ciandt.summit.bootcamp2022.services.UserService;
import com.ciandt.summit.bootcamp2022.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Transactional(readOnly = true)
    @Override
    public UserDto findUserById(String id) {
        log.info("searching userId by id = {}", id);
        var user = userRepository.findById(id);
        if (user.isEmpty()) {
            log.warn("User id {}, not found!", id);
            throw new ResourceNotFoundException("User Not Found!");
        }
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        log.info("Converting UserDto to User");
        var entity = modelMapper.map(userDto, User.class);
        entity.setId(UUID.randomUUID().toString());
        entity.setPlaylist(new Playlist());
        log.info("Saving the user in the repository");
        entity = userRepository.save(entity);
        return modelMapper.map(entity, UserDto.class);
    }
}
