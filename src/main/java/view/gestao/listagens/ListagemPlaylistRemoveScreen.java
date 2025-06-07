package view.gestao.listagens;

import java.util.List;

import model.Utilizador;
import model.playlists.Playlist;
import view.bases.ListarEntidadesBaseScreen;
import view.gestao.apagarEntidades.ApagarPlaylistScreen;

public class ListagemPlaylistRemoveScreen extends ListarEntidadesBaseScreen<Playlist> {
    

    private Utilizador utilizador;

    public ListagemPlaylistRemoveScreen(String titulo, Utilizador utilizador) {
        super(titulo);
        this.utilizador = utilizador;

    }


    @Override
    protected List<Playlist> getEntidades() {
        return this.controller.getPlaylitsPublicas();
    }

    @Override
    protected String resumoEntidade(Playlist entidade) {
        return String.format("%s", entidade.getNome());
    }

    @Override
    protected void aoSelecionarEntidade(Playlist entidade) {
        System.out.println("Playlist selecionado: " + entidade.getNome());

        esperarEnter();

        setNextScreen(new ApagarPlaylistScreen(entidade, utilizador));
        //setNextScreen(new UtilizadorMenuEntidadeScreen(utilizador));

    }

    
}
