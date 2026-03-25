# DSCommerce

Projeto de backend para um sistema de e-commerce, desenvolvido com Spring Boot.

## Tecnologias

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database (ambiente de testes)
- Maven

## Funcionalidades Implementadas

- **Autenticacao e autorizacao (JWT)**
  - `POST /auth/login` retorna token de acesso
  - Endpoints `GET /products` e `GET /products/{id}` sao publicos
  - Endpoints `POST/PUT/DELETE /products` exigem perfil `ADMIN`
  - Endpoint `GET /users/me` retorna o usuario autenticado

- **Categorias**  
  - Associação de produtos a categorias

- **Pedidos**
  - `GET /orders/{id}` e `POST /orders`
  - Regra de acesso: usuario `ADMIN` acessa qualquer pedido, cliente comum acessa apenas pedidos proprios

- **Usuários**  
  - Cadastro de usuários e associação com pedidos

- **Tratamento de Erros**  
  - Exceções customizadas para recursos não encontrados e erros de banco de dados
  - Handler global para respostas de erro padronizadas

## Como executar

1. Clone o repositório
2. Execute o projeto com o Maven:
   ```sh
   ./mvnw spring-boot:run
   ```
3. Acesse o H2 Console em [http://localhost:8080/h2-console](http://localhost:8080/h2-console)  
   (usuário: `sa`, senha: em branco)

## Endpoints principais

- `GET /products` — Lista produtos paginados
- `GET /products/{id}` — Detalha produto por ID
- `POST /auth/login` — Login e emissao do token JWT
- `POST /products` — Cria novo produto (`ADMIN`)
- `PUT /products/{id}` — Atualiza produto (`ADMIN`)
- `DELETE /products/{id}` — Remove produto (`ADMIN`)
- `GET /categories` — Lista categorias (publico)
- `GET /users/me` — Retorna usuario logado
- `GET /orders/{id}` — Busca pedido (dono ou `ADMIN`)
- `POST /orders` — Cria pedido para usuario autenticado

## Testes (portfolio backend jr)

Os testes foram organizados por objetivo para facilitar leitura e manutencao:

- `src/test/java/com/devsuperior/dscommerce/smoke`  
  Teste de smoke (`ApplicationContextSmokeTest`) para garantir que o contexto Spring sobe.
- `src/test/java/com/devsuperior/dscommerce/integration`  
  Testes de integracao com `MockMvc` cobrindo autenticacao, autorizacao e regras de negocio dos endpoints.

### Cobertura dos criterios do desafio

- Endpoints publicos (`GET /products`, `GET /products/{id}`, `GET /categories`)
- Login com token (`POST /auth/login`)
- Restricao de escrita de produtos para `ADMIN` (`POST/PUT/DELETE /products`)
- Usuario logado (`GET /users/me`)
- Pedidos (`GET /orders/{id}`, `POST /orders`)
- Regra de dono/admin para pedido (`GET /orders/{id}`)

### Como rodar os testes

```sh
./mvnw test
```

## Fluxo de branches usado

- `feature/desafio-dscommerce-estrutura`: implementacao incremental
- `develop`: integracao da feature
- `main`: branch de entrega
- `test`: branch de portfolio para evolucao e organizacao de testes

## Exemplo rapido de login

```sh
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"alex@gmail.com","password":"123456"}'
```

Use o valor de `access_token` no header:

```sh
Authorization: Bearer <token>
```

## Estrutura do projeto

- `entities` — Modelos de dados (Produto, Categoria, Pedido, Usuário, etc)
- `dto` — Data Transfer Objects
- `repositories` — Interfaces de acesso ao banco
- `services` — Regras de negócio
- `controllers` — Endpoints REST
- `controllers/handlers` — Tratamento global de exceções

## Observações

- O banco de dados é populado automaticamente com dados de exemplo via `import.sql`.
- O perfil ativo padrão é `test` (H2 em memória).

---

> Projeto desenvolvido para fins de estudo e prática com Spring Boot.
