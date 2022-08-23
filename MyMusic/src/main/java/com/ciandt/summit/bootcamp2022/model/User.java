package com.ciandt.summit.bootcamp2022.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String  id;
    private String name;
    private Playlist playlist;
}
