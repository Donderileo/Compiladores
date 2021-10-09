# Analisador Semantico da Linguagem LA.

#### Especificações usadas no desenvolvimento estão no arquivo "Especificações" neste mesmo repositório.

## Linguagens / Dependências: 
* Java (Maven)
* [Antlr](https://www.antlr.org/)


## Sobre a implementação:

O Analisador semantico foi construido utilizando a gramática disponibilizada nas especificações do trabalho e utilizando a geração de código automática do ANTLR.
Com base nos casos de teste, podemos ver as possibilidades de erro que podem ser geradas pelo compilador e com isso criar um Listener de erros personalizado, criando um switch para cada tipo de erro possível. No nosso caso, os erros tratados são:

* Tipos não declarados
* Variáveis não declaradas
* Variaveis já declaradas
* Compatibilidade de atribuições
* Incompatibilidade de parâmetros nas chamadas de funções/procedimentos.
* Permissão do comando retorno apenas em funções

---

## Para compilar:

Utilize o comando build de sua IDE de preferência ou siga o passo a passo apontados nestes textos:

[[Maven] Compilando com o Plugin Compiler do Maven](
https://medium.com/@andgomes/compilando-com-o-plugin-compiler-do-maven-13c4afe12858)

[Processo de build com o Maven](https://blog.caelum.com.br/processo-de-build-com-o-maven/amp/)

---

## Para executá-lo:

Temos o projeto compilado na pasta target, em formato jar que pode ser executado com o seguinte comando:
```sh
    $ java -jar target/la-semantico-1.0-SNAPSHOT-jar-with-dependencies.jar arquivo_de_entrada arquivo_de_saida
```

#### É esperado que o arquivo de entrada possua um código na linguagem LA, para que no arquivo de saída a análise semântica seja feita corretamente.

---

## Alunos:

* Leonardo Donderi Rodrigues - RA: 754756

