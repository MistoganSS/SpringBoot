package com.formaciondbi.microservicios.app.usuarios.services;

import java.util.List;


import com.formaciondbi.microservicios.commons.alumnos.models.entity.Alumno;
import com.formaciondbi.microservicios.commons.service.CommonService;

public interface AlumnoService extends CommonService<Alumno>{
	
	public List<Alumno> findByNombreOrApellido(String term);
}
