# SideQuest - Backend API

Backend do sistema SideQuest implementado com arquitetura de microserviços usando Spring Boot.

## 📋 Índice

- [Visão Geral](#visão-geral)
- [Arquitetura](#arquitetura)
- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Configuração](#configuração)
- [Como Rodar](#como-rodar)
- [Testando no Insomnia](#testando-no-insomnia)
- [Endpoints Principais](#endpoints-principais)
- [Upload de Arquivos](#upload-de-arquivos)
- [Documentação Swagger](#documentação-swagger)
- [Monitoramento](#monitoramento)
- [Troubleshooting](#troubleshooting)

---

## 🏗️ Arquitetura

```
Frontend (5173)
    ↓
API Gateway (:8080) ← PONTO DE ENTRADA ÚNICO
    ↓
    ├→ Usuario Service (:8082)
    ├→ Projetos Service (:8083)
    ├→ Tarefas Service (:8084)
    └→ Anexo Service (:8085)
           ↓
    MongoDB Atlas (Cloud)
```

### 🚀 Serviços

| Serviço | Porta | Descrição |
|---------|-------|-----------|
| **API Gateway** | 8080 | Ponto de entrada único, autenticação JWT, rate limiting |
| **Usuario Service** | 8082 | Gerenciamento de usuários e autenticação |
| **Projetos Service** | 8083 | CRUD de projetos e gerenciamento de membros |
| **Tarefas Service** | 8084 | CRUD de tarefas com status e prioridades |
| **Anexo Service** | 8085 | Upload e download de arquivos (até 50MB) |

---

## ⚙️ Pré-requisitos

- **Java 21** ([Download](https://adoptium.net/))
- **Maven 3.8+** (ou use o wrapper `./mvnw` incluído)
- **MongoDB Atlas** (conta gratuita em [mongodb.com](https://www.mongodb.com/cloud/atlas))
- **Insomnia ou Postman** para testes de API

---

## 📦 Instalação

### 1. Clone o repositório

```bash
git clone https://github.com/Syntax-Fatec-SJC/SideQuest-BackEnd.git
cd SideQuest-BackEnd
```

### 2. Estrutura de pastas

```
SideQuest-BackEnd/
├── api-gateway/
├── usuario-service/
├── projetos-service/
├── tarefas-service/
└── anexo-service/
```

### 3. Configure o MongoDB Atlas

1. Crie uma conta em [MongoDB Atlas](https://www.mongodb.com/cloud/atlas)
2. Crie um cluster gratuito (M0)
3. Configure o acesso à rede:
   - Network Access → Add IP Address → Allow Access from Anywhere (0.0.0.0/0)
4. Crie um usuário de banco de dados:
   - Database Access → Add New Database User
   - Username: `syntax`, Password: `fatec123` (ou escolha seus próprios)
5. Obtenha a connection string:
   - Clusters → Connect → Connect your application
   ```
   mongodb+srv://syntax:fatec123@cluster.xxxxx.mongodb.net/syntaxbd
   ```

---

## 🔧 Configuração

### Configure cada microserviço

Edite os arquivos `application.properties` em `src/main/resources/` de cada serviço:

#### usuario-service/src/main/resources/application.properties
```properties
server.port=8082
spring.application.name=usuario-service
spring.data.mongodb.uri=mongodb+srv://syntax:fatec123@cluster.xxxxx.mongodb.net/syntaxbd
spring.data.mongodb.database=syntaxbd
jwt.secret=SideQuestSecretKey2024ForJWTAuthenticationAndAuthorizationSystem
```

#### projetos-service/src/main/resources/application.properties
```properties
server.port=8083
spring.application.name=projetos-service
spring.data.mongodb.uri=mongodb+srv://syntax:fatec123@cluster.xxxxx.mongodb.net/syntaxbd
spring.data.mongodb.database=syntaxbd
```

#### tarefas-service/src/main/resources/application.properties
```properties
server.port=8084
spring.application.name=tarefas-service
spring.data.mongodb.uri=mongodb+srv://syntax:fatec123@cluster.xxxxx.mongodb.net/syntaxbd
spring.data.mongodb.database=syntaxbd
projetos.service.url=http://localhost:8080
gateway.secret=SideQuestGatewaySecret2024
```

#### anexo-service/src/main/resources/application.properties
```properties
server.port=8085
spring.application.name=anexo-service
spring.data.mongodb.uri=mongodb+srv://syntax:fatec123@cluster.xxxxx.mongodb.net/sidequest_anexo
spring.servlet.multipart.max-file-size=50MB
spring.servlet.multipart.max-request-size=50MB
```

#### api-gateway/src/main/resources/application.properties
```properties
server.port=8080
spring.application.name=api-gateway
microservices.usuario.url=http://localhost:8082
microservices.projetos.url=http://localhost:8083
microservices.tarefas.url=http://localhost:8084
microservices.anexos.url=http://localhost:8085
jwt.secret=SideQuestSecretKey2024ForJWTAuthenticationAndAuthorizationSystem
jwt.expiration=86400000
```

---

## 🚀 Como Rodar

### ⚠️ IMPORTANTE: Inicie os serviços NESTA ORDEM

Abra **5 terminais diferentes** e execute:

```bash
# Terminal 1 - Usuario Service
cd usuario-service
./mvnw spring-boot:run

# Terminal 2 - Projetos Service
cd projetos-service
./mvnw spring-boot:run

# Terminal 3 - Tarefas Service
cd tarefas-service
./mvnw spring-boot:run

# Terminal 4 - Anexo Service
cd anexo-service
./mvnw spring-boot:run

# Terminal 5 - API Gateway (POR ÚLTIMO!)
cd api-gateway
./mvnw spring-boot:run
```

### ✅ Verificação

Todos os serviços devem estar rodando. Verifique:

```bash
# Gateway
curl http://localhost:8080/health

# Usuario Service
curl http://localhost:8082/actuator/health

# Projetos Service
curl http://localhost:8083/actuator/health

# Tarefas Service
curl http://localhost:8084/actuator/health

# Anexo Service
curl http://localhost:8085/actuator/health
```

Todos devem retornar `{"status":"UP"}`

---

## 🧪 Testando no Insomnia

### 1. Instalação do Insomnia

Baixe e instale: [Insomnia](https://insomnia.rest/download)

### 2. Criar Usuário

**Endpoint:** `POST http://localhost:8080/cadastrar`

**Headers:**
```
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "nome": "João Silva",
  "email": "joao@exemplo.com",
  "senha": "senha123"
}
```

**Resposta esperada (201 Created):**
```json
{
  "id": "691656682789bdd9eba1fb73",
  "nome": "João Silva",
  "email": "joao@exemplo.com"
}
```

### 3. Fazer Login

**Endpoint:** `POST http://localhost:8080/usuarios/login`

**Body (JSON):**
```json
{
  "email": "joao@exemplo.com",
  "senha": "senha123"
}
```

**Resposta esperada (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqb2FvQGV4ZW1wbG8uY29tIiwidXNlcklkIjoiNjkxNjU2NjgyNzg5YmRkOWViYTFmYjczIiwiaWF0IjoxNzYzMDc3NDMyLCJleHAiOjE3NjMxMTM0MzJ9...",
  "userId": "691656682789bdd9eba1fb73",
  "email": "joao@exemplo.com"
}
```

**⚠️ CRÍTICO:** Copie o valor do campo `token` - você vai usá-lo em TODAS as próximas requisições!

### 4. Criar Projeto

**Endpoint:** `POST http://localhost:8080/cadastrar/projetos`

**Headers:**
```
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzM4NCJ9...  ← Cole seu token aqui
```

**Body (JSON):**
```json
{
  "nome": "Projeto SideQuest",
  "descricao": "Sistema de gestão de tarefas e projetos",
  "status": "ativo",
  "prazoFinal": "2025-12-31T23:59:59.000Z",
  "usuarioIds": []
}
```

**Resposta esperada (201 Created):**
```json
{
  "id": "69166272cf1709619b0ffdd9",
  "status": "ativo",
  "nome": "Projeto SideQuest",
  "descricao": "Sistema de gestão de tarefas e projetos",
  "prazoFinal": "2025-12-31T23:59:59.000Z",
  "usuarioIds": ["691656682789bdd9eba1fb73"]
}
```

**⚠️ Copie o `id` do projeto!**

### 5. Criar Tarefa

**Endpoint:** `POST http://localhost:8080/cadastrar/tarefas`

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {SEU_TOKEN}
```

**Body (JSON):**
```json
{
  "nome": "Implementar autenticação JWT",
  "descricao": "Criar sistema completo de autenticação com JWT",
  "status": "pendente",
  "prazoFinal": "2025-12-31T23:59:59.000Z",
  "projetoId": "69166272cf1709619b0ffdd9",
  "comentario": "Verificar requisitos de segurança",
  "anexos": [],
  "usuarioIds": ["691656682789bdd9eba1fb73"]
}
```

**Resposta esperada (201 Created):**
```json
{
  "id": "6916671d91364b14f3fc9478",
  "nome": "Implementar autenticação JWT",
  "descricao": "Criar sistema completo de autenticação com JWT",
  "status": "pendente",
  ...
}
```

**⚠️ Copie o `id` da tarefa!**

---

## 📎 Upload de Arquivos

### Como fazer upload de anexos no Insomnia

#### 1. Configurar a Requisição

**Endpoint:** `POST http://localhost:8080/api/anexos/{tarefaId}`

**Exemplo:** `POST http://localhost:8080/api/anexos/6916671d91364b14f3fc9478`

**Headers:**
```
Authorization: Bearer {SEU_TOKEN}
```

#### 2. Configurar o Body

No Insomnia:

1. Clique na aba **Body**
2. Selecione **Multipart Form** (não JSON!)
3. Clique no botão **+** (Add)
4. Configure o campo:
   - **Name:** `files` (exatamente assim, no plural)
   - **Type:** Selecione **File** no dropdown
   - **Value:** Clique em **Choose File** e selecione seu arquivo

```
┌─────────────┬──────────┬────────────────────┐
│ Name        │ Type     │ Value              │
├─────────────┼──────────┼────────────────────┤
│ files       │ File     │ [documento.pdf]    │
└─────────────┴──────────┴────────────────────┘
```

5. Clique em **Send**

#### 3. Resposta Esperada (201 Created)

```json
{
  "total": 1,
  "enviados": 1,
  "arquivos": [
    {
      "tamanho": "681,4 KB",
      "tipo": "image",
      "nome": "acolhimento primeiro maos na massa.png"
    }
  ],
  "sucesso": true
}
```

### Outros Endpoints de Anexos

#### Listar Anexos da Tarefa
```bash
GET http://localhost:8080/api/anexos/6916671d91364b14f3fc9478
Authorization: Bearer {SEU_TOKEN}
```

**Resposta:**
```json
{
  "tarefaId": "6916671d91364b14f3fc9478",
  "totalArquivos": 2,
  "tamanhoTotal": "1,5 MB",
  "arquivos": [
    {
      "nome": "acolhimento primeiro maos na massa.png",
      "tamanho": "681,4 KB",
      "tipo": "image",
      "dataUpload": "2025-11-14T16:48:22"
    },
    {
      "nome": "documento.pdf",
      "tamanho": "850,0 KB",
      "tipo": "pdf",
      "dataUpload": "2025-11-14T17:00:00"
    }
  ]
}
```

#### Baixar Todos os Anexos (Base64)
```bash
GET http://localhost:8080/api/anexos/6916671d91364b14f3fc9478/download
Authorization: Bearer {SEU_TOKEN}
```

#### Estatísticas dos Anexos
```bash
GET http://localhost:8080/api/anexos/6916671d91364b14f3fc9478/stats
Authorization: Bearer {SEU_TOKEN}
```

#### Deletar Todos os Anexos
```bash
DELETE http://localhost:8080/api/anexos/6916671d91364b14f3fc9478
Authorization: Bearer {SEU_TOKEN}
```

---

## 📡 Endpoints Principais

### 🔓 Públicos (sem autenticação)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/usuario/cadastrar` | Criar novo usuário |
| POST | `/usuarios/login` | Login e obter token JWT |
| POST | `/cadastrar` | Cadastro alternativo |
| GET | `/health` | Status dos serviços |

### 🔒 Protegidos (requer token JWT)

Adicione o header em todas as requisições:
```
Authorization: Bearer {SEU_TOKEN_JWT}
```

#### Usuários

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/usuarios/{id}` | Buscar usuário por ID |
| PUT | `/usuarios/{id}` | Atualizar usuário |
| DELETE | `/usuarios/{id}` | Deletar usuário |

#### Projetos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/cadastrar/projetos` | Criar novo projeto |
| GET | `/listar/projetos` | Listar todos os projetos |
| GET | `/listar/projetos/{id}` | Buscar projeto por ID |
| GET | `/listar/projetos/meus` | Listar meus projetos |
| PUT | `/projetos/{id}` | Atualizar projeto |
| DELETE | `/projetos/{id}` | Deletar projeto |

#### Tarefas

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/cadastrar/tarefas` | Criar nova tarefa |
| GET | `/listar/tarefas` | Listar todas as tarefas |
| GET | `/listar/tarefas/{id}` | Buscar tarefa por ID |
| GET | `/listar/tarefas/projeto/{projetoId}` | Tarefas de um projeto |
| PUT | `/tarefas/{id}` | Atualizar tarefa |
| DELETE | `/tarefas/{id}` | Deletar tarefa |

#### Anexos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/anexos/{tarefaId}` | Upload de arquivo(s) |
| GET | `/api/anexos/{tarefaId}` | Listar anexos da tarefa |
| GET | `/api/anexos/{tarefaId}/download` | Baixar todos (Base64) |
| GET | `/api/anexos/{tarefaId}/stats` | Estatísticas dos anexos |
| DELETE | `/api/anexos/{tarefaId}` | Deletar todos os anexos |

---

## 📚 Documentação Swagger

Acesse a documentação interativa de cada serviço:

- **API Gateway**: http://localhost:8080/swagger-ui.html
- **Usuario Service**: http://localhost:8082/swagger-ui.html
- **Projetos Service**: http://localhost:8083/swagger-ui.html
- **Tarefas Service**: http://localhost:8084/swagger-ui.html
- **Anexo Service**: http://localhost:8085/swagger-ui.html

---

## 📊 Monitoramento

### Health Check
```bash
# Status de todos os serviços
GET http://localhost:8080/health/services
```

### Métricas com Actuator
```bash
# Gateway
GET http://localhost:8080/actuator
GET http://localhost:8080/actuator/metrics
GET http://localhost:8080/actuator/health

# Circuit Breakers
GET http://localhost:8080/actuator/circuitbreakers

# Rate Limiters
GET http://localhost:8080/actuator/ratelimiters
```

---

## 🐛 Troubleshooting

### Erro: "Could not resolve placeholder 'microservices.anexos.url'"

**Problema:** Falta configuração no `application.properties` do API Gateway

**Solução:**
```properties
# Adicione esta linha no api-gateway/application.properties
microservices.anexos.url=http://localhost:8085
```

### Erro 401: "Token JWT ausente" ou "Token JWT inválido"

**Problema:** Token não enviado, malformado ou expirado

**Solução:**
1. Faça login novamente: `POST /usuarios/login`
2. Copie o novo token da resposta
3. Adicione no header: `Authorization: Bearer {NOVO_TOKEN}`
4. **Importante:** O token expira em 24 horas

### Erro 403: "Acesso direto não permitido. Use o API Gateway (porta 8080)"

**Problema:** Tentando acessar microserviço diretamente nas portas 8082-8085

**Solução:** **SEMPRE use a porta 8080** (API Gateway)
```
❌ ERRADO: http://localhost:8084/tarefas
✅ CORRETO: http://localhost:8080/tarefas
```

### Erro 404: "Projeto não encontrado"

**Problema:** ID do projeto inválido ou não existe

**Solução:**
1. Liste todos os projetos: `GET http://localhost:8080/listar/projetos`
2. Use um ID válido da lista retornada
3. Certifique-se de estar usando o projeto correto

### Erro 400: "ProjetoId é obrigatório"

**Problema:** Faltou o campo `projetoId` ao criar tarefa

**Solução:** Sempre inclua o `projetoId` válido no body:
```json
{
  "nome": "Minha tarefa",
  "projetoId": "69166272cf1709619b0ffdd9",
  ...
}
```

### Timeout no upload de arquivo

**Problema:** Arquivo muito grande ou conexão lenta

**Solução:**
1. Verifique o tamanho do arquivo (máx: 50MB)
2. Tente com arquivo menor primeiro (< 1MB)
3. Aumente o timeout no Insomnia:
   - Settings → Request Timeout → 60000 (60 segundos)
4. Verifique se o anexo-service está rodando

### Erro: "Port 8080 already in use"

**Problema:** Porta já está sendo usada por outro processo

**Solução:**

**Windows:**
```cmd
netstat -ano | findstr :8080
taskkill /PID {PID_NUMBER} /F
```

**Linux/Mac:**
```bash
lsof -i :8080
kill -9 {PID_NUMBER}
```

### MongoDB: "Authentication failed"

**Problema:** Credenciais incorretas ou IP não liberado

**Solução:**
1. Verifique usuário e senha no MongoDB Atlas
2. Atualize a connection string em todos os `application.properties`
3. No Atlas: Network Access → Add IP Address → 0.0.0.0/0 (para desenvolvimento)
4. Certifique-se que o cluster está ativo (pode demorar alguns minutos)

### Serviço não inicia: "Address already in use"

**Problema:** Porta já está ocupada

**Solução:** Mate o processo ou mude a porta no `application.properties`

### "Cannot invoke because request is null"

**Problema:** Body da requisição está vazio ou malformado

**Solução:**
1. Certifique-se de enviar um body JSON válido
2. Verifique o header `Content-Type: application/json`
3. Use o formato correto dos exemplos acima

---

## 🎯 Fluxo Completo de Teste

Siga este fluxo para testar todo o sistema:

```bash
# 1. Criar usuário (PÚBLICO)
POST http://localhost:8080/cadastrar
→ Recebe userId: "691656682789bdd9eba1fb73"

# 2. Fazer login (PÚBLICO)
POST http://localhost:8080/usuarios/login
→ Recebe token JWT: "eyJhbGci..."

# 3. Criar projeto (PROTEGIDO - usa token)
POST http://localhost:8080/cadastrar/projetos
Authorization: Bearer {token}
→ Recebe projetoId: "69166272cf1709619b0ffdd9"

# 4. Criar tarefa (PROTEGIDO - usa token e projetoId)
POST http://localhost:8080/cadastrar/tarefas
Authorization: Bearer {token}
Body: { "projetoId": "69166272cf1709619b0ffdd9", ... }
→ Recebe tarefaId: "6916671d91364b14f3fc9478"

# 5. Upload anexo (PROTEGIDO - usa token e tarefaId)
POST http://localhost:8080/api/anexos/6916671d91364b14f3fc9478
Authorization: Bearer {token}
Body: Multipart Form, campo "files", tipo File
→ Arquivo enviado com sucesso!

# 6. Listar anexos (PROTEGIDO)
GET http://localhost:8080/api/anexos/6916671d91364b14f3fc9478
Authorization: Bearer {token}
→ Vê todos os arquivos da tarefa
```

---

## 🛠️ Tecnologias

### Backend
- **Java 21**
- **Spring Boot 3.5.7**
- **Spring Cloud Gateway 2025.0.0**
- **Spring Security** + JWT
- **MongoDB** (Cloud Atlas)

### Bibliotecas Principais
- **jjwt 0.12.6** - Autenticação JWT
- **Resilience4j 2.2.0** - Circuit Breaker e Rate Limiting
- **SpringDoc OpenAPI 2.7.0** - Documentação Swagger
- **Lombok** - Redução de boilerplate

---

## 💡 Dicas Importantes

1. ✅ **SEMPRE use a porta 8080** (API Gateway) - nunca acesse as portas 8082-8085 diretamente
2. ✅ **Copie e guarde o token JWT** após o login - você vai usar em todas as requisições protegidas
3. ✅ **Os IDs são importantes** - guarde `projetoId` e `tarefaId` para usar em outras operações
4. ✅ **Inicie os serviços na ordem correta** - microserviços primeiro, gateway por último
5. ✅ **Para upload de arquivos** - use Multipart Form, campo `files`, tipo File
6. ✅ **Tokens expiram em 24h** - se receber 401, faça login novamente
7. ✅ **Use Swagger** para explorar todos os endpoints disponíveis
8. ✅ **Verifique os logs** se algo der errado - eles mostram o que aconteceu

---

## 👥 Equipe

**Syntax - FATEC São José dos Campos**

Desenvolvido como projeto acadêmico para a disciplina de Desenvolvimento de Software.

---

## 📄 Licença

MIT License

---

## 🎉 Pronto para Usar!

Agora você tem tudo configurado e funcionando:

- ✅ 5 microserviços rodando
- ✅ API Gateway com autenticação JWT
- ✅ Criação de usuários, projetos e tarefas
- ✅ Upload e download de arquivos **TESTADO E FUNCIONANDO!**
- ✅ Documentação Swagger disponível
- ✅ Monitoramento com Actuator
- ✅ Sistema de segurança completo com filtros

### Exemplo Real de Upload Bem-Sucedido:

```json
{
  "total": 1,
  "enviados": 1,
  "arquivos": [
    {
      "tamanho": "681,4 KB",
      "tipo": "image",
      "nome": "acolhimento primeiro maos na massa.png"
    }
  ],
  "sucesso": true
}
```

**SideQuest - Operando Tarefas com Eficiência! 🚀**

Para dúvidas, consulte a seção [Troubleshooting](#troubleshooting) ou os logs dos serviços.
