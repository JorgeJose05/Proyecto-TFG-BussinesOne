package com.BussinesOne.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.BussinesOne.demo.models.Perfil;
import com.BussinesOne.demo.models.Producto;
import com.BussinesOne.demo.models.Dtos.Requests.PerfilPatchRequestDto;
import com.BussinesOne.demo.models.Dtos.Requests.PerfilPostRequestDto;
import com.BussinesOne.demo.models.Dtos.Requests.ProductoPostRequestDto;
import com.BussinesOne.demo.repositories.PerfilRepository;
import com.BussinesOne.demo.servicies.PerfilService;
import com.BussinesOne.demo.servicies.ProductoService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/Perfil")
public class PerfilRestController {
    PerfilService perfilService;
    private final PerfilRepository perfilRepo;

    @Autowired
    public PerfilRestController(PerfilService perfilService, PerfilRepository perfilRepository) {
        this.perfilService = perfilService;
        this.perfilRepo = perfilRepository;
    }

    @GetMapping("/GET")
    public ResponseEntity<List<Perfil>> getAllPerfils() {
        return ResponseEntity.ok(perfilService.getAllPerfils());
    }

    @GetMapping("/GET/{id}")
    public ResponseEntity<Perfil> getPerfilByid(@PathVariable long id) {
        Perfil perfil = perfilService.getPerfilById(id);
        if (perfil == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.ok(perfil);
    }

    @PostMapping("/POST")
    public ResponseEntity<Perfil> createPerfil(@Valid @RequestBody PerfilPostRequestDto entity) {

        Perfil newPerfil = perfilService.crearPerfil(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPerfil);

    }

    @DeleteMapping("/DELETE/{id}")
    public ResponseEntity<Void> deletePerfil(@PathVariable Long id) {
        Perfil perfil = perfilRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfil no encontrado"));

        boolean tieneFacturas = !perfil.getFacturas().isEmpty();
        if (tieneFacturas) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar: el usuario tiene facturas asociadas.");
        }

        perfilRepo.delete(perfil);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/PATCH/{id}")
    public ResponseEntity<Perfil> patchPerfil(
        @PathVariable Long id,
        @Valid @RequestBody PerfilPatchRequestDto patchDto
    ) {
        try {
            Perfil actualizado = perfilService.updatePerfil(id, patchDto);
            return ResponseEntity.ok(actualizado);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

}
