package com.example.store.Impl;

import com.example.store.dto.ProductDto;
import com.example.store.dto.ProductRequestDto;
import com.example.store.entity.ProductEntity;
import com.example.store.exception.ModelNotFoundException;
import com.example.store.repository.ProductRepository;
import com.example.store.service.impl.ProductServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    private ProductRepository repository;
    private ProductServiceImp target;

    @BeforeEach
    void setUp() {
        repository = mock(ProductRepository.class);
        Function<ProductEntity, ProductDto> productToDto = productEntity -> {
            ModelMapper objectMapper = new ModelMapper();

            return objectMapper.map(productEntity, ProductDto.class);
        };
        Function<ProductRequestDto, ProductEntity> productRequestDtoToEntity = productRequestDto -> {
            ModelMapper objectMapper = new ModelMapper();

            return objectMapper.map(productRequestDto, ProductEntity.class);
        };
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        target = new ProductServiceImp(repository, productToDto, productRequestDtoToEntity, jdbcTemplate);
    }

    @Test
    void save_thenOk() {
        ProductEntity entity = getProductEntity();

        when(repository.save(any())).thenReturn(entity);

        ProductDto productDto = target.save(getProductRequestDto());

        assertNotNull(productDto);
        verify(repository, times(1)).save(any());
    }

    @Test
    void update_thenOk() {
        ProductEntity entity = getProductEntity();

        when(repository.save(any())).thenReturn(entity);
        when(repository.findById(any())).thenReturn(Optional.of(entity));

        ProductDto productDto = target.update(getProductRequestDto(), UUID.randomUUID());

        assertNotNull(productDto);
        verify(repository, times(1)).findById(any());
        verify(repository, times(1)).save(any());
    }

    @Test
    void update_whenNotExist_thenThrow() {
        ProductRequestDto requestDto = getProductRequestDto();

        when(repository.findById(any())).thenReturn(Optional.empty());

        ModelNotFoundException exception = assertThrows(ModelNotFoundException.class, () -> target.update(requestDto, UUID.randomUUID()));

        assertNotNull(exception);
        assertNotNull(exception.getMessage());
        verify(repository, times(1)).findById(any());
        verify(repository, never()).save(any());
    }

    private ProductEntity getProductEntity() {
        return ProductEntity.builder()
                .id(UUID.randomUUID())
                .name("name")
                .price(BigDecimal.valueOf(15.5))
                .description("description")
                .dateTime(LocalDateTime.now())
                .amount(1)
                .build();
    }

    private ProductRequestDto getProductRequestDto() {
        return ProductRequestDto.builder()
                .name("name")
                .price(BigDecimal.valueOf(15.5))
                .description("description")
                .build();
    }

}
