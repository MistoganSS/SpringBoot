package net.itinajero.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.itinajero.model.Categoria;

public interface ICategoriasService {
	void save(Categoria categoria);
	List<Categoria> buscarTodas();
	Categoria findId(Integer idCategoria);
	void delete(Integer idCategoria);
	Page<Categoria> buscarTodas(Pageable pageable);
}
