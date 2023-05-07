package net.itinajero.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.itinajero.model.Categoria;

@Service
public class CategoriasServiceImpl implements ICategoriasService{

	private List<Categoria> lista=null;
	
	public CategoriasServiceImpl() {
		lista=new LinkedList<Categoria>();
		Categoria cat1= new Categoria();
		cat1.setId(1);
		cat1.setNombre("Ventas");
		cat1.setDescripcion("Ventas online o personals");
		
		lista.add(cat1);
	}
	
	@Override
	public void save(Categoria categoria) {
		lista.add(categoria);
		
	}

	@Override
	public List<Categoria> buscarTodas() {
		return lista;
	}

	@Override
	public Categoria findId(Integer idCategoria) {
		for (Categoria categoria : lista) {
			if(categoria.getId()==idCategoria) {
				return categoria;
			}
		}
		return null;
	}

	@Override
	public void delete(Integer idCategoria) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Page<Categoria> buscarTodas(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
