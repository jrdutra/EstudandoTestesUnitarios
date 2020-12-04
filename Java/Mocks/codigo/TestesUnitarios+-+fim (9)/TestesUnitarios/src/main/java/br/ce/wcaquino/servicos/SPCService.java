package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Usuario;

public interface SPCService {

	public boolean possuiNegativacao(Usuario usuario) throws Exception;
}
