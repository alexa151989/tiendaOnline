package com.tienda.online.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.online.models.Categoria;
import com.tienda.online.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	private CategoriaRepository categoriaRepository;

	@Autowired
	public CategoriaService(CategoriaRepository categoriaRepository) {
		super();
		this.categoriaRepository = categoriaRepository;
	}
	
	public Categoria guardar(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	
	public List<Categoria> obtenerTodos(){
		return (List<Categoria>) categoriaRepository.findAll();
	}
	
	public void eliminar(String codigo) {
		categoriaRepository.delete(codigo);
	}
}
