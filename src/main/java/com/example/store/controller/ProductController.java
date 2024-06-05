package com.example.store.controller;

import com.example.store.commons.GeneralBodyResponse;
import com.example.store.dto.ProductDto;
import com.example.store.dto.ProductRequestDto;
import com.example.store.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.example.store.commons.Constants.GENERAL_CREATE_SUCCESS;
import static com.example.store.commons.Constants.GENERAL_DELETE_SUCCESS;
import static com.example.store.commons.Constants.GENERAL_LIST_SUCCESS;
import static com.example.store.commons.Constants.GENERAL_SUCCESS;
import static com.example.store.commons.Constants.GENERAL_UPDATE_SUCCESS;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping()
    public ResponseEntity<GeneralBodyResponse<ProductDto>> save(@Valid @RequestBody ProductRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(GENERAL_CREATE_SUCCESS, service.save(dto)));
    }

    @PutMapping("{id}")
    public ResponseEntity<GeneralBodyResponse<ProductDto>> update(@Valid @PathVariable("id") UUID id, @Valid @RequestBody ProductRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(GENERAL_UPDATE_SUCCESS,
                        service.update(dto, id)));
    }

    @GetMapping("{id}")
    public ResponseEntity<GeneralBodyResponse<ProductDto>> getById(@PathVariable("id") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(GENERAL_SUCCESS,
                        service.getById(id)));
    }

    @GetMapping("page")
    public ResponseEntity<GeneralBodyResponse<Page<ProductDto>>> getPage(Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(GENERAL_LIST_SUCCESS,
                        service.getAll(pageable)));
    }

    @GetMapping("all")
    public ResponseEntity<GeneralBodyResponse<List<ProductDto>>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(GENERAL_LIST_SUCCESS,
                        service.getAll()));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<GeneralBodyResponse<Void>> delete(@PathVariable("id") UUID id) {
        service.delete(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(GENERAL_DELETE_SUCCESS, null));
    }

    @PutMapping("{id}/increase/{amount}")
    public ResponseEntity<GeneralBodyResponse<Void>> increaseQuantity(@PathVariable("id") UUID id, @PathVariable("amount") Integer amount) {
        service.increaseQuantity(id, amount);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(GENERAL_SUCCESS, null));
    }

    @PutMapping("{id}/decrease/{amount}")
    public ResponseEntity<GeneralBodyResponse<Void>> decreaseQuantity(@PathVariable("id") UUID id, @PathVariable("amount") Integer amount) {
        service.decreaseQuantity(id, amount);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(GENERAL_SUCCESS, null));
    }
}
