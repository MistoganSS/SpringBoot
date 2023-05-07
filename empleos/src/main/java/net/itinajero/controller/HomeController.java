package net.itinajero.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.itinajero.model.Perfil;
import net.itinajero.model.Usuario;
import net.itinajero.model.Vacante;
import net.itinajero.service.ICategoriasService;
import net.itinajero.service.IUsuariosService;
import net.itinajero.service.IVacantesService;

@Controller
public class HomeController {
	@Autowired
	private IVacantesService serviceVacantes;
	
	@Autowired
	private IUsuariosService serviceUsuarios;
	
	@Autowired
	private ICategoriasService serviceCategorias;
	
	@GetMapping("/tabla")
	public String mostraTabla(Model model) {
		List<Vacante> lista=serviceVacantes.buscarTodas();
		model.addAttribute("vacantes",lista);
		return "tabla";
	}
	
	
	@GetMapping("/")
	public String index(Model model) {
		return "home";
	}
	@GetMapping("/signup")
	public String registrarse(Usuario usuario) {
		return "usuarios/formRegistro";
	}
	@PostMapping("/signup")
	public String saveUsuario(Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return "usuarios/formRegistro";
		}
		Perfil perfil=new Perfil();
		perfil.setId(3);
		usuario.addPerfil(perfil);
		usuario.setFechaRegistro(new Date());
		usuario.setEstatus(1);
		serviceUsuarios.save(usuario);
		attributes.addFlashAttribute("msg","Usuario Guardado");
		return "redirect:/usuarios/index";
	}
	
	@GetMapping("/search")
	public String search(@ModelAttribute("search") Vacante vacante,Model model) {
		System.err.println(vacante);
		
		//para usar en el sql el LIKE '%?%'
		ExampleMatcher matcher= ExampleMatcher.
				matching().withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains());	
		Example<Vacante> example=Example.of(vacante, matcher);
		List<Vacante> lista=serviceVacantes.searchByExample(example);
		model.addAttribute("vacantes",lista);
		return "home";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	//Agregar al modelo todos los tributos al modelo 
	@ModelAttribute
	public void setGenericos(Model model) {
		Vacante vacanteSearch= new Vacante();
		vacanteSearch.reset();
		System.err.println(vacanteSearch);
		model.addAttribute("categorias", serviceCategorias.buscarTodas());
		model.addAttribute("vacantes",serviceVacantes.findDesatacadas());
		model.addAttribute("search",vacanteSearch);
	}

}
