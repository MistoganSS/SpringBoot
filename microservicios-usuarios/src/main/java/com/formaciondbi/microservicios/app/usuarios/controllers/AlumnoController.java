package com.formaciondbi.microservicios.app.usuarios.controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.formaciondbi.microservicios.app.usuarios.services.AlumnoService;
import com.formaciondbi.microservicios.commons.alumnos.models.entity.Alumno;
import com.formaciondbi.microservicios.commons.controllers.CommonController;

import jakarta.validation.Valid;

@RestController
public class AlumnoController extends CommonController<Alumno, AlumnoService> {

	@GetMapping("/uploads/img/{id}")
	public ResponseEntity<?> viewFhoto(@PathVariable Long id){
		Optional<Alumno> opt=service.findById(id);
		if(opt.isEmpty() || opt.get().getFoto() == null) {
			return ResponseEntity.notFound().build();
		}
		Resource image= new ByteArrayResource(opt.get().getFoto());
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(image);
	}
	
	@PostMapping("/create-with-foto")
	public ResponseEntity<?> createWithFoto(@Valid Alumno alumno, BindingResult result, 
			@RequestParam MultipartFile archive) throws IOException {
		if(!archive.isEmpty()) {
			alumno.setFoto(archive.getBytes());
		}
		return super.create(alumno, result);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@Valid @RequestBody Alumno alumno, BindingResult result, @PathVariable Long id){
		if(result.hasErrors()) {
			return this.validation(result);
		}
		Optional<Alumno> opt=service.findById(id);
		if(opt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Alumno alumnodb=opt.get();
		alumnodb.setNombre(alumno.getNombre());
		alumnodb.setApellido(alumno.getApellido());
		alumnodb.setEmail(alumno.getEmail());
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnodb));
	}
	
	@PutMapping("/edit-with-foto/{id}")
	public ResponseEntity<?> editWithFoto(@Valid Alumno alumno, BindingResult result, @PathVariable Long id, 
			@RequestParam MultipartFile archive) throws IOException{
		if(result.hasErrors()) {
			return this.validation(result);
		}
		Optional<Alumno> opt=service.findById(id);
		if(opt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Alumno alumnodb=opt.get();
		alumnodb.setNombre(alumno.getNombre());
		alumnodb.setApellido(alumno.getApellido());
		alumnodb.setEmail(alumno.getEmail());
		if(!archive.isEmpty()) {
			alumnodb.setFoto(archive.getBytes());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnodb));
	}
	
	@GetMapping("/search/{term}")
	public ResponseEntity<?> search(@PathVariable String term){
		return ResponseEntity.ok(service.findByNombreOrApellido(term));
	}

}
