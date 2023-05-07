package net.itinajero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.itinajero.service.IUsuariosService;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private IUsuariosService serviceUsuarios;
	
	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("usuarios",serviceUsuarios.buscarTodas());
		
		return "/usuarios/index";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int idUsuario, RedirectAttributes attributes) {
		serviceUsuarios.delete(idUsuario);
		attributes.addFlashAttribute("msg","Usuario Eliminado");
		return "redirect:/usuarios/index";
	}
}
