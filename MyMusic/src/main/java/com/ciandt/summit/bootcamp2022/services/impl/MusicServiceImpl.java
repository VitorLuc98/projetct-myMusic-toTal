package com.ciandt.summit.bootcamp2022.services.impl;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.repositories.MusicRepository;
import com.ciandt.summit.bootcamp2022.services.MusicService;
import com.ciandt.summit.bootcamp2022.services.exceptions.EntityNotFoundException;
import com.ciandt.summit.bootcamp2022.services.exceptions.ListIsEmptyException;
import com.ciandt.summit.bootcamp2022.services.exceptions.NameLenghtException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    public MusicDto getMusicById(String id) {
        var musicEntity = repository.findById(id);
        if (musicEntity.isEmpty()){
            throw new EntityNotFoundException();
        }
        return  modelMapper.map(musicEntity.get(), MusicDto.class);
    }

    private List<MusicDto> findAll() {
        return repository.findAll().stream()
                .map(music -> modelMapper.map(music, MusicDto.class))
                .collect(Collectors.toList());
    }
}
