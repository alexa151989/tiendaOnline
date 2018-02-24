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

import com.tienda.online.models.Usuario;
import com.tienda.online.services.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController extends BaseController{

private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class); 
	
	private UsuarioService usuarioService;

	@Autowired
	public UsuarioController(UsuarioService usuarioService) {
		super();
		this.usuarioService = usuarioService;
	}
	
	/**
	 * Metodo que permite guardar un usuario
	 * @param usuario
	 * @return
	 */
	@PostMapping(produces="application/json")
	public Usuario guardar(@RequestBody @Validated Usuario usuario) {
		try {
			Usuario nuevoUsuario = usuarioService.guardar(usuario);
			if(nuevoUsuario == null) {
				throw new DataIntegrityViolationException("Ya existe un usuario con el email: " + usuario.getEmail());
			}
			return nuevoUsuario;
		} catch (NoSuchElementException e) {
			logger.info("Error en el consumo del servicio guardar Usuario: " + e.getMessage());
			throw new NoSuchElementException("Error en el consumo del servicio guardar Usuario: ");
		}
		
	}
	
	@GetMapping(produces="application/json")
	public List<Usuario> obtenerTodos() {
		try {
			return usuarioService.obtenerTodos();
		} catch (NoSuchElementException e) {
			logger.info("Error en el consumo del servicio obtenerTodos: " + e.getMessage());
			throw new NoSuchElementException(e.getMessage());
		}
	}
	
	
	@PutMapping(produces="application/json")
	public Usuario actualizar(@RequestBody @Validated Usuario usuario) {
		return usuarioService.guardar(usuario);
	}
	
	@RequestMapping(path="/{id}", produces="application/json", method=RequestMethod.DELETE)
	public void eliminar(@PathVariable(value="id") Integer id) {
		usuarioService.eliminar(id);
	}

}
