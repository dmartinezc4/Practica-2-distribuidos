# Calculadora

Esta calculadora hace operaciones para numeros de una cifra.

## Instrucciones

1º Compilar IDL

2º Compilar server y cliente con las instrucciones:
idlj -fclient Calc.idl
idlj -fserver Calc.idl

3º runear el código
orbd -ORBInitialPort 1050 -ORBDInitialHost localhost
java CalcServer -ORBInitialPort 1050 -ORBDInitialHost localhost
java CalcClient -ORBInitialPort 1050 -ORBDInitialHost localhost

4º Una vez funcione a la hora de meter las operaciones las meteremos de manera que pongamos símbolo, numero 1 y numero 2.
Si queremos hacer 4 + 5 lo que teclearemos será: +45.

## Operaciones

Las principales que estaban en el github eran: suma ( + ), resta ( - ), división ( / ) y  multiplicación ( * ). 

Las añadidas son: comprobar si es par ( % ), elevar a una potencia ( ^ ), hacer el factorial ( ! ) y realizar la raíz cuadrada ( # ).

### Even 

Funcionará cuando uno de los miembros sea 0; Devolviendo 1 si es par y 0 si es impar. (%60  || %07)  <- ejemplos
En caso de poner dos números que no sean 0 se devolverá -1337 que es un número de error. (%68 || %36) 
En caso de que los dos sean 0 mandará un 11 indicando que ambos 0s son par.

### Power

El primer número se considerará la base y el siguiente su exponente. Si queremos hacer 4^3 tendremos que poner (^43).

### Factorial
Funcionará cuando uno de los miembros sea 0; devolviendo el resultado del factorial. Si queremos saber el factorial de 7 tenderemos que poner (!70 || !07).
En caso de que uno de los miembros sea 0 se retornará -1337 como resultado de error. (!89 || !54)

### Square Root

Funcionará cuando uno de los miembros sea 0; devolviendo el resultado de la raíz casteado a entero. Si queremos saber la raíz de 6 tenderemos que poner (#60 || #06).
En caso de que uno de los miembros sea 0 se retornará -1337 como resultado de error. (#12 || #94)
