package com.autobots.automanager.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.DTO.DocumentoDTO;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.mapper.DocumentoMapper;
import com.autobots.automanager.repositorios.RepositorioDocumento;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@Service
public class DocumentoService {
    
    @Autowired
    private RepositorioDocumento repositorio;
    
    @Autowired
	private RepositorioUsuario usuarioRepositorio;

    // Método para obter todos os documentos
    public List<DocumentoDTO> obterDocumentos() {
        List<Documento> documentos = repositorio.findAll();
        return documentos.stream()
                .map(documento -> DocumentoMapper.toDTO(documento))
                .collect(Collectors.toList());
    }

    // Método para obter um documento específico pelo ID
    public DocumentoDTO obterDocumento(Long id) {
        Documento documento = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado com id: " + id));
        return DocumentoMapper.toDTO(documento);
    }

    // Método para cadastrar um novo documento
    public DocumentoDTO cadastrarDocumento(DocumentoDTO dto, Long id) {
    	Usuario usuario = usuarioRepositorio.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + dto.getId()));
        Documento documento = DocumentoMapper.toEntity(dto);
        Documento documentoCadastrado = repositorio.save(documento);
        usuario.getDocumentos().add(documentoCadastrado);
        usuarioRepositorio.save(usuario);
        return DocumentoMapper.toDTO(documentoCadastrado);
    }

    // Método para atualizar um documento existente
    public DocumentoDTO atualizarDocumento(DocumentoDTO dto) {
        Documento documento = repositorio.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Documento não encontrado com id: " + dto.getId()));

        if (dto.getTipo() != null) {
            documento.setTipo(dto.getTipo());
        }

        if (dto.getNumero() != null) {
            documento.setNumero(dto.getNumero());
        }

        if (dto.getDataEmissao() != null) {
            documento.setDataEmissao(dto.getDataEmissao());
        }

        Documento documentoAtualizado = repositorio.save(documento);
        return DocumentoMapper.toDTO(documentoAtualizado);
    }

    public void deletarDocumento(DocumentoDTO dto) {
        Documento documento = repositorio.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Documento não encontrado com id: " + dto.getId()));
        
        Optional<Usuario> usuarioOpt = usuarioRepositorio.findByDocumentosId(dto.getId());
	    if (usuarioOpt.isPresent()) {
	        Usuario usuario = usuarioOpt.get();
	        usuario.getDocumentos().remove(documento);
	        usuarioRepositorio.save(usuario);
	    }
        repositorio.delete(documento);
    }
}
