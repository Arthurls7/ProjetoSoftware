# ProjetoSoftware
Disciplina Programação 2 / Projeto de Software em Java

Primeira versão entregue.
Aplicado conceitos de O.O (segunda versão). 
Analisado os code smells.
Aplicando design patterns e corrigindo code smells (versão atual).

----------------------------------

Design patterns e soluções de smells:

Padrao Singleton:

O padrão de projeto Singleton é um padrão criacional, cujo objetivo é criar apenas uma instância de uma classe e fornecer apenas um ponto global de acesso àquele objeto. 

-> Acesso a conta agora é restrito e feito de forma exclusiva, padronizado globalmente.
-> Acesso a comunidade agora tambem eh restrito e feito de forma exclusiva, padronizado globalmente.
-> Ambas classes agora utilizam construtores privados.

Com isso conseguimos buscar facilmente os pontos onde instanciamos as classes essenciais do nosso projeto e ter a manutenção do código de forma mais simples e legível. Resolvendo também o problema em que as contas e comunidades poderiam ser instanciadas de formas diferentes e em lugares aleatórios do código.

Basicamente utilizamos: 
Uma variável estática privada, que contém a única instância da classe.
Um construtor privado, de modo a não ser possível instanciá-la de qualquer outro lugar.
Um método estático público, para retornar a instância única da classe.

-----

Padrão Bridge:

Resolvendo nosso problema da large class de operações com mais de 700 linhas, resolvemos utilizar subclasses que se relacionariam mais com as funções de cada tipo, conta, comunidade e feed, e não deixando essas funções numa única classe gerenciando todas as operações do projeto.

O padrão de projeto Bridge divide a lógica de negócio ou uma enorme classe (nosso caso) em hierarquias de classe separadas que podem ser desenvolvidas independentemente. Uma dessas (as subclasses com as operações) obterá uma referência a um objeto da segunda hierarquia (agora nossas funções estão dentro das novas classes -> OpAccount, OpCommunity, OpFeed), facilitando manutenção, legibilidade e resolvendo o smell.

O foco foi separar nossa large class em classes menores que se relacionam mais entre si.
Foi criado também uma nova classe GeneralOps (com funções gerais de busca e checagens que serviram pra todas as nossas classes filhas de operações que herdam as operações gerais).

-----

Smells simples resolvidos: 

Generative especulation -> construtores feitos em várias classes, inclusive na nossa classe principal Account, e os mesmos nunca foram utilizados. Todos foram removidos.

Inverted booleans -> métodos que sempre tinham que vir com um operador de '!' nas nossas condicionais, esses métodos tiveram seus retornos refeitos, principalmente a função checkRegex que possuia mais de 4 condicionais para retorno, o que deixava o código contra-intuitivo, agora temos algo legível e de simples entendimento.

Middleman -> métodos que aumentam a complexidade do código e eram usados apenas 1 vez ou 2, além desses métodos tinhamos alguns métodos com apenas uma linha, como no caso do método containsSpaces, que poderia ser simplesmente re-escrito quando fosse ser usado no lugar de ser um método. Solucionamos removendo o método containsSpaces e removendo métodos como o seeMsg que era utilizado apenas uma vez em todo o programa, também foi re-escrito para simplicar e ajudar na legibilidade.

Move Method -> métodos de busca que se relacionam de forma fraca com suas classes foram movidos para nossa nova classe GeneralOps (que inclui as nossas operações generalizadas para que as diversas classes (Account, Community, Feed) possam utilizá-las).

-----
