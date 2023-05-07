package com.formaciondbi.microservicios.app.respuestas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formaciondbi.microservicios.app.respuestas.models.entity.Respuesta;
import com.formaciondbi.microservicios.app.respuestas.services.RespuestaService;

@RestController
public class RespuestaController {

	@Autowired
	private RespuestaService service;
	
	@PostMapping("/")
	public ResponseEntity<?> create(@RequestBody Iterable<Respuesta> respuestas){
		Iterable<Respuesta> respuestasDb=service.saveAll(respuestas);
		return ResponseEntity.status(HttpStatus.CREATED).body(respuestasDb);
	}
	
	@GetMapping("/alumno/{alumnoId}/examen/{examenId}")
	public ResponseEntity<?> findRespuestaWithAlumnoExamen(@PathVariable Long alumnoId, @PathVariable Long examenId){
		Iterable<Respuesta> respuestas= service.findRespuestaByAlumnoByExamen(alumnoId, examenId);
		return ResponseEntity.ok(respuestas);
	}
	
	@GetMapping("/alumno/{alumnoId}/examenes-respondido")
	public ResponseEntity<?> getExamenIdsWithRespuestaAlumno(@PathVariable Long alumnoId){
		Iterable<Long> examenIds= service.findExamenesWithRespuestaByAlumno(alumnoId);
		return ResponseEntity.ok(examenIds);
	}
	
	
}
