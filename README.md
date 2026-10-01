# Crossword

Aplicação de palavras cruzadas desenvolvida para prática de inglês, composta por um backend em Java/Spring Boot, um frontend em React e um repositório de automação em Python para geração e inserção dos dados dos níveis.

---

## Repositórios

O projeto é dividido em três repositórios independentes:

- **Backend:** https://github.com/JoaoPMV/crossword-backend
- **Frontend:** https://github.com/JoaoPMV/crossword-frontend
- **Crossword Generator:** https://github.com/JoaoPMV/crossword-generator

### Backend

Responsável pela API REST, autenticação de usuários, gerenciamento dos níveis e salvamento do progresso.

### Frontend

Responsável pela interface da aplicação e interação do usuário com o jogo.

### Crossword Generator

Repositório de automação desenvolvido em Python, responsável pela geração e inserção dos dados dos níveis no PostgreSQL.

---

## Objetivo

Fornecer uma aplicação de palavras cruzadas para prática de inglês, com foco em:

- autenticação e gerenciamento de usuários
- controle de acesso às rotas protegidas
- gerenciamento dos níveis do jogo
- salvamento do progresso dos usuários
- integração entre frontend e backend por meio de uma API REST

---

## Tecnologias Utilizadas

### Backend (API)

- Java 21
- Spring Boot
- Spring Security
- JWT (autenticação)
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven
- BCrypt (hash de senhas)

### Frontend (cliente)

- React + Vite
- JavaScript
- React Router
- Bootstrap

### Crossword Generator

- Python

---

## Funcionalidades

- Registro de usuários
- Login com geração de token JWT
- Proteção de rotas com autenticação baseada em JWT
- Logout e revogação de tokens
- Recuperação de senha
- Redefinição de senha
- Listagem de níveis disponíveis
- Consulta de níveis individuais
- Salvamento do progresso do usuário
- Integração com frontend via API REST
- Geração e inserção automatizada dos dados dos níveis

---

## Arquitetura Atual

- API REST desenvolvida com Spring Boot
- Autenticação baseada em JWT com expiração
- Filtro de segurança para validação dos tokens JWT
- Senhas protegidas com hash utilizando BCrypt
- Rotas de autenticação públicas
- Rotas de conteúdo e progresso protegidas por autenticação
- Persistência dos dados utilizando Spring Data JPA / Hibernate
- PostgreSQL como banco de dados
- Dados das palavras armazenados como JSON no PostgreSQL
- Dados dos níveis inseridos por meio do repositório `crossword-generator`
- Sem painel/admin CRUD público nesta versão

---

## Melhorias Futuras

- [ ] Refresh token
- [ ] Rate limit para rotas de autenticação
- [ ] Painel admin para gerenciamento de níveis
- [ ] Logs e monitoramento de erros em produção
- [ ] Testes automatizados
- [ ] Documentação da API com Swagger/OpenAPI

---

## Como Executar o Projeto

### Pré-requisitos

- Java 21 instalado
- Maven instalado
- PostgreSQL em execução
- Python instalado
- Node.js e npm instalados

### 1. Clonar os repositórios

Clone os três repositórios:

```bash
git clone https://github.com/JoaoPMV/crossword-backend.git backend
git clone https://github.com/JoaoPMV/crossword-frontend.git frontend
git clone https://github.com/JoaoPMV/crossword-generator.git crossword-generator
```

A estrutura local ficará:

```text
crossword/
├── backend/
├── frontend/
└── crossword-generator/
```

### 2. Configurar o banco de dados

Crie um banco de dados PostgreSQL para o projeto.

Exemplo:

```sql
CREATE DATABASE crossword;
```

### 3. Configurar as variáveis de ambiente do backend

Acesse a pasta do backend:

```bash
cd backend
```

Crie um arquivo `.env` e configure as variáveis necessárias para conexão com o PostgreSQL, autenticação JWT, envio de e-mails e comunicação com o frontend.

Exemplo:

```env
DB_URL=jdbc:postgresql://localhost:5432/crossword
DB_USER=seu_usuario
DB_PASSWORD=sua_senha

MAIL_USER=seu_email
MAIL_PASSWORD=sua_senha_ou_senha_de_aplicativo

JWT_SECRET=sua_chave_secreta

FRONTEND_URL=http://localhost:5173
```

> Não versionar o arquivo `.env` no GitHub. Utilize valores próprios para as credenciais e chaves secretas.

### 4. Executar o backend

Dentro da pasta `backend`, execute:

```bash
mvn spring-boot:run
```

Ou execute a aplicação diretamente pela sua IDE.

Servidor disponível em:

```text
http://localhost:8080
```

### 5. Executar o frontend

Em outro terminal, acesse a pasta do frontend:

```bash
cd frontend
```

Instale as dependências:

```bash
npm install
```

Execute a aplicação:

```bash
npm run dev
```

O frontend estará disponível em:

```text
http://localhost:5173
```

### 6. Popular o banco com dados dos níveis

O repositório `crossword-generator` possui um script de automação desenvolvido em Python, responsável pela geração e inserção dos dados dos níveis no PostgreSQL.

Acesse a pasta:

```bash
cd crossword-generator
```

Instale as dependências necessárias:

```bash
pip install -r requirements.txt
```

Execute o script de geração dos níveis conforme sua configuração.

> Esse processo é utilizado para inserir os dados dos níveis necessários para a aplicação.

---

## Autor

Desenvolvido por **JoaoPMV**

GitHub: https://github.com/JoaoPMV
