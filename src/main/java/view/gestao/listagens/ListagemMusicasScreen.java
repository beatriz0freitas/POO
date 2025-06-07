package view.gestao.listagens;

import java.util.List;

import model.Album;
import model.musica.Musica;
import view.bases.ListarEntidadesBaseScreen;
import view.gestao.menus.MenuMusica;

public class ListagemMusicasScreen extends ListarEntidadesBaseScreen<Musica> {

    private Album album;

    public ListagemMusicasScreen(Album album) {
        super("Músicas do Álbum: " + album.getNome());
        this.album = album;
    }

    @Override
    protected List<Musica> getEntidades() {
        return album.getMusicas(); // ou album.getMusicas() se já estiver clonando corretamente
    }

    @Override
    protected String resumoEntidade(Musica musica) {
        return String.format("%s - %s (%d seg)", 
            musica.getNome(), 
            musica.getInterprete(), 
            musica.getDuracaoEmSegundos());
    }

    @Override
    protected void aoSelecionarEntidade(Musica musica) {
        
        setNextScreen(new MenuMusica(musica));
    }
}
    