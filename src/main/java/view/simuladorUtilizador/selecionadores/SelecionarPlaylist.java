package view.simuladorUtilizador.selecionadores;

import java.util.List;
import java.util.function.Consumer;

import model.playlists.Playlist;
import view.bases.BaseScreen;
import view.bases.SelecionarEntidadeDeListagemScreen;

public class SelecionarPlaylist extends SelecionarEntidadeDeListagemScreen<Playlist> {

    public SelecionarPlaylist(Consumer<Playlist> acao, BaseScreen nextScreen) {
        super("Selecionar Playlist", acao, nextScreen);
    }

    @Override
    protected List<Playlist> getEntidades() {
        return controller.getPlaylitsPublicas();
    }

    @Override
    protected String resumoEntidade(Playlist playlist) {
        return playlist.getNome() + " - " + playlist.getTipoDeColecao();
    }
    
}
