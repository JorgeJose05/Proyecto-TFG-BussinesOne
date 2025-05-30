package com.BussinesOne.demo.models.Dtos.Requests;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ProductoPostRequestDto {
    
    @NotNull
    private Long codigo;  
    
    @NotBlank
    @Size(max = 100)
    private String nombre;

    @NotNull
    @PositiveOrZero
    private Double precio;

    private String foto;

    @NotNull
    private Long usuarioCreadorId;

}
