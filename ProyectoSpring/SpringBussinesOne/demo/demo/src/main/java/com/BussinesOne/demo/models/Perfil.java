package com.BussinesOne.demo.models;



import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "Perfil")
public class Perfil {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 50)
    private String rol;

    @Column(unique = true, length = 100)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String direccion;

    @Column(name = "infopersonal" ,columnDefinition = "TEXT")
    private String infoPersonal;

    @Column(name = "formacionacademica" ,columnDefinition = "TEXT")
    private String formacionAcademica;

    @Column(name = "datospersonales" ,columnDefinition = "TEXT")
    private String datosPersonales;

    @OneToMany(mappedBy = "usuarioCreador", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Producto> productos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Factura> facturas;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Fichaje> fichajes;
}
/*CREATE TABLE Perfil (
    Id SERIAL PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    Rol VARCHAR(50),
    Email VARCHAR(100) UNIQUE,
    Direccion TEXT,
    InfoPersonal TEXT,
    FormacionAcademica TEXT,
    DatosPersonales TEXT
); */