package com.tienda.online.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.online.models.Articulo;
import com.tienda.online.services.ArticuloService;

@RestController
@RequestMapping("/articulo")
public class ArticuloController {

private static final Logger logger = LoggerFactory.getLogger(ArticuloController.class); 
	
	private ArticuloService articuloService;

	@Autowired
	public ArticuloController(ArticuloService articuloService) {
		super();
		this.articuloService = articuloService;
	}
	
	@PostMapping(produces="application/json")
	public Articulo guardar(@RequestBody @Validated Articulo articulo) {
		try {
			Articulo item = articuloService.guardar(articulo);
			if(item == null) {
				throw new DataIntegrityViolationException("Ya existe un articulo con nombre: " + articulo.getNombre());
			}
			return item;
		} catch (NoSuchElementException e) {
			logger.info("Error en el consumo del servicio guardaArticulo: " + e.getMessage());
			throw new NoSuchElementException(e.getMessage());
		}
	}
	
	@GetMapping(produces="application/json")
	public List<Articulo> obtenerTodos() {
		try {
			return articuloService.obtenerTodos();
		} catch (NoSuchElementException e) {
			logger.info("Error en el consumo del servicio obtenerTodos: " + e.getMessage());
			throw new NoSuchElementException(e.getMessage());
		}
	}
	
	
	@PutMapping(produces="application/json")
	public Articulo actualizar(@RequestBody @Validated Articulo articulo) {
		return articuloService.guardar(articulo);
	}
	
	@RequestMapping(path="/{codigo}", produces="application/json", method=RequestMethod.DELETE)
	public void eliminar(@PathVariable(value="codigo") Integer codigo) {
		articuloService.eliminar(codigo);
	}
}
