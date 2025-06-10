package com.BussinesOne.demo.mappers;
import org.springframework.stereotype.Component;

import com.BussinesOne.demo.models.Modulo;
import com.BussinesOne.demo.models.Dtos.Requests.ModuloPostRequestDto;


public class ModuloRequestMapper {
    public static Modulo toEntity(ModuloPostRequestDto dto) {
        if (dto == null) return null;
        Modulo modulo = new Modulo();
        modulo.setNombre(dto.getNombre());
        modulo.setGrupo(dto.getGrupo());
        modulo.setIcono(dto.getIcono());
        
        return modulo;
    }
}
