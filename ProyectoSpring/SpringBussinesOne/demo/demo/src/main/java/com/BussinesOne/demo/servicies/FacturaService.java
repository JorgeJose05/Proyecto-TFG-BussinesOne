package com.BussinesOne.demo.servicies;

import java.util.List;

import com.BussinesOne.demo.models.Factura;
import com.BussinesOne.demo.models.Producto;
import com.BussinesOne.demo.models.Dtos.Requests.FacturaPostRequestDto;

public interface FacturaService {
    List<Factura> getAllFacturas();
    Factura createFactura(FacturaPostRequestDto dto);
}
