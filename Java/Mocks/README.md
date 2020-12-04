### Mocks

Um teste unitário não deve ter dependẽncia externa, caso contrário, é um teste de integração.

**O framework Mockito**

Esse FW é capaz de gerar objetos Mocks, onde nos objetos Mocks eu sou capaz de definir os comportamentos dinâmicos.

Devemos adicionar a dependencia do POM.xml a dependênca do Mockito.

Para criar um objeto Mockado devo fazer com o seguinte código:

```java
LotacaoDAO dao = Mockito.mock(LotacaoDAO.class);
```
O que o Mock faz é alterar o comportamento padrão os métodos, se é um método que retorna um VOID, o método do obj mockado não faz nada, se retorna um inteiro, ele faz retornar m zero, se retorna uma string, ele faz retornar uma string vazia, etc etc. 
Mas, podemos alterar o comportamento padrão de um Objeto MOckado.

Um exemplo dentro de uma classe de testes:

```java
private SPCService spc;

@Before
public void setup(){
    service = new LocacaoService();
    dao = Mockito.mock(LocacaoDAO.class);
    service.setLocacaoDAO(dao);
    spc = Mockito.mock(SPCService.class);
    service.setSPCService(spc);
}

@Test
public void naoDeveAlugarFilmeParaNegativadoSPC() throws FilmeSemEstoqueException, LocadoraException{
    //cenario
    Usuario usuario = umUsuario().agora();
    Usuario usuario2 = umUsuario().comNome("Usuario 2").agora();
    List<Filme> filmes = Arrays.asList(umFilme().agora());

    when(spc.possuiNegativacao(usuario)).thenReturn(true);

    exception.expect(LocadoraException.class);
    exception.expectMessage("Usuário Negativado");

    //acao
    service.alugarFilme(usuario, filmes);
}

```

Posso também verificar se um determinado método foi chamado com o verify:

```java
private SPCService spc;

@Test
public void naoDeveAlugarFilmeParaNegativadoSPC(){
	verify(spc).possuiNegativação(usuario);
}
```
