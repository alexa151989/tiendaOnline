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

import com.tienda.online.models.IngresoProducto;
import com.tienda.online.services.IngresoProductoService;

@RestController
@RequestMapping("/ingreso")
public class IngresoProductoController {

private static final Logger logger = LoggerFactory.getLogger(IngresoProductoController.class); 
	
	private IngresoProductoService ingresoProductoService;

	@Autowired
	public IngresoProductoController(IngresoProductoService ingresoProductoService) {
		super();
		this.ingresoProductoService = ingresoProductoService;
	}
	
	@PostMapping(produces="application/json")
	public IngresoProducto guardar(@RequestBody @Validated IngresoProducto ingresoProducto) {
		try {
			return ingresoProductoService.guardar(ingresoProducto);
		} catch (DataIntegrityViolationException e) {
			logger.info("Error en el consumo del servicio guardaIngresoProducto: " + e.getMessage());
			throw new DataIntegrityViolationException(e.getMessage());
		}
	}
	
	@GetMapping(produces="application/json")
	public List<IngresoProducto> obtenerTodos() {
		try {
			return ingresoProductoService.obtenerTodos();
		} catch (NoSuchElementException e) {
			logger.info("Error en el consumo del servicio obtenerTodos: " + e.getMessage());
			throw new NoSuchElementException(e.getMessage());
		}
	}
	
	
	@PutMapping(produces="application/json")
	public IngresoProducto actualizar(@RequestBody @Validated IngresoProducto ingresoProducto) {
		return ingresoProductoService.guardar(ingresoProducto);
	}
	
	@RequestMapping(path="/{codigo}", produces="application/json", method=RequestMethod.DELETE)
	public void eliminar(@PathVariable(value="codigo") Integer codigo) {
		ingresoProductoService.eliminar(codigo);
	}
}
