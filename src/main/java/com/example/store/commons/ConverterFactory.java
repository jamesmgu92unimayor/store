package com.example.store.commons;

import com.example.store.dto.ProductDto;
import com.example.store.dto.ProductRequestDto;
import com.example.store.dto.UserDto;
import com.example.store.entity.ProductEntity;
import com.example.store.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ConverterFactory {

    @Bean
    public Function<UserEntity, UserDto> userToDto(ModelMapper mapper) {
        return entity -> mapper.map(entity, UserDto.class);
    }

    @Bean
    public Function<UserDto, UserEntity> userDtoToEntity(ModelMapper mapper) {
        return dto -> mapper.map(dto, UserEntity.class);
    }

    @Bean
    public Function<ProductEntity, ProductDto> productToDto(ModelMapper mapper) {
        return entity -> mapper.map(entity, ProductDto.class);
    }

    @Bean
    public Function<ProductDto, ProductEntity> productDtoToEntity(ModelMapper mapper) {
        return dto -> mapper.map(dto, ProductEntity.class);
    }

    @Bean
    public Function<ProductRequestDto, ProductEntity> productRequestDtoToEntity(ModelMapper mapper) {
        return dto -> mapper.map(dto, ProductEntity.class);
    }
}
