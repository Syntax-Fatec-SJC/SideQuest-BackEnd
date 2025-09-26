# 🚀 API SideQuest - Guia Completo de Autenticação

## 📋 Visão Geral

Esta é uma API REST completa para autenticação de usuários construída com:
- **Spring Boot 3.5.5** + **Java 21**
- **MongoDB Atlas** (banco não-relacional)
- **JWT** para autenticação
- **Google OAuth2** para login social
- **BCrypt** para criptografia de senhas

## 🏗️ Estrutura do Projeto

### 📁 Pacotes e Responsabilidades

```
com.syntax.sidequest_backend/
├── config/security/           # Configurações de segurança
│   ├── SecurityConfig.java        # Configuração principal do Spring Security
│   ├── JwtUtil.java               # Utilitários para JWT
│   ├── JwtAuthenticationFilter.java # Filtro para validar tokens
│   └── OAuth2AuthenticationSuccessHandler.java # Handler OAuth2
├── controller/                # Controllers REST
│   ├── AuthController.java        # Endpoints de autenticação
│   └── UsuarioController.java     # Endpoints protegidos
├── modelo/                    # Modelos de dados
│   ├── entidade/
│   │   └── Usuario.java           # Entidade principal
│   └── dto/                       # Data Transfer Objects
│       ├── CadastroUsuarioDTO.java
│       ├── LoginUsuarioDTO.java
│       ├── AuthResponseDTO.java
│       └── UsuarioDTO.java
├── repositorio/               # Acesso a dados
│   └── UsuarioRepositorio.java    # Repository MongoDB
├── service/                   # Lógica de negócio
│   └── UsuarioService.java        # Serviços de usuário
└── excecao/                   # Tratamento de erros
    └── ManipuladorGlobal.java     # Global exception handler
```

## 🔐 Endpoints da API

### Base URL: `http://localhost:8080`

### 1. **Cadastro de Usuário**
```http
POST /api/auth/cadastro
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao@exemplo.com",
  "senha": "123456"
}
```

**Resposta de Sucesso (200):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tipo": "Bearer",
  "id": "670123abc...",
  "nome": "João Silva",
  "email": "joao@exemplo.com",
  "fotoPerfil": null,
  "provedor": "local"
}
```

### 2. **Login Local**
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "joao@exemplo.com",
  "senha": "123456"
}
```

**Resposta de Sucesso (200):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tipo": "Bearer",
  "id": "670123abc...",
  "nome": "João Silva",
  "email": "joao@exemplo.com",
  "fotoPerfil": null,
  "provedor": "local"
}
```

### 3. **Login com Google**
```http
GET /api/auth/google
```
Redireciona para o Google OAuth2 e depois para o frontend com o token.

### 4. **Perfil do Usuário (Protegido)**
```http
GET /api/usuarios/perfil
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

**Resposta de Sucesso (200):**
```json
{
  "id": "670123abc...",
  "nome": "João Silva",
  "email": "joao@exemplo.com",
  "fotoPerfil": null,
  "provedor": "local",
  "ativo": true,
  "dataCriacao": "2025-09-22T20:00:00"
}
```

### 5. **Dashboard (Protegido)**
```http
GET /api/usuarios/dashboard
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

## 🔧 Como Testar a API

### Usando curl:

1. **Cadastrar usuário:**
```bash
curl -X POST http://localhost:8080/api/auth/cadastro \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Test User",
    "email": "test@exemplo.com",
    "senha": "123456"
  }'
```

2. **Fazer login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@exemplo.com",
    "senha": "123456"
  }'
```

3. **Acessar perfil (substitua o TOKEN):**
```bash
curl -X GET http://localhost:8080/api/usuarios/perfil \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

## 🛠️ Configuração do Google OAuth2

Para usar o login com Google, você precisa:

1. **Criar projeto no Google Cloud Console:**
   - Acesse: https://console.cloud.google.com/
   - Crie um novo projeto
   - Ative a API "Google+ API"

2. **Configurar OAuth2:**
   - Vá em "Credenciais" > "Criar Credenciais" > "ID do cliente OAuth"
   - Tipo: Aplicação da Web
   - URIs de redirecionamento autorizados:
     - `http://localhost:8080/login/oauth2/code/google`

3. **Atualizar application.properties:**
```properties
spring.security.oauth2.client.registration.google.client-id=SEU_CLIENT_ID_AQUI
spring.security.oauth2.client.registration.google.client-secret=SEU_CLIENT_SECRET_AQUI
```

## 📊 Modelo de Dados

### Usuario (MongoDB Document)
```json
{
  "_id": "ObjectId",
  "nome": "string",
  "email": "string (único)",
  "senha": "string (hash BCrypt, opcional para OAuth2)",
  "provedor": "local | google",
  "provedorId": "string (ID do Google, opcional)",
  "fotoPerfil": "string (URL, opcional)",
  "ativo": "boolean",
  "dataCriacao": "LocalDateTime",
  "dataAtualizacao": "LocalDateTime"
}
```

## 🔒 Segurança Implementada

1. **JWT Tokens:**
   - Expiração: 24 horas
   - Algoritmo: HS256
   - Secret configurável

2. **Senhas:**
   - Criptografia BCrypt
   - Mínimo 6 caracteres

3. **CORS:**
   - Configurado para localhost:5173 e localhost:8080

4. **Validações:**
   - Email formato válido
   - Campos obrigatórios
   - Tratamento global de erros

## 🚀 Como Rodar

1. **Iniciar a aplicação:**
```bash
cd sidequest-backend
./mvnw spring-boot:run
```

2. **Aplicação estará disponível em:**
   - http://localhost:8080

3. **MongoDB Atlas:**
   - Já configurado com suas credenciais
   - Database: `syntaxbd`

## 📝 Próximos Passos

Para integrar com seu frontend React:

1. **Instalar axios no frontend:**
```bash
npm install axios
```

2. **Exemplo de uso no React:**
```javascript
// Login
const login = async (email, senha) => {
  try {
    const response = await axios.post('http://localhost:8080/api/auth/login', {
      email,
      senha
    });
    
    const { token } = response.data;
    localStorage.setItem('token', token);
    
    return response.data;
  } catch (error) {
    console.error('Erro no login:', error.response.data);
  }
};

// Requisição protegida
const getProfile = async () => {
  const token = localStorage.getItem('token');
  
  try {
    const response = await axios.get('http://localhost:8080/api/usuarios/perfil', {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });
    
    return response.data;
  } catch (error) {
    console.error('Erro ao buscar perfil:', error.response.data);
  }
};
```

## ✅ Status Atual

- ✅ Aplicação rodando na porta 8080
- ✅ MongoDB Atlas conectado
- ✅ JWT funcionando
- ✅ Endpoints de autenticação prontos
- ✅ Validações implementadas
- ✅ Tratamento de erros global
- ⚠️ Google OAuth2 (precisa configurar credenciais)

Sua API está **100% funcional** para cadastro e login local! 🎉