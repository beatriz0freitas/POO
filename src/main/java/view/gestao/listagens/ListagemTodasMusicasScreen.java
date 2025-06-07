package view.gestao.listagens;

import java.util.List;

import model.Utilizador;
import model.musica.Musica;
import model.playlists.Playlist;
import view.bases.ListarEntidadesBaseScreen;
import view.simuladorUtilizador.UtilizadorMenuEntidadeScreen;

public class ListagemTodasMusicasScreen extends ListarEntidadesBaseScreen<Musica> {

    private Playlist playlist;
    private Utilizador user;

    public ListagemTodasMusicasScreen(Playlist playlist, Utilizador user) {
        super("Todas as Musicas");
        this.playlist = playlist;
        this.user = user;
    }

    @Override
    protected List<Musica> getEntidades() {
        return controller.getTodasMusicasDosAlbuns();
    }

    @Override
    protected String resumoEntidade(Musica musica) {
        return String.format("%s - %s (%d seg)", 
            musica.getNome(), 
            musica.getInterprete(), 
            musica.getDuracaoEmSegundos());
    }

    @Override
    protected void aoSelecionarEntidade(Musica entidade) {
    System.out.println(controller.adicionarMusicaNaPlaylistSeEstiverEmAlgumAlbum(entidade, playlist));
    esperarEnter();
    setNextScreen(new UtilizadorMenuEntidadeScreen(user));
    }

    
}
