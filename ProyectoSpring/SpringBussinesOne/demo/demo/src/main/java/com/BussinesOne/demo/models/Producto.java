package com.BussinesOne.demo.models;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Producto")
public class Producto {
   
    @Id
    @Column(name="Codigo")
    private Long codigo;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private Double precio;

    @Column(columnDefinition = "TEXT")
    private String foto;

    @ManyToOne
    @JoinColumn(name = "usuariocreador")
    @JsonManagedReference
    private Perfil usuarioCreador;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<FacturaProducto> facturaProductos;

}
/*-- Tabla Producto
CREATE TABLE Producto (
    Codigo SERIAL PRIMARY KEY,
    Nombre VARCHAR(100) NOT NULL,
    Precio DOUBLE PRECISION NOT NULL,
    Foto TEXT,
    UsuarioCreador INT REFERENCES Perfil(Id) ON DELETE SET NULL
);
 */