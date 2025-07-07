package com.autobots.sistema.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.sistema.entidades.Telefone;


public interface RepositorioTelefone extends JpaRepository<Telefone, Long> {
}
