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