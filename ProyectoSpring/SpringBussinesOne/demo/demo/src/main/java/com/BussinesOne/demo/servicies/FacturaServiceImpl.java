package com.BussinesOne.demo.servicies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BussinesOne.demo.mappers.FacturaRequestMapper;
import com.BussinesOne.demo.models.Factura;
import com.BussinesOne.demo.models.Producto;
import com.BussinesOne.demo.models.Dtos.Requests.FacturaPostRequestDto;
import com.BussinesOne.demo.repositories.FacturaRepository;
import com.BussinesOne.demo.repositories.ProductoRepository;

@Service
public class FacturaServiceImpl implements FacturaService {
    private FacturaRepository facturaRepository;

    @Autowired
    public FacturaServiceImpl(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    @Override
    public List<Factura> getAllFacturas() {
        return facturaRepository.findAll();
    }

    @Override
    public Factura createFactura(FacturaPostRequestDto dto) {
        // 1. Mapear de DTO de petición a entidad
        Factura entidad = FacturaRequestMapper.toEntity(dto);

        // 2. Guardar en BD
        Factura guardada = facturaRepository.save(entidad);

        return guardada;
    }
}