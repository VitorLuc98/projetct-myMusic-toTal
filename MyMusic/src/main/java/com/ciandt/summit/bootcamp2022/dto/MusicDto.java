package com.ciandt.summit.bootcamp2022.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicDto {

    private String id;

    @NotBlank(message = "field 'name' is necessary")
    private String name;

    @NotNull(message = "field 'artist' is necessary")
    private ArtistDto artist;
}
