# Índice

* [Índice](#índice)
* [Título e Imagem de capa](#projeto-responsável-pelo-domínio-de-pedidos)
* [Arquitetura Hexagonal](#arquitetura-hexagonal)
* [Testes do Projeto](#testes)
* [Como executar Projeto](#como-executar)
* [Swagger](#swagger)
* [Observabilidade](#observabilidade)

# Projeto responsável pelo domínio de pedidos

Este projeto é o responsável por gerenciar o domínio de pedidos, centralizando as regras de negócio que envolvem o pedido de um usuário.

# Arquitetura Hexagonal
Este projeto segue o conceito de arquitetura hexagonal, onde o domínio é o centro da aplicação e as camadas de entrada e saída são periféricas.


## Testes
Este projeto aborda o conceito de pirâmide de testes, onde a base da pirâmide são os testes unitários que são mais rápidos e mais baratos de serem executados, seguido pelos testes de integração, que são um pouco mais lentos e um pouco mais caros, e por fim os testes end-to-end, que visam a representação dos cenários de uso da aplicação e são mais lentos e mais caros.

## Como Executar

Para a execução deste projeto, se faz necessário o clone do projeto **legaltech-infra**, que é o responsável por inicializar toda a infraestrutura necessária para o funcionamento do projeto localmente.

- Rodar a aplicação com perfil local:
```
./mvnw spring-boot:run -P local
```

# Swagger
Este projeto disponibiliza uma documentação de API através do Swagger.  
Para acessar a documentação, basta acessar o link abaixo:  

[Swagger Pedido - Local](http://localhost:8090/swagger-ui.html)  

# Observabilidade
Este projeto disponibiliza alguns endpoints para monitoramento da aplicação.  
Abaixo a lista de endpoints disponíveis:

 [Management](http://localhost:8090/management)  
 [Healthcheck](http://localhost:8090/management/health)  
 [Metrics](http://localhost:8090/management/metrics)  
 [Info](http://localhost:8090/management/info)  
 [Enviroment](http://localhost:8090/management/env)






