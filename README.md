# ativade de desenvolvimento web III - autobots II

Url para as requisisições:
```
http://localhost:8080/
```

Rotas relacionadas à cliente:
```
    GET /cliente/{id}
    Obter um cliente em específico.

    GET /cliente/clientes
    Obter todos os clientes.

    POST /cliente/cadastrar
    Cadastrar um novo cliente.
    Recebe no corpo da requisição uma instância de usuário.

    PUT /cliente/atualizar
    Atualizar os dados de um cliente existente.
    Recebe no corpo da requisição uma instância de usuário.

    DELETE /cliente/excluir
    Excluir um cliente existente.
    Recebe no corpo da requisição um id do usuário a ser excluído.
```

Rotas relacionadas à telefone:
```
    GET /telefone
    Obter todos os telefones.

    GET /telefone/{id}
    Obter um telefone em específico.

    GET /telefone/cliente/{id}
    Obter todos os telefones de um cliente em específico.

    POST /telefone/cadastrar/{clienteId}
    Cadastrar um novo telefone para um cliente.
    Recebe no corpo da requisição uma instância de telefone.

    PUT /telefone/atualizar
    Atualizar um telefone existente.
    Recebe no corpo da requisição uma instância de telefone.

    DELETE /telefone/excluir
    Excluir um telefone associado a um cliente.
    Recebe no corpo da requisição um id do telefone a ser excluído
```

Rotas relacionadas à documento:
```
    GET /documento
    Obter todos os documentos.

    GET /documento/{id}
    Obter um documento em específico.

    GET /documento/cliente/{id}
    Obter todos os documentos de um cliente em específico.

    POST /documento/cadastrar/{clienteId}
    Cadastrar um novo documento para um cliente específico.
    Recebe no corpo da requisição uma instância de documento.

    PUT /documento/atualizar
    Atualizar os dados de um documento existente.
    Recebe no corpo da requisição uma instância de documento.

    DELETE /documento/excluir
    Excluir um documento associado a um cliente.
    Recebe no corpo da requisição um id do documento a ser excluído.
```

Rotas relacionadas à endereço:
```
    GET /endereco
    Obter todos os endereços.

    GET /endereco/{id}
    Obter um endereço em específico.

    GET /endereco/cliente/{id}
    Obter o endereço de um cliente em específico.

    POST /endereco/cadastrar/{clienteId}
    Cadastrar um endereço para um cliente.
    Recebe no corpo da requisição uma instância de endereço.

    PUT /endereco/atualizar
    Atualizar um endereço existente.
    Recebe no corpo da requisição uma instância de endereço.

    DELETE /endereco/excluir
    Excluir um endereço associado a um cliente.
    Recebe no corpo da requisição um id do endereço a ser exluído.
```