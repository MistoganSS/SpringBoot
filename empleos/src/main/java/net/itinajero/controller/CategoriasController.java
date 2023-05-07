package net.itinajero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.itinajero.model.Categoria;
import net.itinajero.service.ICategoriasService;

@Controller
@RequestMapping(value="/categorias")
public class CategoriasController {
	
	@Autowired
	private ICategoriasService serviceCategorias;
	
	@GetMapping("/index")
	public String index(Model model,Pageable pageable) {
		model.addAttribute("categorias",serviceCategorias.buscarTodas(pageable));
		return "categorias/index";
	}
	
	@GetMapping("/create")
	public String create(Categoria categoria) {
		return "categorias/create";
	}
	
	@PostMapping("/save")
	public String save(Categoria categoria, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return "categorias/create";
		}
		serviceCategorias.save(categoria);
		attributes.addFlashAttribute("msg","Registro Guardado");
		return "redirect:/categorias/index";
	}
	
	@GetMapping("/edit/{id}")
	public String show(@PathVariable("id") int idCategoria,Model model) {
		model.addAttribute("categoria",serviceCategorias.findId(idCategoria));
		return "categorias/create";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int idCategoria, RedirectAttributes attribute) {
		serviceCategorias.delete(idCategoria);
		attribute.addFlashAttribute("msg","Categoria Eliminada");
		return "redirect:/categorias/index";
	}
	
	
}
