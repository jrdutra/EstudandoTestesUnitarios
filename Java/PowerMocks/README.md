### PowerMocks

Power Mock é um FW 	que extende as funcionalidade de outros FW de Mock.

OBS: É preciso adicionar a dependência no POM.


Com ele eu consigo Mockar os contrutores.

Por exemplo, posso mockar o construtor da classe date, para simular dias específicos nos testes.

Exemplo:
```java
PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(29, 4, 2020));
```

Para usar isso, tenho que anotar a classe de teste em questão com `@RunWith(PowerMockRunner.class)` e também `@PrepareForTest(LocacaoService.class)`.

A classe da anotação *PrepareForTest* deve referenciar a tal classe onde estará sendo usado o construtor da classe Date.

O powerMock também permite trabalhar com métodos estáticos.

```java
Calendar calendar = Calendar.getInstance();
calendar.set(Calendar.DAY_OF_MONTH, 29);
calendar.set(Calendar.MONTH, Calendar.APRIL);
calendar.set(Calendar.YEAR, 2017);
PowerMockito.mockStatic(Calendar.class);
PowerMockito.when(Calendar.getInstance()).thenReturn(calendar);
```
O powerMock pode também mockar métodos privados. Isso pode ser feito da seguinte maneira:

```java
@Before
public void setup(){
    MockitoAnnotations.initMocks(this);
    service = PowerMockito.spy(service);
}

@Test
public void deveAlugarFilme_SemCalcularValor() throws Exception{
    //cenario
    Usuario usuario = umUsuario().agora();
    List<Filme> filmes = Arrays.asList(umFilme().agora());

    PowerMockito.doReturn(1.0).when(service, "calcularValorLocacao", filmes);

    //acao
    Locacao locacao = service.alugarFilme(usuario, filmes);

    //verificacao
    Assert.assertThat(locacao.getValor(), is(1.0));
    PowerMockito.verifyPrivate(service).invoke("calcularValorLocacao", filmes);
}
```


