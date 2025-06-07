package view.simuladorUtilizador.selecionadores;

import java.util.List;
import java.util.function.Consumer;

import model.Album;
import view.bases.BaseScreen;
import view.bases.SelecionarEntidadeDeListagemScreen;

public class SelecionarAlbum extends SelecionarEntidadeDeListagemScreen<Album> {

    public SelecionarAlbum(Consumer<Album> acao, BaseScreen nextScreen) {
        super("Selecionar Álbum", acao, nextScreen);
    }

    @Override
    protected List<Album> getEntidades() {
        return controller.getAlbums();
    }

    @Override
    protected String resumoEntidade(Album album) {
        return album.getNome() + " - " + album.getArtista();
    }
}