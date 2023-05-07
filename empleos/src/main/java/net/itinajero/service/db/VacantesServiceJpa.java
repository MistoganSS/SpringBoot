package net.itinajero.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.itinajero.model.Vacante;
import net.itinajero.repository.VacantesRepository;
import net.itinajero.service.IVacantesService;

@Service
@Primary
public class VacantesServiceJpa implements IVacantesService {

	@Autowired
	private VacantesRepository repoVacantes;
	
	@Override
	public List<Vacante> buscarTodas() {
		return repoVacantes.findAll();
	}

	@Override
	public Vacante findId(Integer idVacante) {
		Optional<Vacante> opt= repoVacantes.findById(idVacante);
		if(opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	@Override
	public void save(Vacante vacante) {
		repoVacantes.save(vacante);
	}

	@Override
	public List<Vacante> findDesatacadas() {
		return repoVacantes.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");
		
	}

	@Override
	public void delete(Integer idVacante) {
		repoVacantes.deleteById(idVacante);
	}

	@Override
	public List<Vacante> searchByExample(Example<Vacante> example) {
		
		return repoVacantes.findAll(example);
	}

	@Override
	public Page<Vacante> buscarTodas(Pageable pageable) {
		return repoVacantes.findAll(pageable);
	}


}
