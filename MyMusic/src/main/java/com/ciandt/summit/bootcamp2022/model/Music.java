package com.ciandt.summit.bootcamp2022.model;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Musicas")
public class Music implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "Nome")
    private String name;

    @JoinColumn(name = "ArtistaId")
    @ManyToOne
    private Artist artist;
}
