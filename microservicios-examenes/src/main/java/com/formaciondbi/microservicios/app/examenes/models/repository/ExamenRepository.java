package com.formaciondbi.microservicios.app.examenes.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.formaciondbi.microservicios.commons.examenes.models.entity.Examen;

public interface ExamenRepository extends JpaRepository<Examen, Long> {

	@Query("SELECT e FROM Examen e WHERE e.nombre LIKE %?1%")
	public List<Examen> findByNombre(String term);
}
