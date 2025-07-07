package com.autobots.automanager.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.autobots.automanager.entidades.Empresa;



public interface RepositorioEmpresa extends JpaRepository<Empresa, Long> {
	Optional<Empresa> findByTelefonesId(Long telefoneId);
	Optional<Empresa> findByMercadoriasId(Long mercadoriaId);
	Optional<Empresa> findByServicosId(Long servicoId);
}