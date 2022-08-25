package com.ciandt.summit.bootcamp2022.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Playlists")
public class Playlist implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "PlaylistMusicas",
            joinColumns = {@JoinColumn(name = "PlaylistId")},
            inverseJoinColumns = {@JoinColumn(name = "MusicaId")})
    private List<Music> musics = new ArrayList<>();


}
