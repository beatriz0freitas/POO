package view.gestao.listagens;

import java.util.List;

import model.Utilizador;
import view.bases.ListarEntidadesBaseScreen;
import view.gestao.menus.MenuUtilizador;

public class ListagemUtilizadoresScreen extends ListarEntidadesBaseScreen<Utilizador> {

    public ListagemUtilizadoresScreen(String titulo) {
        super(titulo);
    }

    @Override
    protected List<Utilizador> getEntidades() {
        return this.controller.getUtilizadores();
    }

    @Override
    protected String resumoEntidade(Utilizador entidade) {
        return String.format("%s (%s)", entidade.getNome(), entidade.getEmail());
    }

    @Override
    protected void aoSelecionarEntidade(Utilizador entidade) {
        
        setNextScreen(new MenuUtilizador(entidade));

    }

}

