package com.formaciondbi.microservicios.app.cursos.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formaciondbi.microservicios.app.cursos.clients.RespuestaFeignClient;
import com.formaciondbi.microservicios.app.cursos.models.entity.Curso;
import com.formaciondbi.microservicios.app.cursos.repository.CursoRepository;
import com.formaciondbi.microservicios.commons.service.CommonServiceImpl;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, CursoRepository> implements CursoService {

	@Autowired
	private RespuestaFeignClient client;
	
	@Override
	@Transactional(readOnly = true)
	public Curso findyCursoByAlumno(Long id) {
		
		return repository.findyCursoByAlumno(id);
	}

	@Override
	public Iterable<Long> getExamenIdsWithRespuestaAlumno(Long alumnoId) {
		return client.getExamenIdsWithRespuestaAlumno(alumnoId);
	}

}
