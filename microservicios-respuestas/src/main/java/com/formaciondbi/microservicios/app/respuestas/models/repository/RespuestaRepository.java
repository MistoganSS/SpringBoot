package com.formaciondbi.microservicios.app.respuestas.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.formaciondbi.microservicios.app.respuestas.models.entity.Respuesta;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

	@Query("SELECT r FROM Respuesta r JOIN FETCH r.alumno a JOIN FETCH r.pregunta p JOIN FETCH p.examen e WHERE a.id=?1 AND e.id=?2")
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);
	
	@Query("SELECT e.id FROM Respuesta r JOIN r.alumno a JOIN r.pregunta p JOIN p.examen e WHERE a.id=?1 GROUP BY e.id")
	public Iterable<Long> findExamenesWithRespuestaByAlumno(Long alumnoId);
}
