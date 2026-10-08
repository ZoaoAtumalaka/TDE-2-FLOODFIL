# TDE 2 – Parte 2: Flood Fill com Pilha e Fila

Aplicação de console em Java que implementa o algoritmo Flood Fill (balde de tinta) sobre uma imagem, em duas versões:

- Com pilha (LIFO), que preenche a região em profundidade;
- Com fila (FIFO), que preenche a região em largura.

---

## Funcionalidades

| Opção | Descrição |
|-------|-----------|
| 1 | Executa o Flood Fill usando pilha |
| 2 | Executa o Flood Fill usando fila |
| 3 | Escolhe (carrega) a imagem |
| 4 | Escolhe a coordenada de início `(X, Y)` |
| 0 | Encerra o programa |

## Requisitos

- JDK 17 ou superior (desenvolvido e testado com JDK 21).
- Nenhuma biblioteca externa. 

Para conferir se o JDK está instalado e no `PATH`:

```bash
javac -version
java -version
```

> É necessário o JDK (que inclui o `javac`), e não apenas o JRE.

---

## Como compilar

Abra um terminal na pasta raiz do projeto (a que contém a pasta `src`) e execute:

```bash
javac -encoding UTF-8 -d out src/Main.java
```

Isso cria a pasta `out/` com os arquivos `.class` do projeto.

## Como executar

> Importante: os quadros gerados (`pilha_passo_XXXX.png` e `fila_passo_XXXX.png`) são salvos na pasta em que o comando `java` é executado. Execute a partir de uma pasta dedicada (por exemplo, a raiz do projeto ou uma pasta `saida/`) para não misturar os arquivos com outros.

Na pasta raiz do projeto:

```bash
java -cp out Main
```

### Atalho: compilar e executar em um só comando

Com o Java 11+ também é possível executar direto, sem gerar a pasta `out`:

```bash
java src/Main.java
```

## Como usar

O menu exibido é:

```
--- MENU FLOOD FILL ---
1. Executar com pilha
2. Executar com fila
3. Escolher imagem
4. Escolher coordenada de inicio
0. Encerrar
Opção:
```

Antes de executar o preenchimento (opções 1 ou 2), é obrigatório carregar uma imagem (opção 3) e definir a coordenada inicial (opção 4). Caso contrário, o programa avisa "Carregue uma imagem primeiro." ou "Defina a coordenada inicial primeiro.".

### Passo a passo

1. Carregar a imagem (opção 3) – informe o caminho do arquivo:

```
Opção: 3
Caminho da imagem: /home/joao/imagens/desenho.png
Imagem carregada.
```

Exemplo de caminho no Windows: `C:\Users\joao\imagens\desenho.png`

2. Definir a coordenada inicial (opção 4):

```
Opção: 4
Coordenada X: 50
Coordenada Y: 50
Coordenada definida: (50, 50)
```

- `X` é a coluna (da esquerda para a direita) e `Y` é a linha (de cima para baixo).
- A origem `(0, 0)` é o canto superior esquerdo da imagem.
- A cor desse pixel é a "cor original": todos os pixels conectados a ele (vizinhos acima, abaixo, à esquerda e à direita) que tenham exatamente essa cor serão pintados.

3. Executar (opção 1 ou 2) – informe a nova cor em RGB, com valores de 0 a 255 separados por espaço:

```
Opção: 1
Digite a nova cor RGB (R G B):
255 0 0
Executando com pilha...
Imagem salva: /home/joao/projeto/pilha_passo_0011.png
Concluído. Pixels alterados: 3249
```

4. Encerrar (opção 0).

### Arquivos gerados

| Algoritmo | Arquivos |
|-----------|----------|
| Pilha | `pilha_passo_0001.png`, `pilha_passo_0002.png`, … |
| Fila  | `fila_passo_0001.png`, `fila_passo_0002.png`, … |

- Um quadro é salvo a cada 300 pixels preenchidos, e o último quadro (o resultado final) é salvo ao término do algoritmo.
- Os arquivos sempre são gravados em formato PNG, mesmo que a imagem original seja de outro formato. A imagem original não é alterada em disco.
- Quanto maior a região preenchida, mais quadros são gerados. Imagens grandes podem produzir centenas de arquivos.
- Ao executar o mesmo algoritmo novamente, os quadros anteriores com o mesmo nome são sobrescritos. Mova ou renomeie os arquivos se quiser guardá-los.

## Observações e limitações

- Prefira imagens PNG com áreas de cor sólida. O algoritmo só preenche pixels com cor exatamente igual à do pixel inicial; em imagens JPG, a compressão cria pequenas variações de cor e a região preenchida pode ficar muito menor que o esperado.
- Se a coordenada estiver fora da imagem, o programa exibe "Coordenada fora da imagem.". Se a nova cor for igual à cor original do pixel, exibe "Cor igual à original." Em ambos os casos nada é alterado.
- Se o caminho da imagem estiver errado ou o arquivo não for uma imagem válida, o programa exibe "Erro ao abrir arquivo." ou "Erro ao carregar imagem.".
- Digite apenas números inteiros no menu, nas coordenadas e nas cores. Valores de cor fora do intervalo 0–255 ou texto no lugar de números fazem o programa encerrar com erro.
