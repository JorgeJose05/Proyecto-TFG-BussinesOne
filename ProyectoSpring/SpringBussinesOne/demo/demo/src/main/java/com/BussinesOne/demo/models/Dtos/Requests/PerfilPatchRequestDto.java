package com.BussinesOne.demo.models.Dtos.Requests;


import lombok.Data;
import java.time.LocalDate;

@Data
public class PerfilPatchRequestDto {
    private String nombre;
    private String rol;
    private String email;
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
