package view.gestao.listagens;

import java.util.List;

import model.playlists.Playlist;
import view.bases.ListarEntidadesBaseScreen;

public class ListagemPlaylistScreen extends ListarEntidadesBaseScreen<Playlist> {
    

    public ListagemPlaylistScreen(String titulo) {
        super(titulo);

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

        setNextScreen(getPrevious());

    }

    
}
