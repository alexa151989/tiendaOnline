package com.tienda.online.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.online.model.Compra;
import com.tienda.online.repository.CompraRepository;


@Service
public class CompraService {

	private CompraRepository compraRepository;

	public CompraService() {

	}

	@Autowired
	public CompraService(CompraRepository compraRepository) {
		super();
		this.compraRepository = compraRepository;
	}

	public Compra guardar(Compra compra) {
		compra.setFecha(new Date());
		compra.setNroDocumento("0000000" + compraRepository.count()+1);
		compra.getListaDetallecompra().forEach(detalle->{
			detalle.setCompra(compra);
		    detalle.setSubtotal(new BigDecimal(detalle.getCantidad()).multiply(detalle.getArticulo().getPrecio()));
		});
		return compraRepository.save(compra);
		
	}

	public List<Compra> obtenerTodos() {
		return (List<Compra>) compraRepository.findAll();
	}

	public long total() {
		return compraRepository.count();
	}

	public void eliminar(Integer id) {
		compraRepository.delete(id);
	}

}
