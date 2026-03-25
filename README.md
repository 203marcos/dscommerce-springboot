# DSCommerce

Backend de e-commerce com Java e Spring Boot, implementado no desafio de login e controle de acesso.

## Stack usada no projeto

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring Security
- Bean Validation (`spring-boot-starter-validation`)
- JWT (`jjwt-api`, `jjwt-impl`, `jjwt-jackson`)
- H2 Database (perfil `test`)
- Maven Wrapper (`./mvnw`)
- Testes com JUnit 5 + Spring Boot Test + MockMvc

## Funcionalidades entregues

- Autenticacao JWT em `POST /auth/login`
- Endpoints publicos de produto: `GET /products`, `GET /products/{id}`
- Escrita de produto (`POST/PUT/DELETE /products`) restrita a `ADMIN`
- `GET /users/me` retorna usuario autenticado
- `GET /orders/{id}` e `POST /orders`
- Regra de seguranca para pedido: cliente nao-admin so acessa pedido proprio
- `GET /categories` publico

## Endpoints e regra de acesso

- `POST /auth/login` -> publico
- `GET /products` -> publico
- `GET /products/{id}` -> publico
- `POST /products` -> `ADMIN`
- `PUT /products/{id}` -> `ADMIN`
- `DELETE /products/{id}` -> `ADMIN`
- `GET /categories` -> publico
- `GET /users/me` -> autenticado
- `GET /orders/{id}` -> `ADMIN` ou dono do pedido
- `POST /orders` -> `CLIENT` ou `ADMIN`

## Como executar

```sh
./mvnw spring-boot:run
```

Aplicacao: `http://localhost:8080`  
H2 Console: `http://localhost:8080/h2-console`

## Login rapido

```sh
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"alex@gmail.com","password":"123456"}'
```

Use o token retornado no header:

```sh
Authorization: Bearer <access_token>
```

## Usuarios seed para teste

- `alex@gmail.com` / `123456` -> `ROLE_ADMIN` + `ROLE_CLIENT`
- `maria@gmail.com` / `123456` -> `ROLE_CLIENT`
- `bob@gmail.com` / `123456` -> `ROLE_CLIENT`

## Testes

Organizacao da suite:

- `src/test/java/com/devsuperior/dscommerce/smoke`
  - `ApplicationContextSmokeTest` (subida do contexto)
- `src/test/java/com/devsuperior/dscommerce/integration`
  - testes de API com MockMvc (auth, autorizacao e regras de negocio)

Executar testes:

```sh
./mvnw test
```

## Estrutura do projeto

- `src/main/java/com/devsuperior/dscommerce/config` -> seguranca JWT e filtro
- `src/main/java/com/devsuperior/dscommerce/controllers` -> endpoints REST
- `src/main/java/com/devsuperior/dscommerce/controllers/handlers` -> tratamento global de erros
- `src/main/java/com/devsuperior/dscommerce/dto` -> contratos de entrada/saida
- `src/main/java/com/devsuperior/dscommerce/entities` -> modelo de dominio
- `src/main/java/com/devsuperior/dscommerce/repositories` -> acesso a dados
- `src/main/java/com/devsuperior/dscommerce/services` -> regras de negocio
- `src/main/resources/import.sql` -> carga inicial de dados

## Branches do fluxo

- `feature/desafio-dscommerce-estrutura`
- `develop`
- `main`
- `test` (portifolio, melhoria e organizacao dos testes)

## Sobre a pasta `target`

`target/` **nao e codigo-fonte**. Ela e gerada automaticamente pelo Maven com:

- classes compiladas
- relatorios de teste
- artefatos de build

Por isso, normalmente **nao deve ser versionada no Git**.  
No projeto, ela ja esta ignorada em `.gitignore`.
