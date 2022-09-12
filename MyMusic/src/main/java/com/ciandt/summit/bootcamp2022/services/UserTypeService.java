package com.ciandt.summit.bootcamp2022.services;

import com.ciandt.summit.bootcamp2022.dto.UserTypeDto;

public interface UserTypeService {
    UserTypeDto findUserById(String id);
}
