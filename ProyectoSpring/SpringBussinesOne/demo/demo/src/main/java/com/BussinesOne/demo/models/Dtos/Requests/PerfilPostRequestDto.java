package com.BussinesOne.demo.models.Dtos.Requests;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
public class PerfilPostRequestDto {
    @NotBlank
    private String nombre;

    @NotBlank
    private String rol;

    @Email
    private String email;

    private String direccion;
    private String infoPersonal;
    private String formacionAcademica;
    private String datosPersonales;

}
