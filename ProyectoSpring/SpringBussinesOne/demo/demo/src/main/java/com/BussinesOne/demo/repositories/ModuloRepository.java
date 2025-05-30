package com.BussinesOne.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BussinesOne.demo.models.Modulo;

public interface ModuloRepository extends JpaRepository<Modulo, Long>
{
}

