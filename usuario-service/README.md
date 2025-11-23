# Usuario Service - SideQuest

Microserviço responsável pelo gerenciamento de usuários e autenticação do sistema SideQuest.

## 📋 Funcionalidades

- **Cadastro de Usuários**: Registro de novos usuários com validação de email único
- **Autenticação JWT**: Login com geração de tokens JWT para autenticação
- **Listagem de Usuários**: Consulta de todos os usuários cadastrados
- **Busca por ID**: Consulta de usuário específico por identificador
- **Criptografia de Senhas**: BCrypt para armazenamento seguro de senhas
- **Validação de Dados**: Jakarta Validation para validação de DTOs

## 🏗️ Arquitetura

O serviço segue o padrão de arquitetura em camadas:

```
usuario-service/
├── src/main/java/com/syntax/usuario_service/
│   ├── configuracao/          # Configurações do Spring
│   │   ├── CorsConfig.java
│   │   ├── OpenApiConfig.java
│   │   ├── PasswordEncoderConfig.java
│   │   └── SecurityConfig.java
│   ├── controller/usuario/    # Endpoints REST
│   │   ├── LoginController.java
│   │   ├── CadastrarController.java
│   │   ├── ListarController.java
│   │   └── BuscarController.java
│   ├── excecao/              # Tratamento de exceções
│   │   ├── ManipuladorGlobal.java
│   │   └── personalizado/
│   │       ├── CredenciaisInvalidasException.java
│   │       └── UsuarioExistenteException.java
│   ├── modelo/
│   │   ├── conversor/        # Conversores Entity ↔ DTO
│   │   │   ├── ConversorUsuario.java
│   │   │   └── ConversorUsuarioDTO.java
│   │   ├── dto/usuarioDTO/   # Data Transfer Objects
│   │   │   ├── LoginDTO.java
│   │   │   ├── LoginResponseDTO.java
│   │   │   ├── CadastrarUsuarioDTO.java
│   │   │   └── UsuarioDTO.java
│   │   └── entidade/         # Entidades MongoDB
│   │       └── Usuario.java
│   ├── repositorio/          # Camada de dados
│   │   └── UsuarioRepositorio.java
│   ├── seguranca/            # JWT e autenticação
│   │   └── JwtUtil.java
│   └── service/usuario/      # Lógica de negócio
│       ├── LoginUsuarioService.java
│       ├── CadastrarUsuarioService.java
│       ├── BuscarUsuarioService.java
│       └── ListarUsuarioService.java
└── src/main/resources/
    └── application.properties
```

## 🚀 Endpoints

### Autenticação

#### POST /login
Realiza o login e retorna o token JWT.

**Request Body:**
```json
{
  "email": "usuario@example.com",
  "senha": "senha123"
}
```

**Response (200 OK):**
```json
{
  "id": "507f1f77bcf86cd799439011",
  "nome": "João Silva",
  "email": "usuario@example.com",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Erros:**
- `401 Unauthorized`: Credenciais inválidas
- `400 Bad Request`: Campos inválidos

### Cadastro

#### POST /cadastrar
Cadastra um novo usuário no sistema.

**Request Body:**
```json
{
  "nome": "João Silva",
  "email": "usuario@example.com",
  "senha": "senha123"
}
```

**Response (201 Created):**
Sem corpo na resposta.

**Erros:**
- `409 Conflict`: Email já está em uso
- `400 Bad Request`: Campos inválidos

### Usuários

#### GET /usuarios
Lista todos os usuários cadastrados.

**Response (200 OK):**
```json
[
  {
    "id": "507f1f77bcf86cd799439011",
    "nome": "João Silva",
    "email": "usuario@example.com"
  }
]
```

#### GET /usuarios/{id}
Busca um usuário específico por ID.

**Response (200 OK):**
```json
{
  "id": "507f1f77bcf86cd799439011",
  "nome": "João Silva",
  "email": "usuario@example.com"
}
```

**Erros:**
- `404 Not Found`: Usuário não encontrado

## 🔧 Tecnologias Utilizadas

- **Spring Boot 3.5.7**: Framework principal
- **Spring Data MongoDB**: Integração com MongoDB
- **Spring Security**: Segurança e autenticação
- **JWT (jjwt 0.12.6)**: Tokens de autenticação
- **BCrypt**: Criptografia de senhas
- **Jakarta Validation**: Validação de dados
- **SpringDoc OpenAPI 2.7.0**: Documentação Swagger
- **Lombok**: Redução de boilerplate
- **Java 21**: Linguagem de programação

## ⚙️ Configuração

### Porta do Serviço
```properties
server.port=8082
```

### MongoDB
```properties
spring.data.mongodb.uri=mongodb+srv://syntax:fatec123@syntax-bd.vulmuug.mongodb.net/syntaxbd
```

### JWT
- **Secret Key**: Sincronizada com o API Gateway
- **Validade**: 10 horas
- **Claims**: email (subject) e userId

### CORS
Permitido acesso de:
- `http://localhost:5173` (Frontend)
- `http://localhost:8080` (API Gateway)

## 📝 Segurança

### Endpoints Públicos
- `/login` - Autenticação
- `/cadastrar` - Registro de novos usuários
- `/actuator/**` - Monitoramento
- `/swagger-ui/**` - Documentação
- `/v3/api-docs/**` - OpenAPI

### Endpoints Protegidos
- `/usuarios` - Requer autenticação via API Gateway
- `/usuarios/{id}` - Requer autenticação via API Gateway

### Criptografia
- Senhas são criptografadas usando **BCrypt** antes de serem armazenadas
- O token JWT é gerado com **HS256** e inclui `userId` como claim adicional

## 🗄️ Modelo de Dados

### Collection: usuarios

```json
{
  "_id": "ObjectId",
  "nome": "String",
  "email": "String (unique)",
  "senha": "String (encrypted with BCrypt)"
}
```

## 🔍 Tratamento de Erros

O serviço possui um manipulador global que trata os seguintes erros:

- **400 Bad Request**: Erros de validação de campos
- **401 Unauthorized**: Credenciais inválidas
- **404 Not Found**: Recurso não encontrado
- **409 Conflict**: Email já em uso
- **500 Internal Server Error**: Erros genéricos

Formato de resposta de erro:
```json
{
  "timestamp": "2024-11-07T12:24:28",
  "status": 401,
  "error": "Credenciais Inválidas",
  "message": "Credenciais Inválidas. Verifique seu e-mail e senha."
}
```

## 🚀 Como Executar

### Desenvolvimento Local
```bash
cd usuario-service
./mvnw spring-boot:run
```

### Build do Projeto
```bash
./mvnw clean package
```

### Executar JAR
```bash
java -jar target/usuario-service-0.0.1-SNAPSHOT.jar
```

## 📖 Documentação da API

Acesse o Swagger UI em:
```
http://localhost:8082/swagger-ui.html
```

## 🔗 Integração com Outros Serviços

Este microserviço se integra com:

- **API Gateway (8080)**: Roteamento e autenticação centralizada
- **Projetos Service (8083)**: Validação de usuários em projetos
- **Tarefas Service (8084)**: Validação de usuários em tarefas

## 👥 Desenvolvido por

**Equipe Syntax - FATEC São José dos Campos**
- Email: syntax@fatec.sp.gov.br
- GitHub: [Syntax-Fatec-SJC](https://github.com/Syntax-Fatec-SJC)

## 📄 Licença

Este projeto está sob a licença MIT.
