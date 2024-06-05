package com.example.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto implements Serializable {

    @NotNull(message = "El nombre del producto es requerido")
    @NotBlank(message = "El nombre del producto no puede ser vacio")
    private String name;
    @NotNull(message = "El precio del producto es requerido")
    private BigDecimal price;
    private String description;
}
