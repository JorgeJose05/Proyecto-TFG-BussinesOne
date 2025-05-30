package com.BussinesOne.demo.servicies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BussinesOne.demo.mappers.FacturaRequestMapper;
import com.BussinesOne.demo.mappers.FichajeRequestMapper;
import com.BussinesOne.demo.models.Factura;
import com.BussinesOne.demo.models.Fichaje;
import com.BussinesOne.demo.models.Producto;
import com.BussinesOne.demo.models.Dtos.Requests.FichajePostRequestDto;
import com.BussinesOne.demo.repositories.FichajeRepository;
import com.BussinesOne.demo.repositories.ProductoRepository;

@Service
public class FichajeServiceImpl implements FichajeService{

 private FichajeRepository fichajeRepository;
 @Autowired
 public FichajeServiceImpl(FichajeRepository fichajeRepository){
 this.fichajeRepository = fichajeRepository;
 }
 @Override
 public List<Fichaje> getAllFichajes() {
 return fichajeRepository.findAll();
 }
 @Override
 public Fichaje getFichajeByID(long id) {
    return fichajeRepository.findById(id).orElse(null);
 }
 @Override
 public Fichaje crearFichaje(FichajePostRequestDto dto) {
   Fichaje entidad = FichajeRequestMapper.toEntity(dto);

        // 2. Guardar en BD
        Fichaje guardada = fichajeRepository.save(entidad);

        return guardada;
   
 }
}