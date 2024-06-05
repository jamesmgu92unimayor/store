package com.example.store.service.impl;

import com.example.store.dto.UserDto;
import com.example.store.entity.UserEntity;
import com.example.store.exception.BusinessLogicException;
import com.example.store.exception.ModelNotFoundException;
import com.example.store.repository.UserRepository;
import com.example.store.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Function;

import static com.example.store.commons.Constants.EXCEPTION_MODEL_EMAIL;
import static com.example.store.commons.Constants.EXCEPTION_MODEL_USER_INVALID;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository repository;
    private final Function<UserDto, UserEntity> userDtoToEntity;
    private final Function<UserEntity, UserDto> userToDto;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto save(UserDto userDto) {
        this.checkIfExistEmail(userDto.getEmail());

        UserEntity userEntity = this.userDtoToEntity.apply(userDto);

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        userEntity = repository.save(userEntity);

        return userToDto.apply(userEntity);
    }

    @Override
    @Transactional
    public UserDto update(UserDto userDto, UUID id) {
        UserEntity user = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(EXCEPTION_MODEL_USER_INVALID));

        if (repository.existsByEmailAndUser(userDto.getEmail(), id)) {
            throw new BusinessLogicException(EXCEPTION_MODEL_EMAIL);
        }

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user = repository.save(user);
        return userToDto.apply(user);
    }

    @Override
    public UserDto getById(UUID id) {
        UserEntity user = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(EXCEPTION_MODEL_USER_INVALID));

        return userToDto.apply(user);
    }

    @Override
    public Page<UserDto> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::convert);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        UserEntity user = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(EXCEPTION_MODEL_USER_INVALID));

        repository.deleteById(user.getId());
    }

    private UserDto convert(UserEntity entity) {
        return userToDto.apply(entity);
    }


    private void checkIfExistEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new BusinessLogicException(EXCEPTION_MODEL_EMAIL);
        }
    }

}
