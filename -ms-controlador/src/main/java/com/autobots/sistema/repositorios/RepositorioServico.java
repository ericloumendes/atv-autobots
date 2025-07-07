package com.autobots.sistema.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.sistema.entidades.Servico;


public interface RepositorioServico extends JpaRepository<Servico, Long> {

}
