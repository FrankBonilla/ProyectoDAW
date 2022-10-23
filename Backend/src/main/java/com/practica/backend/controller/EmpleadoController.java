package com.practica.backend.controller;


import java.util.HashMap;
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
import com.practica.backend.service.EmpleadoService;

@CrossOrigin(origins = {"http://localhost:8081"})
@RestController
@RequestMapping(path="api/")
public class EmpleadoController {

	private static final Logger LOGGER = LogManager.getLogger(EmpleadoController.class);
	//inyectamos el servicio
	@Autowired
	EmpleadoService empleadoService;
	HttpStatus  httpStatus;

	/**
	 * Este método devuelve una lista con todos los empleados
	 * o en su defecto los errores que se puedan producir en la consulta
	 * **/
	@GetMapping(path="empleados/lista")
	public ResponseEntity<?> mostarTodos(){
		LOGGER.info("Consultando todos empleados");
		List<Empleado> result;
		Map<String, Object> response = new HashMap<>();

		try{
			result = empleadoService.listar();

		}catch (DataAccessException e){
			LOGGER.error("Error al realizar la consulta a la base de datos");
			response.put("mensaje","Error al realizar la consulta a la base de datos");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response,httpStatus.BAD_REQUEST);
		}

		if(result == null) {
			LOGGER.warn("La base de datos no tiene empleados registrados");
			response.put("mensaje","La base de datos no tiene empleados registrados");
			return new ResponseEntity<>(response,httpStatus.NOT_FOUND);
		}

		LOGGER.info("Consulta de empleados finalizada, resultados: [{}] resultados",result.size());
		return new ResponseEntity<>(result,httpStatus.OK);
	}

	/**
	 * Este método devuelve una lista con todos los empleados que están activos
	 * o en su defecto los errores que se puedan producir en la consulta
	 * **/
	@GetMapping(path="empleados/activos")
	public ResponseEntity<?> showAct(){
		LOGGER.info("Consultando todos empleados de alta");
		List<Empleado> result;
		Map<String, Object> response = new HashMap<>();

		try{
			result = empleadoService.mostarActivos();

		}catch (DataAccessException e){
			LOGGER.error("Error al realizar la consulta a la base de datos");
			response.put("mensaje","Error al realizar la consulta a la base de datos");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response,httpStatus.BAD_REQUEST);
		}

		if(result == null) {
			LOGGER.warn("La base de datos no tiene empleados activos");
			response.put("mensaje","La base de datos no tiene empleados activos");
			return new ResponseEntity<>(response,httpStatus.NOT_FOUND);
		}

		LOGGER.info("Consulta de empleados finalizada, resultados: [{}]",result.size());
		return new ResponseEntity<>(result, httpStatus.OK);
	}
	
	@GetMapping(path="empleados/inactivos")
	public ResponseEntity<List<Empleado>> showUnsuscribe(){
		LOGGER.info("Consultando todos empleados de baja");
		List<Empleado> result = null;

		try{
			result = empleadoService.showUnsuscribe();

		}catch(DataAccessException e){
			LOGGER.error("error: "+e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(result,httpStatus.BAD_REQUEST);
		}

		if(result == null){
			LOGGER.warn("La base de datos no tiene empleados de baja");
			return new ResponseEntity<>(result,httpStatus.NO_CONTENT);
		}

		LOGGER.info("Consulta de empleados finalizada, resultados: [{}]",result.size());
		return new ResponseEntity<>(result,httpStatus.OK);
	}
	
	@PostMapping(path="empleados/agregar")
	@ResponseBody
	public ResponseEntity<Empleado> guardar(@RequestBody Empleado empleado) {
		LOGGER.info("Agregando al empleado: "+empleado.getNombre()+" "+empleado.getApellido1());

		try{
			empleadoService.save(empleado);
			LOGGER.info("Agregando al empleado: "+empleado.getNombre()+" "+empleado.getApellido1());

		}catch(DataAccessException e){
			LOGGER.error("error: "+e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(empleado,httpStatus.OK);
		}

		LOGGER.info("Se ha agregado al empleado [{}]", empleado.toString());
		return new ResponseEntity<>(empleado,httpStatus.OK);
	}
	
	@DeleteMapping(path="empleados/borrar")
	public void borrar(@RequestParam int idEmpleado) {
		LOGGER.info("Borrando al empleado con ID: [{}]", idEmpleado);

		try{
			empleadoService.delete(idEmpleado);
			LOGGER.info("Se ha borrado correctamennte el empleado con ID: [{}]", idEmpleado);

		}catch (DataAccessException e){
			LOGGER.error("Error al realizar la operación a la base de datos");
			LOGGER.error("error "+e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}

	}
	/** Revisar para eliminar**/
	@PostMapping(path="/status")
	@ResponseBody
	public List<?> status(@RequestBody Map<String, String> json) {
		int idPro = Integer.parseInt(json.get("id_proyecto"));
		return empleadoService.showStatus(idPro);
		
		
	}
	//metodo para verificar si el proyecto tiene empleados asignados antes de darle de baja
	@PostMapping(path="/verificar")
	@ResponseBody
	public List<?> check(@RequestBody Map<String, String> json){
		int idPro = Integer.parseInt(json.get("id_proyecto"));
		return empleadoService.searchEmployeesProject(idPro);
		
		
	}
	
	@PostMapping(path="empleados/update")
	@ResponseBody
	public ResponseEntity<Empleado> updateEmployee(@RequestBody Empleado empleado) {

		LOGGER.info("actualizando empleado con ID: ",empleado.getIdEmpleado());
		try{
			empleadoService.updateEmployee(empleado);
			LOGGER.info("Se ha actualiza correctamente el empleado [{}]", empleado.toString());
			return new ResponseEntity<>(empleado,httpStatus.OK);

		}catch (DataAccessException e){
			LOGGER.error("Error accediendo a la base de datos");
			LOGGER.error("error {}",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(httpStatus.BAD_REQUEST);

		}
		

	}
	
	//método para dar de baja directamente
	@PostMapping(path="empleados/baja")
	public ResponseEntity<Empleado> baja(@RequestParam int idEmpleado) {

		LOGGER.info("Dando de baja al empleado con ID: [{}]", idEmpleado);
		Empleado empleado = empleadoService.listarId(idEmpleado);

			try{
				empleadoService.darBaja(idEmpleado);
				LOGGER.info("Baja realizada al empleado con ID: [{}]",idEmpleado);
				return new ResponseEntity<>(empleado,httpStatus.OK);

			}catch(DataAccessException e){
				LOGGER.error("Error accediendo a la base de datos");
				LOGGER.error("error {}",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<>(empleado,httpStatus.BAD_REQUEST);
			}

		}
	
	//método para volver a dar de alta a un empleado
	@PostMapping(path="empleados/volverAlta")
	public ResponseEntity<String> volverAlta(@RequestParam int idEmpleado){
		LOGGER.info("Dando de alta de nuevo al empleado con ID: [{}]", idEmpleado);
		String result;

		try{
			empleadoService.volver(idEmpleado);
			LOGGER.info("El empleado con ID: [{}] ha sido dado de alta nuevamente", idEmpleado);
			result = "El empleado con ID: "+idEmpleado+" ha sido dado de alta nuevamente ";
			return new ResponseEntity<>(result,httpStatus.OK);
		}catch(DataAccessException e){

			LOGGER.error("error {}",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			result = "Error dando de alta: "+e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(result,httpStatus.BAD_REQUEST);
		}
	}
	
}