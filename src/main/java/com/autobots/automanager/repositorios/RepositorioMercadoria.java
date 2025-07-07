package com.autobots.automanager.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Usuario;

public interface RepositorioMercadoria extends JpaRepository<Mercadoria, Long> {

}
