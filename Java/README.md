## Anotações Sobre Testes Unitários Com Java

Ao realizar um teste eu sempre vou precisar desses três itens:

**Cenário:**

É onde as variáveis são inicializadas.

**Ação:**

É onde vamos invocar os métodos
	
**Validação:**

É onde vamos coletar os resultados da ação para o cenário especificado.

### O princípio FIRST

**F**ast

**I**ndependent

**R**epeatable

**S**elf-Verifying

**T**imely

**Fast:** Um teste deve ser rápido.

**Independent:** Um teste deve ser independente.

**Repeatable:** Um teste deve ser repetitível.

**Self-verifying:** Um teste deve se auto verificar.

**Timely:** Um teste deve ser oportuno.

### Assetivas

1. AssertTrue
2. AssetFalse
3. AssertEquals (Para floats, tenho que passar um delta de comparação)
4. AssertNotEquals
4. AssertSame (Verifica se os objetos são da mesma instância)
5. AssertnotSame
5. AssetNull
6. AssetNotNull
7. AssertThat

Posso colocar uma String para mencionar que erro foi nos asserts. Por exemplo:

```java
Assert.assertEquals("Erro de comparação", 1, 2);
```

Nos assets, quando com dois valores, o primeiro valor é o esperado e o segundo o aual. É importante colocar nos locais corretos pois quando o teste não passa, é mais fácil de ler o erro.

**AssertThat**
No AcertThat o primeiro valor é o valor atual e o segundo o valor esperado.

Exemplo de uso:

```java
Assert.assertThat(5.0, CoreMatchers.is(5,0));
```
Com as devidas impotações de funções estáticas do CoreMatchers, posso escrever da seguinte forma:

```java
assertThat(5.0, is(equalTo(5.0)));
assertThat(5.0, is(not(5.0)));
```