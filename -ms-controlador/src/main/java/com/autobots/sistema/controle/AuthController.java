package com.autobots.sistema.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.sistema.entidades.Usuario;
import com.autobots.sistema.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.authenticate(loginRequest.getNomeUsuario(), loginRequest.getSenha());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario usuario) {
        Usuario newUser = authService.register(usuario);
        return ResponseEntity.ok(newUser);
    }
}