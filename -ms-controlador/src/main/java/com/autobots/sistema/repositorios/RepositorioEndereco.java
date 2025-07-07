package com.autobots.sistema.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.sistema.entidades.Endereco;


public interface RepositorioEndereco extends JpaRepository<Endereco, Long> {

}
