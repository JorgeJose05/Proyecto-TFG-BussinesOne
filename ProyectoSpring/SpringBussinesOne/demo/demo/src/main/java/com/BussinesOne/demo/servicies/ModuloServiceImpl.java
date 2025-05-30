package com.BussinesOne.demo.servicies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BussinesOne.demo.mappers.FacturaRequestMapper;
import com.BussinesOne.demo.mappers.ModuloRequestMapper;
import com.BussinesOne.demo.models.Factura;
import com.BussinesOne.demo.models.Modulo;
import com.BussinesOne.demo.models.Producto;
import com.BussinesOne.demo.models.Dtos.Requests.ModuloPostRequestDto;
import com.BussinesOne.demo.repositories.ModuloRepository;
import com.BussinesOne.demo.repositories.ProductoRepository;

@Service
public class ModuloServiceImpl implements ModuloService{
 private ModuloRepository moduloRepository;
 @Autowired
 public ModuloServiceImpl(ModuloRepository moduloRepository){
 this.moduloRepository = moduloRepository;
 }
 @Override
 public List<Modulo> getAllModulos() {
 return moduloRepository.findAll();
 }
 @Override
 public Modulo getModuloById(long id) {
    return moduloRepository.findById(id).orElse(null);
 }
 @Override
 public Modulo crearModulo(ModuloPostRequestDto dto) {
   Modulo entidad = ModuloRequestMapper.toEntity(dto);

        // 2. Guardar en BD
   Modulo guardada = moduloRepository.save(entidad);

        return guardada;
 }
}