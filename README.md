# ativade de desenvolvimento web III - autobots IV

Url para as requisisições:
```
http://localhost:8080/
```

Rotas relacionadas à autênticação:
```
    POST /auth/login
    Autêntica um usuário e retorna um jwt caso bem-sucedido.

    POST /auth/registrar
    Registra um novo usuário
```

Rotas relacionadas à credencial:
```
    GET /credencial/  
    Obter todas as credenciais. (ADMIN)

    GET /credencial/{id}
    Obter credencial por ID. (Dono, ADMIN)

    POST /credencial/cadastrar/usuario  
    Cadastrar nova credencial do tipo usuário/senha. (ADMIN)

    PUT /credencial/atualizar
    Atualizar uma credencial existente. (ADMIN)

    DELETE /credencial/deletar
    Excluir uma credencial. (ADMIN)
```

Rotas relacionadas à empresa:
```
    GET /empresa/  
    Obter todos as empresas. (ADMIN, GERENTE, VENDEDOR)

    GET /empresa/{id}
    Obter empresa por ID. (ADMIN, GERENTE, VENDEDOR)

    POST /empresa/cadastrar  
    Cadastrar uma nova empresa. (ADMIN, GERENTE, VENDEDOR)

    PUT /empresa/atualizar  
    Atualizar um empresa existente. (ADMIN, GERENTE)

    DELETE /empresa/deletar  
    Excluir um empresa. (ADMIN, GERENTE)
```

Rotas relacionadas ao documento:
```
    GET /documento/  
    Obter todos os documentos. 

    GET /documento/{id}  
    Obter documento por ID. (Dono)

    POST /documento/cadastrar/usuario/{usuarioId}  
    Cadastrar novo documento para um usuário específico. (ADMIN, GERENTE, VENDEDOR)

    PUT /documento/atualizar  
    Atualizar um documento existente. (ADMIN, GERENTE, VENDEDOR)

    DELETE /documento/deletar  
    Excluir um documento. (ADMIN, GERENTE, VENDEDOR)
```

Rotas relacionadas ao email:
```
    GET /email/  
    Obter todos os e-mails. (CLIENTE)

    GET /email/{id}  
    Obter e-mail por ID. (CLIENTE)

    POST /email/cadastrar/usuario/{usuarioId}  
    Cadastrar e-mail para um usuário. (ADMIN, GERENTE, VENDEDOR)

    PUT /email/atualizar  
    Atualizar um e-mail existente. (ADMIN, GERENTE, VENDEDOR)

    DELETE /email/deletar  
    Excluir um e-mail. (ADMIN, GERENTE, VENDEDOR)
```

Rotas relacionadas ao endereço:
```
    GET /endereco/  
    Obter todos os endereços. (Dono)

    GET /endereco/{id}
    Obter endereço por ID. (Dono)

    POST /endereco/cadastrar/usuario/{usuarioId}  
    Cadastrar endereço para um usuário. (ADMIN, GERENTE, VENDEDOR)

    PUT /endereco/atualizar  
    Atualizar um endereço existente. (ADMIN, GERENTE, VENDEDOR)

    DELETE /endereco/deletar  
    Excluir um endereço. (ADMIN, GERENTE, VENDEDOR)
```

Rotas relacionadas à mercadoria:
```
    GET /mercadoria/      
    Obter todas as mercadorias. (Dono)

    GET /mercadoria/{id}  
    Obter mercadoria por ID. (Dono)

    POST /mercadoria/cadastrar/usuario/{usuarioId}  
    Cadastrar mercadoria para um usuário. (ADMIN, GERENTE, VENDEDOR)

    POST /mercadoria/cadastrar/empresa/{empresaId}  
    Cadastrar mercadoria para uma empresa. (ADMIN, GERENTE, VENDEDOR)

    PUT /mercadoria/atualizar  
    Atualizar uma mercadoria existente. (ADMIN, GERENTE, VENDEDOR)

    DELETE /mercadoria/deletar
    Excluir uma mercadoria. (ADMIN, GERENTE, VENDEDOR)
```

Rotas relacionadas ao serviço
```
    GET /servico/  
    Obter todos os serviços. (Dono)

    GET /servico/{id}  
    Obter serviço por ID. (Dono)

    POST /servico/cadastrar/empresa/{empresaId}   
    Cadastrar serviço para uma empresa. (ADMIN, GERENTE, VENDEDOR)

    PUT /servico/atualizar    
    Atualizar um serviço existente. (ADMIN, GERENTE, VENDEDOR)

    DELETE /servico/deletar  
    Excluir um serviço. (ADMIN, GERENTE, VENDEDOR)
```

Rotas relacionadas ao telefone:
```
    GET /telefone/  
    Obter todos os telefones. (Dono)

    GET /telefone/{id}  
    Obter telefone por ID. (Dono)

    POST /telefone/cadastrar/{usuarioId}  
    Cadastrar telefone para um usuário. (ADMIN, GERENTE, VENDEDOR)

    PUT /telefone/atualizar  
    Atualizar um telefone existente. (ADMIN, GERENTE, VENDEDOR)

    DELETE /telefone/deletar  
    Excluir um telefone. (ADMIN, GERENTE, VENDEDOR)
```

Rotas relacionadas ao usuário:
```
    GET /usuario/     
    Obter todos os usuários. (Dono)

    GET /usuario/{id}  
    Obter usuário por ID. (Dono)

    POST /usuario/cadastrar  
    Cadastrar um novo usuário. (ADMIN, GERENTE, VENDEDOR)

    PUT /usuario/atualizar  
    Atualizar um usuário existente. (ADMIN, GERENTE, VENDEDOR)

    DELETE /usuario/deletar   
    Excluir um usuário. (ADMIN, GERENTE, VENDEDOR)
```
Rotas relacionadas ao veículo:
```
    GET /veiculo/ 
    Obter todos os veículos. (Dono)

    GET /veiculo/{id} 
    Obter veículo por ID. (Dono)

    POST /veiculo/cadastrar/usuario/{usuarioId} 
    Cadastrar um veículo para um usuário. (ADMIN, GERENTE, VENDEDOR)

    PUT /veiculo/atualizar 
    Atualizar um veículo existente. (ADMIN, GERENTE, VENDEDOR)

    DELETE /veiculo/deletar  
    Excluir um veículo. (ADMIN, GERENTE, VENDEDOR)
```
Rotas relacionadas à venda
```
    GET /venda/  
    Obter todas as vendas. (Dono)

    GET /venda/{id}  
    Obter venda por ID. (Dono)

    POST /venda/cadastrar/usuario/{usuarioId}     
    Cadastrar venda para um usuário. (ADMIN, GERENTE, VENDEDOR)

    POST /venda/cadastrar/empresa/{empresaId}  
    Cadastrar venda para uma empresa. (ADMIN, GERENTE, VENDEDOR)

    PUT /venda/atualizar  
    Atualizar uma venda existente. (ADMIN, GERENTE, VENDEDOR)

    DELETE /venda/deletar  
    Excluir uma venda. (ADMIN, GERENTE, VENDEDOR)
```