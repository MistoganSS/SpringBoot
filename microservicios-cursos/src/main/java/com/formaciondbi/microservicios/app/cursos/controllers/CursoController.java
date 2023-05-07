package com.formaciondbi.microservicios.app.cursos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formaciondbi.microservicios.app.cursos.models.entity.Curso;
import com.formaciondbi.microservicios.app.cursos.services.CursoService;
import com.formaciondbi.microservicios.commons.alumnos.models.entity.Alumno;
import com.formaciondbi.microservicios.commons.controllers.CommonController;
import com.formaciondbi.microservicios.commons.examenes.models.entity.Examen;

import jakarta.validation.Valid;

@RestController
public class CursoController extends CommonController<Curso, CursoService>{

	@Value("${config.balanceador.test}")
	private String balanceadortest;
	
	@GetMapping("/balanceador-test")
	public ResponseEntity<?> balanceadorTest() {
		Map<String, Object> respuestas= new HashMap<String, Object>();
		respuestas.put("balanceador", balanceadortest);
		respuestas.put("cursos", service.findAll());
		return ResponseEntity.ok(respuestas);
	}
	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id){
		if(result.hasErrors()) {
			return this.validation(result);
		}
		Optional<Curso> opt= this.service.findById(id);
		if(!opt.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso cursoDb=opt.get();
		cursoDb.setNombre(curso.getNombre());
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoDb));
	}
	@PutMapping("/{id}/asignar-alumnos")
	public ResponseEntity<?> asignaAlumnos(@RequestBody List<Alumno> alumnos,@PathVariable Long id){
		Optional<Curso> opt= this.service.findById(id);
		if(!opt.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso cursoDb=opt.get();
		alumnos.forEach(cursoDb::addAlumno);
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoDb));
	}
	@PutMapping("/{id}/eliminar-alumno")
	public ResponseEntity<?> eliminarAlumno(@RequestBody Alumno alumno,@PathVariable Long id){
		Optional<Curso> opt= this.service.findById(id);
		if(!opt.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso cursoDb=opt.get();
		cursoDb.removeAlumno(alumno);
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoDb));
	}
	
	@GetMapping("/alumno/{id}")
	public ResponseEntity<?> buscarPorAlumnoId(@PathVariable Long id){
		Curso curso=service.findyCursoByAlumno(id);
		
		if(curso != null) {
			List<Long> examenesIds=(List<Long>) service.getExamenIdsWithRespuestaAlumno(id);
			List<Examen> examenes=curso.getExamenes().stream().map(examen->{
				if(examenesIds.contains(examen.getId())) {
					examen.setRespondido(true);
				}
				return examen;
			}).collect(Collectors.toList());
			
			curso.setExamenes(examenes);
		}
		
		return ResponseEntity.ok(curso);
	}
	
	@PutMapping("/{id}/asignar-examenes")
	public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examenes,@PathVariable Long id){
		Optional<Curso> opt= this.service.findById(id);
		if(!opt.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso cursoDb=opt.get();
		examenes.forEach(cursoDb::addExamen);
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoDb));
	}
	
	@PutMapping("/{id}/eliminar-examen")
	public ResponseEntity<?> eliminarExamen(@RequestBody Examen examen,@PathVariable Long id){
		Optional<Curso> opt= this.service.findById(id);
		if(!opt.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso cursoDb=opt.get();
		cursoDb.removeExamen(examen);
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoDb));
	}
	
}
