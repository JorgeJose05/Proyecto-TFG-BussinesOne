package com.BussinesOne.demo.servicies;

import java.util.List;

import com.BussinesOne.demo.models.Fichaje;
import com.BussinesOne.demo.models.Producto;
import com.BussinesOne.demo.models.Dtos.Requests.FichajePostRequestDto;

public interface FichajeService {
    List<Fichaje> getAllFichajes();
    Fichaje getFichajeByID(long id);
    Fichaje crearFichaje(FichajePostRequestDto dto);
}
