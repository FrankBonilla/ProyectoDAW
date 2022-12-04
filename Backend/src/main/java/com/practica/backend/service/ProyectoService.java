package com.practica.backend.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practica.backend.repositories.ProyectoRepository;
import com.practica.backend.entities.Proyecto;

@Service
public class ProyectoService {
	
	@Autowired
	private ProyectoRepository repo;
	
	public List<Proyecto> listar(){
		return repo.findAll();
	}
	
	public Proyecto listarId(int id) {
		return repo.getReferenceById(id);
	}
	
	public Proyecto save(Proyecto proyecto) {
		repo.save(proyecto);
		return proyecto;
	}
	
	public void delete(int id) {
		repo.deleteById(id);
	}
	//mostrar los proyexctos que estan dados de alta
	public List<Proyecto> mostrarActivos(){
		return repo.findByF_alta();
	}
	//mostrar los proyectos que esten dados de baja
	public List<Proyecto> mostrarInactivos(){
		return repo.findByF_baja();
	}
	
	//metodo para dar de baja el pryecto
	public void darBaja(int id) {
		Proyecto proyecto = repo.getReferenceById(id);
		LocalDate fecha = LocalDate.now();
		proyecto.setFechaBaja(Date.valueOf(fecha));
		repo.save(proyecto);
		
	}
	
	public void updateProject(Proyecto p) {
		
		Proyecto proyecto = repo.getReferenceById(p.getId_proyecto());
		proyecto.setDescripcion(p.getDescripcion());
		proyecto.setFechaInicio(p.getFechaInicio());
		proyecto.setFechaFin(p.getFechaFin());
		proyecto.setLugar(p.getLugar());
		proyecto.setObservaciones(p.getObservaciones());
		
		repo.save(proyecto);
	}
	
	//implantacion del método 2 definido en el DAO
	public List<ProyectoRepository.ProyectosAsignados> searchProjectsOfEmple(int id){

		List<ProyectoRepository.ProyectosAsignados> list = repo.showAsigaciones(id);
		if(list != null && !list.isEmpty()) {
			return list;
		}else {
			return null;
		}
	}

}