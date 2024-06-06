package com.example.store.controller;

import com.example.store.commons.GeneralBodyResponse;
import com.example.store.dto.ProductDto;
import com.example.store.dto.ProductRequestDto;
import com.example.store.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ProductController {

    public static final String RESPONSE = "response: {}";
    private final ProductService service;

    @PostMapping()
    public ResponseEntity<GeneralBodyResponse<ProductDto>> save(@Valid @RequestBody ProductRequestDto dto) {
        log.info("initial save product [{}]", dto.getName());
        log.debug("request: {}", dto);

        ProductDto productDto = service.save(dto);

        log.debug(RESPONSE, productDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(GENERAL_CREATE_SUCCESS, productDto));
    }

    @PutMapping("{id}")
    public ResponseEntity<GeneralBodyResponse<ProductDto>> update(@Valid @PathVariable("id") UUID id, @Valid @RequestBody ProductRequestDto dto) {
        log.info("initial update product [{}]", id.toString());
        log.debug("request: {}", dto);

        ProductDto productDto = service.update(dto, id);

        log.debug(RESPONSE, productDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(GENERAL_UPDATE_SUCCESS,
                        productDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<GeneralBodyResponse<ProductDto>> getById(@PathVariable("id") UUID id) {
        log.info("initial get product, by id: [{}]", id);

        ProductDto productDto = service.getById(id);

        log.debug(RESPONSE, productDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(GENERAL_SUCCESS,
                        productDto));
    }

    @GetMapping("page")
    public ResponseEntity<GeneralBodyResponse<Page<ProductDto>>> getPage(Pageable pageable) {
        log.info("initial get all products paged");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(GENERAL_LIST_SUCCESS,
                        service.getAll(pageable)));
    }

    @GetMapping("all")
    public ResponseEntity<GeneralBodyResponse<List<ProductDto>>> getAll() {
        log.info("initial get all products");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(GENERAL_LIST_SUCCESS,
                        service.getAll()));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<GeneralBodyResponse<Void>> delete(@PathVariable("id") UUID id) {
        log.info("initial delete state [{}]", id);
        service.delete(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(GENERAL_DELETE_SUCCESS, null));
    }

    @PutMapping("{id}/increase/{amount}")
    public ResponseEntity<GeneralBodyResponse<Void>> increaseQuantity(@PathVariable("id") UUID id, @PathVariable("amount") Integer amount) {
        log.info("initial increase amount product, by id: [{}]", id);
        service.increaseQuantity(id, amount);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(GENERAL_SUCCESS, null));
    }

    @PutMapping("{id}/decrease/{amount}")
    public ResponseEntity<GeneralBodyResponse<Void>> decreaseQuantity(@PathVariable("id") UUID id, @PathVariable("amount") Integer amount) {
        log.info("initial decrease amount product, by id: [{}]", id);
        service.decreaseQuantity(id, amount);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GeneralBodyResponse<>(GENERAL_SUCCESS, null));
    }
}
