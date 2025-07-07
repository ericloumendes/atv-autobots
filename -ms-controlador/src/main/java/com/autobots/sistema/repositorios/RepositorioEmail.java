package com.autobots.sistema.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.sistema.entidades.Email;



public interface RepositorioEmail extends JpaRepository<Email, Long> {

}
