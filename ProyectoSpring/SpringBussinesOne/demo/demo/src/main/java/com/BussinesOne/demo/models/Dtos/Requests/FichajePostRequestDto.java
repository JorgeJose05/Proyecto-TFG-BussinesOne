package com.BussinesOne.demo.models.Dtos.Requests;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class FichajePostRequestDto {
    @NotNull
    private Long empleadoId;

    @NotNull
    private LocalDate fecha;

    @NotNull
    private LocalTime horaEntrada;

    private LocalTime horaSalida;

    private Boolean tiempoReal = true;

    private LocalDateTime createdAt; // si se deja null, el Backend asigna ahora con @PrePersist

}