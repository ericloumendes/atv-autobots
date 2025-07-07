package com.autobots.sistema.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.sistema.entidades.Veiculo;


public interface RepositorioVeiculo extends JpaRepository<Veiculo, Long> {

}
