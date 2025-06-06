package com.BussinesOne.demo.mappers;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.BussinesOne.demo.models.Factura;
import com.BussinesOne.demo.models.FacturaProducto;
import com.BussinesOne.demo.models.Perfil;
import com.BussinesOne.demo.models.Producto;
import com.BussinesOne.demo.models.Dtos.PerfilDTO;
import com.BussinesOne.demo.models.Dtos.ProductoDto;
import com.BussinesOne.demo.models.Dtos.Requests.FacturaPostRequestDto;
import com.BussinesOne.demo.models.Dtos.Requests.ProductoPostRequestDto;

import lombok.RequiredArgsConstructor;

public class FacturaRequestMapper {
   public static Factura toEntity(
        FacturaPostRequestDto dto,
        Function<Long, Producto> findProductoById
    ) {
        if (dto == null) return null;

        // 1) Cabecera
        Factura factura = new Factura();
        factura.setPrecioTotal(dto.getPrecioTotal());
        factura.setFecha(dto.getFecha());

        Perfil perfil = new Perfil();
        perfil.setId(dto.getUsuarioId());
        factura.setUsuario(perfil);

        // 2) Items
        List<FacturaProducto> detalles = dto.getItems().stream().map(itemDto -> {
            Producto prod = findProductoById.apply(itemDto.getProductoId());
            FacturaProducto fp = new FacturaProducto();
            fp.setFactura(factura);
            fp.setProducto(prod);
            fp.setCantidad(itemDto.getCantidad());
            fp.setPrecioUnitario(itemDto.getPrecioUnitario());
            fp.setPrecioSubtotal(itemDto.getPrecioUnitario() * itemDto.getCantidad());
            return fp;
        }).collect(Collectors.toList());

        factura.setItems(detalles);
        return factura;
    }
}