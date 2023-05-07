package net.itinajero.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.itinajero.model.Vacante;

@Service
public class VacantesServiceImpl implements IVacantesService{
	private List<Vacante> lista=null;

	public VacantesServiceImpl() {
		SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy");
		lista= new LinkedList<Vacante>();
		try {
			Vacante vac1= new Vacante();
			vac1.setId(1);
			vac1.setNombre("Ingenir aa");
			vac1.setDescripcion("descripcio 1dwdc ecd dddddddddddddddddddddddddddddddddddddd");
			vac1.setFecha(sdf.parse("08-02-2019"));
			vac1.setSalario(565.0);
			vac1.setDestacado(1);
			vac1.setImagen("empresa1.png");
			
			Vacante vac2= new Vacante();
			vac2.setId(2);
			vac2.setNombre("Ingenir aa");
			vac2.setDescripcion("descripcio 2aaaaaaaaaaaaaaaaaaaaaaaaaaa");
			vac2.setFecha(sdf.parse("08-03-2019"));
			vac2.setSalario(565.3);
			vac2.setDestacado(0);
			vac2.setImagen("empresa2.png");
			
			Vacante vac3= new Vacante();
			vac3.setId(3);
			vac3.setNombre("Ingenir aa");
			vac3.setDescripcion("descripcio 3sdddddddddddddddddddddddddddddddc ddddddddddddd");
			vac3.setFecha(sdf.parse("08-04-2019"));
			vac3.setSalario(565.0);
			vac3.setDestacado(1);
			
			
			lista.add(vac1);
			lista.add(vac2);
			lista.add(vac3);
			lista.add(vac1);
			
		}catch(ParseException e) {
			System.out.println("Error: "+e.getMessage());
		}
	}
	
	@Override
	public List<Vacante> buscarTodas() {
		return lista;
	}

	@Override
	public Vacante findId(Integer idVacante) {
		for (Vacante vacante : lista) {
			if(vacante.getId()==idVacante) {
				return vacante;
			}
		}
		return null;
	}

	@Override
	public void save(Vacante vacante) {
		lista.add(vacante);
		
	}

	@Override
	public List<Vacante> findDesatacadas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer idVacante) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Vacante> searchByExample(Example<Vacante> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Vacante> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}


	
	
}
