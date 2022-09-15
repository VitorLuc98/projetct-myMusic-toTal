package com.ciandt.summit.bootcamp2022.repositories;

import com.ciandt.summit.bootcamp2022.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music, String> {

    @Query("SELECT music FROM Music music " +
            "INNER JOIN Artist artist " +
            "ON music.artist = artist.id " +
            "WHERE music.name LIKE LOWER(CONCAT('%', :name, '%' )) " +
            "OR artist.name LIKE LOWER(CONCAT('%', :name, '%' )) " +
            "ORDER BY artist.name, music.name")
    List<Music> findAllByNameMusicOrNameArtist(@Param("name") String name);

}
