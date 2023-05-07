package com.formaciondbi.microservicios.app.examenes.services;

import java.util.List;

import com.formaciondbi.microservicios.commons.examenes.models.entity.Asignatura;
import com.formaciondbi.microservicios.commons.examenes.models.entity.Examen;
import com.formaciondbi.microservicios.commons.service.CommonService;

public interface ExamenService extends CommonService<Examen>{

	public List<Examen> findByNombre(String term);
	
	public Iterable<Asignatura> findAllAsignaturas();
}
