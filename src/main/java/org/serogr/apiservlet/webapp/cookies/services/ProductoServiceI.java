package org.serogr.apiservlet.webapp.cookies.services;

import org.serogr.apiservlet.webapp.cookies.models.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoServiceI {
    List<Producto> listar();
    Optional<Producto> buscarProducto(String nombre);
}
