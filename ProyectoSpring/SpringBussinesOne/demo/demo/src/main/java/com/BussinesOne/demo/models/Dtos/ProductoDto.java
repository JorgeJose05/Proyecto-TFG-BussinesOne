package com.BussinesOne.demo.models.Dtos;

import lombok.Data;

@Data
public class ProductoDto {
    private Long codigo;
    private String nombre;
    private double precio;
    private String foto;
    private long usuarioCreador;
}
