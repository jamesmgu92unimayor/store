package com.example.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "producto", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "nombre", nullable = false)
    private String name;
    @Column(name = "precio", precision = 10, scale = 2)
    private BigDecimal price;
    @Column(name = "descripcion")
    private String description;
    @Column(name = "fecha_registro")
    private LocalDateTime dateTime;
    @Column(name = "cantidad")
    private Integer amount;
}
