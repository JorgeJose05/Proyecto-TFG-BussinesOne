package com.BussinesOne.demo.servicies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BussinesOne.demo.mappers.PerfilMapper;
import com.BussinesOne.demo.models.Perfil;
import com.BussinesOne.demo.models.Producto;
import com.BussinesOne.demo.models.Dtos.Requests.PerfilPostRequestDto;
import com.BussinesOne.demo.repositories.PerfilRepository;
import com.BussinesOne.demo.repositories.ProductoRepository;

@Service
public class PerfilServiceImpl implements PerfilService{
 private PerfilRepository perfilRepository;
 @Autowired
 public PerfilServiceImpl(PerfilRepository perfilRepository){
 this.perfilRepository = perfilRepository;
 }
 @Override
 public List<Perfil> getAllPerfils() {
 return perfilRepository.findAll();
 }
 @Override
 public Perfil getPerfilById(long id) {
    return perfilRepository.findById(id).orElse(null);
 }
 @Override
 public Perfil crearPerfil(PerfilPostRequestDto dto) {
      Perfil entidad = PerfilMapper.toEntity(dto);
      Perfil guardad = perfilRepository.save(entidad);

      return guardad;
   
 }
}