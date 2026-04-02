# Coupon API

API REST desenvolvida em Java com Spring Boot para o desafio técnico de gerenciamento de cupons.

O projeto foi construído com foco em código limpo, regras de negócio claras e estrutura simples de entender.

## O que a API faz

A API permite:

- criar cupons
- buscar cupons por id
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

A aplicação foi organizada para manter as regras de negócio próximas do domínio e deixar as responsabilidades separadas de forma simples:

- `config` → configurações da aplicação
- `coupon` → regra principal do cupom
- `dto` → entrada e saída da API
- `repository` → acesso a dados
- `service` → fluxo da aplicação
- `exception` → tratamento de erros

## Endpoints

### Criar cupom
`POST /coupon`

### Buscar cupom por id
`GET /coupon/{id}`

### Deletar cupom
`DELETE /coupon/{id}`

## Exemplo de requisição

```json
{
  "code": "ABC-123",
  "description": "Cupom de desconto",
  "discountValue": 10.5,
  "expirationDate": "2026-12-31T23:59:59",
  "published": true
}