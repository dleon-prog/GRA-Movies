# GRA-Movies
#Golden Raspberry Awards API
#Descrição
#Este projeto implementa uma API RESTful para listar indicados e vencedores da categoria "Pior Filme" do Golden Raspberry Awards. A API também identifica o produtor com:

#O menor intervalo entre dois prêmios consecutivos.
#Tecnologias Utilizadas
#Java 17
#Spring Boot 3.3.5
#H2 Database (em memória)
#OpenCSV (para leitura de arquivos CSV)
#Maven (gerenciador de dependências)
#JUnit 5 (testes de integração)
#Funcionalidades
#Carregar dados CSV no banco de dados ao iniciar a aplicação.
#Consultar intervalos de prêmios:
#Endpoint: GET /api/producers/interval
#Retorno: Produtores com os maiores e menores intervalos entre prêmios consecutivos.
#
#bash
#git clone https://github.com/dleon-prog/GRA-Movies.git
#cd golden-raspberry-awards
#Compile e rode a aplicação:
#
#bash
#mvn spring-boot:run
#
#Tomcat started on port 8080 (http) with context path '/'
#Acesse a API pelo endpoint principal:
#
#bash
#GET http://localhost:8080/api/producers/interval
#Testando a Aplicação
#Teste Manual
#Endpoint de Intervalos: Faça uma requisição GET para o endpoint:
#
#http://localhost:8080/api/producers/interval
#Exemplo de Resposta:
#
#json
{
    "min": [
        {
            "producer": "Producer A",
            "interval": 1,
            "previousWin": 2008,
            "followingWin": 2009
        }
    ],
    "max": [
        {
            "producer": "Producer B",
            "interval": 5,
            "previousWin": 2000,
            "followingWin": 2005
        }
    ]
}

#URL: http://localhost:8080/h2-console
#JDBC URL: jdbc:h2:mem:testdb
#Username: sa
#Password: (deixe em branco)
#Exemplo de Query:

#SELECT * FROM movies;
#Teste Automatizado
#
#Rode os testes de integração:
#mvn test
#Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
#
#Cenários de Teste
#Cenário 1: Produtores com Menor e Maior Intervalo
#Dado: Um banco de dados carregado com os seguintes filmes:
#release_year;title;studios;producers;winner
#2000;Movie A;Studio A;Producer A;yes
#2001;Movie B;Studio B;Producer A;yes
#2005;Movie C;Studio C;Producer B;yes
#2010;Movie D;Studio D;Producer B;yes
#Quando: Uma requisição é feita para GET /api/producers/interval.
#Então: O retorno será:
json
{
    "min": [
        {
            "producer": "Producer A",
            "interval": 1,
            "previousWin": 2000,
            "followingWin": 2001
        }
    ],
    "max": [
        {
            "producer": "Producer B",
            "interval": 5,
            "previousWin": 2005,
            "followingWin": 2010
        }
    ]
}
#Cenário 2: Nenhum Produtor Elegível
#Dado: Um banco de dados carregado sem filmes vencedores.
#Quando: Uma requisição é feita para GET /api/producers/interval.
#Então: O retorno será:
json
{
    "min": [],
    "max": []
}
#Cenário 3: Vários Produtores com o Mesmo Intervalo
#Dado: Um banco de dados carregado com os seguintes filmes:
#release_year;title;studios;producers;winner
#2000;Movie A;Studio A;Producer A;yes
#2005;Movie B;Studio B;Producer A;yes
#2000;Movie C;Studio C;Producer B;yes
#2005;Movie D;Studio D;Producer B;yes
#Quando: Uma requisição é feita para GET /api/producers/interval.
#Então: O retorno será:
json
{
    "min": [
        {
            "producer": "Producer A",
            "interval": 5,
            "previousWin": 2000,
            "followingWin": 2005
        },
        {
            "producer": "Producer B",
            "interval": 5,
            "previousWin": 2000,
            "followingWin": 2005
        }
    ],
    "max": [
        {
            "producer": "Producer A",
            "interval": 5,
            "previousWin": 2000,
            "followingWin": 2005
        },
        {
            "producer": "Producer B",
            "interval": 5,
            "previousWin": 2000,
            "followingWin": 2005
        }
    ]
}

#Email: cv.deleon.dias@gmail.com
