package com.ciandt.summit.bootcamp2022.repositories;

import com.ciandt.summit.bootcamp2022.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, String> {
}