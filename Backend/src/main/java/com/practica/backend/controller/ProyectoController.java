package com.practica.backend.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.practica.backend.entities.Empleado;
import com.practica.backend.entities.Proyecto;
import com.practica.backend.service.EmpleadoService;
import com.practica.backend.service.ProyectoService;

@CrossOrigin(origins = {"http://localhost:8081"})
@RestController
@RequestMapping(path="proyectos")
public class ProyectoController {

	private static final Logger LOGGER = LogManager.getLogger(ProyectoController.class);
	HttpStatus httpStatus;
	//inyectamos el servicio
	@Autowired
	ProyectoService service;
	@Autowired
	EmpleadoService serviceEmp;
	
	@GetMapping(path="/lista")
	public List<Proyecto> showAll(){
		
		return service.listar();
	}
	
	//listar solo dados de alta
	@GetMapping(path="/activos")
	public List<Proyecto> showAct(){
		
		return service.mostrarActivos();
	}
	
	//mostrar solo dados de baja
	@GetMapping(path="/inactivos")
	public List<Proyecto> showInac(){
		
		return service.mostrarInactivos();
	}
	
	@PostMapping(path="/guardar")
	@ResponseBody
	public String saveProject(@RequestBody Proyecto proyecto) {
		
		service.save(proyecto);
		return "se guardó el proyecto "+proyecto.getDescripcion();
		
	}
	
	@DeleteMapping(path="/borrar/{id}")
	public void borrar(@PathVariable(name="id", required=true) int id) {
		service.delete(id);
	}
	
	//Asiganmos los empleados al proyecto 
	@PostMapping(path="/save-employee")
	@ResponseBody
	public void addEmp(@RequestBody Map<String, String> json) {
		int idPro = Integer.parseInt(json.get("id_proyecto"));
		int idEmp = Integer.parseInt(json.get("id_empleado"));
		
		Proyecto proyecto = service.listarId(idPro);
		Empleado empleado = serviceEmp.listarId(idEmp);
		
		proyecto.addEmployee(empleado);
		service.save(proyecto);
		
	}
	//Eliminamos el empleado del proyecto
	@PostMapping(path="/remove-employee")
	@ResponseBody
	public void removeEmp(@RequestBody Map<String, String> json) {
		int idPro = Integer.parseInt(json.get("id_proyecto"));
		int idEmp = Integer.parseInt(json.get("id_empleado"));
		
		Proyecto proyecto = service.listarId(idPro);
		Empleado empleado = serviceEmp.listarId(idEmp);
		
		proyecto.removeEmployee(empleado);
		service.save(proyecto);
		
	}
	
	//Verificamos los proyectos asigandos un empleado
	@GetMapping(path="/verificar")
	public ResponseEntity<List<String>> searchProjectsOfEmple(@RequestParam int idEmpleado){
		LOGGER.info("verificando asignaciones a proyectos del empleado: [{}]", idEmpleado);
		List<String> result = null;

		try{
			result = service.searchProjectsOfEmple(idEmpleado);

		}catch (DataAccessException e){
			LOGGER.error("Error al realizar la consulta a la base de datos");
			LOGGER.error("error {}",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}

		if(result == null){
			LOGGER.info("No tiene asignaciones a proyectos empleado: [{}]",idEmpleado);
			return new ResponseEntity<>(result,httpStatus.NOT_FOUND);
		}else{
			LOGGER.info("empleado con ID: [{}] tiene [{}] asignaciones",idEmpleado, result.size());
			return new ResponseEntity<>(result, httpStatus.OK);
		}

	}
	
	//metodo para dar de baja al proyecto
	@PostMapping(path="/baja/{id}")
	public void baja(@PathVariable(name="id",required= true) int id) {
		service.darBaja(id);
	}
	
	@PostMapping(path="/update")
	@ResponseBody
	public void updateProject(@RequestBody Proyecto proyecto) {
		service.updateProject(proyecto);
	}
	
}