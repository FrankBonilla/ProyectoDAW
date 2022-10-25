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
	ProyectoService proyectoService;
	@Autowired
	EmpleadoService empleadoService;
	
	@GetMapping(path="/lista")
	public List<Proyecto> showAll(){
		
		return proyectoService.listar();
	}
	
	//listar solo dados de alta
	@GetMapping(path="/activos")
	public List<Proyecto> showAct(){
		
		return proyectoService.mostrarActivos();
	}
	
	//mostrar solo dados de baja
	@GetMapping(path="/inactivos")
	public List<Proyecto> showInac(){
		
		return proyectoService.mostrarInactivos();
	}
	
	@PostMapping(path="/guardar")
	@ResponseBody
	public String saveProject(@RequestBody Proyecto proyecto) {
		
		proyectoService.save(proyecto);
		return "se guardó el proyecto "+proyecto.getDescripcion();
		
	}
	
	@DeleteMapping(path="/borrar/{id}")
	public void borrar(@PathVariable(name="id", required=true) int id) {
		proyectoService.delete(id);
	}
	
	//Asiganmos los empleados al proyecto 
	@PostMapping(path="/asignarEmpleado")
	public void addEmp(@RequestParam int idProyecto, @RequestParam int idEmpleado) {

		Proyecto proyecto = proyectoService.listarId(idProyecto);
		Empleado empleado = empleadoService.listarId(idEmpleado);
		
		proyecto.addEmployee(empleado);
		proyectoService.save(proyecto);
		
	}
	/** mejorar **/
	//Eliminamos el empleado del proyecto
	@PostMapping(path="/removeEmployee")
	public ResponseEntity<Boolean> removeEmp(@RequestParam int idProyecto, @RequestParam int idEmpleado) {
		LOGGER.info("removiendo al empleado con ID: [{}] del proyecto [{}]",idEmpleado,idProyecto);
		Boolean resultOK = false;

		try{
			Proyecto proyecto = proyectoService.listarId(idProyecto);
			Empleado empleado = empleadoService.listarId(idEmpleado);
			proyecto.removeEmployee(empleado);
			proyectoService.save(proyecto);
			resultOK = true;
		}catch(DataAccessException e){
			LOGGER.error("Error accediendo a la base de datos");
			LOGGER.error("error {}",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(httpStatus.INTERNAL_SERVER_ERROR);
		}

		if(resultOK){
			return new ResponseEntity<>(resultOK,httpStatus.OK);
		}else{
			return new ResponseEntity<>(resultOK,httpStatus.BAD_REQUEST);
		}
		
	}
	
	//Verificamos los proyectos asigandos un empleado
	@GetMapping(path="/verificar")
	public ResponseEntity<List<String>> searchProjectsOfEmple(@RequestParam int idEmpleado){
		LOGGER.info("verificando asignaciones a proyectos del empleado: [{}]", idEmpleado);
		List<String> result = null;

		try{
			result = proyectoService.searchProjectsOfEmple(idEmpleado);

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
		proyectoService.darBaja(id);
	}
	
	@PostMapping(path="/update")
	@ResponseBody
	public void updateProject(@RequestBody Proyecto proyecto) {
		proyectoService.updateProject(proyecto);
	}
	
}