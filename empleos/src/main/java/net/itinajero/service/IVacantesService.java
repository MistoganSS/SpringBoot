package net.itinajero.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.itinajero.model.Vacante;

public interface IVacantesService {
	
	List<Vacante> buscarTodas();
	Vacante findId(Integer idVacante);
	void save(Vacante vacante);
	List<Vacante> findDesatacadas();
	void delete(Integer idVacante);
	List<Vacante> searchByExample(Example<Vacante> example);
	Page<Vacante> buscarTodas(Pageable page);
}
