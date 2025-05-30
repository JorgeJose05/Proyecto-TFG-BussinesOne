package com.BussinesOne.demo.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Fichaje")
public class Fichaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fichajeID;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "empleadoid")
    private Perfil empleado;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "horaentrada", nullable = false)
    private LocalTime horaEntrada;

    @Column(name = "horasalida")
    private LocalTime horaSalida;

    @Column(name = "tiemporeal", nullable = false)
    private Boolean tiempoReal = true;

    @Column(name = "createdat", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    
}