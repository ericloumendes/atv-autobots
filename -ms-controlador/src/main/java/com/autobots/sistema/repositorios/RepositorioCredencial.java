package com.autobots.sistema.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.sistema.entidades.Credencial;

public interface RepositorioCredencial extends JpaRepository<Credencial, Long> {

}
