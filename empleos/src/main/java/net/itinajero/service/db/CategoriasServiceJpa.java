package net.itinajero.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.itinajero.model.Categoria;
import net.itinajero.repository.CategoriasRepository;
import net.itinajero.service.ICategoriasService;

@Service
@Primary
public class CategoriasServiceJpa implements ICategoriasService {

	@Autowired
	private CategoriasRepository repoCategorias;
	
	@Override
	public void save(Categoria categoria) {
		
		repoCategorias.save(categoria);

	}

	@Override
	public List<Categoria> buscarTodas() {
		
		return repoCategorias.findAll();
	}

	@Override
	public Categoria findId(Integer idCategoria) {
		
		Optional<Categoria> opt=repoCategorias.findById(idCategoria);
		if(opt.isPresent()) {
			return opt.get();
		}
		return null; 
	}

	@Override
	public void delete(Integer idCategoria) {
		repoCategorias.deleteById(idCategoria);
		
	}

	@Override
	public Page<Categoria> buscarTodas(Pageable pageable) {
		
		return repoCategorias.findAll(pageable);
	}

}
