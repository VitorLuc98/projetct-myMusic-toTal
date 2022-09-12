package com.ciandt.summit.bootcamp2022.services.impl;

import com.ciandt.summit.bootcamp2022.dto.UserTypeDto;
import com.ciandt.summit.bootcamp2022.model.UserType;
import com.ciandt.summit.bootcamp2022.repositories.UserRepository;
import com.ciandt.summit.bootcamp2022.services.UserTypeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserTypeImpl implements UserTypeService {

    private final UserRepository userRepository;

    private final UserTypeService userTypeService;
    UserType userType;
    private final ModelMapper modelMapper;
    private static final Logger log = LoggerFactory.getLogger(UserTypeImpl.class);

    @Transactional(readOnly = true)
    @Override
    public UserTypeDto findUserById(String id) {
        log.info("searching userId by id = {}", id);
        var userTypeEntity = userRepository.findById(id);
        if (userTypeEntity.isEmpty()) {
            log.warn("User type id {}, not found!", id);
        }
        return modelMapper.map(userTypeEntity,UserTypeDto.class);
    }
}
