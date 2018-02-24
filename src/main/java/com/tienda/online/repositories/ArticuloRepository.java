package com.tienda.online.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tienda.online.models.Articulo;

@Repository
public interface ArticuloRepository extends CrudRepository<Articulo, Integer>{

}
