package com.BussinesOne.demo.controllers;

import java.util.List;

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

import com.BussinesOne.demo.models.Modulo;
import com.BussinesOne.demo.models.Perfil;
import com.BussinesOne.demo.models.Producto;
import com.BussinesOne.demo.models.Dtos.Requests.ModuloPostRequestDto;
import com.BussinesOne.demo.models.Dtos.Requests.ProductoPostRequestDto;
import com.BussinesOne.demo.repositories.ModuloRepository;
import com.BussinesOne.demo.servicies.ModuloService;
import com.BussinesOne.demo.servicies.ProductoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Modulo")
public class ModuloRestController {
    ModuloService moduloService;
    ModuloRepository moduloRepo;

    @Autowired
    public ModuloRestController(ModuloService moduloService, ModuloRepository moduloRepository) {
        this.moduloService = moduloService;
        this.moduloRepo = moduloRepository;
    }

    @GetMapping("/GET")
    public ResponseEntity<List<Modulo>> getAllModulos() {
        return ResponseEntity.ok(moduloService.getAllModulos());
    }

    @GetMapping("/GET/{id}")
    public ResponseEntity<Modulo> getModuloById(@PathVariable long id) {
        Modulo modulo = moduloService.getModuloById(id);
        if (modulo == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.ok(modulo);
    }

    

    @PostMapping("/POST")
    public ResponseEntity<Modulo> createModulo(@Valid @RequestBody ModuloPostRequestDto entity) {

        Modulo newModulo = moduloService.crearModulo(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(newModulo);

    }
    @DeleteMapping("/DELETE/{id}")
    public ResponseEntity<Void> deleteModulo(@PathVariable Long id) {
        Modulo modulo = moduloRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Módulo no encontrado"));

        // Suponiendo que Modulo no se usa en cascada con otras entidades,
        // lo eliminamos directamente.
        moduloRepo.delete(modulo);
        return ResponseEntity.noContent().build();
    }
}
