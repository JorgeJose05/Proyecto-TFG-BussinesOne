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

import com.BussinesOne.demo.models.Fichaje;
import com.BussinesOne.demo.models.Perfil;
import com.BussinesOne.demo.models.Producto;
import com.BussinesOne.demo.models.Dtos.Requests.FichajePostRequestDto;
import com.BussinesOne.demo.models.Dtos.Requests.ProductoPostRequestDto;
import com.BussinesOne.demo.repositories.FichajeRepository;
import com.BussinesOne.demo.repositories.PerfilRepository;
import com.BussinesOne.demo.servicies.FichajeService;
import com.BussinesOne.demo.servicies.ProductoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Fichaje")
public class FichajeRestController {
    FichajeService fichajeService;

    private final PerfilRepository perfilRepo;
    private final FichajeRepository fichajeRepo;

    @Autowired
    public FichajeRestController(FichajeService fichajeService, PerfilRepository perfilRepo,
            FichajeRepository fichajeRepo) {
        this.fichajeService = fichajeService;
        this.perfilRepo = perfilRepo;
        this.fichajeRepo = fichajeRepo;
    }

    @GetMapping("/GET")
    public ResponseEntity<List<Fichaje>> getAllFichajes() {
        return ResponseEntity.ok(fichajeService.getAllFichajes());
    }

    @GetMapping("/GET/{id}")
    public ResponseEntity<Fichaje> getFichajeByID(@PathVariable long id) {
        Fichaje fichaje = fichajeService.getFichajeByID(id);
        if (fichaje == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.ok(fichaje);
    }

    @PostMapping("/POST")
    public ResponseEntity<Fichaje> createFichaje(@Valid @RequestBody FichajePostRequestDto dto) {

        Perfil empleado = perfilRepo.findById(dto.getEmpleadoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado"));

        Fichaje f = new Fichaje();
        f.setEmpleado(empleado);
        f.setFecha(dto.getFecha());
        f.setHoraEntrada(dto.getHoraEntrada());
        f.setHoraSalida(dto.getHoraSalida());
        f.setTiempoReal(dto.getTiempoReal() != null ? dto.getTiempoReal() : true);
        // createdAt se pone en @PrePersist

        Fichaje saved = fichajeRepo.save(f);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/DELETE/{id}")
    public ResponseEntity<Void> deleteFichaje(@PathVariable Long id) {
        Fichaje fichaje = fichajeRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Fichaje no encontrado"));

        fichajeRepo.delete(fichaje);
        return ResponseEntity.noContent().build();
    }

}
