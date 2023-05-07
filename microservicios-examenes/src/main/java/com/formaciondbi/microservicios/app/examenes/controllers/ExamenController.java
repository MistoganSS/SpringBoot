package com.formaciondbi.microservicios.app.examenes.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formaciondbi.microservicios.app.examenes.services.ExamenService;
import com.formaciondbi.microservicios.commons.controllers.CommonController;
import com.formaciondbi.microservicios.commons.examenes.models.entity.Examen;

import jakarta.validation.Valid;

@RestController
public class ExamenController extends CommonController<Examen, ExamenService> {

	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@Valid @RequestBody Examen examen, BindingResult result, @PathVariable Long id){
		
		if(result.hasErrors()) {
			return this.validation(result);
		}
		
		Optional<Examen> opt=service.findById(id);
		if(!opt.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Examen examenDb=opt.get();
		examenDb.setNombre(examen.getNombre());
		
		examenDb.getPreguntas()
		.stream()
		.filter(pdb->!examen.getPreguntas().contains(pdb)) 
		.forEach(examenDb::removePregunta);
		
		examenDb.setPreguntas(examen.getPreguntas());
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examenDb));
	}
	
	@GetMapping("/filter/{term}")
	public ResponseEntity<?> filter(@PathVariable String term){
		return ResponseEntity.ok(service.findByNombre(term));
	}
	
	@GetMapping("/asignaturas")
	public ResponseEntity<?> listarAsignaturas(){
		return ResponseEntity.ok(service.findAllAsignaturas());
	}
	
}
