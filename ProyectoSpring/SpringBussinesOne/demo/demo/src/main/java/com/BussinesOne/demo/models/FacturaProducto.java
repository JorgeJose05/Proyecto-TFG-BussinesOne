package com.BussinesOne.demo.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "factura_producto")
public class FacturaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "facturaid")
    @JsonBackReference
    private Factura factura;

    @ManyToOne
    @JoinColumn(name = "productoid")
    private Producto producto;

    @Column(name = "Cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "preciounitario", nullable = false)
    private Double precioUnitario;

    @Column(name = "preciosubtotal", nullable = false)
    private Double precioSubtotal;
    
}
/*-- Tabla intermedia FacturaProducto
CREATE TABLE FacturaProducto (
    Id SERIAL PRIMARY KEY,
    FacturaId INT REFERENCES Factura(FacturaId) ON DELETE CASCADE,
    ProductoId INT REFERENCES Producto(Codigo) ON DELETE CASCADE,
    Cantidad INT NOT NULL CHECK (Cantidad > 0),
    PrecioUnitario DOUBLE PRECISION NOT NULL,
    PrecioSubtotal DOUBLE PRECISION NOT NULL
); */