package com.BussinesOne.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.BussinesOne.demo.mappers.FacturaRequestMapper;
import com.BussinesOne.demo.models.Factura;
import com.BussinesOne.demo.models.FacturaProducto;
import com.BussinesOne.demo.models.Perfil;
import com.BussinesOne.demo.models.Producto;
import com.BussinesOne.demo.models.Dtos.Requests.FacturaPostRequestDto;
import com.BussinesOne.demo.models.Dtos.Requests.ProductoPostRequestDto;
import com.BussinesOne.demo.repositories.FacturaRepository;
import com.BussinesOne.demo.repositories.PerfilRepository;
import com.BussinesOne.demo.repositories.ProductoRepository;
import com.BussinesOne.demo.servicies.FacturaService;
import com.BussinesOne.demo.servicies.ProductoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Factura")
public class FacturaRestController {
    FacturaService facturaService;

    private final PerfilRepository perfilRepo;
    private final ProductoRepository productoRepo;
    private final FacturaRepository facturaRepo;

    @Autowired
    public FacturaRestController(FacturaService facturaService, PerfilRepository perfilRepo,
            ProductoRepository productoRepo, FacturaRepository facturaRepo) {
        this.facturaService = facturaService;
        this.perfilRepo = perfilRepo;
        this.productoRepo = productoRepo;
        this.facturaRepo = facturaRepo;
    }

    @GetMapping("/GET")
    public ResponseEntity<List<Factura>> getAllFacturas() {
        return ResponseEntity.ok(facturaService.getAllFacturas());
    }

    @PostMapping("/POST")
    public ResponseEntity<Factura> createFactura(@Valid @RequestBody FacturaPostRequestDto dto) {

        Perfil usuario = perfilRepo.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Factura facturaParaGuardar = FacturaRequestMapper.toEntity(
            dto,
            // Esta función se encarga de buscar cada Producto en la BD
            (Long productoId) -> productoRepo.findById(productoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con ID " + productoId))
        );

        facturaParaGuardar.setUsuario(usuario);

        Factura saved = facturaRepo.save(facturaParaGuardar);

        Factura fullyLoaded = facturaRepo.findById(saved.getFacturaId())
                .orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error recargando factura")
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(fullyLoaded);

    }

    @DeleteMapping("/DELETE/{id}")
    public ResponseEntity<Void> deleteFactura(@PathVariable Long id) {
        Factura factura = facturaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Factura no encontrada"));

        facturaRepo.delete(factura);
        return ResponseEntity.noContent().build();
    }

}
