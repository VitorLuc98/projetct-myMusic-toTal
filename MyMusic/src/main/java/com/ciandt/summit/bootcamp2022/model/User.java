package com.ciandt.summit.bootcamp2022.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name ="Usuarios")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String  id;

    @Column(name = "Nome", nullable = false )
    private String name;

    @JoinColumn(name = "PlayslistId")
    @ManyToOne
    private Playlist playlist;
}
