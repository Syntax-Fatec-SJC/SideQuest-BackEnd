# SideQuest - Backend (Microserviços)

## 📋 Visão Geral
Backend do sistema SideQuest com arquitetura de microserviços em Spring Boot. Cada domínio isolado, comunicação via API Gateway.

## 🏗️ Arquitetura

```
Frontend → API Gateway (:8080)
            ├─ Usuario Service (:8082)
            ├─ Projetos Service (:8083)
            ├─ Tarefas Service (:8084)
            ├─ Avisos Service (:8085)
            └─ Anexo Service (:8086)
```

Portas dos serviços adicionais estimadas. Todos acessados via Gateway na porta 8080.

## 🚀 Serviços

### API Gateway
- Autenticação JWT
- Roteamento central
- Circuit breaker / resilience
- Swagger: /swagger-ui.html

### Usuario Service
Gerenciamento de usuários (CRUD, login, autenticação, próximas entregas).

### Projetos Service
CRUD de projetos e membros.

### Tarefas Service
CRUD de tarefas, atualização parcial, verificação de prazos, próximas entregas.

### Avisos Service
CRUD de avisos (notas/comunicados internos).

### Anexo Service
Gerenciamento de anexos (upload/download) — estrutura presente; consultar Swagger para endpoints.

## 📡 Endpoints (via Gateway http://localhost:8080)

### Públicos
- POST /usuario/login
- POST /usuario/cadastrar
- GET  /health

### Protegidos (Authorization: Bearer <token>)
- /usuario/*
- /projetos/*
- /tarefas/*
- /avisos/*
- /anexos/*

### Exemplos comuns
- GET  /usuario/listar
- GET  /usuario/{id}
- GET  /tarefas/listar/tarefas/proximas-entregas
- GET  /tarefas/projetos/{projetoId}/tarefas
- PATCH /tarefas/tarefas/{id}/responsaveis
- GET  /projetos/listar
- GET  /avisos/listar
(Ver Swagger para relação completa.)

## 🔐 Autenticação
1. Login: POST /usuario/login
2. Recebe JWT
3. Usar header: Authorization: Bearer <token>

## 📚 Swagger
Acesso direto (sem Gateway) para debug:
- Gateway:        http://localhost:8080/swagger-ui.html
- Usuario:        http://localhost:8082/swagger-ui.html
- Projetos:       http://localhost:8083/swagger-ui.html
- Tarefas:        http://localhost:8084/swagger-ui.html
- Avisos:         (se ativo) http://localhost:8085/swagger-ui.html
- Anexo:          (se ativo) http://localhost:8086/swagger-ui.html

## ▶️ Inicialização Manual

Ordem recomendada (services podem subir em paralelo):

```bash
# Usuario Service
cd usuario-service && ./mvnw spring-boot:run

# Projetos Service
cd projetos-service && ./mvnw spring-boot:run

# Tarefas Service
cd tarefas-service && ./mvnw spring-boot:run

# Avisos Service (opcional)
cd avisos-service && ./mvnw spring-boot:run

# Anexo Service (opcional)
cd anexo-service && ./mvnw spring-boot:run

# API Gateway (após serviços ou por último)
cd api-gateway && ./mvnw spring-boot:run
```

## 📊 Monitoramento

```bash
GET /health/services
GET /actuator        # via gateway
```

## 🛠️ Tecnologias
- Java 21
- Spring Boot 3.5.7
- Spring Cloud Gateway 2025.0.0
- MongoDB
- JWT (jjwt 0.12.6)
- Resilience4j 2.2.0

## 🧪 Testes
Cada serviço contém testes básicos (src/test). Executar:
```bash
./mvnw test
```

## 📦 Build
```bash
./mvnw clean package
```
Artefatos gerados em target/ de cada serviço.

## 📁 Estrutura Principal (resumo)
- usuario-service/
- projetos-service/
- tarefas-service/
- avisos-service/
- anexo-service/
- api-gateway/

## 🔄 Comunicação
Sincronismo via HTTP (REST) através do Gateway. Services podem usar RestTemplate interna para chamadas internas (ex.: tarefas → usuários).

## ✅ Boas Práticas
- Manter token JWT válido no frontend.
- Consultar Swagger para nomes exatos de endpoints.
- Usar portas padrão ou configurar em application.properties.

## 👥 Equipe
Syntax - FATEC São José dos Campos

