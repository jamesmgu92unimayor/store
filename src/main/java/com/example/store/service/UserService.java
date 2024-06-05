package com.example.store.service;


import com.example.store.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    UserDto save(UserDto userDto);

    UserDto update(UserDto userDto, UUID id);

    UserDto getById(UUID id);

    Page<UserDto> getAll(Pageable pageable);

    void delete(UUID id);
}
