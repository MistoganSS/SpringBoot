package com.formaciondbi.microservicios.app.usuarios.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.formaciondbi.microservicios.commons.alumnos.models.entity.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

	@Query("SELECT a from Alumno a WHERE a.nombre LIKE %?1% or a.apellido LIKE %?1%")
	public List<Alumno> findByNombreOrApellido(String term);
}
