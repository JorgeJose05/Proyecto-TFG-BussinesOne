package com.BussinesOne.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.BussinesOne.demo.mappers.ProductoMapper;
import com.BussinesOne.demo.models.Perfil;
import com.BussinesOne.demo.models.Producto;
import com.BussinesOne.demo.models.Dtos.Requests.ProductoPostRequestDto;
import com.BussinesOne.demo.repositories.PerfilRepository;
import com.BussinesOne.demo.repositories.ProductoRepository;
import com.BussinesOne.demo.servicies.ProductoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/Producto")
public class ProductoRestController {
    ProductoService productoService;

    private final PerfilRepository perfilRepo;
    private final ProductoRepository productoRepo;

    @Autowired
    public ProductoRestController(ProductoService productoService, PerfilRepository perfilRepo,
            ProductoRepository productoRepo) {
        this.productoService = productoService;
        this.perfilRepo = perfilRepo;
        this.productoRepo = productoRepo;
    }

    @GetMapping("/GET")
    public ResponseEntity<List<Producto>> getAllProductos() {
        return ResponseEntity.ok(productoService.getAllProductos());
    }

    @GetMapping("/GET/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        Producto producto = productoService.getProductoByID(id);
        if (producto == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.ok(producto); // 200 OK
    }

    @PostMapping("/POST")
    public ResponseEntity<Producto> createProducto(@Valid @RequestBody ProductoPostRequestDto dto) {
        if (productoRepo.existsById(dto.getCodigo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El código ya existe.");
        }

        Perfil perfil = perfilRepo.findById(dto.getUsuarioCreadorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfil no encontrado"));
        Producto p = new Producto();
        p.setNombre(dto.getNombre());
        p.setPrecio(dto.getPrecio());
        p.setFoto(dto.getFoto());
        p.setCodigo(dto.getCodigo());
        p.setUsuarioCreador(perfil);
        Producto saved = productoRepo.save(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);

    }

    @DeleteMapping("/DELETE/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        Producto producto = productoRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        boolean enUso = !producto.getFacturaProductos().isEmpty();
        if (enUso) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar: el producto está en uso en facturas.");
        }

        productoRepo.delete(producto);
        return ResponseEntity.noContent().build();
    }

}
