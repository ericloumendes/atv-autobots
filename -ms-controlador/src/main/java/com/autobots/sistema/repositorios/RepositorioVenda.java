package com.autobots.sistema.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.sistema.entidades.Venda;

public interface RepositorioVenda extends JpaRepository<Venda, Long> {

}
