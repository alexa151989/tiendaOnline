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

import com.tienda.online.models.Categoria;
import com.tienda.online.services.CategoriaService;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

private static final Logger logger = LoggerFactory.getLogger(CategoriaController.class); 
	
	private CategoriaService categoriaService;

	@Autowired
	public CategoriaController(CategoriaService categoriaService) {
		super();
		this.categoriaService = categoriaService;
	}
	
	@PostMapping(produces="application/json")
	public Categoria guardar(@RequestBody @Validated Categoria categoria) {
		try {
			Categoria cat = categoriaService.guardar(categoria);
			if(cat == null) {
				throw new DataIntegrityViolationException("Ya existe una categoría con código: " + categoria.getCodigo());
			}
			return cat;
		} catch (NoSuchElementException e) {
			logger.info("Error en el consumo del servicio guardaCategoria: " + e.getMessage());
			throw new NoSuchElementException(e.getMessage());
		}
	}
	
	@GetMapping(produces="application/json")
	public List<Categoria> obtenerTodos() {
		try {
			return categoriaService.obtenerTodos();
		} catch (NoSuchElementException e) {
			logger.info("Error en el consumo del servicio obtenerTodos: " + e.getMessage());
			throw new NoSuchElementException(e.getMessage());
		}
	}
	
	
	@PutMapping(produces="application/json")
	public Categoria actualizar(@RequestBody @Validated Categoria categoria) {
		return categoriaService.guardar(categoria);
	}
	
	@RequestMapping(path="/{codigo}", produces="application/json", method=RequestMethod.DELETE)
	public void eliminar(@PathVariable(value="codigo") String codigo) {
		categoriaService.eliminar(codigo);
	}
}
