# HMI - Hotel Manager Integration API

O HMI (Hotel Manager Integration API) é uma aplicação desenvolvida para gerenciar reservas de um hotel. A aplicação permite criar, listar e notificar reservas utilizando RabbitMQ para mensageria e WebSockets para notificação em tempo real.
## Problema que o Projeto Resolve

Gerenciar reservas de hotel de forma eficiente, com notificações em tempo real para o frontend, garantindo que os dados estejam sempre atualizados e sincronizados.
Independete se a reserva for criada por um usario no front-end ou integrada via api.

## Tecnologias Utilizadas

- Java 
- Spring Boot 3.4.2
    - Spring Boot Starter AMQP
    - Spring Boot Starter Data JPA
    - Spring Boot Starter Validation
    - Spring Boot Starter Web
    - Spring Boot Starter WebSocket
    - Spring Boot DevTools
    - Spring Boot Starter Test
    - Spring Rabbit Test 
- PostgreSQL
- Flyway (migrations)
- RabbitMQ
- WebSockets

## Estrutura
```
hmi/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── matgdev/
│   │   │           └── hmi/
│   │   │               ├── config/
│   │   │               │   ├── RabbitMQConfig.java
│   │   │               │   ├── WebConfig.java
│   │   │               │   └── WebSocketConfig.java
│   │   │               ├── consumer/
│   │   │               │   └── ReservaConsumer.java
│   │   │               ├── controller/
│   │   │               │   └── ReservaController.java
│   │   │               ├── model/
│   │   │               │   ├── Reserva.java
│   │   │               │   └── StatusReserva.java
│   │   │               ├── repository/
│   │   │               │   └── ReservaRepository.java
│   │   │               └── service/
│   │   │                   └── ReservaService.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/
│   │           └── migration/
│   │               └── V1__Initial_setup.sql
│   └── test/
│       └── java/
│           └── com/
│               └── matgdev/
│                   └── hmi/
│                       └── HmiApplicationTests.java
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Endpoints da API

### Criação de Reserva
1. **POST /reservas**
    - O cliente envia uma requisição POST para o endpoint /reservas com os dados da reserva.
    - O ReservaController recebe a requisição e chama o ReservaService para criar a reserva.
    - O ReservaService salva a reserva no banco de dados e envia uma mensagem para o RabbitMQ.
    - O ReservaConsumer recebe a mensagem da fila reservaQueue e notifica o frontend via WebSocket.

### Listagem de Reservas
1. **GET /reservas**
    - O cliente envia uma requisição GET para o endpoint /reservas.
    - O ReservaController recebe a requisição e chama o ReservaService para listar as reservas.
    - O ReservaService retorna a lista de reservas do banco de dados.
    - O ReservaController retorna a lista de reservas para o cliente.

### No Front-End
Em seu front end devemos configurar uma comunicação com o WebSocket usando o endpoint /ws-reservas e StompJs como message broker para ficar ouvindo o topic/reservas.
Acrescente um fetch para o /reservas para carregar a lista de reservas enquanto o WebSocket atualiza a lista com novas reservas em tempo real.

## Executando

1. Certifique-se de que o RabbitMQ e o PostgreSQL estão em execução.
2. Configure o banco de dados PostgreSQL conforme especificado no arquivo application.properties.
3. Execute a aplicação usando o Maven:
    ```sh
    ./mvnw spring-boot:run
    ```
4. Acesse a aplicação nos endpoints:
    - **POST /reservas** para criar uma reserva.
    - **GET /reservas** para listar as reservas.
    - **ws://localhost:8080/ws-reservas** para conectar ao WebSocket.

## Como Contribuir

1. Faça um fork do projeto.
2. Crie uma nova branch: `git checkout -b minha-nova-feature`.
3. Faça suas alterações e commit: `git commit -m 'Adiciona nova feature'`.
4. Envie para o branch original: `git push origin minha-nova-feature`.
5. Crie um pull request.

## Arquitetura do Projeto

A arquitetura do projeto é baseada em uma aplicação Spring Boot com uma estrutura de camadas bem definida, utilizando RabbitMQ para mensageria e WebSockets para notificações em tempo real. A escolha dessa arquitetura se deve à necessidade de um sistema robusto e escalável, capaz de lidar com um grande volume de reservas e notificações em tempo real.
### Estratégia de Banco de Dados

A arquitetura do projeto abre possibilidade para utilizar uma estratégia de banco de dados replicado. A ideia é ter uma cópia do banco de dados principal, que será usada exclusivamente para operações de leitura. Isso reduzirá a carga no banco de dados principal, que continuará a ser responsável pelas operações de inserção e atualização. A replicação dos dados será feita de forma assíncrona para garantir que as leituras sejam rápidas e não impactem o desempenho das operações de escrita. garantindo a eficiência e a escalabilidade do sistema durante períodos de alta demanda, como Black Friday ou promoções.

### Uso do RabbitMQ

Além da estratégia de banco de dados replicado, o RabbitMQ desempenha um papel crucial na arquitetura do sistema. Ele é utilizado para:

- **Desacoplamento de Componentes**: O RabbitMQ permite que os componentes do sistema se comuniquem de forma assíncrona, desacoplando as operações de escrita e leitura e melhorando a resiliência do sistema.
- **Gerenciamento de Carga**: Durante períodos de alta demanda, o RabbitMQ ajuda a distribuir a carga de trabalho, garantindo que as mensagens sejam processadas de forma eficiente e que o sistema continue a funcionar sem interrupções.
- **Escalabilidade Horizontal**: Com o RabbitMQ, é possível escalar horizontalmente os consumidores de mensagens, adicionando mais instâncias conforme necessário para lidar com o aumento do volume de mensagens.

### Desenho da Arquitetura
<div style="width: 640px; height: 480px; margin: 10px; position: relative;"><iframe allowfullscreen frameborder="0" style="width:640px; height:480px" src="https://lucid.app/documents/embedded/66db22db-df05-4046-963d-133b33deb653" id="41cO8lbdzCA7"></iframe></div>


### Argumentos para o Desenho Escolhido

- **Modularidade**: A arquitetura é modular, facilitando a manutenção e a escalabilidade do projeto.
- **Mensageria**: Utiliza RabbitMQ para comunicação assíncrona, garantindo a entrega de mensagens e a desacoplagem entre os componentes.
- **Notificações em Tempo Real**: Utiliza WebSockets para notificação em tempo real, melhorando a experiência do usuário.
- **Banco de Dados Replicado**: Utiliza uma cópia do banco de dados principal para operações de leitura, reduzindo a carga no banco de dados principal e melhorando o desempenho das operações de leitura.

## Principais Features

- Criação e listagem de reservas.
- Notificações em tempo real via WebSocket.
- Integração com RabbitMQ para mensageria.
- Migrações de banco de dados com Flyway.

## Padrões de Projeto Adotados

- **MVC (Model-View-Controller)**: Para separar as responsabilidades da aplicação.
- **Repository Pattern**: Para abstrair o acesso ao banco de dados.
- **Service Layer**: Para encapsular a lógica de negócios.

## Style Guide

O projeto segue as convenções de código do Java e do Spring Boot, garantindo um código limpo e fácil de manter.

## Exemplos de Consumo da API

### Criar Reserva
```sh
curl -X POST http://localhost:8080/reservas -H "Content-Type: application/json" -d '{"nome": "John Doe", "data": "2023-10-01"}'
```

### Listar Reservas
```sh
curl -X GET http://localhost:8080/reservas
```

## Lições Aprendidas

Durante o desenvolvimento deste projeto, aprendemos a importância de uma arquitetura bem definida e a utilização de ferramentas de mensageria e notificações em tempo real para melhorar a experiência do usuário e escabilidade do sistema como todo.

## Por Onde Começar?

1. Clone o repositório.
2. Configure o ambiente conforme descrito na seção "Executando".
3. Explore a estrutura do projeto e os endpoints da API.
4. Contribua com novas features e melhorias.

