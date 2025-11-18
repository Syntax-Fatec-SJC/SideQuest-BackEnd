# SideQuest - Backend (Microserviços)

## 📋 Visão Geral

Backend do sistema SideQuest implementado com arquitetura de microserviços usando Spring Boot.

## 🏗️ Arquitetura

```
Frontend → API Gateway (:8080) → Usuario Service (:8082)
                                → Projetos Service (:8083)
                                → Tarefas Service (:8084)
```

## 🚀 Serviços

### API Gateway (`:8080`)
- Ponto de entrada único
- Autenticação JWT
- Rate limiting e Circuit breaker
- **Documentação**: [api-gateway/README.md](./api-gateway/README.md)

### Usuario Service (`:8082`)
- Gerenciamento de usuários e autenticação

### Projetos Service (`:8083`)
- CRUD de projetos e gerenciamento de membros

### Tarefas Service (`:8084`)
- CRUD de tarefas com status e prioridades

### Inicialização Manual

# 1. Usuario Service
cd usuario-service && ./mvnw spring-boot:run

# 2. Projetos Service
cd projetos-service && ./mvnw spring-boot:run

# 3. Tarefas Service
cd tarefas-service && ./mvnw spring-boot:run

# 4. API Gateway
cd api-gateway && ./mvnw spring-boot:run
```

## 📡 Endpoints

Todas as requisições passam pelo API Gateway: `http://localhost:8080`

### Públicos (sem autenticação)
- `POST /usuario/login` - Login
- `POST /usuario/cadastrar` - Cadastro
- `GET /health` - Status dos serviços

### Protegidos (requer JWT)
- `/usuario/*` - Gerenciamento de usuários
- `/projetos/*` - Gerenciamento de projetos  
- `/tarefas/*` - Gerenciamento de tarefas

## 📚 Documentação Swagger

- **API Gateway**: http://localhost:8080/swagger-ui.html
- **Usuario Service**: http://localhost:8082/swagger-ui.html
- **Projetos Service**: http://localhost:8083/swagger-ui.html
- **Tarefas Service**: http://localhost:8084/swagger-ui.html

## 🔐 Autenticação

1. Faça login em `/usuario/login`
2. Use o token JWT retornado
3. Inclua no header: `Authorization: Bearer <token>`

## 📊 Monitoramento

```bash
# Status dos serviços
GET http://localhost:8080/health/services

# Métricas
GET http://localhost:8080/actuator
```

## 🛠️ Tecnologias

- Java 21
- Spring Boot 3.5.7
- Spring Cloud Gateway 2025.0.0
- MongoDB
- JWT (jjwt 0.12.6)
- Resilience4j 2.2.0

## 👥 Equipe

**Syntax - FATEC São José dos Campos**

## 📄 Licença

MIT License
