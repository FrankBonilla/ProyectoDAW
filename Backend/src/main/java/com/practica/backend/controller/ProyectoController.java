package com.practica.backend.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.lowagie.text.DocumentException;
import com.practica.backend.reports.ActivesProjectsReportExcel;
import com.practica.backend.reports.ProjectReportExcell;
import com.practica.backend.reports.UnsuscribedProjectsReportExcel;
import com.practica.backend.repositories.ProyectoRepository;
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

import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = {"http://localhost:8081"})
@RestController
@RequestMapping(path="api/")
public class ProyectoController {

	private static final Logger LOGGER = LogManager.getLogger(ProyectoController.class);
	HttpStatus httpStatus;
	//inyectamos el servicio
	@Autowired
	ProyectoService proyectoService;
	@Autowired
	EmpleadoService empleadoService;

	/**Obtenemos todos los proyectos**/
	@GetMapping(path="proyectos/lista")
	public ResponseEntity<List<Proyecto>> getAllProjects(){
		LOGGER.info(">>>> Entrando al método: getAllProjects");
		List<Proyecto> result = null;

		try{
			LOGGER.info("Consultando todos los proyectos de la BDD");
			result = proyectoService.listar();

		}catch (DataAccessException e){
			LOGGER.error("Error accediendo a la base de datos");
			LOGGER.error("error: "+e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(httpStatus.BAD_REQUEST);
		}
		
		if(result == null){
			LOGGER.info("No se han encontrado proyectos en la BDD");
			return new ResponseEntity<>(null,httpStatus.NOT_FOUND);
		}

		LOGGER.info("Consulta de proyectos finalizada [{}] resultados",result.size());
		return new ResponseEntity<>(result,httpStatus.OK);
	}

	/**Obtenemos todos los proyectos activos de la empresa**/
	@GetMapping(path="proyectos/activos")
	public ResponseEntity<List<Proyecto>> getActiveProjects(){
		LOGGER.info(">>>> Entrando al método: getActiveProjects");
		List<Proyecto> result = null;

		try{
			LOGGER.info("Consultando todos los proyectos activos");
			result = proyectoService.mostrarActivos();

		}catch (DataAccessException e){
			LOGGER.error("Error accediendo a la base de datos");
			LOGGER.error("error: "+e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(httpStatus.BAD_REQUEST);
		}

		if(result == null){
			LOGGER.info("No se han encontrado proyectos activos en la BDD");
			return new ResponseEntity<>(result,httpStatus.NOT_FOUND);
		}

		LOGGER.info("Consulta de proyectos activos finalizada [{}] resultados",result.size());
		return new ResponseEntity<>(result,httpStatus.OK);

	}

	/**Obtenemos los proyectos ya dados de baja**/
	@GetMapping(path="proyectos/inactivos")
	public ResponseEntity<List<Proyecto>> getInactiveProjects(){
		LOGGER.info(">>>> Entrando al método: getInactiveProjects");
		List<Proyecto> result = null;

		try{
			LOGGER.info("Consultando todos los proyectos de baja");
			result = proyectoService.mostrarInactivos();

		}catch (DataAccessException e){
			LOGGER.error("Error accediendo a la base de datos");
			LOGGER.error("error: "+e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(httpStatus.BAD_REQUEST);
		}

		if(result == null){
			LOGGER.info("No se han encontrado proyectos de baja en la BDD");
			return new ResponseEntity<>(result,httpStatus.NOT_FOUND);
		}

		LOGGER.info("Consulta de proyectos de baja finalizada [{}] resultados",result.size());
		return new ResponseEntity<>(result,httpStatus.OK);
	}

	/**Creamos un nuevo proyecto**/
	@PostMapping(path="proyectos/guardar")
	@ResponseBody
	public ResponseEntity<Proyecto> saveProject(@RequestBody Proyecto proyecto) {
		LOGGER.info(">>>> Entrando al método: saveProject");
		Proyecto newProject = null;

		try{
			LOGGER.info("Guardando el Proyecto: [{}]", proyecto.getDescripcion());
			newProject = proyectoService.save(proyecto);
		}catch(DataAccessException e){
			LOGGER.error("Error accediendo a la base de datos");
			LOGGER.error("error: "+e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(httpStatus.BAD_REQUEST);
		}

		if(newProject == null){
			LOGGER.info("No se han encontrado proyectos de baja en la BDD");
			return new ResponseEntity<>(newProject,httpStatus.NO_CONTENT);
		}else{
			LOGGER.info("Se ha guardado un nuevo proyecto: [{}]",newProject.toString());
			return new ResponseEntity<>(newProject,httpStatus.CREATED);
		}
		
	}

	/**Método para borrar proyectos**/
	@DeleteMapping(path="proyectos/borrar")
	public void deleteProject(@RequestParam int idProyecto) {
		LOGGER.info(">>>> Entrando al método: deleteProject");

		try{
			LOGGER.info("Borrando el projecto con ID: [{}]", idProyecto);
			proyectoService.delete(idProyecto);

		}catch(DataAccessException e){
			LOGGER.error("Error accediendo a la base de datos");
			LOGGER.error("error: "+e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		LOGGER.info("Se ha borrado el projecto con ID: [{}]", idProyecto);
	}

	/**Método para asignar empleados a un proyecto**/
	@PostMapping(path="proyectos/asignarEmpleado")
	public ResponseEntity<Boolean> addEmp(@RequestParam int idProyecto, @RequestParam int idEmpleado) {
		LOGGER.info(">>>> Entrando al método: addEmp");
		Proyecto proyecto = proyectoService.listarId(idProyecto);
		Empleado empleado = empleadoService.listarId(idEmpleado);
		Boolean resultOK = false;

		if(proyecto != null && empleado != null){
			try{
				LOGGER.info("Asignando empleado [{}] a proyecto [{} - {}]",empleado.getNif(),proyecto.getId_proyecto(),proyecto.getDescripcion());
				proyecto.addEmployee(empleado);
				proyectoService.save(proyecto);

			}catch (DataAccessException e){
				LOGGER.error("Error accediendo a la base de datos");
				LOGGER.error("error: "+e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			}
			LOGGER.info("Empleado [{}] asignado correctamente a proyecto: [{} - {}]",empleado.getNif(),proyecto.getId_proyecto(),proyecto.getDescripcion());
			return new ResponseEntity<>(resultOK,httpStatus.OK);
		}else{
			LOGGER.info("Empleado o Proyecto no encontrado");
			return new ResponseEntity<>(resultOK,httpStatus.NOT_FOUND);
		}

	}

	/**Método para remover un empleado de un proyecto**/
	@PostMapping(path="proyectos/removeEmployee")
	public ResponseEntity<Boolean> removeEmp(@RequestParam int idProyecto, @RequestParam int idEmpleado) {
		LOGGER.info(">>>> Entrando al método: removeEmp");
		Boolean resultOK = false;

		try{
			Proyecto proyecto = proyectoService.listarId(idProyecto);
			Empleado empleado = empleadoService.listarId(idEmpleado);

			LOGGER.info("removiendo al empleado: [{}] del proyecto [{}]",empleado.getNif()+" - "+empleado.getNombre()+" "+empleado.getApellido1(),
					proyecto.getId_proyecto()+" - "+proyecto.getDescripcion());

			proyecto.removeEmployee(empleado);
			proyectoService.save(proyecto);
			resultOK = true;
		}catch(DataAccessException e){
			LOGGER.error("Error accediendo a la base de datos");
			LOGGER.error("error {}",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(httpStatus.INTERNAL_SERVER_ERROR);
		}

		if(resultOK){
			LOGGER.info("Se ha removido correctamente el empleado");
			return new ResponseEntity<>(resultOK,httpStatus.OK);
		}else{
			LOGGER.info("NO se ha removido correctamente el empleado del proyecto");
			return new ResponseEntity<>(resultOK,httpStatus.BAD_REQUEST);
		}
		
	}

	/**Verificamos si un empleado tiene asignaciones a un proyecto**/
	@GetMapping(path="proyectos/verificar")
	public ResponseEntity<List<ProyectoRepository.ProyectosAsignados>> searchProjectsOfEmple(@RequestParam int idEmpleado){
		LOGGER.info(">>>> Entrando al método: searchProjectsOfEmple");
		List<ProyectoRepository.ProyectosAsignados> result = null;

		try{
			LOGGER.info("verificando asignaciones a proyectos del empleado: [{}]", idEmpleado);
			result = proyectoService.searchProjectsOfEmple(idEmpleado);

		}catch (DataAccessException e){
			LOGGER.error("Error al realizar la consulta a la base de datos");
			LOGGER.error("error {}",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(result,httpStatus.BAD_REQUEST);
		}

		if(result == null){
			LOGGER.info("No tiene asignaciones a proyectos empleado: [{}]",idEmpleado);
			return new ResponseEntity<>(result,httpStatus.NOT_FOUND);
		}else{
			LOGGER.info("empleado con ID: [{}] tiene [{}] asignaciones",idEmpleado, result.size());
			return new ResponseEntity<>(result, httpStatus.OK);
		}

	}

	/**metodo para verificar si el proyecto tiene empleados asignados antes de darle de baja**/
	@GetMapping(path="proyectos/verificarPro")
	public ResponseEntity<List<Empleado>> checkProyect(@RequestParam int idProyecto){
		LOGGER.info(">>>> Entrando al método: checkProyect");
		List<Empleado> result = null;

		try{
			Proyecto proyectoVerificado = proyectoService.listarId(idProyecto);
			result = proyectoVerificado.getEmployees();
			LOGGER.info("Proyecto [{}] [{}] verificado ",proyectoVerificado.getId_proyecto(),proyectoVerificado.getDescripcion());
		}catch(DataAccessException e){
			LOGGER.error("Error al realizar la consulta a la base de datos");
			LOGGER.error("error {}",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(httpStatus.INTERNAL_SERVER_ERROR);
		}

		if(result != null && result.size() > 1){
			LOGGER.info("El proyecto verificado tiene [{}] asignaciones ",result.size());
			return new ResponseEntity<>(result,httpStatus.OK);
		}else{
			LOGGER.info("El proyecto verificado no tiene asignaciones ");
			return new ResponseEntity<>(result,httpStatus.NO_CONTENT);
		}

	}
	
	/**metodo para dar de baja al proyecto**/
	@PostMapping(path="proyectos/baja")
	public ResponseEntity<Boolean> bajaProyecto(@RequestParam int idProyecto) {
		LOGGER.info(">>>> Entrando al método: bajaProyecto");
		Boolean resultadoOK = false;

		try{
			LOGGER.info("Dando de baja al proyecto: [{}]", idProyecto);
			proyectoService.darBaja(idProyecto);
			resultadoOK = true;
		}catch (DataAccessException e){
			LOGGER.error("Error al realizar la consulta a la base de datos");
			LOGGER.error("error {}",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(resultadoOK,httpStatus.BAD_REQUEST);
		}

		if(resultadoOK){
			LOGGER.info("Se ha dado de baja el proyecto con ID=[{}]",idProyecto);
			return new ResponseEntity<>(resultadoOK,httpStatus.OK);

		}else{
			LOGGER.info("NO se ha podido dar de baja el proyecto con ID=[{}]",idProyecto);
			return new ResponseEntity<>(resultadoOK,httpStatus.BAD_REQUEST);
		}

	}

	/**Método para actualizar un proyecto**/
	@PostMapping(path="proyectos/update")
	@ResponseBody
	public ResponseEntity<Boolean> updateProject(@RequestBody Proyecto proyecto) {
		LOGGER.info(">>>> Entrando al método: updateProject");
		Boolean resultOK = false;

		try{
			LOGGER.info("Actualizando el proyecto: [{}] - [{}]",proyecto.getId_proyecto(),proyecto.getDescripcion());
			proyectoService.updateProject(proyecto);
			resultOK = true;
		}catch (DataAccessException e){
			LOGGER.error("Error al realizar la consulta a la base de datos");
			LOGGER.error("error {}",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(httpStatus.BAD_REQUEST);

		}

		if(resultOK == false){
			LOGGER.info("NO se ha podido actualizar el proyecto correctamente:[{}] - [{}]",proyecto.getId_proyecto(),proyecto.getDescripcion());
			return new ResponseEntity<>(resultOK,httpStatus.BAD_REQUEST);
		}else{
			LOGGER.info("Proyecto actualizado correctamente:[{}]",proyecto.toString());
			return new ResponseEntity<>(resultOK,httpStatus.OK);
		}

	}
	///////////////////////////////////// EXPORTACIONES A EXCEL ///////////////////////////////////////////////////////////////
	/**Exportación a excell de los proyectos activos**/
	@GetMapping(path = "proyectos/exportar/excel/proyectos/activos")
	public void exportActivesProjectsReportExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octect-stream");

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String today = dateFormat.format(new Date());

		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Proyectos_"+today+".xlsx";

		List<Proyecto> proyectoList = proyectoService.mostrarActivos();

		ActivesProjectsReportExcel reportExcel = new ActivesProjectsReportExcel(proyectoList);
		reportExcel.export(response);
	}

	/**Exportación a excell de un proyecto**/
	@GetMapping(path = "proyectos/exportar/excel/proyecto")
	public void exportProjectReportToExcel(HttpServletResponse response, @RequestParam Integer idProyecto) throws DocumentException, IOException {
		response.setContentType("application/octect-stream");

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String today = dateFormat.format(new Date());

		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Proyectos_"+today+".xlsx";

		Proyecto proyecto = proyectoService.listarId(idProyecto);

		ProjectReportExcell projectReportExcell = new ProjectReportExcell(proyecto);
		projectReportExcell.export(response);

	}

	/**Exportación a excell de los proyectos de baja**/
	@GetMapping(path = "proyectos/exportar/excel/inactivos")
	public void exportUnsuscribesProjectsReportExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octect-stream");

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String today = dateFormat.format(new Date());

		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Proyectos_"+today+".xlsx";

		List<Proyecto> proyectoList = proyectoService.mostrarInactivos();

		UnsuscribedProjectsReportExcel unsuscribedProjectsReportExcel = new UnsuscribedProjectsReportExcel(proyectoList);
		unsuscribedProjectsReportExcel.export(response);

	}
}