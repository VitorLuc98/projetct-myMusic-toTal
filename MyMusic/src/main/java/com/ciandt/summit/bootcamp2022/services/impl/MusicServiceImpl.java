package com.ciandt.summit.bootcamp2022.services.impl;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.model.Music;
import com.ciandt.summit.bootcamp2022.repositories.MusicRepository;
import com.ciandt.summit.bootcamp2022.services.MusicService;
import com.ciandt.summit.bootcamp2022.services.exceptions.ResourceNotFoundException;
import com.ciandt.summit.bootcamp2022.services.exceptions.ListIsEmptyException;
import com.ciandt.summit.bootcamp2022.services.exceptions.NameLenghtException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

    private final MusicRepository repository;
    private final ModelMapper modelMapper;

    private static final Logger log = LoggerFactory.getLogger(MusicServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public List<MusicDto> findByMusicOrArtist(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            return findAll();
        }

        if (name.length() < 2) {
            throw new NameLenghtException("The name should have more than 2 characters");
        }

        var list = repository.findAllByNameMusicOrNameArtist(name);

        if(list.isEmpty()){
            throw new ListIsEmptyException("Couldn't find any artist or song with the given name");
        }
        return list.stream()
                .map(music -> modelMapper.map(music,MusicDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MusicDto getMusicById(String id) {
        log.info("searching music by id = {}", id);
        var musicEntity = repository.findById(id);
        if (musicEntity.isEmpty()){
            log.warn("Music of id {}, not found!", id);
            throw new ResourceNotFoundException("Music not found!");
        }
        return  modelMapper.map(musicEntity.get(), MusicDto.class);
    }

    private List<MusicDto> findAll() {
        return repository.findAll().stream()
                .map(music -> modelMapper.map(music, MusicDto.class))
                .collect(Collectors.toList());
    }
}
