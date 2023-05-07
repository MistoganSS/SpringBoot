package net.itinajero.service;

import java.util.List;

import net.itinajero.model.Usuario;

public interface IUsuariosService {

	void save(Usuario usuario);
	void delete(Integer idUsuario);
	List<Usuario> buscarTodas();
}
