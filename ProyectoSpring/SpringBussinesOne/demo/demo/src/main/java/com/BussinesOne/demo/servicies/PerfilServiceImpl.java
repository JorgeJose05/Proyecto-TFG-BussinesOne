package com.BussinesOne.demo.servicies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BussinesOne.demo.mappers.PerfilMapper;
import com.BussinesOne.demo.models.Perfil;
import com.BussinesOne.demo.models.Producto;
import com.BussinesOne.demo.models.Dtos.Requests.PerfilPatchRequestDto;
import com.BussinesOne.demo.models.Dtos.Requests.PerfilPostRequestDto;
import com.BussinesOne.demo.repositories.PerfilRepository;
import com.BussinesOne.demo.repositories.ProductoRepository;

import jakarta.persistence.EntityNotFoundException;

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
 @Override
 public Perfil updatePerfil(Long id, PerfilPatchRequestDto patch){
    Perfil perfil = perfilRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Perfil no encontrado: " + id));

        if (patch.getNombre() != null)             perfil.setNombre(patch.getNombre());
        if (patch.getRol() != null)                perfil.setRol(patch.getRol());
        if (patch.getEmail() != null)              perfil.setEmail(patch.getEmail());
        if (patch.getContraseña() != null)         perfil.setContrasena(patch.getContraseña());
        if (patch.getDireccion() != null)          perfil.setDireccion(patch.getDireccion());
        if (patch.getInfoPersonal() != null)       perfil.setInfoPersonal(patch.getInfoPersonal());
        if (patch.getFormacionAcademica() != null) perfil.setFormacionAcademica(patch.getFormacionAcademica());
        if (patch.getDatosPersonales() != null)    perfil.setDatosPersonales(patch.getDatosPersonales());
        if (patch.getTelefono() != null)           perfil.setTelefono(patch.getTelefono());
        if (patch.getDni() != null)                perfil.setDni(patch.getDni());
        if (patch.getFechaNacimiento() != null)    perfil.setFechaNacimiento(patch.getFechaNacimiento());
        if (patch.getNumeroSeguridadSocial() != null)
                                                   perfil.setNumeroSeguridadSocial(patch.getNumeroSeguridadSocial());
        if (patch.getIban() != null)               perfil.setIban(patch.getIban());

        return perfilRepository.save(perfil);
 }



}