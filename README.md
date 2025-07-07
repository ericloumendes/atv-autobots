# ativade de desenvolvimento web III - autobots III

Url para as requisisições:
```
http://localhost:8080/
```

Rotas relacionadas à credencial:
```
    GET /credencial/  
    Obter todas as credenciais.

    GET /credencial/{id}
    Obter credencial por ID.

    POST /credencial/cadastrar/usuario  
    Cadastrar nova credencial do tipo usuário/senha.

    POST /credencial/cadastrar/codigo-barra  
    Cadastrar nova credencial com código de barras.

    PUT /credencial/atualizar
    Atualizar uma credencial existente.

    DELETE /credencial/deletar
    Excluir uma credencial.
```

Rotas relacionadas à empresa:
```
    GET /empresa/  
    Obter todos as empresas.

    GET /empresa/{id}
    Obter empresa por ID.

    POST /empresa/cadastrar  
    Cadastrar uma nova empresa.

    PUT /empresa/atualizar  
    Atualizar um empresa existente.

    DELETE /empresa/deletar  
    Excluir um empresa.
```

Rotas relacionadas ao documento:
```
    GET /documento/  
    Obter todos os documentos.

    GET /documento/{id}  
    Obter documento por ID.

    POST /documento/cadastrar/usuario/{usuarioId}  
    Cadastrar novo documento para um usuário específico.

    PUT /documento/atualizar  
    Atualizar um documento existente.

    DELETE /documento/deletar  
    Excluir um documento.
```

Rotas relacionadas ao email:
```
    GET /email/  
    Obter todos os e-mails.

    GET /email/{id}  
    Obter e-mail por ID.

    POST /email/cadastrar/usuario/{usuarioId}  
    Cadastrar e-mail para um usuário.

    PUT /email/atualizar  
    Atualizar um e-mail existente.

    DELETE /email/deletar  
    Excluir um e-mail.
```

Rotas relacionadas ao endereço:
```
    GET /endereco/  
    Obter todos os endereços.

    GET /endereco/{id}
    Obter endereço por ID.

    POST /endereco/cadastrar/usuario/{usuarioId}  
    Cadastrar endereço para um usuário.

    PUT /endereco/atualizar  
    Atualizar um endereço existente.

    DELETE /endereco/deletar  
    Excluir um endereço.
```

Rotas relacionadas à mercadoria:
```
    GET /mercadoria/      
    Obter todas as mercadorias.

    GET /mercadoria/{id}  
    Obter mercadoria por ID.

    POST /mercadoria/cadastrar/usuario/{usuarioId}  
    Cadastrar mercadoria para um usuário.

    POST /mercadoria/cadastrar/empresa/{empresaId}  
    Cadastrar mercadoria para uma empresa.

    PUT /mercadoria/atualizar  
    Atualizar uma mercadoria existente.

    DELETE /mercadoria/deletar
    Excluir uma mercadoria.
```

Rotas relacionadas ao serviço
```
    GET /servico/  
    Obter todos os serviços.

    GET /servico/{id}  
    Obter serviço por ID.

    POST /servico/cadastrar/empresa/{empresaId}   
    Cadastrar serviço para uma empresa.

    PUT /servico/atualizar    
    Atualizar um serviço existente.

    DELETE /servico/deletar  
    Excluir um serviço.
```

Rotas relacionadas ao telefone:
```
    GET /telefone/  
    Obter todos os telefones.

    GET /telefone/{id}  
    Obter telefone por ID.

    POST /telefone/cadastrar/{usuarioId}  
    Cadastrar telefone para um usuário.

    PUT /telefone/atualizar  
    Atualizar um telefone existente.

    DELETE /telefone/deletar  
    Excluir um telefone.
```

Rotas relacionadas ao usuário:
```
    GET /usuario/     
    Obter todos os usuários.

    GET /usuario/{id}  
    Obter usuário por ID.

    POST /usuario/cadastrar  
    Cadastrar um novo usuário.

    PUT /usuario/atualizar  
    Atualizar um usuário existente.

    DELETE /usuario/deletar   
    Excluir um usuário.
```
Rotas relacionadas ao veículo:
```
    GET /veiculo/ 
    Obter todos os veículos.

    GET /veiculo/{id} 
    Obter veículo por ID.

    POST /veiculo/cadastrar/usuario/{usuarioId} 
    Cadastrar um veículo para um usuário.

    PUT /veiculo/atualizar 
    Atualizar um veículo existente.

    DELETE /veiculo/deletar  
    Excluir um veículo.
```
Rotas relacionadas à venda
```
    GET /venda/  
    Obter todas as vendas.

    GET /venda/{id}  
    Obter venda por ID.

    POST /venda/cadastrar/usuario/{usuarioId}     
    Cadastrar venda para um usuário.

    POST /venda/cadastrar/empresa/{empresaId}  
    Cadastrar venda para uma empresa.

    PUT /venda/atualizar  
    Atualizar uma venda existente.

    DELETE /venda/deletar  
    Excluir uma venda.
```