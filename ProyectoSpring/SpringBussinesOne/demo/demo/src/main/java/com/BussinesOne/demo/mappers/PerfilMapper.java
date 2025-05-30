package com.BussinesOne.demo.mappers;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.BussinesOne.demo.models.Perfil;
import com.BussinesOne.demo.models.Dtos.PerfilDTO;
import com.BussinesOne.demo.models.Dtos.PerfilDTO.ProductoSinUsuarioDTO;
import com.BussinesOne.demo.models.Dtos.Requests.PerfilPostRequestDto;
import com.BussinesOne.demo.repositories.PerfilRepository;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class PerfilMapper {

    private final PerfilRepository perfilRepository;

    public static PerfilDTO toDTO(Perfil perfil) {
        if (perfil == null) return null;
        PerfilDTO dto = new PerfilDTO();
        dto.setId(perfil.getId());
        dto.setNombre(perfil.getNombre());
        dto.setEmail(perfil.getEmail());
        dto.setRol(perfil.getRol());
        dto.setDireccion(perfil.getDireccion());
        dto.setInfoPersonal(perfil.getInfoPersonal());
        dto.setFormacionAcademica(perfil.getFormacionAcademica());
        dto.setDatosPersonales(perfil.getDatosPersonales());
        // Si decidiste incluir productos en el PerfilDTO:
        if (perfil.getProductos() != null) {
            dto.setProductos(
                perfil.getProductos().stream()
                      .map(p -> {
                          ProductoSinUsuarioDTO pdto = new ProductoSinUsuarioDTO();
                          pdto.setCodigo(p.getCodigo());
                          pdto.setNombre(p.getNombre());
                          pdto.setPrecio(p.getPrecio());
                          pdto.setFoto(p.getFoto());
                          return pdto;
                      })
                      .collect(Collectors.toList())
            );
        }
        return dto;
    }

    public static Perfil toEntity(PerfilPostRequestDto dto) {
        if (dto == null) return null;
        Perfil perfil = new Perfil();
        perfil.setNombre(dto.getNombre());
        perfil.setRol(dto.getRol());
        perfil.setEmail(dto.getEmail());
        perfil.setDireccion(dto.getDireccion());
        perfil.setInfoPersonal(dto.getInfoPersonal());
        perfil.setFormacionAcademica(dto.getFormacionAcademica());
        perfil.setDatosPersonales(dto.getDatosPersonales());
        return perfil;
    }
}

