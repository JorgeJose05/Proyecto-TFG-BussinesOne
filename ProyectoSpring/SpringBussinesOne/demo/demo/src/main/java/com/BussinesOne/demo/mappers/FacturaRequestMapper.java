package com.BussinesOne.demo.mappers;

import org.springframework.stereotype.Component;

import com.BussinesOne.demo.models.Factura;
import com.BussinesOne.demo.models.Perfil;
import com.BussinesOne.demo.models.Producto;
import com.BussinesOne.demo.models.Dtos.PerfilDTO;
import com.BussinesOne.demo.models.Dtos.ProductoDto;
import com.BussinesOne.demo.models.Dtos.Requests.FacturaPostRequestDto;
import com.BussinesOne.demo.models.Dtos.Requests.ProductoPostRequestDto;

import lombok.RequiredArgsConstructor;

public class FacturaRequestMapper {
    public static Factura toEntity(FacturaPostRequestDto dto) {
        if (dto == null) return null;
        Factura factura = new Factura();
        factura.setPrecioTotal(dto.getPrecioTotal());
        factura.setFecha(dto.getFecha());
        Perfil perfil = new Perfil();
        perfil.setId(dto.getUsuarioId());
        factura.setUsuario(perfil);
        return factura;
    }
}