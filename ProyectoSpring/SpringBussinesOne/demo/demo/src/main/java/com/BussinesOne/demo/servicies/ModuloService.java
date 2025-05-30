package com.BussinesOne.demo.servicies;

import java.util.List;

import com.BussinesOne.demo.models.Modulo;
import com.BussinesOne.demo.models.Producto;
import com.BussinesOne.demo.models.Dtos.Requests.ModuloPostRequestDto;

public interface ModuloService {
    List<Modulo> getAllModulos();
    Modulo getModuloById(long id);
    Modulo crearModulo(ModuloPostRequestDto dto);
}
