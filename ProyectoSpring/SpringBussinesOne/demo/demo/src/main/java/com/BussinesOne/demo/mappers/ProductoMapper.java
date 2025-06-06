package com.BussinesOne.demo.mappers;

import org.springframework.stereotype.Component;

import com.BussinesOne.demo.models.Perfil;
import com.BussinesOne.demo.models.Producto;
import com.BussinesOne.demo.models.Dtos.PerfilDTO;
import com.BussinesOne.demo.models.Dtos.ProductoDto;
import com.BussinesOne.demo.models.Dtos.Requests.ProductoPostRequestDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductoMapper {

    public static ProductoDto toDTO(Producto producto) {
        ProductoDto dto = new ProductoDto();
        dto.setCodigo(producto.getCodigo());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        dto.setFoto(producto.getFoto());

        PerfilDTO perfilDTO = new PerfilDTO();
        Perfil perfil = producto.getUsuarioCreador();
        perfilDTO.setId(perfil.getId());
        perfilDTO.setNombre(perfil.getNombre());
        perfilDTO.setEmail(perfil.getEmail());
        perfilDTO.setRol(perfil.getRol());
        perfilDTO.setDireccion(perfil.getDireccion());
        perfilDTO.setInfoPersonal(perfil.getInfoPersonal());
        perfilDTO.setFormacionAcademica(perfil.getFormacionAcademica());
        perfilDTO.setDatosPersonales(perfil.getDatosPersonales());

        dto.setUsuarioCreador(perfilDTO.getId());
        return dto;
    }

    public static Producto toEntity(ProductoPostRequestDto dto) {
        if (dto == null) return null;
        Producto producto = new Producto();
        producto.setCodigo(dto.getCodigo());
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setFoto(dto.getFoto());
        
        // Crear perfil solo con el ID para referencia, sin más datos
        if (dto.getUsuarioCreadorId() != null) {
            Perfil perfil = new Perfil();
            perfil.setId(dto.getUsuarioCreadorId());
            producto.setUsuarioCreador(perfil);
        } else {
            producto.setUsuarioCreador(null);
        }

        return producto;
    }
}

