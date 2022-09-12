package com.ciandt.summit.bootcamp2022.services;

import com.ciandt.summit.bootcamp2022.dto.UserDto;
import com.ciandt.summit.bootcamp2022.dto.UserTypeDto;

public interface UserService {
    UserDto findUserById(String id);
}
