package net.itinajero.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import net.itinajero.model.Usuario;
import net.itinajero.repository.UsuariosRepository;
import net.itinajero.service.IUsuariosService;

@Service
@Primary
public class UsuariosServiceJpa implements IUsuariosService {

	@Autowired
	private UsuariosRepository repoUsuarios;
	
	@Override
	public void save(Usuario usuario) {
		repoUsuarios.save(usuario);

	}

	@Override
	public void delete(Integer idUsuario) {
		repoUsuarios.deleteById(idUsuario);
		
	}

	@Override
	public List<Usuario> buscarTodas() {
		return repoUsuarios.findAll();
	}

}
