# Coupon API

API REST desenvolvida em Java com Spring Boot para um desafio técnico de gerenciamento de cupons.

A proposta do projeto foi manter a solução simples, organizada e com as regras de negócio bem definidas, sem complexidade desnecessária.

## Funcionalidades

A API permite:

- criar cupons
- buscar um cupom por id
- listar cupons
- listar cupons com filtro por status
- remover cupons com soft delete

## Regras implementadas

### Criação de cupom
- `code` é obrigatório
- `description` é obrigatória
- `discountValue` é obrigatório e deve ser no mínimo `0.5`
- `expirationDate` é obrigatória e não pode estar no passado
- o código pode receber caracteres especiais, mas eles são removidos antes de salvar
- o código final deve ter exatamente 6 caracteres alfanuméricos
- o cupom pode ser criado como publicado
- o campo `redeemed` inicia como `false`

### Exclusão de cupom
- a exclusão é lógica, sem apagar o registro do banco
- um cupom já deletado não pode ser deletado novamente

### Listagem de cupons
O endpoint de listagem possui 3 comportamentos:

- `GET /coupon` → retorna todos os cupons
- `GET /coupon?status=ACTIVE` → retorna apenas os cupons ativos
- `GET /coupon?status=DELETED` → retorna apenas os cupons deletados

## Tecnologias usadas

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- Bean Validation
- Spring Security
- Swagger / OpenAPI
- JUnit 5
- Mockito
- Docker
- Docker Compose

## Estrutura do projeto

O projeto foi organizado de forma simples, separando as responsabilidades principais da aplicação:

- `config` → configurações gerais
- `coupon` → regra principal do cupom
- `dto` → objetos de entrada e saída da API
- `repository` → acesso a dados
- `service` → fluxo da aplicação
- `exception` → tratamento de erros

## Endpoints

### Criar cupom
`POST /coupon`

### Listar todos os cupons
`GET /coupon`

### Listar cupons por status
`GET /coupon?status=ACTIVE`  
`GET /coupon?status=DELETED`

### Buscar cupom por id
`GET /coupon/{id}`

### Deletar cupom
`DELETE /coupon/{id}`

## Exemplo de requisição para criação

```json
{
  "code": "ABC-123",
  "description": "Cupom de desconto",
  "discountValue": 10.5,
  "expirationDate": "2026-12-31T23:59:59",
  "published": true
}