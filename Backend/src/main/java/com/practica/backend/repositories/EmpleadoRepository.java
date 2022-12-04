package com.practica.backend.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.practica.backend.entities.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository <Empleado,Integer> {
	//creamos una consulta donde devolve´ra los empleados que no esten de baja
	@Query(value="SELECT l FROM Empleado l WHERE l.fechaBaja IS NULL")
	List<Empleado> empleadosActivos();

	/** Creamos una interface para solo recoger los valores que deseamos de la consulta **/
	interface EmpleadosAsignaciones {
		Integer getIdEmpleado();
		String getNif();
		String getNombre();
		String getApellido1();
		String getApellido2();
		Boolean getAsignado();
	}

	//hacemos una query nativa para listar a los empleados que nos devolverá una columna con true
	//si estan asiganos al proyecto o de lo contrario false segun el id del proyecto
	@Query(value="SELECT l.idEmpleado AS idEmpleado, l.nif AS nif, l.nombre AS nombre, l.apellido1 AS apellido1, l.apellido2 AS apellido2, "
			+ "CASE WHEN l.idEmpleado IN (SELECT id_empleado FROM pr_empleados_proyecto WHERE id_proyecto = :idPro) THEN 'true' "
			+ "WHEN l.idEmpleado  NOT IN (SELECT id_empleado FROM pr_empleados_proyecto WHERE id_proyecto = :idPro) THEN 'false' END AS asignado "
			+ "FROM em_empleados l WHERE l.fechaBaja IS NULL", nativeQuery = true)
	List<EmpleadosAsignaciones> showStatus(@Param("idPro") int id);
	
	//definimos los empleados asignados al proyecto segun id
	@Query(value="select nombre from em_empleados a join "
			+ "pr_empleados_proyecto b on a.idEmpleado = b.id_empleado where b.id_proyecto = :idPro",nativeQuery=true)
	List<?> searchEmployeesInProject(@Param("idPro") int id);
	
	//creamos una consulta donde devolverá los empleados que  esten de baja
	@Query(value="SELECT l FROM Empleado l WHERE l.fechaBaja IS NOT NULL")
	List<Empleado> empleadoInactivos();
	
	//método para volver a dar el alta a un empleado dado de baja, añadimos dos anotaciones para poder ejecutar este tipo de query 
	@Modifying
	@Transactional
	@Query(value="UPDATE em_empleados SET fechaBaja= null WHERE idEmpleado = :idEmp", nativeQuery=true)
	void volverAlta(@Param("idEmp") int id);
	
	
}