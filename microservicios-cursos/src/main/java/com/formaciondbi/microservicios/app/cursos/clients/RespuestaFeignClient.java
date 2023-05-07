package com.formaciondbi.microservicios.app.cursos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-respuestas")
public interface RespuestaFeignClient {

	@GetMapping("/alumno/{alumnoId}/examenes-respondido")
	public Iterable<Long> getExamenIdsWithRespuestaAlumno(@PathVariable Long alumnoId);
}
