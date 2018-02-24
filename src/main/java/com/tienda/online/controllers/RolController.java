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

import com.tienda.online.models.Rol;
import com.tienda.online.services.RolService;

@RestController
@RequestMapping("/rol")
public class RolController {

private static final Logger logger = LoggerFactory.getLogger(RolController.class); 
	
	private RolService rolService;

	@Autowired
	public RolController(RolService rolService) {
		super();
		this.rolService = rolService;
	}
	
	@PostMapping(produces="application/json")
	public Rol guardar(@RequestBody @Validated Rol rol) {
		try {
			Rol newRol = rolService.guardar(rol);
			if(newRol == null) {
				throw new DataIntegrityViolationException("Ya existe un rol con nombre: " + rol.getRol());
			}
			return newRol;
		} catch (NoSuchElementException e) {
			logger.info("Error en el consumo del servicio guardaRol: " + e.getMessage());
			throw new NoSuchElementException(e.getMessage());
		}
	}
	
	@GetMapping(produces="application/json")
	public List<Rol> obtenerTodos() {
		try {
			return rolService.obtenerTodos();
		} catch (NoSuchElementException e) {
			logger.info("Error en el consumo del servicio obtenerTodos: " + e.getMessage());
			throw new NoSuchElementException(e.getMessage());
		}
	}
	
	
	@PutMapping(produces="application/json")
	public Rol actualizar(@RequestBody @Validated Rol rol) {
		return rolService.guardar(rol);
	}
	
	@RequestMapping(path="/{id}", produces="application/json", method=RequestMethod.DELETE)
	public void eliminar(@PathVariable(value="id") Integer id) {
		rolService.eliminar(id);
	}
}
