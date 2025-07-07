package com.autobots.sistema.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.sistema.entidades.Documento;


public interface RepositorioDocumento extends JpaRepository<Documento, Long> {

}
