package com.practica.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.practica.backend.entities.Proyecto;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
	
	//creamos una consulta donde devolverá los proyectos que no esten de baja
		@Query(value="SELECT l FROM Proyecto l WHERE l.fechaBaja IS NULL")
		List<Proyecto> findByF_alta();
		
	//creamos otro para buscar los metodos que estan de baja
		@Query(value="SELECT l FROM Proyecto l WHERE l.fechaBaja IS NOT NULL")
		List<Proyecto> findByF_baja();

		/*
	//definimos un método para saber que proyectos tiene asginado un empleado segun su id
		@Query(value="SELECT text_descripcion FROM pr_proyectos a "
				+ "JOIN pr_empleados_proyecto b ON a.id_proyecto=b.id_proyecto "
				+ "WHERE b.id_empleado = :idEmp", nativeQuery=true)
		List<String> listProjects(@Param("idEmp") int id);
*/
		//creamos una interface con el id del proyecto y su descripción/nombre
	interface ProyectosAsignados{
		Integer getIdProyecto();
		String getDescripcion();
		}

		@Query(value="SELECT a.text_descripcion AS descripcion, a.id_proyecto AS idProyecto FROM pr_proyectos a "
				+ "JOIN pr_empleados_proyecto b ON a.id_proyecto=b.id_proyecto "
				+ "WHERE b.id_empleado = :idEmpleado", nativeQuery=true)
		List<ProyectosAsignados> showAsigaciones(@Param("idEmpleado") int id);


	@Query(value="SELECT * FROM pr_proyectos a "
			+ "JOIN pr_empleados_proyecto b ON a.id_proyecto=b.id_proyecto "
			+ "WHERE b.id_empleado = :idEmpleado", nativeQuery=true)
	List<Proyecto> getProjectsOfEmployee(@Param("idEmpleado") int id);
}
	
