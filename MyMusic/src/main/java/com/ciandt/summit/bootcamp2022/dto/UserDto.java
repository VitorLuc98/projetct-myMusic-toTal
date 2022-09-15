package com.ciandt.summit.bootcamp2022.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

    private String id;

    @NotBlank(message = "field 'name' is necessary")
    private String name;

    @NotNull(message = "field 'playlist' is necessary")
    private PlaylistDto playlist;

    @NotNull(message = "field 'userType' is necessary")
    private UserTypeDto userType;
}
