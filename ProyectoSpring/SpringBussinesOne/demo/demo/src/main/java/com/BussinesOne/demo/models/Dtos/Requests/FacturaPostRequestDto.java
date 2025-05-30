package com.BussinesOne.demo.models.Dtos.Requests;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Data
public class FacturaPostRequestDto {
    @NotNull
    @PositiveOrZero
    private Double precioTotal;

    @NotNull
    private LocalDate fecha;

    @NotNull
    private Long usuarioId;

    @NotNull
    private List<FacturaItemDto> items;

    
}