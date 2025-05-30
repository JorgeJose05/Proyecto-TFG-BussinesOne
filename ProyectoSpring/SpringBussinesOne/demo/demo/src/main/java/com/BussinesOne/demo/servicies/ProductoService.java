package com.BussinesOne.demo.servicies;

import java.util.List;

import com.BussinesOne.demo.models.Producto;
import com.BussinesOne.demo.models.Dtos.Requests.ProductoPostRequestDto;

public interface ProductoService {
    List<Producto> getAllProductos();
    Producto getProductoByID(long id);
    Producto creaProducto(ProductoPostRequestDto dto);
}
