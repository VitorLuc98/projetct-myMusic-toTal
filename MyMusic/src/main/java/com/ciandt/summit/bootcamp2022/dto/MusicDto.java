package com.ciandt.summit.bootcamp2022.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MusicDto {

    @NotBlank(message = "field 'id' is necessary")
    private String id;

    @NotBlank(message = "field 'name' is necessary")
    private String name;

    @NotNull(message = "field 'artist' is necessary")
    private ArtistDto artist;
}
