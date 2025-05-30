package com.BussinesOne.demo.mappers;

import org.springframework.stereotype.Component;

import com.BussinesOne.demo.models.Factura;
import com.BussinesOne.demo.models.FacturaProducto;
import com.BussinesOne.demo.models.Perfil;
import com.BussinesOne.demo.models.Producto;
import com.BussinesOne.demo.models.Dtos.PerfilDTO;
import com.BussinesOne.demo.models.Dtos.ProductoDto;
import com.BussinesOne.demo.models.Dtos.Requests.FacturaPostRequestDto;
import com.BussinesOne.demo.models.Dtos.Requests.FacturaProductoPostRequestDto;
import com.BussinesOne.demo.models.Dtos.Requests.ProductoPostRequestDto;

import lombok.RequiredArgsConstructor;

public class FacturaProductoRequestMapper {
    public static FacturaProducto toEntity(FacturaProductoPostRequestDto dto) {
        if (dto == null) return null;
        FacturaProducto fp = new FacturaProducto();
        // relacion con factura
        Factura factura = new Factura();
        factura.setFacturaId(dto.getFacturaId());
        fp.setFactura(factura);
        // relacion con producto
        Producto producto = new Producto();
        producto.setCodigo(dto.getProductoId());
        fp.setProducto(producto);
        fp.setCantidad(dto.getCantidad());
        fp.setPrecioUnitario(dto.getPrecioUnitario());
        fp.setPrecioSubtotal(dto.getPrecioSubtotal());
        return fp;
    }
}