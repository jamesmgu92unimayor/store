package com.example.store.controller;

import com.example.store.commons.GeneralBodyResponse;
import com.example.store.dto.UserDto;
import com.example.store.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.store.commons.Constants.GENERAL_CREATE_SUCCESS;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<GeneralBodyResponse<UserDto>> save(@Valid @RequestBody UserDto userDto) {
        log.info("initial save user [{}]", userDto.getName());
        log.debug("request: {}", userDto);

        UserDto user = service.save(userDto);

        log.debug("response: {}", user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new GeneralBodyResponse<>(GENERAL_CREATE_SUCCESS, user));
    }

}
