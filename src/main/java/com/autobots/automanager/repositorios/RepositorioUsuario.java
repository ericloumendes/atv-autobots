package com.autobots.automanager.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.autobots.automanager.entidades.Usuario;


public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByTelefonesId(Long telefoneId);
	Optional<Usuario> findByMercadoriasId(Long mercadoriaId);
	Optional<Usuario> findByEmailsId(Long emailId);
	Optional<Usuario> findByDocumentosId(Long documentoId);
	@Query("SELECT u FROM Usuario u JOIN u.credenciais c WHERE c.id = :credencialId")
    Optional<Usuario> findByCredenciaisId(@Param("credencialId") Long credencialId);
	Optional<Usuario> findByEnderecoId(Long enderecoId);
	Optional<Usuario> findByNome(String nome);
	@Query("SELECT u FROM Usuario u JOIN u.credenciais c WHERE c.nomeUsuario = :nomeUsuario")
    Optional<Usuario> buscarPorNomeUsuario(@Param("nomeUsuario") String nomeUsuario);
}
