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

**I**ndependent e Isolado

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

### Rules
Por exemplo no lugar do assertThat, eu posso usar:

```java
@Rule
public ErrorCollector error = new ErrorCollector();

@Test
public void meuTeste(){
	error.checkThat(5.0, is(5,0));
    error.checkThat(5.0, is(not(5.0)));
}
```
Usando as Rules, eu consegui rastrear todos os erros dentro de uma função de teste, enquanto usando o Assert, eu devo ter apenas um assert por função de teste, pois eu não consigo rastrear todos os erros, apenas o primeiro.

### Exceções

Escrevendo uma função de teste que já espera uma exceção:

**Forma elegante:**
```java
@Test(expected = Exception.class)
public void testLocacao_filmeSemEstoque() throws Exception{
    //cenario
    LocacaoService service = new LocacaoService();
    Usuario usuario = new Usuario("Usuario 1");
    Filme filme = new Filme("Filme 2", 0, 4.0);

    //acao
    service.alugarFilme(usuario, filme);
}
```

**Forma robusta:**
```java
@Test
public void testLocacao_filmeSemEstoque_2() {
    //cenario
    LocacaoService service = new LocacaoService();
    Usuario usuario = new Usuario("Usuario 1");
    Filme filme = new Filme("Filme 2", 0, 4.0);

    //acao
    try {
        service.alugarFilme(usuario, filme);
        Assert.fail("Deveria ter lancado uma excecao");
    } catch (Exception e) {
        assertThat(e.getMessage(), is("Filme sem estoque"));
    }
}
```

**Forma nova**
```java
@Rule
public ExpectedException exception = ExpectedException.none();

@Test
public void testLocacao_filmeSemEstoque_3() throws Exception {
    //cenario
    LocacaoService service = new LocacaoService();
    Usuario usuario = new Usuario("Usuario 1");
    Filme filme = new Filme("Filme 2", 1, 4.0);

    exception.expect(Exception.class);
    exception.expectMessage("Filme sem estoque");

    //acao
    service.alugarFilme(usuario, filme);
}
```

A mais recomendada é a robusta, pois atende a maior parte dos casos.

#### Anitações @Before e @After

**Before e After**
São duas anotações que me permite criar bloco de códigos a serem executados antes e depois de cada teste.
Exemplo:
```java

private LocacaoService service; //Essa instancia deve ser global para
								//ser visível em cada teste.
@Before
public void setup(){
    service = new LocacaoService();
}

@After
public void setup(){
    service = new LocacaoService();
}
```

**BeforeClass e AfterClass**

São anotações que utilizamos para executar bloco de código antes de classe ser instanciada e depois da classe ser destruida.
Essas anotações são utilizadas quando eu preciso de executar um bloco de código antes de todos os testes e depois de todos os testes.

Exemplo:
```java
@BeforeClass
public static void setupClass(){
	System.out.println("Before Class")
}
```
Devemos observar que o método com essas anotações devem ser estáticos, pois são executados antes da classe ser instanciada.

#### Ordem dos testes

Para garantir a ordem dos testes dentro de uma classe de teste, eu devo usar a seguinte anotação na classe:

```java
@FixedMethodorder(MethodSorters.NAME_ASCENDING)
```

E claro, de alguma forma, nomear as funções de testes em ordem alfabética.
O ideal é deixar os testes independentes e não necessitar de ordens.

####  Anotação @Ignore e os Assumptions

**Ignore**
O @Ignore simplesmente igrnora o teste e mostra na switch de teste que está sendo ignorado.

**Assumptions**
Devo colocar logo no início do teste:
```java
Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
```
Esse codigo faz com que esse teste somente execute aos sábados.

#### Matchers

Eu posso criar meus próprios matchers.

Para isso, devo criar o meu Matcher propriamente dito que nesse caso sera o DiaSemanaMAtcher.java:

```java
package br.ce.wcaquino.matchers;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.ce.wcaquino.utils.DataUtils;

public class DiaSemanaMatcher extends TypeSafeMatcher<Date> {

	private Integer diaSemana;
	
	public DiaSemanaMatcher(Integer diaSemana) {
		this.diaSemana = diaSemana;
	}
	
	public void describeTo(Description desc) {
		Calendar data = Calendar.getInstance();
		data.set(Calendar.DAY_OF_WEEK, diaSemana);
		String dataExtenso = data.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("pt", "BR"));
		desc.appendText(dataExtenso);
	}

	@Override
	protected boolean matchesSafely(Date data) {
		return DataUtils.verificarDiaSemana(data, diaSemana);
	}

}
```
E depois reuni-los em uma classe central que chamarei de MatchersProprios.java:
```java
package br.ce.wcaquino.matchers;

import java.util.Calendar;

public class MatchersProprios {

	public static DiaSemanaMatcher caiEm(Integer diaSemana) {
		return new DiaSemanaMatcher(diaSemana);
	}
	
	public static DiaSemanaMatcher caiNumaSegunda(){
		return new DiaSemanaMatcher(Calendar.MONDAY);
	}
}
```

por fim, para usar o meu próprio Matcher, posso fazer da seguinte forma:

```java
@Test
public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException{
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		//cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));
		
		//acao
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		//verificacao
		assertThat(retorno.getDataRetorno(), caiNumaSegunda());
		
	}
```

