package com.ciandt.summit.bootcamp2022.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class MusicRepositoryTest {

    @Autowired
    private MusicRepository musicRepository;

    private long countTotalMusics;
    private long countTotalMusicsByArtist;
    private long countTotalMusicsByMusic;

    private long countTotalMusicsIgnoringCase;

    @BeforeEach
    void setUp() throws Exception {
        countTotalMusicsByArtist = 25L;
        countTotalMusics = 33573L;
        countTotalMusicsByMusic = 24L;
        countTotalMusicsIgnoringCase = 2L;
    }

    @Test
    public void findAllByNameMusicOrNameArtistShouldBringMusicsWhenFilterIsArtist() {
        String filter = "Bruno Mars";

        var musics = musicRepository.findAllByNameMusicOrNameArtist(filter);

        assertFalse(musics.isEmpty());
        assertEquals(musics.size(), countTotalMusicsByArtist);
    }

    @Test
    public void findAllByNameMusicOrNameArtistShouldBringMusicsWhenFilterIsMusic() {
        String filter = "Phone";

        var musics = musicRepository.findAllByNameMusicOrNameArtist(filter);

        assertFalse(musics.isEmpty());
        assertEquals(musics.size(), countTotalMusicsByMusic);
    }

    @Test
    public void findAllByNameMusicOrNameArtistShouldBringMusicsWhenMusicExistsIgnoringCase() {
        String filter = "CrY wOlF";

        var musics = musicRepository.findAllByNameMusicOrNameArtist(filter);

        assertFalse(musics.isEmpty());
        assertEquals(musics.size(), countTotalMusicsIgnoringCase);
    }

    @Test
    public void findAllByNameMusicOrNameArtistShouldBringAllMusicsWhenFilterIsEmpty() {
        String filter = "";

        var musics = musicRepository.findAllByNameMusicOrNameArtist(filter);

        assertFalse(musics.isEmpty());
        assertEquals(musics.size(), countTotalMusics);
    }
}
