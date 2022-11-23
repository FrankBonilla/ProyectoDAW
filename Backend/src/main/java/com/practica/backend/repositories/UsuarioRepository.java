package com.practica.backend.repositories;

import com.practica.backend.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUserName(String username);

    Boolean existsByUserName(String username);

}
