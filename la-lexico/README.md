# Analisador Lexico da Linguagem LA.

#### Especificações usadas no desenvolvimento estão no arquivo "Especificações" neste mesmo repositório.

## Linguagens / Dependências: 
* Java (Maven)
* [Antlr](https://www.antlr.org/)


## Sobre a implementação:

O Analisador léxico foi construido utilizando como base os exemplos de código providos pelos casos de teste, portanto, optou-se por cobrir todas as possibilidades de tokens já na gramática, ao invés de tratar todas posteriormente, tornando a gramática mais específica e portanto, menos genérica.

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
    $ java -jar target/lexico-la-1.0-SNAPSHOT-jar-with-dependencies.jar arquivo_de_entrada arquivo_de_saida
```
#### É esperado que o arquivo de entrada possua um código na linguagem LA, para que no arquivo de saída seja escrito os tokens adequadamente.

---

## Alunos:

* Leonardo Donderi Rodrigues - RA: 754756

