package com.example.store.service.impl;

import com.example.store.dto.ProductDto;
import com.example.store.dto.ProductRequestDto;
import com.example.store.entity.ProductEntity;
import com.example.store.exception.BusinessLogicException;
import com.example.store.exception.ModelNotFoundException;
import com.example.store.repository.ProductRepository;
import com.example.store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import static com.example.store.commons.Constants.CALL_DECREASE_QUANTITY;
import static com.example.store.commons.Constants.CALL_INCREASE_QUANTITY;
import static com.example.store.commons.Constants.EXCEPTION_MODEL_AMOUNT;
import static com.example.store.commons.Constants.EXCEPTION_MODEL_NOTFOUND;
import static com.example.store.commons.Constants.TIME_ZONE;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductRepository repository;
    private final Function<ProductEntity, ProductDto> productToDto;
    private final Function<ProductRequestDto, ProductEntity> productRequestDtoToEntity;
    private final JdbcTemplate jdbcTemplate;

    private ProductDto convert(ProductEntity entity) {
        return productToDto.apply(entity);
    }

    @Override
    @Transactional
    public ProductDto save(ProductRequestDto dto) {
        ProductEntity entity = productRequestDtoToEntity.apply(dto);

        entity.setDateTime(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        entity.setAmount(0);
        return convert(repository.save(entity));
    }

    @Override
    @Transactional
    public ProductDto update(ProductRequestDto dto, UUID id) {
        ProductEntity entity = repository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException(EXCEPTION_MODEL_NOTFOUND));

        BeanUtils.copyProperties(dto, entity, "id", "dateTime", "amount");
        return convert(repository.save(entity));
    }

    @Override
    public ProductDto getById(UUID id) {
        ProductEntity entity = repository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException(EXCEPTION_MODEL_NOTFOUND));

        return convert(entity);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        ProductEntity entity = repository.findById(id).orElseThrow(() ->
                new ModelNotFoundException(EXCEPTION_MODEL_NOTFOUND));

        repository.deleteById(entity.getId());
    }

    @Override
    public Page<ProductDto> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::convert);
    }

    @Override
    public List<ProductDto> getAll() {
        return repository.findAll().stream().map(this::convert).toList();
    }

    @Override
    @Transactional
    public void increaseQuantity(UUID id, Integer amount) {
        if(repository.findById(id).isEmpty())
            throw new ModelNotFoundException(EXCEPTION_MODEL_NOTFOUND);

        jdbcTemplate.execute(CALL_INCREASE_QUANTITY, (PreparedStatementCallback<Object>) preparedStatement -> {
            preparedStatement.setObject(1, id);
            preparedStatement.setInt(2, amount);
            preparedStatement.execute();
            return null;
        });
    }

    @Override
    @Transactional
    public void decreaseQuantity(UUID id, Integer amount) {
        ProductEntity entity = repository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException(EXCEPTION_MODEL_NOTFOUND));

        if (amount > entity.getAmount())
            throw new BusinessLogicException(EXCEPTION_MODEL_AMOUNT);

        jdbcTemplate.execute(CALL_DECREASE_QUANTITY, (PreparedStatementCallback<Object>) preparedStatement -> {
            preparedStatement.setObject(1, id);
            preparedStatement.setInt(2, amount);
            preparedStatement.execute();
            return null;
        });
    }

}
