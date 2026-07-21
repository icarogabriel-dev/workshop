# Workshop E-commerce API 🛒

Uma API RESTful de e-commerce, desenvolvida em **Java com Spring Boot**, com foco em **modelagem de domínio relacional**, **boas práticas de arquitetura em camadas** e **tratamento de exceções padronizado**.

O projeto simula o backend de uma loja virtual, contemplando usuários, produtos, categorias, pedidos e pagamentos, com seus respectivos relacionamentos (1:1, 1:N e N:N).

---

## 🚀 Tecnologias e Ferramentas

- **Java 26**
- **Spring Boot 4** (Web MVC & Data JPA)
- **Hibernate / JPA** (mapeamento objeto-relacional)
- **PostgreSQL** (banco de dados de produção/desenvolvimento)
- **H2 Database** (banco em memória para o perfil de testes)
- **Maven** (gerenciador de dependências e build)
- **Jackson** (serialização/deserialização JSON)

---

## 🏗️ Arquitetura e Padrões de Desenvolvimento

O projeto segue a divisão em camadas tradicional do Spring, promovendo baixo acoplamento e responsabilidade única:

- **Controllers**: Camada responsável por expor os endpoints REST e lidar com as requisições HTTP.
- **Services**: Camada de regras de negócio, orquestrando o acesso aos repositórios e às entidades.
- **Repositories**: Abstração do acesso a dados via Spring Data JPA, eliminando a necessidade de SQL manual para operações comuns (CRUD).
- **Entities**: Classes de domínio mapeadas via JPA/Hibernate, representando as tabelas do banco de dados.
- **Exception Handling centralizado**: Uso de `@ControllerAdvice` para tratar exceções de forma global e retornar respostas padronizadas (`StandardError`).
- **DTO-free approach (didático)**: As entidades são utilizadas diretamente na camada de controller, com uso de `@JsonIgnore` para evitar recursão infinita e vazamento de dados sensíveis em relacionamentos bidirecionais.

---

## 🗂️ Modelo de Domínio

O sistema é composto pelas seguintes entidades e relacionamentos:

| Entidade | Descrição | Relacionamentos |
|---|---|---|
| **User** | Cliente da loja | 1:N com `Order` |
| **Product** | Produto disponível para venda | N:N com `Category`, N:N com `Order` (via `OrderItem`) |
| **Category** | Categoria de produtos | N:N com `Product` |
| **Order** | Pedido realizado por um usuário | N:1 com `User`, 1:1 com `Payment`, 1:N com `OrderItem` |
| **OrderItem** | Item de um pedido (chave composta) | N:1 com `Order`, N:1 com `Product` |
| **Payment** | Pagamento associado a um pedido | 1:1 com `Order` |

**Enum `OrderStatus`**: controla o ciclo de vida de um pedido, com os estados `WAITING_PAYMENT`, `PAID`, `SHIPPED`, `DELIVERED` e `CANCELED`.

---

## 📍 Endpoints da API

### Users
| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/users` | Lista todos os usuários |
| `GET` | `/users/{id}` | Busca um usuário por ID |
| `POST` | `/users` | Cadastra um novo usuário |
| `PUT` | `/users/{id}` | Atualiza um usuário existente |
| `DELETE` | `/users/{id}` | Remove um usuário |

### Products
| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/products` | Lista todos os produtos |
| `GET` | `/products/{id}` | Busca um produto por ID |
| `POST` | `/products` | Cadastra um novo produto |
| `PUT` | `/products/{id}` | Atualiza um produto existente (nome, descrição, preço, imagem) |
| `DELETE` | `/products/{id}` | Remove um produto |

### Categories
| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/categories` | Lista todas as categorias |
| `GET` | `/categories/{id}` | Busca uma categoria por ID |
| `POST` | `/categories` | Cadastra uma nova categoria |
| `PUT` | `/categories/{id}` | Atualiza uma categoria existente |
| `DELETE` | `/categories/{id}` | Remove uma categoria |

### Orders
| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/orders` | Lista todos os pedidos |
| `GET` | `/orders/{id}` | Busca um pedido por ID |
| `POST` | `/orders` | Cria um novo pedido, com seus itens |
| `PUT` | `/orders/{id}` | Atualiza o status de um pedido existente |
| `DELETE` | `/orders/{id}` | Remove um pedido |

> 💡 No `POST /orders`, apenas `client.id` e a lista `items` (cada item com `product.id` e `quantity`) precisam ser enviados. O `moment`, o `orderStatus` (definido como `WAITING_PAYMENT`) e o `price` de cada item são calculados automaticamente pelo backend, a partir dos dados já existentes no banco.

Exemplo de corpo para criar um pedido:
```json
{
  "client": { "id": 1 },
  "items": [
    { "product": { "id": 3 }, "quantity": 2 },
    { "product": { "id": 5 }, "quantity": 1 }
  ]
}
```

> ⚠️ O `PUT /orders/{id}` é intencionalmente restrito: só permite alterar o `orderStatus`. Itens e cliente de um pedido já criado não podem ser trocados por essa rota, para preservar a integridade do valor total do pedido.

---

## ⚠️ Tratamento de Erros

A API possui um tratamento de exceções centralizado, aplicado a **todas as entidades** (`User`, `Product`, `Category`, `Order`), retornando respostas padronizadas em JSON:

| Exceção | Status HTTP | Cenário |
|---|---|---|
| `ResourceNotFoundException` | `404 Not Found` | Recurso buscado por ID não existe |
| `DatabaseException` | `400 Bad Request` | Violação de integridade no banco (ex: exclusão de recurso vinculado a outro) |

Exemplo de resposta de erro:
```json
{
  "timestamp": "2026-07-20T14:32:10Z",
  "status": 404,
  "error": "Resource not found",
  "message": "Resource not found! Id 99",
  "path": "/products/99"
}
```

---

## ⚙️ Configuração de Ambientes

O projeto utiliza **profiles do Spring** para alternar entre ambientes:

- **`dev`** *(padrão)*: conecta-se a um banco **PostgreSQL** local.
- **`test`**: utiliza banco **H2 em memória**, populado automaticamente com dados de exemplo via `TestConfig` (usuários, produtos, categorias, pedidos e pagamentos), além de disponibilizar o console web do H2.

---

## 🧰 Como Executar

### Pré-requisitos
- **Java 26** instalado
- **Maven** (ou usar o wrapper `mvnw` incluso no projeto)
- **PostgreSQL** rodando localmente (para o profile `dev`)

### Passo a passo

1. Clone o repositório:
   ```bash
   git clone https://github.com/icarogabriel-dev/workshop.git
   cd workshop
   ```

2. Configure o banco de dados no arquivo `application-dev.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/workshop
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   ```

3. Execute o projeto com o Maven Wrapper:
   ```bash
   ./mvnw spring-boot:run
   ```

4. A API estará disponível em:
   ```
   http://localhost:8080
   ```

### Rodando com o profile de testes (dados de exemplo + H2)
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=test
```
O console do H2 ficará disponível em `http://localhost:8080/h2-console`.

---

## 🗺️ Roadmap

- [ ] Implementar autenticação e autorização (Spring Security + JWT)
- [ ] Adicionar camada de DTOs para desacoplar entidades da API
- [ ] Permitir atualização de categorias vinculadas a um produto
- [ ] Implementar paginação nas listagens
- [ ] Adicionar testes automatizados (unitários e de integração)
- [ ] Documentar a API com Swagger/OpenAPI

---

Desenvolvido por **Icaro Gabriel**
