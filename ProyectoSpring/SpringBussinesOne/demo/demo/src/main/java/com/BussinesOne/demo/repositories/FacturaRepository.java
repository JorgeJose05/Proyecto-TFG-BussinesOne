package com.BussinesOne.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BussinesOne.demo.models.Factura;
import com.BussinesOne.demo.models.Producto;

public interface FacturaRepository extends JpaRepository<Factura, Long>
{
    void deleteById(Long id);
}

