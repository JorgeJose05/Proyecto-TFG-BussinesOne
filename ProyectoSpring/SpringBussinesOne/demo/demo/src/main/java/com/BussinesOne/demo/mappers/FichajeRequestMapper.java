package com.BussinesOne.demo.mappers;

import org.springframework.stereotype.Component;

import com.BussinesOne.demo.models.Factura;
import com.BussinesOne.demo.models.FacturaProducto;
import com.BussinesOne.demo.models.Fichaje;
import com.BussinesOne.demo.models.Perfil;
import com.BussinesOne.demo.models.Producto;
import com.BussinesOne.demo.models.Dtos.PerfilDTO;
import com.BussinesOne.demo.models.Dtos.ProductoDto;
import com.BussinesOne.demo.models.Dtos.Requests.FacturaPostRequestDto;
import com.BussinesOne.demo.models.Dtos.Requests.FacturaProductoPostRequestDto;
import com.BussinesOne.demo.models.Dtos.Requests.FichajePostRequestDto;
import com.BussinesOne.demo.models.Dtos.Requests.ProductoPostRequestDto;

import lombok.RequiredArgsConstructor;

public class FichajeRequestMapper {
    public static Fichaje toEntity(FichajePostRequestDto dto) {
        if (dto == null) return null;
        Fichaje fichaje = new Fichaje();
        Perfil empleado = new Perfil();
        empleado.setId(dto.getEmpleadoId());
        fichaje.setEmpleado(empleado);
        fichaje.setFecha(dto.getFecha());
        fichaje.setHoraEntrada(dto.getHoraEntrada());
        fichaje.setHoraSalida(dto.getHoraSalida());
        fichaje.setTiempoReal(dto.getTiempoReal());
        // createdAt se setea con @PrePersist, si dto.createdAt != null, asignar
        if (dto.getCreatedAt() != null) {
            fichaje.setCreatedAt(dto.getCreatedAt());
        }
        return fichaje;
    }
}
