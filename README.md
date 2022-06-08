# ProjetoSoftware
Disciplina Programação 2 / Projeto de Software em Java

Aplicado conceitos de O.O.

----------------------------------

Code Smells:
MiddleMan -> containSpaces (Função que pode ser simplesmente reescrita no lugar de ser chamada toda vez)

PrimitiveObsession -> modifyAccount (Função que está recebendo CONSTANTES com nomes de atributos)

LargeClass -> Operations (Manipula 4 classes -> Account, Community, Message, Feed)

LongMethod -> Menu de Switch/Case muito extenso

Duplicated Code -> Repetição da estrutura Switch/Case para tratar o menu logado e deslogado.

Duplicated Code -> Repetição da estrutura para buscar uma conta numa função (Account find = findAcc(login));

Generative Speculation -> Construtores utilizados apenas para simplificar testes, mas nunca na prática (pois sempre o objeto é atualizado com Setters)
