# **Sucury-lang**

É um interpretador baseado em Java, desenvolvido na disciplina de Programação 1 da Universidade Federal da Fronteira Sul (UFFS).

**Desenvolvida pelos Estudantes Alisson Peloso, Eduardo Lazaretti, Guilherme Graeff e Stefani Meneghetti.**

**Extenção:** .sy

Sucury-lang é flexível em relação aos espaçamentos e identação.

***
***
## **Sumário**
1. [**Variáveis**](#variáveis) 
   - [**int**:](#int)
   - [**double**:](#double)
   - [**float**:](#float)
   - [**string**:](#string)
2. [**Operações**](#operações)
3. [**Entrada e Saída**](#entrada-e-saída)
   - [**Scan:**](#scan)
   - [**Print:**](#print)
4. [**Condições**](#condições)
5. [**Laços**](#laços)
   - [**while**:](#while)
   - [**for**:](#for)
6. [**Controlador de Fluxo**](#controlador-de-fluxo)
***
***

## **Variáveis**
Para declaração de variáveis, utilizamos a síntaxe:
```
tipo nome
```
* Declaração de uma variável sem atribuir o seu valor. Nesse caso, recebe o valor *zero* se númerico, e uma *string vazia* se a variável é do tipo string.
```
tipo nome = valor
```
* Declaração de uma variável com atribuição de valor.
* O valor também pode ser uma expressão do tipo *valorA + valorB*.

***

### **int**:
Tipo de variável utilizada para armazenar números inteiros. Exemplos:

```
int var
var = 5 + 5 
int var1 = 7897
int var2 = var + var1
```

***

### **double**: 
Tipo de variável utilizada para armazenar números com pontos flutuantes com até 8 bytes. Exemplos:

```
double var
var = 5.5 + 5.987 
double var1 = 7897.5
double var2 = var + var1
```

***

### **float**:
Tipo de variável utilizada para armazenar números com pontos flutuantes com até 4 bytes. Exemplos:

```
double var
var = 5.5 + 5.987 
double var1 = 7897.5
double var2 = var + var1
```

***

### **string**: 
Tipo de variável utilizada para armazenar *palavras*. Exemplos:

```
string var
var = 'Hello ' + 'World' 
string var1 = 'My first program using Sucury :D'
string var2 = var + var1
```

***
***

## **Operações**
Podemos utilizar operações como **soma (+)**, **subtração (-)**, **multiplicação (*)**, **divisão (/)** e **resto (%)**.

Não há limite de operações em uma expressão.

<center>

| Operador | Ordem de Prioridade |
|:--------:|:-------------------:|
|    ()    |          1º         |
|    *     |          2º         |
|    /     |          2º         |
|    %     |          2º         |
|    +     |          3º         |
|    -     |          3º         |
</center>

Para utilizarmos um número negativo, utilizamos () para dar prioridade para resolução da parte negativa inicialmente.

Exemplo:
```
int soma = (-550) + 555
```
Em Sucury, podemos também utilizar os operadores **++** para somar 1 ao valor da variável e **--** para subtrair 1 do valor da variável.

Exemplo:
```
int num = 5
num++
num--
```
Também podemos utilizar os operadores (**+=**), (**-=**), (***=**), (**/=**).

Exemplos:
```
int num = 5
num += 5
```
- **num** recebe o valor 10 (num + 5);

```
int num = 5
num -= 5
```
- **num** recebe o valor 0 (num - 5);

```
int num = 5
num *= 5
```
- **num** recebe o valor 25 (num * 5);
  
```
int num = 5
num /= 5
```
- **num** recebe o valor 1 (num / 5);

***
***

## **Entrada e Saída**
### **scan:**
Para entradas do teclado utilizamos o **scan**:

Síntaxe:

```
int var
scan(var)
```
- **OBS:** A variável precisa ser declarada anteriorimente

Exemplo de utilização:

```
int i 
scan(i)
```

***
### **print:**

Para saídas na tela utilizamos o **print** ou **println**. Ambos funcionam da mesma forma, porém o **println** adiciona um "\n" no final da linha automaticamente. 
Para imprimir uma variável, podemos utilizar **print(variável)**.

Síntaxe:

```
print(texto)
println(texto)
```
ou
```
print(variável)
println(variável)
```
- **OBS:** A variável precisa ser declarada anteriorimente
  
Para imprimir mais de uma variável ou variáveis juntamente com um texto, utiliza-se **{}** na síntaxe:

```
print('{} {}', var1, var2)
println('texto {} {} {}', var1, var2, var3)
```

Exemplo de utilização:
```
int idade = 5
print('Oi! Eu tenho {} anos\n', idade)
println(idade)
println('boi')
```
Saída:
```
Oi! Eu tenho 5 anos
5
boi
```

***
***

## **Condições**
Sucury também pode utilizar de operadores condicionais.

Sendo eles: **igual** (**==**), **diferente** (**!=**), **menor ou igual** (**<=**), maior ou igual (**>=**), **menor que** (**<**) e **maior que** (**>**).

Também podemos verificar mais de uma condição utilizando operadores lógicos **and &&** e **or ||**. Além disso, podemos negar (inverter) a condição utilizando o **not !**.

```
if(!5 == 5 || 5 == 5)
    println('true')
endif
```

***
***


## **Laços**
Sucury utiliza duas estruturas para realizar repetições de blocos no código. São elas: **while** e **for**.

***

### **while**:
Síntaxe:

```
while(condição)
    Bloco
endwhile
```

- Repete o bloco enquanto a condição for verdadeira.

Exemplo de utilização:

```
int i = 0
while(i < 5)
    print('oi boi\n')
    i++
endwhile
```
Saída:
```
oi boi
oi boi
oi boi
oi boi
oi boi
```

***

### **for**:
Síntaxe:

```
for(variável de controle;condição;incremento)
    Bloco
endfor
```

- A variável de controle pode ser declarada anteriormente ou dentro da própria chamada (caso declarado na chamada, a váriavel só fica disponível no escopo do for);
- O bloco é repetido enquanto a condição for verdadeira; 
- O incremento é realizado ao final de cada execução do bloco;

Exemplo de utilização:

```
print('1 Elefante incomoda muita gente.\n')
int i 
for(i = 2;i <= 5; i++)
    print('{} Elefantes ', i)
    for(int j = 0; j < i; j++)
        print('incomodam ');
    endfor
    print('muito mais.\n')
endfor
```
Saída:
```
1 Elefante incomoda muita gente.
2 Elefantes incomodam incomodam muito mais.
3 Elefantes incomodam incomodam incomodam muito mais.
4 Elefantes incomodam incomodam incomodam incomodam muito mais.
5 Elefantes incomodam incomodam incomodam incomodam incomodam muito mais.
```

***
***
## **Controlador de Fluxo**
Sucury utiliza os controladores de fluxo **if** e **else**

Síntaxe:

```
if(condição)
    bloco
endif
```
- Se a condição for verdadeira, o bloco é executado. Se não, o programa não executa o bloco.
```
if(condição)
    bloco 1 
else
    bloco 2 
endif
```
- Se a condição for verdadeira, o bloco 1 é executado. Se não, o bloco 2 é executado.

Exemplo de utilização:
```
int numero = 5
if(numero <= 5)
    print('é menor ou igual a 5\n')
else
    print('não é menor ou igual a 5\n')
endif
```
Saída:
```
é menor ou igual a 5
```