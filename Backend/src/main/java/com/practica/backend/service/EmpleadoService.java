package com.practica.backend.service;



import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practica.backend.repositories.EmpleadoRepository;
import com.practica.backend.entities.Empleado;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpleadoService {
	//inyectamos el repositorio
		@Autowired
		private EmpleadoRepository repo;

		public List<Empleado> listar() {
			
			return repo.findAll();
		}

		@Transactional
		public Empleado save(Empleado empleado) {
			
			return repo.save(empleado);
		}

		public void delete(int idEmpleado) {
			
			repo.deleteById(idEmpleado);
		}
		@Transactional(readOnly = true)
		public Empleado listarId(int id) {
			//si lo encuentra retorna un objeto tipo empleado, si no un null
			return repo.findById(id).orElse(null);
		}

		public Empleado updateEmployee(Empleado a) {
			Empleado empleadoUpdated = null;
			Empleado empleado = repo.getReferenceById(a.getIdEmpleado());

			if(empleado != null){
				empleado.setNif(a.getNif());
				empleado.setNombre(a.getNombre());
				empleado.setApellido1(a.getApellido1());
				empleado.setApellido2(a.getApellido2());
				empleado.setNacimiento(a.getNacimiento());
				empleado.setTelefono1(a.getTelefono1());
				empleado.setTelefono2(a.getTelefono2());
				empleado.setEmail(a.getEmail());
				empleado.setFechaAlta(a.getFechaAlta());
				empleado.setEdoCivil(a.getEdoCivil());
				empleado.setSerMilitar(a.getSerMilitar());

				empleadoUpdated = repo.save(empleado);
				return empleadoUpdated;

			}else{
				return null;
			}
		}
		
		
		public Empleado darBaja(int id) {
			
			Empleado empleado = repo.getReferenceById(id);

			if(empleado != null){
				LocalDate fecha = LocalDate.now();
				empleado.setFechaBaja(Date.valueOf(fecha));
				empleado = repo.save(empleado);
				return empleado;
			}else{
				return null;
			}
		}
		//método 1 que define la consulta creada en el repositorio
		public List<Empleado> mostarActivos(){
			
			return repo.empleadosActivos();
		}
		
		//metodo 2 que define la consulta de empleados y muestra su asignación
		public List<?>  showStatus(int id){
			return repo.showStatus(id);
		}
		//metodo 3 creado en el repositorio
		public List<?> searchEmployeesProject(int id) {
			List<?> asignedProjects = repo.searchEmployeesInProject(id);
			if(asignedProjects != null || asignedProjects.size() != 0) {
					
				return asignedProjects;
			}
			return null;
		}
		//método 4 que busca a los empleados de baja
		public List<Empleado> showUnsuscribe(){
			
			return repo.empleadoInactivos();
		}
		
		//metodo 5 para volver a dar de alta
		public void volver(int id) {
			//obtenemos el empleado y cambiamos la fecha de alta a la actual
			Empleado empleado = repo.getReferenceById(id);
			LocalDate fecha = LocalDate.now();
			empleado.setFechaAlta(Date.valueOf(fecha));
			//establecemos null en su fecha de baja para que aparezca de lata nuevamente
			repo.volverAlta(id);
			//guardamos el empleado con la nueva fecha de alta
			repo.save(empleado);
		}
}
