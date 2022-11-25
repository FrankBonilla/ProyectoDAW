package com.practica.backend.controller;

import com.practica.backend.auth.JwtAuthResponseDTO;
import com.practica.backend.auth.JwtTokenProvider;
import com.practica.backend.entities.Usuario;
import com.practica.backend.repositories.RoleRepository;
import com.practica.backend.repositories.UsuarioRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:8081"})
@RestController
@RequestMapping("api/auth")
public class AuthController {

    private static final Logger LOGGER = LogManager.getLogger(AuthController.class);
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/iniciarSesion")
    public ResponseEntity<JwtAuthResponseDTO> authenticateUser(@RequestParam String username, @RequestParam String password){
        LOGGER.info(">>>> Entrando al método de inicio de sesión: authenticateUser");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        //obtenemos el token
        String token = jwtTokenProvider.generatorToken(authentication);
        LOGGER.info("Inicio de Sesión realizado por [{}]",authentication.getName());

        return ResponseEntity.ok(new JwtAuthResponseDTO(token));
    }
    /** POR PROBAR **/
    @PostMapping("/registrar")
    @ResponseBody
    public ResponseEntity<?> registerUser(@RequestBody Usuario usuario){
        LOGGER.info(">>>> Entrando al método de inicio de sesión: registerUser");

        if(usuarioRepository.existsByUserName(usuario.getUserName())){
            return  new ResponseEntity<>("Nombre de usuario ya existe", HttpStatus.BAD_REQUEST);
        }
        //hash de de la password antes de guardar usuario
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);

        return  new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.OK);
    }
}
