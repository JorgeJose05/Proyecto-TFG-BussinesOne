package com.BussinesOne.demo.servicies;

import java.util.List;

import com.BussinesOne.demo.models.Perfil;
import com.BussinesOne.demo.models.Producto;
import com.BussinesOne.demo.models.Dtos.Requests.PerfilPatchRequestDto;
import com.BussinesOne.demo.models.Dtos.Requests.PerfilPostRequestDto;

public interface PerfilService {
    List<Perfil> getAllPerfils();
    Perfil getPerfilById(long id);
    Perfil crearPerfil(PerfilPostRequestDto dto);
    
    Perfil updatePerfil(Long id, PerfilPatchRequestDto patch);
}
