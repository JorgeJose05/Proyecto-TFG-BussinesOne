package com.BussinesOne.demo.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cglib.core.Local;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "Factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facturaid")
    private Long facturaId;

    @Column(name = "preciototal", nullable = false)
    private Double precioTotal;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "usuarioid")
    private Perfil usuario;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<FacturaProducto> items = new ArrayList<>();
    
}
/*-- Tabla Factura
CREATE TABLE Factura (
    FacturaId SERIAL PRIMARY KEY,
    PrecioTotal DOUBLE PRECISION NOT NULL,
    Fecha DATE NOT NULL,
    UsuarioId INT REFERENCES Perfil(Id) ON DELETE SET NULL
);
 */