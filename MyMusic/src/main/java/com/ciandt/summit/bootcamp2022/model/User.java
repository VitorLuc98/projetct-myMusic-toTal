package com.ciandt.summit.bootcamp2022.model;

import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "Nome", nullable = false)
    private String name;

    @JoinColumn(name = "PlayslistId")
    @ManyToOne
    private Playlist playlist;
}
