# Recon Automation API

Recon Automation é uma API desenvolvida para automatizar o processo de reconhecimento de vulnerabilidades de segurança cibernética em aplicações e redes. Utilizando ferramentas como Docker e Docker Compose para fácil implantação, a API interage com um banco de dados PostgreSQL e segue as melhores práticas de desenvolvimento com Spring Boot e Java.

## Índice

- [Instalação](#instalação)
- [Endpoints da API](#endpoints-da-api)
- [Uso](#uso)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)

## Instalação

### Pré-requisitos

- Java 11 ou superior
- Maven
- Docker
- Docker Compose
- Git

### Configuração do Ambiente com Docker

1. **Clone este repositório**:

   ```bash
   git clone https://github.com/usuario/tcc-recon-automation.git
   

   
2. Navegue até a pasta do projeto:
   ```bash
   cd tcc-recon-automation


3. Execute o comando abaixo para iniciar os serviços:
   ```bash
   docker-compose up --build
Isso fará o build da API e inicializará o PostgreSQL no Docker. A API estará disponível em http://localhost:8080 e o banco de dados PostgreSQL em localhost:5432.
Rotas da API

## Endpoints da API

1. Listar Domínios
   - Endpoint: /domains
   - Método: GET
   - Descrição: Retorna a lista de domínios cadastrados com seus respectivos subdomínios e vulnerabilidades identificadas.

    ```json
    [
        {
            "id": 22,
            "address": "www.example.com",
            "subdomains": [
                {
                    "id": 352,
                    "address": "sub.example.com.br",
                    "vulnerabilities": []
                }
            ],
            "vulnerabilities": [
                {
                    "id": 11,
                    "info": "[error-logs] [http] [low] https://www.example.com.br/error.log [paths=\"/error.log\"]",
                    "firstSeen": "2024-09-06"
                }
            ]
        }
    ]


2. Cadastrar Domínio
   - Endpoint: /new-domain
   - Método: POST
   - Descrição: Cadastre um novo domínio.
   - Body Request:
   ```json
   {
   "address": "www.example.com.br"
   }

3. Deletar Domínio
   - Rota: /domains/{id}
   - Método: DELETE
   - Descrição: Remove um domínio.
   - Resposta: 204 No Content

## Uso


    curl -X GET http://localhost:8080/domains   

## Tecnologias Utilizadas
- Java 11 ou superior: Linguagem principal do projeto.
- Spring Boot: Framework para desenvolvimento rápido e produtivo de aplicações Java.
- Maven: Ferramenta de automação de build e gerenciamento de dependências.
- PostgreSQL: Banco de dados relacional utilizado para armazenamento de dados.
- Docker: Ferramenta de containerização utilizada para subir a máquina onde as ferramentas de segurança são executadas.
- Docker Compose: Orquestração de containers Docker, facilitando a integração do banco de dados e da API.
