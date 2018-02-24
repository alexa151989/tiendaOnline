package com.tienda.online.controller;

import java.util.List;
import java.util.NoSuchElementException;

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

import com.tienda.online.model.Usuario;
import com.tienda.online.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController extends BaseController{

	private UsuarioService usuarioService;

	public UsuarioController() {

	}

	@Autowired
	public UsuarioController(UsuarioService usuarioService) {
		super();
		this.usuarioService = usuarioService;
	}

	@PostMapping(produces = "application/json")
	public Usuario guardarUsuario(@RequestBody @Validated Usuario usuario) {
		try {
			Usuario usuarioNuevo = usuarioService.guardar(usuario);
			if (usuarioNuevo == null) {
				throw new DataIntegrityViolationException("Ya existe el usuario con el email:" + usuario.getEmail());
			}
			return usuarioNuevo;
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException(e.getMessage());
		}
	}

	@GetMapping(produces = "application/json")
	public List<Usuario> obtenerTodos() {
		try {
			return usuarioService.obtenerTodos();
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Error en el consumo del servicio:" + e.getMessage());
		}
	}

	@PutMapping(produces = "application/json")
	public Usuario actualizarUsuario(@RequestBody @Validated Usuario usuario) {
		try {
			return usuarioService.guardar(usuario);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMessage());
		}
	}

	@RequestMapping(path = "/{id}", produces = "application/json", method = RequestMethod.DELETE)
	public void eliminar(@PathVariable(value = "id") Integer id) {
		try {
			usuarioService.eliminar(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMessage());
		}
	}

}
