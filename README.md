# TampinhaLegal
APP Android, que consome um webservice e a API do Google Maps

Esse app seria relacionado a um projeto que arrecada tampinhas de plástico. Devido ao cliente não
solicitar contato para continuar o app, foi feita só a parte de Pontos de Coleta, onde mostra a localização dos pontos de coleta
de tampinhas. Essa localização está vindo do webservice, que faz conexão com o banco MySql. Esse webservice não foi hospedado, é executado em localhost, através do Tomcat e xampp.
Também há a função de pegar a localização do usuário do app em tempo real, como também a função de pesquisar por endereço/cep. No video abaixo mostro as funções do app.

Foi utilizado o padrão de arquitetura MVC no projeto. 

Segue o video:

[![](http://img.youtube.com/vi/CYWFeImkCfo/0.jpg)](http://www.youtube.com/watch?v=CYWFeImkCfo "")


# Alguns frameworks/serviços utilizados na criação do app

- Google Play Services - para usar a API do Google Maps
- API GOOGLE MAPS FOR ANDROID SDK - para utilizar a parte de mapa do google
- Carousel - para fazer a parte carousel mesmo das imagens de patrocinio ( Só aparece 1 imagem no video pois só botei 1 imagem )
- Glide - para carregar a imagem do patrocinio através da url vinda do servidor 
- LocationManager - para pegar localização atual do usuário
- Geocoder - pra permitir a pesquisa através do endereço inserido pelo usuário
- Android Async Http Client, Http Client - para estabelecer uma conexão http assincrona com o servidor e fazer a requisição  
- Gson - para pegar o gson vindo do webservice

# Tecnologias utilizadas na criação do webservice:
- API REST usando JAX-RS - Para fazer a requisição GET listando os dados
- MySql - Banco de dados, para guardar as informações necessárias
- Apache Tomcat - Rodar o projeto localhost

# Como executar o app
- Abrir o zip webservice&bancodedados, descompactar o projeto do servidor que é o arquivo .zip, e abrir em alguma IDE, após isso abrir o mysql importar o arquivo .dump que é o banco de dados. Após isso ir no projeto do app na classe PontosDeColetaControl e ir no método carregarPontos() e mudar o ip inserido na 'String url' pro ip da sua rede. Após isso só executar o  webservice com apache tomcat ou glassfish e depois executar o app.




