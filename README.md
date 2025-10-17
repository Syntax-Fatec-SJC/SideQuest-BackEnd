# SideQuest API - Documentação de Endpoints

## Visão Geral

---

## Autenticação

### 1. Cadastro de Usuário

**Endpoint:** `POST /cadastrar/usuarios`  
**Token:** Não necessário  

**Exemplo de JSON:**

```json
{
    "nome": "João Silva",
    "email": "joao.silva@exemplo.com",
    "senha": "SenhaForte123!"
}
2. Login
Endpoint: POST /login
Token: Não necessário

Exemplo de JSON:

json
Copiar código
{
    "email": "joao.silva@exemplo.com",
    "senha": "SenhaForte123!"
}
Retorno:

json
Copiar código
{
    "id": "id_do_usuario",
    "nome": "João Silva",
    "email": "joao.silva@exemplo.com",
    "token": "token_jwt_gerado"
}
Projetos
3. Criar Projeto
Endpoint: POST /cadastrar/projetos?usuarioIdCriador={ID_USUARIO}
Token: Necessário

Exemplo de JSON:

json
Copiar código
{
    "nome": "Projeto SideQuest",
    "descricao": "Sistema de gerenciamento de projetos",
    "status": "Em andamento",
    "prazoFinal": "2024-12-31"
}
4. Listar Projetos do Usuário
Endpoint: GET /listar/{usuarioId}/projetos
Token: Necessário

Membros do Projeto
5. Adicionar Membro
Endpoint: POST /adicionar/{projetoId}/membros/{usuarioId}
Token: Necessário

6. Listar Membros do Projeto
Endpoint: GET /listar/{projetoId}/membros
Token: Necessário

Tarefas
7. Criar Tarefa
Endpoint: POST /cadastrar/tarefas
Token: Necessário

Exemplo de JSON:

json
Copiar código
{
    "nome": "Primeira Tarefa",
    "descricao": "Descrição da tarefa",
    "status": "Pendente",
    "projetoId": "ID_DO_PROJETO",
    "prazoFinal": "2024-06-30",
    "usuarioIds": ["ID_USUARIO_RESPONSAVEL"]
}
8. Listar Tarefas do Projeto
Endpoint: GET /projetos/{projetoId}/tarefas
Token: Necessário

Dicas Importantes
Sempre incluir token JWT no header Authorization

Formato do token: Bearer {token_gerado_no_login}

Verificar IDs ao criar/adicionar membros/tarefas

🛠️ Status Possíveis
Status de Projeto
"Não iniciado"

"Em andamento"

"Concluído"

"Pausado"

Status de Tarefa
"Pendente"

"Em progresso"

"Concluída"

"Bloqueada"

Códigos de Resposta
Código	Significado
200 OK	Sucesso na requisição
201 CREATED	Recurso criado com sucesso
400 BAD REQUEST	Erro nos dados enviados
401 UNAUTHORIZED	Token inválido ou ausente
404 NOT FOUND	Recurso não encontrado