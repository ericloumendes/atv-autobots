package com.autobots.automanager.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entitades.Telefone;
import com.autobots.automanager.entitades.Usuario;

public interface RepositorioTelefone extends JpaRepository<Telefone, Long> {
}
