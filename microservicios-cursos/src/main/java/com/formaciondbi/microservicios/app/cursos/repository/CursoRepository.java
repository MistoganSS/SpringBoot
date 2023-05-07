package com.formaciondbi.microservicios.app.cursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.formaciondbi.microservicios.app.cursos.models.entity.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	@Query("SELECT c FROM Curso c JOIN FETCH c.alumnos a WHERE a.id=?1")
	public Curso findyCursoByAlumno(Long id);
	
}
