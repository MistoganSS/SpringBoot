package com.formaciondbi.microservicios.app.cursos.services;


import com.formaciondbi.microservicios.app.cursos.models.entity.Curso;
import com.formaciondbi.microservicios.commons.service.CommonService;

public interface CursoService extends CommonService<Curso> {

	public Curso findyCursoByAlumno(Long id);
	
	public Iterable<Long> getExamenIdsWithRespuestaAlumno(Long alumnoId);
}
