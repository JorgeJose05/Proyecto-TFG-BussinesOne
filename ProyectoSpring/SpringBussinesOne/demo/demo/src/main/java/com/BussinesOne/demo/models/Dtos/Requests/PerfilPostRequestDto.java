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

    @NotBlank
    private String contraseña;

    private String direccion;
    private String infoPersonal;
    private String formacionAcademica;
    private String datosPersonales;

    private String telefono;
    private String dni;
    private LocalDate fechaNacimiento;
    private String numeroSeguridadSocial;
    private String iban;

}
