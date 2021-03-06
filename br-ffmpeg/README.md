# Compilador texto -> comando FFMPEG.

Dada a dificuldade de escrever comandos FFMPEG, o brffmpeg tem uma linguagem simples que é compilada para a linha de comando e irá efetivamente editar o vídeo.

Disponibiliza-se possíveis comandos: 

* Cortar
* Acelerar / Desacelerar
* Legendar

Terá as seguintes validações:
* Ao menos uma função de alteração.
* Sempre possuir arquivo de entrada e saída.
* Nome de funções / Declarações.


## Exemplo Cortar:

### Entrada:

```
    ENTRADA:
        'C:\Users\donde\Compiladores\br-ffmpeg\input.mp4'
    SAIDA:
        'C:\Users\donde\Compiladores\br-ffmpeg\output.mp4'
    CORTAR:
        INICIO 00:00:00
        FIM 00:00:30
```

### Saida: 

```
 ffmpeg -ss 00:00:00 -i  C:\Users\donde\Compiladores\br-ffmpeg\input.mp4 -to 00:00:30 -c copy C:\Users\donde\Compiladores\br-ffmpeg\output.mp4
```

## Exemplo Acelerar

Aqui, temos algumas definições não tão óbvidas

VELOCIDADE 2 -> Dobra a velocidade
VELOCIDADE 5 -> 5x da velocidade padrão

VELOCIDADE -2 -> Dobra a duração do vídeo, 2x mais lento.
VELOCIDADE -5 -> 5x mais lento.

AUDIO Y -> Mantem o áudio
AUDIO N -> Remove o áudio

### Entrada:

```
    ENTRADA:
        'C:\Users\donde\Compiladores\br-ffmpeg\input.mp4'
    SAIDA:
        'C:\Users\donde\Compiladores\br-ffmpeg\output.mp4'
    ACELERAR:
        VELOCIDADE 5
        AUDIO Y
```

### Saida: 

```
  ffmpeg -i  C:\Users\donde\Compiladores\br-ffmpeg\input.mp4 -vf "setpts=0.2*PTS" -af "atempo=5" C:\Users\donde\Compiladores\br-ffmpeg\outputSpeed.mp4
```


## Exemplo Legenda


### Entrada:

```
    ENTRADA:
       'C:\Users\donde\Compiladores\br-ffmpeg\input.mp4'
    SAIDA:
       'C:\Users\donde\Compiladores\br-ffmpeg\output.mp4'
    LEGENDA:
        'C:\Users\donde\Compiladores\br-ffmpeg\legenda.srt'
```

### Saida: 

```
 ffmpeg -i C:\Users\donde\Compiladores\br-ffmpeg\input.mp4 -i C:\Users\donde\Compiladores\br-ffmpeg\legenda.srt -map 0 -map 1 -c copy -c:s mov_text C:\Users\donde\Compiladores\br-ffmpeg\output.mp4
```

## Para compilar:

Utilize o comando build de sua IDE de preferência ou siga o passo a passo apontados nestes textos:

[[Maven] Compilando com o Plugin Compiler do Maven](
https://medium.com/@andgomes/compilando-com-o-plugin-compiler-do-maven-13c4afe12858)

[Processo de build com o Maven](https://blog.caelum.com.br/processo-de-build-com-o-maven/amp/)

---

## Para executá-lo:

Com o projeto compilado na pasta target, com o arquivo em formato jar que pode ser executado com o seguinte comando:

```sh
    $ java -jar target/brffmpeg-1.0-SNAPSHOT-jar-with-dependencies.jar entrada.txt saida.txt
```

#### É esperado que o arquivo de entrada possua um código seguindo o padrão descrito anteriormente, para que no arquivo de saída seja gerado o comando ffmpeg.
