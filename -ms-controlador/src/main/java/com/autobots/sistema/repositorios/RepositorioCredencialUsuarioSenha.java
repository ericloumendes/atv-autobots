package com.autobots.sistema.repositorios;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.sistema.entidades.CredencialUsuarioSenha;

public interface RepositorioCredencialUsuarioSenha extends JpaRepository<CredencialUsuarioSenha, Long> {
    Optional<CredencialUsuarioSenha> findByNomeUsuario(String nomeUsuario);
}
