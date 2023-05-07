package net.itinajero.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.itinajero.model.Vacante;
import net.itinajero.service.ICategoriasService;
import net.itinajero.service.IVacantesService;
import net.itinajero.util.Utileria;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {
	
	@Value("${empleoapp.route.images}")
	private String PATHROUTE;
	
	@Autowired
	private IVacantesService serviceVacantes;
	
	@Autowired
	private ICategoriasService serviceCategorias;
	
	@GetMapping("/index")
	public String index(Model model) {
		 model.addAttribute("vacantes",serviceVacantes.buscarTodas());
		return "vacantes/index";
	}
	
	@GetMapping("/indexPaginado")
	public String mostraIndexPaginado(Model model, Pageable pageable) {
		
		model.addAttribute("vacantes",serviceVacantes.buscarTodas(pageable));
		return "vacantes/index";
	}
	
	@GetMapping("/show/{id}")
	public String show(@PathVariable("id") int idVacante, Model model) {
		Vacante vacante=serviceVacantes.findId(idVacante);
		model.addAttribute("vacante",vacante);
		return "vacantes/show";
	}
	
	@GetMapping("/create")
	public String create(Vacante vacante, Model model) {
		return "vacantes/create";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int idVacante,Model model) {
		Vacante vacante=serviceVacantes.findId(idVacante);
		model.addAttribute("vacante",vacante);
		return "vacantes/create";
	}
	
	@PostMapping("/save")
	public String save(Vacante vacante, BindingResult result, RedirectAttributes attributes, @RequestParam("archivoImagen") MultipartFile multipart) {
		if(result.hasErrors()) {
			for(ObjectError error: result.getAllErrors()) {
				System.err.println("ERROR: "+error.getDefaultMessage());
			}
			return "vacantes/create";
			
		}
		if(!multipart.isEmpty()) {
			String route=PATHROUTE;
			String nameImage=Utileria.saveFile(multipart, route);
			if(nameImage!=null) {
				vacante.setImagen(nameImage);
			}
		}
		attributes.addFlashAttribute("msg","Registro guardado");
		System.err.println("vacante: "+vacante);
		serviceVacantes.save(vacante);
		return "redirect:/vacantes/index";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int idVacante, RedirectAttributes attributes) {//le quite Model model, tercer parametro
		serviceVacantes.delete(idVacante);
		attributes.addFlashAttribute("msg","Vacante Eliminada");
		return "redirect:/vacantes/index";
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("categorias",serviceCategorias.buscarTodas());
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class,new CustomDateEditor(sdf, false));
	}
}
