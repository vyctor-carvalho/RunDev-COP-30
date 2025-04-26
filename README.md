
# Waste Residue API

A **Waste Residue API** é uma API RESTful construída para gerenciar resíduos de lixo, autenticação JWT, e controle de usuários com base em suas permissões (Admin, Estudante). A API fornece endpoints para autenticação, gerenciamento de estudantes e resíduos.

## Endpoints

### 1. **Autenticação**

- **POST** `/auth/login`  
  Realiza o login de um usuário e retorna um token JWT.

  **Requisição:**
  ```json
  {
      "username": "string",
      "password": "string"
  }
  ```

**Resposta:**
  ```json
  {
      "token": "JWT_TOKEN"
  }
  ```

- **POST** `/auth/register`  
  Registra um novo usuário (somente para Admin).

  **Requisição:**
  ```json
  {
      "username": "string",
      "password": "string",
      "role": "ADMIN" // ou "STUDENT"
  }
  ```

  **Resposta:**
  ```json
  {
      "message": "User registered successfully"
  }
  ```

### 2. **Estudantes**

- **GET** `/students/protected/{id}`  
  Retorna informações sobre um estudante específico. Somente acessível por `ADMIN` e `STUDENT` logados.

  **Resposta:**
  ```json
  {
      "id": 1,
      "name": "John Doe",
      "registration": 12345,
      "role": "STUDENT"
  }
  ```

- **GET** `/students/{id}`  
  Retorna informações sobre um estudante específico. Somente acessível por `ADMIN`.

  **Resposta:**
  ```json
  {
      "id": 1,
      "name": "John Doe",
      "registration": 12345,
      "role": "STUDENT"
  }
  ```

- **POST** `/students`  
  Cria um novo estudante. Somente acessível por `ADMIN`.

  **Requisição:**
  ```json
  {
      "name": "John Doe",
      "registration": 12345,
      "role": "STUDENT"
  }
  ```

  **Resposta:**
  ```json
  {
      "message": "Student created successfully"
  }
  ```

- **DELETE** `/students/{id}`  
  Deleta um estudante. Somente acessível por `ADMIN`.

  **Resposta:**
  ```json
  {
      "message": "Student deleted successfully"
  }
  ```

### 3. **Resíduos**

- **GET** `/waste`  
  Retorna uma lista de todos os resíduos registrados. Somente acessível por `ADMIN`.

  **Resposta:**
  ```json
  [
      {
          "id": 1,
          "type": "plastic",
          "quantity": 10
      },
      {
          "id": 2,
          "type": "glass",
          "quantity": 5
      }
  ]
  ```

- **POST** `/waste`  
  Registra um novo resíduo. Somente acessível por `ADMIN`.

  **Requisição:**
  ```json
  {
      "type": "plastic",
      "quantity": 15
  }
  ```

  **Resposta:**
  ```json
  {
      "message": "Waste registered successfully"
  }
  ```

- **DELETE** `/waste/{id}`  
  Deleta um resíduo registrado. Somente acessível por `ADMIN`.

  **Resposta:**
  ```json
  {
      "message": "Waste deleted successfully"
  }
  ```

---

## Configuração

### 1. **application.yml**

O arquivo `application.yml` contém as configurações necessárias para a API. Abaixo está um exemplo básico para configurar a aplicação:

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/seu-banco
    username: root  # Altere para o seu user
    password: root  # Altere para a sua senha
    driver-class-name: com.mysql.cj.jdbc.Driver
    hikari:
      pool-name: HikariCP

  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
        format_sql: true
    show-sql: true

jwt:
  secret-key: "P4t$a5d3G4t05"
  expiration-time: 86400000 # 1 dia
```

### 2. **Banco de Dados**

A aplicação utiliza o banco de dados MySQL para persistência de dados. O banco pode ser configurado conforme mostrado no exemplo do `application.yml`. Certifique-se de criar o banco de dados `waste_residue_db` e a tabela correspondente para que a aplicação funcione corretamente.

```sql
CREATE DATABASE waste_residue_db;

USE waste_residue_db;

CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    registration INT NOT NULL UNIQUE,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE waste (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    quantity INT NOT NULL
);
```

---

## Como Rodar a Aplicação

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/waste-residue-api.git
   cd waste-residue-api
   ```

2. **Instale as dependências:**
   Certifique-se de ter o Java 17 ou superior e o Maven instalados. Então, execute:

   ```bash
   mvn install
   ```

3. **Configure o banco de dados:**
   Configure o arquivo `application.yml` com as credenciais do seu banco de dados MySQL.

4. **Execute a aplicação:**
   Execute a aplicação com o comando:

   ```bash
   mvn spring-boot:run
   ```

5. **Testando os endpoints:**
   Você pode testar os endpoints usando o Postman ou qualquer outra ferramenta para fazer requisições HTTP.

---

## Como Funciona a Autenticação JWT

- Para acessar endpoints protegidos, você precisa enviar o token JWT no cabeçalho da requisição.

  Exemplo de cabeçalho:
  ```plaintext
  Authorization: Bearer JWT_TOKEN
  ```

- O token pode ser obtido após realizar o login com o endpoint `/auth/login`.

---

## Tecnologias Utilizadas

- **Spring Boot** (Java 17)
- **Spring Security** (Autenticação com JWT)
- **MySQL** (Banco de Dados)
- **Maven** (Gerenciador de dependências)

---

## Contribuindo

1. Faça o fork do repositório.
2. Crie uma branch (`git checkout -b feature/nova-funcionalidade`).
3. Faça as alterações.
4. Envie um pull request para a branch principal.

---
