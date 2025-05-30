package com.BussinesOne.demo.models.Dtos.Requests;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ModuloPostRequestDto {
    @NotBlank
    @Size(max = 100)
    private String nombre;

    @NotBlank
    private String grupo;
    private String icono;

    // getters y setters
}
