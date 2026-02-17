# DSCommerce

Projeto de backend para um sistema de e-commerce, desenvolvido com Spring Boot.

## Tecnologias

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database (ambiente de testes)
- Maven

## Funcionalidades Implementadas

- **CRUD de Produtos**  
  - Listagem paginada de produtos
  - Consulta de produto por ID
  - Cadastro de novo produto
  - Atualização de produto existente
  - Remoção de produto

- **Categorias**  
  - Associação de produtos a categorias

- **Pedidos**  
  - Estrutura de entidades para pedidos, itens de pedido e pagamentos

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
- `POST /products` — Cria novo produto
- `PUT /products/{id}` — Atualiza produto
- `DELETE /products/{id}` — Remove produto

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
