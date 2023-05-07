package net.itinajero;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import jakarta.persistence.EntityManager;
import net.itinajero.model.Categoria;
import net.itinajero.model.Perfil;
import net.itinajero.model.Usuario;
import net.itinajero.model.Vacante;
import net.itinajero.repository.CategoriasRepository;
import net.itinajero.repository.PerfilesRepository;
import net.itinajero.repository.UsuariosRepository;
import net.itinajero.repository.VacantesRepository;

@SpringBootApplication
public class JpaDemoApplication implements CommandLineRunner{

	@Autowired
	private CategoriasRepository repoCategorias;
	@Autowired
	private VacantesRepository repoVacantes;
	
	@Autowired
	private PerfilesRepository repoPerfiles;
	
	@Autowired
	private UsuariosRepository repoUsuarios;
	
	public static void main(String[] args) {
		SpringApplication.run(JpaDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		findVacantesForEstatusIN();
	}
	
	private void findVacantesForEstatusIN() {
		String[] est= new String[] {"ELiminada","Creada"};
		List<Vacante> vac=repoVacantes.findByEstatusIn(est);
		System.err.println("Cantidad: "+vac.size());
		for (Vacante item : vac) {
			System.err.println("item: "+item.getNombre()+" /**/ "+item.getEstatus());
		}
	}
	
	private void findVacantesForSalario() {
		List<Vacante> vac=repoVacantes.findBySalarioBetweenOrderBySalarioDesc(7000, 14000);
		System.err.println("Cantidad: "+vac.size());
		for (Vacante item : vac) {
			System.err.println("item: "+item.getNombre()+" /**/ "+item.getSalario());
		}
	}
	
	private void findVacantesForDestacadoEstatus() {
		List<Vacante> vac=repoVacantes.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");
		System.err.println("Cantidad: "+vac.size());
		for (Vacante item : vac) {
			System.err.println("item: "+item.getNombre()+" /**/ "+item.getEstatus());
		}
	}
	
	private void findByVacantesForEstatus() {
		List<Vacante> vac=repoVacantes.findByEstatus("Eliminada");
		System.err.println("Cantidad: "+vac.size());
		for (Vacante item : vac) {
			System.err.println("item: "+item.getNombre()+" /**/ "+item.getEstatus());
		}
	}
	
	private void findUserPerfil() {
			Optional<Usuario> optional= repoUsuarios.findById(1);
			if(optional.isPresent()) {
				Usuario user= optional.get();
				System.err.println("Usuario: "+user.getNombre());
				System.out.println("Perfiles asginados");
				for (Perfil item : user.getPerfiles()) {
					System.out.println(item.getPerfil());
				}
			}else {
				System.out.println("Usuario no econtrado");
			}	
	}
	
	private void saveUserPerfil() {
		Usuario user= new Usuario();
		user.setNombre("Jhon Quispe");
		user.setEmail("quispejhonn021@gmail.com");
		user.setFechaRegistro(new Date());
		user.setUsername("jhon");
		user.setPassword("12345");
		user.setEstatus(1);
		
		Perfil perfil= new Perfil();
		perfil.setId(2);
		Perfil perfil2=new Perfil();
		perfil2.setId(3);
		
		user.addPerfil(perfil);
		user.addPerfil(perfil2);
		
		repoUsuarios.save(user);
	}
	
	private void savePerfilesAplication() {
		repoPerfiles.saveAll(getPerfilAplication());
	}
	
	private List<Perfil> getPerfilAplication(){
		List<Perfil> list= new LinkedList<Perfil>();
		Perfil per1= new Perfil();
		per1.setPerfil("SUPERVISOR");
		Perfil per2= new Perfil();
		per2.setPerfil("ADMINISTRADOR");
		Perfil per3= new Perfil();
		per3.setPerfil("USUARIO");
		
		list.add(per1);
		list.add(per2);
		list.add(per3);
		
		return list;
	}
	
	public void allVacantes() {
		List<Vacante> vacantes=repoVacantes.findAll();
		for (Vacante item : vacantes) {
			System.out.println("id: \t"+item.getId()+" "+item.getNombre()+"->"+item.getCategoria().getNombre());
		}
	}
	
	public void saveVacante() {
		Vacante vacante= new Vacante();
		vacante.setId(14);
		vacante.setNombre("Profesor de matematica");
		vacante.setDescripcion("Escuela de primaria");
		vacante.setFecha(new Date());
		vacante.setSalario(8500);
		vacante.setEstatus("Aprobada");
		vacante.setDestacado(0);
		vacante.setDetalles("<h1>Requisitos de profesor de amtematica</h1>");
		Categoria cat=new Categoria();
		cat.setId(2);
		vacante.setCategoria(cat);
		repoVacantes.save(vacante);
	}
	
	private void allData() {
		/*Iterable<Categoria> categorias= repoCategorias.findAll();
		for (Categoria item : categorias) {
			System.out.println(item);
		}*/
		/*Usando JPA-ordenamiento*/
		List<Categoria> cat= repoCategorias.findAll(Sort.by("nombre").descending());
		for (Categoria item : cat) {
			System.out.println(item.getId()+" "+item.getNombre());
		}
	}
	private void findAllPagination() {
		/*Paginacion y ordenamiento usando JPA*/
		Page<Categoria> page= repoCategorias.findAll(PageRequest.of(0, 5, Sort.by("nombre").descending()));
		System.out.println("total registor: "+page.getTotalElements());
		System.out.println("paginas: "+page.getTotalPages());
		for (Categoria item : page.getContent()) {
			System.out.println("id: "+item.getId()+" "+item.getNombre());
		}
	}
	
	private void save() {
		Categoria cat1= new Categoria();
		cat1.setNombre("Venta");
		cat1.setDescripcion("Online o presencial");
		
		repoCategorias.save(cat1);
		System.out.println("categorias: "+cat1);
	}
	private void findId() {
		Optional<Categoria> optional= repoCategorias.findById(1);
		if(optional.isPresent()) {
			System.out.println("Categoria"+optional.get());
		}else {
			System.out.println("Categoria no encontrada");
		}
	}
	private void findForIds() {
		List<Integer> ids= new LinkedList<Integer>();
		ids.add(1);
		ids.add(4);
		ids.add(10);
		Iterable<Categoria> categorias= repoCategorias.findAllById(ids);
		for (Categoria item : categorias) {
			System.out.println(item);
		}
	}
	private void update() {
		Optional<Categoria> optional= repoCategorias.findById(2);
		if(optional.isPresent()) {
			Categoria catTmp=optional.get();
			catTmp.setNombre("Ing. de software");
			catTmp.setDescripcion("desarrollo de sistemas");
			repoCategorias.save(catTmp);

		}else {
			System.out.println("Categoria no encontrada");
		}
	}
	private void allDelete() {
		
		//repoCategorias.deleteAll();
		/**
		 * Delete usando JPA
		 * Eliminar todos los registros de la tabla
		 */
		repoCategorias.deleteAllInBatch();
	}

}
