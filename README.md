# ProjetoSoftware
<p align="center">
    Disciplina Programação 2 / Projeto de Software em Java
</p>

Sistema funcional (primeira versão).
Aplicado conceitos de O.O (segunda versão). 
Analisado os code smells (apenas análise).
Aplicado design patterns e corrigindo code smells (versão atual).

----------------------------------
<p align="center">
    Design patterns e soluções de smells
</p>

Smells simples resolvidos: 

Generative especulation -> construtores feitos em várias classes, inclusive na nossa classe principal Account, e os mesmos nunca foram utilizados. Todos foram removidos.

Inverted booleans -> métodos que sempre tinham que vir com um operador de '!' nas nossas condicionais, esses métodos tiveram seus retornos refeitos, principalmente a função checkRegex que possuia mais de 4 condicionais para retorno, o que deixava o código contra-intuitivo, agora temos algo legível e de simples entendimento.

Middleman -> métodos que aumentam a complexidade do código e eram usados apenas 1 vez ou 2, além desses métodos tinhamos alguns métodos com apenas uma linha, como no caso do método containsSpaces, que poderia ser simplesmente re-escrito quando fosse ser usado no lugar de ser um método. Solucionamos removendo o método containsSpaces e removendo métodos como o seeMsg que era utilizado apenas uma vez em todo o programa, também foi re-escrito para simplicar e ajudar na legibilidade.

Move Method -> métodos de busca que se relacionam de forma fraca com suas classes foram movidos para nossa nova classe GeneralOps (que inclui as nossas operações generalizadas para que as diversas classes (Account, Community, Feed) possam utilizá-las).

-----


-----

Padrão Bridge:

Resolvendo nosso problema da large class de operações com mais de 700 linhas, resolvemos utilizar subclasses que se relacionariam mais com as funções de cada tipo, conta, comunidade e feed, e não deixando essas funções numa única classe gerenciando todas as operações do projeto.

O padrão de projeto Bridge divide a lógica de negócio ou uma enorme classe (nosso caso) em hierarquias de classe separadas que podem ser desenvolvidas independentemente. Uma dessas (as subclasses com as operações) obterá uma referência a um objeto da segunda hierarquia, agora nossas funções estão dentro das novas classes: 
- OpAccount,
- OpCommunity, 
- OpFeed; 

Facilitando manutenção, legibilidade e resolvendo o nosso code smell. O foco foi separar nossa large class em classes menores que se relacionam mais entre si. Foi criado também uma nova classe GeneralOps, que será nossa "Ponte (Bridge)", com funções gerais de busca e checagens que serviram pra todas as nossas classes filhas de operações que herdam as operações gerais.

Classe base da aplicação (classe de operações gerais):
<p align="center">
    <img width="200" src="https://github.com/Arthurls7/ProjetoSoftware/blob/main/imgs/BridgeExample.png" alt="Bridge Example">
</p>

-----

Padrão Builder

Utilizado para resolver nosso smell de código duplicado encontrado na nossa função de criar um objeto de conta e definir seus atributos, o padrão builder tem como objetivo eliminar a complexidade na criação de objetos e também deixar esse processo mais intuitivo. Com isso conseguimos poupar várias linhas de código e o deixar mais legível, um exemplo de onde aplicamos o padrão:

<p align="center">
  <img width="200" src="https://github.com/Arthurls7/ProjetoSoftware/blob/main/imgs/BuilderExample.png" alt="Builder Example">
</p>

-----

Padrão FactoryMethod

Utilizado de forma simples para resolver nosso problema com os tipos de mensagens, utilizando polimorfismo fizemos uma super classe Message, e subclasses PrivateMessage, FeedMessage, CommunityMessage, que herdam da nossa classe Message, e variam nosso método showData(), pois cada classe possui um atributo diferente, e fazendo isso podemos printar a mensagem formatada em qualquer loop do tipo Message (super classe) e teremos a mensagem de qualquer um dos 3 tipos sendo mostrada de forma correta. Utilizamos a linha de pensamento desse design pattern para o polimorfismo.

<p align="center">
  <img width="200" src="https://github.com/Arthurls7/ProjetoSoftware/blob/main/imgs/FactoryExample.png" alt="Factory Example">
</p>

-----

Aplicação do Extract Method:

Métodos muito grandes fazendo mais do que deveriam, com foco no nosso método de remover, que PERCORRIA TODAS AS CLASSES ESSENCIAIS, partimos ele em 6 métodos, agora facilitando a manutenção e legibilidade do mesmo. Fora isso, alguns métodos extensos e fazendo muitas verificações também foram refeitos em métodos menores.

<p align="center">
  <img width="200" src="https://github.com/Arthurls7/ProjetoSoftware/blob/main/imgs/ExtractMethodExample.png" alt="Extract Method Example">
</p>

-----
