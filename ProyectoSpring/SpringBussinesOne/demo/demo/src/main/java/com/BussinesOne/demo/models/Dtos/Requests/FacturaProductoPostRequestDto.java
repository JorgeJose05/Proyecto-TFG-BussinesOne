package com.BussinesOne.demo.models.Dtos.Requests;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class FacturaProductoPostRequestDto {
    @NotNull
    private Long facturaId;

    @NotNull
    private Long productoId;

    @NotNull
    @Min(1)
    private Integer cantidad;

    @NotNull
    @PositiveOrZero
    private Double precioUnitario;

    @NotNull
    @PositiveOrZero
    private Double precioSubtotal;

    // getters y setters
}
