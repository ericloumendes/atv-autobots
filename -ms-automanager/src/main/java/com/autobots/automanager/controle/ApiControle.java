package com.autobots.automanager.controle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth") 
public class ApiControle {

    @Autowired
    private RestTemplate restTemplate;

    // --- URLs dos Serviços Internos (Configuráveis em application.properties) ---
    @Value("${url.servico.usuario:http://localhost:8081/usuario}")
    private String urlBaseServicoUsuario;

    @Value("${url.servico.mercadoria:http://localhost:8081/mercadoria}")
    private String urlBaseServicoMercadoria;

    @Value("${url.servico.servico:http://localhost:8081/servico}")
    private String urlBaseServicoServico;

    @Value("${url.servico.veiculo:http://localhost:8081/veiculo}")
    private String urlBaseServicoVeiculo;

    @Value("${url.servico.venda:http://localhost:8081/venda}")
    private String urlBaseServicoVenda;


    // ======================== USUÁRIOS ========================
    @Operation(summary = "Obter todos os usuários", description = "Retorna uma lista de todos os usuários. Acesso restrito a ADMIN.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuários encontrada"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/usuarios")
    public ResponseEntity<?> obterUsuarios(HttpServletRequest request) {
        return encaminharRequisicao(request, urlBaseServicoUsuario + "/", HttpMethod.GET, null);
    }

    @Operation(summary = "Obter todos os clientes", description = "Retorna uma lista de usuários com o perfil de CLIENTE.")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
    @GetMapping("/clientes")
    public ResponseEntity<?> obterClientes(HttpServletRequest request) {
        return encaminharRequisicao(request, urlBaseServicoUsuario + "/clientes", HttpMethod.GET, null);
    }

    @Operation(summary = "Obter todos os funcionários", description = "Retorna uma lista de usuários que são funcionários (GERENTE, VENDEDOR).")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE')")
    @GetMapping("/funcionarios")
    public ResponseEntity<?> obterFuncionarios(HttpServletRequest request) {
        return encaminharRequisicao(request, urlBaseServicoUsuario + "/funcionarios", HttpMethod.GET, null);
    }

    // ======================== MERCADORIAS ========================
    @Operation(summary = "Obter todas as mercadorias", description = "Retorna a lista de todas as mercadorias. Clientes veem apenas as suas.")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/mercadorias")
    public ResponseEntity<?> obterMercadorias(HttpServletRequest request) {
        return encaminharRequisicao(request, urlBaseServicoMercadoria + "/", HttpMethod.GET, null);
    }

    @Operation(summary = "Obter mercadoria por ID", description = "Retorna uma mercadoria específica pelo seu ID.")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/mercadorias/{id}")
    public ResponseEntity<?> obterMercadoria(HttpServletRequest request, @PathVariable Long id) {
        return encaminharRequisicao(request, urlBaseServicoMercadoria + "/" + id, HttpMethod.GET, null);
    }

    // ======================== SERVIÇOS ========================
    @Operation(summary = "Obter todos os serviços", description = "Retorna a lista de todos os serviços. Clientes veem apenas os seus.")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/servicos")
    public ResponseEntity<?> obterServicos(HttpServletRequest request) {
        return encaminharRequisicao(request, urlBaseServicoServico + "/", HttpMethod.GET, null);
    }

    @Operation(summary = "Obter serviço por ID", description = "Retorna um serviço específico pelo seu ID.")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/servicos/{id}")
    public ResponseEntity<?> obterServico(HttpServletRequest request, @PathVariable Long id) {
        return encaminharRequisicao(request, urlBaseServicoServico + "/" + id, HttpMethod.GET, null);
    }

    // ======================== VEÍCULOS ========================
    @Operation(summary = "Obter todos os veículos", description = "Retorna a lista de todos os veículos. Clientes veem apenas os seus.")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/veiculos")
    public ResponseEntity<?> obterVeiculos(HttpServletRequest request) {
        return encaminharRequisicao(request, urlBaseServicoVeiculo + "/", HttpMethod.GET, null);
    }

    @Operation(summary = "Obter veículo por ID", description = "Retorna um veículo específico pelo seu ID.")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/veiculos/{id}")
    public ResponseEntity<?> obterVeiculo(HttpServletRequest request, @PathVariable Long id) {
        return encaminharRequisicao(request, urlBaseServicoVeiculo + "/" + id, HttpMethod.GET, null);
    }

    // ======================== VENDAS ========================
    @Operation(summary = "Obter todas as vendas", description = "Retorna a lista de todas as vendas. Clientes veem apenas as suas.")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/vendas")
    public ResponseEntity<?> obterVendas(HttpServletRequest request) {
        return encaminharRequisicao(request, urlBaseServicoVenda + "/", HttpMethod.GET, null);
    }
    
    @Operation(summary = "Obter venda por ID", description = "Retorna uma venda específica pelo seu ID.")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
    @GetMapping("/vendas/{id}")
    public ResponseEntity<?> obterVenda(HttpServletRequest request, @PathVariable Long id) {
        return encaminharRequisicao(request, urlBaseServicoVenda + "/" + id, HttpMethod.GET, null);
    }

    /**
     * Método auxiliar genérico para extrair o token JWT e encaminhar a requisição para um serviço interno.
     * @param request A requisição HTTP original vinda do cliente.
     * @param urlDestino A URL completa do serviço interno para o qual a requisição será enviada.
     * @param metodo O método HTTP a ser usado (GET, POST, PUT, DELETE).
     * @param corpo O corpo da requisição (pode ser nulo para GETs).
     * @return A resposta exata do serviço interno, seja de sucesso ou erro.
     */
    private ResponseEntity<?> encaminharRequisicao(HttpServletRequest request, String urlDestino, HttpMethod metodo, @Nullable Object corpo) {
        try {
            final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token JWT ausente ou mal formatado.");
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, authorizationHeader);
            headers.set(HttpHeaders.CONTENT_TYPE, "application/json");

            HttpEntity<?> entity = new HttpEntity<>(corpo, headers);

            return restTemplate.exchange(
                urlDestino,
                metodo,
                entity,
                Object.class
            );

        } catch (HttpClientErrorException e) {
            // Retorna o erro exato (status e corpo) do serviço de destino
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        } catch (Exception e) {
            // Lida com outros erros, como o serviço de destino estar offline
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao se comunicar com o serviço de destino: " + e.getMessage());
        }
    }
}