package com.BussinesOne.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BussinesOne.demo.models.Fichaje;
import com.BussinesOne.demo.models.Producto;

public interface FichajeRepository extends JpaRepository<Fichaje, Long>
{
}

