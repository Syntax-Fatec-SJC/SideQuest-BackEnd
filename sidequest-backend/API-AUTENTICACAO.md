# API de Autenticação - SideQuest Backend

Este documento descreve como usar a API de autenticação implementada no projeto SideQuest.

## Configuração Inicial

### 1. Configurar Google OAuth2

Para usar a autenticação Google, você precisa:

1. Ir ao [Google Cloud Console](https://console.cloud.google.com/)
2. Criar um novo projeto ou selecionar um existente
3. Habilitar a Google+ API
4. Criar credenciais OAuth 2.0
5. Adicionar URLs de redirecionamento:
   - `http://localhost:8080/login/oauth2/code/google` (para desenvolvimento)
6. Copiar Client ID e Client Secret
7. Atualizar no `application.properties`:
   ```properties
   spring.security.oauth2.client.registration.google.client-id=SEU_CLIENT_ID_AQUI
   spring.security.oauth2.client.registration.google.client-secret=SEU_CLIENT_SECRET_AQUI
   ```

### 2. Configurar JWT Secret

Em produção, altere a chave JWT no `application.properties`:
```properties
jwt.secret=SUA_CHAVE_SECRETA_MUITO_SEGURA_AQUI
```

## Endpoints da API

### 1. Cadastro de Usuário

**POST** `/api/auth/cadastro`

**Corpo da Requisição:**
```json
{
  "nome": "João Silva",
  "email": "joao@example.com",
  "senha": "minhasenha123"
}
```

**Resposta de Sucesso (200):**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "tipo": "Bearer",
  "id": "64f1234567890abcdef123456",
  "nome": "João Silva",
  "email": "joao@example.com",
  "provedor": "local",
  "fotoPerfil": null
}
```

**Resposta de Erro (400):**
```json
{
  "message": "Email já está em uso",
  "status": 400
}
```

### 2. Login de Usuário

**POST** `/api/auth/login`

**Corpo da Requisição:**
```json
{
  "email": "joao@example.com",
  "senha": "minhasenha123"
}
```

**Resposta de Sucesso (200):**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "tipo": "Bearer",
  "id": "64f1234567890abcdef123456",
  "nome": "João Silva",
  "email": "joao@example.com",
  "provedor": "local",
  "fotoPerfil": null
}
```

### 3. Obter Informações do Usuário Autenticado

**GET** `/api/auth/me`

**Headers:**
```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

**Resposta de Sucesso (200):**
```json
{
  "id": "64f1234567890abcdef123456",
  "nome": "João Silva",
  "email": "joao@example.com",
  "provedor": "local",
  "fotoPerfil": null,
  "ativo": true,
  "dataCriacao": "2024-09-22T10:30:00"
}
```

### 4. Login com Google OAuth2

**GET** `/oauth2/authorization/google`

Este endpoint redireciona automaticamente para a página de login do Google. Após a autenticação bem-sucedida, o usuário será redirecionado para:

```
http://localhost:5173/auth/callback?token=eyJhbGciOiJIUzUxMiJ9...&user=Nome do Usuário
```

## Testando com cURL

### Cadastro:
```bash
curl -X POST http://localhost:8080/api/auth/cadastro \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@example.com",
    "senha": "minhasenha123"
  }'
```

### Login:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@example.com",
    "senha": "minhasenha123"
  }'
```

### Obter dados do usuário (substitua TOKEN pelo token recebido):
```bash
curl -X GET http://localhost:8080/api/auth/me \
  -H "Authorization: Bearer TOKEN_AQUI"
```

## Testando com Postman

### 1. Configurar Environment
Crie um environment no Postman com:
- `base_url`: `http://localhost:8080`
- `token`: (será preenchido automaticamente)

### 2. Cadastro/Login
- Method: POST
- URL: `{{base_url}}/api/auth/cadastro` ou `{{base_url}}/api/auth/login`
- Body: raw JSON com os dados do usuário
- Tests (para salvar o token automaticamente):
```javascript
if (pm.response.code === 200) {
    const response = pm.response.json();
    pm.environment.set("token", response.token);
}
```

### 3. Requisições Autenticadas
- Headers: `Authorization: Bearer {{token}}`

## Estrutura do Banco de Dados (MongoDB)

### Coleção: usuarios
```javascript
{
  "_id": ObjectId("..."),
  "nome": "João Silva",
  "email": "joao@example.com",
  "senha": "$2a$10$...", // Hash BCrypt (null para usuários OAuth2)
  "provedor": "local", // ou "google"
  "provedorId": "123456789", // ID no provedor OAuth2 (se aplicável)
  "fotoPerfil": "https://...", // URL da foto (se disponível)
  "ativo": true,
  "dataCriacao": ISODate("2024-09-22T10:30:00.000Z"),
  "dataAtualizacao": ISODate("2024-09-22T10:30:00.000Z")
}
```

## Segurança Implementada

1. **Senhas**: Criptografadas com BCrypt
2. **JWT**: Tokens com expiração configurável
3. **CORS**: Configurado para permitir requisições do frontend
4. **Validação**: Validação de dados de entrada com Bean Validation
5. **OAuth2**: Integração segura com Google OAuth2

## Próximos Passos

1. Implementar refresh tokens para renovação automática
2. Adicionar rate limiting para prevenir ataques de força bruta
3. Implementar sistema de roles/permissões
4. Adicionar logs de auditoria
5. Implementar recuperação de senha por email
