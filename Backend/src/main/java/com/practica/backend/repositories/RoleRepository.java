package com.practica.backend.repositories;

import com.practica.backend.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role, Integer> {

    Optional<Role> findByNombre(String nombre);
}
