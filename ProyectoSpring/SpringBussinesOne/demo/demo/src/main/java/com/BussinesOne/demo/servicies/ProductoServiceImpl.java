package com.BussinesOne.demo.servicies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BussinesOne.demo.mappers.ProductoMapper;
import com.BussinesOne.demo.models.Producto;
import com.BussinesOne.demo.models.Dtos.Requests.ProductoPostRequestDto;
import com.BussinesOne.demo.repositories.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService{
 private ProductoRepository productoRepository;
 @Autowired
 public ProductoServiceImpl(ProductoRepository productoRepository){
 this.productoRepository = productoRepository;
 }
 @Override
 public List<Producto> getAllProductos() {
 return productoRepository.findAll();
 }
 @Override
 public Producto getProductoByID(long id) {
    return productoRepository.findById(id).orElse(null);
 }
 @Override
 public Producto creaProducto(ProductoPostRequestDto dto) {
   Producto entidad = ProductoMapper.toEntity(dto);

   Producto guardad = productoRepository.save(entidad);
   
   return guardad;
 }
}