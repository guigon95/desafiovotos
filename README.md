# desafiovotos

Informações

Para a execução da aplicação é necessário possuir docker instalado e após realzar o download do docker-compose deste repositório, 
executar o comando abaixo:

docker compose up -d

Após isso será feito o download da imagem do kafka e kafkadrop, caso ainda não possua em sua máquina, 
e o download da imagem da aplicação desafiovotos, e por fim, a execução dos containers

Para realizar operações na API basta acessar http://localhost:8080

A documentaçãa da API encontra-se no link http://localhost.com:8080/swagger-ui.html

Para acessar via navegador os tópicos do kafka, acesse: http://localhost:19000

#####################################################################

A API também pode ser acessada através do https://desafio-votos.herokuapp.com/

Recomendo a utlização de um API Client (Ex: Postman) para realização dos testes

#####################################################################

- A documentacão da API realizada com o Swagger. URL: https://desafio-votos.herokuapp.com/swagger-ui.html
- O armazenamento dos dados foi realizado utilizando o banco Postgres que está rodando em RDS na AWS.
- A integração com o sistema foi feita através do OpenFeing do Spring Cloud.
- O envio de mensagens após o encerramento das sessões foi feito utilizando o Apache Kafka.
- O versionamento da API foi realizado utilizando o modelo de subdominio. Ex: https://desafio-votos.herokuapp.com/v1/sessao
- Para os testes unitários foi utilizado WebMvcTest, Junit e Mockito. 

