package org.serogr.apiservlet.webapp.cookies.services;

import org.serogr.apiservlet.webapp.cookies.models.Producto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductoServiceImpl implements ProductoServiceI{
    @Override
    public List<Producto> listar() {
        return Arrays.asList(new Producto(1L, "Notebook", "Computación", 13500),
                new Producto(2L, "Escritorio", "Oficina", 1500),
                new Producto(3L, "Teclado mecánico", "Computación", 2000));
    }

    @Override
    public Optional<Producto> buscarProducto(String nombre) {
        ProductoServiceI service = new ProductoServiceImpl();
        Optional<Producto> encontrado = service.listar().stream().filter(producto -> {
            if (nombre == null || nombre.isBlank()){
                return false;
            }
            return producto.getNombre().contains(nombre);
        }).findFirst();;
        return encontrado;
    }
}
