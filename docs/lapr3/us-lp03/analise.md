## Planeamento da US-LP03

>Como Product Owner, pretendo que seja desenvolvida uma funcionalidade que permita de forma automática consuma o plano de rega gerado pelo
simulador do controlador desenvolvido na USLP02 e que de forma escalonada após
a conclusão da rega em cada sector registe essa operação no caderno de campo.




### 1.1. Análise
    
>Nesta user story é necessário fazer uma funcionalidade que irá, através do ficheiro .csv gerado na US-LP02, registar regas no Caderno de Campo. 



### 1.2. Clarificações

**Feitas pelo cliente:** 

> **Questão:** Quando o enunciado se refere a "e que de forma escalonada após a conclusão da rega em cada sector registe essa operação"  ,  significa que a operação apenas tem que ser registada comparando a data e hora atual do computador com a do ficheiro que o plano de rega gerou?
> 
> **Resposta:**

> **Questão:** Qual deve ser o formato do caderno de campo? Também é um ficheiro .csv?
>
> **Resposta:**

> **Questão:** O conceito de Sistemas Distribuidos, no contexto desta US, assumimos que é especificamente referente à convergência do registo da operação no caderno de campo nos processos. Está correta a assunção?
>
> **Resposta:** Existe uma gralha na escrita do texto, deveria ser:
"No desenvolvimento desta US serao intencionalmente ignorados aspectos estudados em Sistemas Distribuidos (como por exemplo tolerancia a falhas)."
Isto significa que o simulador que está a ser desenvolvido, por exemplo, assume que os sistemas que lhe fornecem dados vão funcionar adequadamente, sem falhas. O que obviamente é uma simplificação do caso real mas pedagógicamente adequada ao ano curricular em que decorre LAPR3.

> **Questão:** Como é que, a partir do ficheiro gerado na US-LP02, sabemos a quantidade de água utilizada na rega?
> 
> **Resposta:**



### 1.3. Planeamento

> O grupo idealizou várias maneiras de implementação da user story, no entanto devido à falta de informação ainda não chegamos a uma conclusão definitiva.


#### Opção A 

Criação das classes:
* CadernoCampo
* Parcela
* Setor

Alteração das classes:
* Rega
* ControladorRega
* SistemaRega

Pensamos em alterar o método de criação do ficheiro .csv de forma a que este adicionalmente possa adicionar as operações de rega ao caderno de campo.
Desta forma, temos uma adição automáticas das operações.

#### Opção B

Criação das classes:
* CadernoCampo
* Parcela
* Setor

Alteração das classes:
* Rega

A funcionalidade importa um ficheiro .csv, gerado em US-LP02, e regista todas as operações de rega lá descritas.






