package com.BussinesOne.demo.models.Dtos;

import java.util.List;

import lombok.Data;

@Data
public class PerfilDTO {
    private Long id;
    private String nombre;
    private String email;
    private String rol;
    private String direccion;
    private String infoPersonal;
    private String formacionAcademica;
    private String datosPersonales;

    private List<ProductoSinUsuarioDTO> productos;

    @Data
    public static class ProductoSinUsuarioDTO {
    private Long codigo;
    private String nombre;
    private double precio;
    private String foto;
    }
}
