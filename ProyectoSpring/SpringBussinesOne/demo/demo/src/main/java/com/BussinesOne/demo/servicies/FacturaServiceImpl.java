package com.BussinesOne.demo.servicies;

import java.util.List;
import java.util.function.Function;

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
    private final ProductoRepository productoRepo;

    @Autowired
    public FacturaServiceImpl(FacturaRepository facturaRepository, ProductoRepository productoRepository) {
        this.facturaRepository = facturaRepository;
        this.productoRepo = productoRepository;

    }

    @Override
    public List<Factura> getAllFacturas() {
        return facturaRepository.findAll();
    }

    @Override
    public Factura createFactura(FacturaPostRequestDto dto) {
        // 1. Mapear de DTO de petición a entidad

         Function<Long, Producto> fetchProducto = prodId -> productoRepo.findById(prodId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID " + prodId));
        Factura entidad = FacturaRequestMapper.toEntity(dto, fetchProducto);

        // 2. Guardar en BD
        Factura guardada = facturaRepository.save(entidad);

        return guardada;
    }
}