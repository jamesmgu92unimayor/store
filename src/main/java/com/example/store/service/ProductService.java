package com.example.store.service;


import com.example.store.dto.ProductDto;
import com.example.store.dto.ProductRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductDto save(ProductRequestDto dto);

    ProductDto update(ProductRequestDto dto, UUID id);

    ProductDto getById(UUID id);

    void delete(UUID id);

    Page<ProductDto> getAll(Pageable pageable);

    List<ProductDto> getAll();

    void increaseQuantity(UUID id, Integer amount);

    void decreaseQuantity(UUID id, Integer amount);

}
