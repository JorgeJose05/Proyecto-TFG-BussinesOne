package com.BussinesOne.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BussinesOne.demo.models.Perfil;
import com.BussinesOne.demo.models.Producto;

public interface PerfilRepository extends JpaRepository<Perfil, Long>
{
}

