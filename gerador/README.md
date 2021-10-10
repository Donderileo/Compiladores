# Gerador de código da Linguagem LA para a Linguagem C.

#### Especificações usadas no desenvolvimento estão no arquivo "Especificações" neste mesmo repositório.

## Linguagens / Dependências: 
* Java (Maven)
* [Antlr](https://www.antlr.org/)


## Sobre a implementação:

O Gerador de código passa pelas analise lexica, sintatica e semantica antes de gerar o código em C. Por isso, não se faz necessária muitas verificações em busca de erros, assim, pode-se focar apenas em realizar a geração de código.
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

