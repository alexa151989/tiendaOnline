package com.tienda.online.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.online.models.Articulo;
import com.tienda.online.repositories.ArticuloRepository;

@Service
public class ArticuloService {

	private ArticuloRepository articuloRepository;

	@Autowired
	public ArticuloService(ArticuloRepository articuloRepository) {
		super();
		this.articuloRepository = articuloRepository;
	}
	
	public Articulo guardar(Articulo articulo) {
		articulo.setFecha(new Date());
		return articuloRepository.save(articulo);
	}
	
	public List<Articulo> obtenerTodos(){
		return (List<Articulo>) articuloRepository.findAll();
	}
	
	public void eliminar(Integer codigo) {
		articuloRepository.delete(codigo);
	}
}
