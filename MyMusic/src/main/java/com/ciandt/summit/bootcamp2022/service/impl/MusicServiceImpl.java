package com.ciandt.summit.bootcamp2022.service.impl;

import com.ciandt.summit.bootcamp2022.dto.MusicDto;
import com.ciandt.summit.bootcamp2022.repository.MusicRepository;
import com.ciandt.summit.bootcamp2022.service.MusicService;
import com.ciandt.summit.bootcamp2022.service.exceptions.ListIsEmptyException;
import com.ciandt.summit.bootcamp2022.service.exceptions.NameLenghtException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MusicServiceImpl implements MusicService {

    private final MusicRepository repository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MusicDto> findByMusicOrArtist(String name) {

        if(Objects.isNull(name) || name.isEmpty()){
            return findAll();
        }

        if(name.length() < 2 ){
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
    private List<MusicDto> findAll(){
         return repository.findAll().stream()
                .map(music -> modelMapper.map(music,MusicDto.class))
                .collect(Collectors.toList());
    }
}
