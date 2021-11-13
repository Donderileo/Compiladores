# Compilador texto -> comando FFMPEG.

Este compilador fará a tradução de um comando em texto para o comando específico do programa FFMPEG

Funções disponíveis:
* Cortar
* Acelerar / Desacelerar
* Legendar

Terá as seguintes validações:
* Ao menos uma função de alteração.
* Tempos de corte Inicio < Fim.
* Sempre possuir arquivo de entrada e saída.
* Nome de funções / Declarações.


## Exemplo Cortar:

### Entrada:

```
    Entrada:
        C:\Users\donde\Compiladores\CompiladorFFMPEG\input.mp4
    Saida:
        C:\Users\donde\Compiladores\CompiladorFFMPEG\output.mp4
    Cortar:
        INICIO 00:00:00
        FIM 00:00:30
```

### Saida: 

```
 ffmpeg -ss 00:00:00 -i  C:\Users\donde\Compiladores\CompiladorFFMPEG\input.mp4 -to 00:00:30 -c copy C:\Users\donde\Compiladores\CompiladorFFMPEG\output.mp4
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
    Entrada:
        C:\Users\donde\Compiladores\CompiladorFFMPEG\input.mp4
    Saida:
        C:\Users\donde\Compiladores\CompiladorFFMPEG\output.mp4
    Acelerar:
        VELOCIDADE 5
        AUDIO Y
```

### Saida: 

```
  ffmpeg -i  C:\Users\donde\Compiladores\CompiladorFFMPEG\input.mp4 -vf "setpts=0.2*PTS" -af "atempo=5" C:\Users\donde\Compiladores\CompiladorFFMPEG\outputSpeed.mp4
```


## Exemplo Legenda


### Entrada:

```
    Entrada:
        C:\Users\donde\Compiladores\CompiladorFFMPEG\input.mp4
    Saida:
        C:\Users\donde\Compiladores\CompiladorFFMPEG\output.mp4
    Legenda:
        C:\Users\donde\Compiladores\CompiladorFFMPEG\legenda.srt
```

### Saida: 

```
 ffmpeg -i C:\Users\donde\Compiladores\CompiladorFFMPEG\input.mp4 -i C:\Users\donde\Compiladores\CompiladorFFMPEG\legenda.srt -map 0 -map 1 -c copy -c:s mov_text C:\Users\donde\Compiladores\CompiladorFFMPEG\output.mp4
```

