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
@Table(name = "Usuarios")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "Nome", nullable = false)
    private String name;

    @JoinColumn(name = "PlayslistId")
    @ManyToOne
    private Playlist playlist;

    @JoinColumn(name = "TipoUsuarioId")
    @ManyToOne(cascade = CascadeType.REMOVE)
    private UserType userType;



}
