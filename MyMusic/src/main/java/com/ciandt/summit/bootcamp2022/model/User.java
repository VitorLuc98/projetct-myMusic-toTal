package com.ciandt.summit.bootcamp2022.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Usuarios")
public class User implements Serializable {

    @Id
    private String id;

    @Column(name = "Nome", nullable = false)
    private String name;

    @JoinColumn(name = "PlaylistId")
    @ManyToOne(cascade = CascadeType.ALL)
    private Playlist playlist;

    @JoinColumn(name = "TipoUsuarioId")
    @ManyToOne(cascade = CascadeType.REMOVE)
    private UserType userType;



}
