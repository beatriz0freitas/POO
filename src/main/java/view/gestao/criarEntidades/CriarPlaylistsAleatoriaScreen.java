package view.gestao.criarEntidades;

import java.util.Arrays;
import java.util.List;

import model.Utilizador;
import model.playlists.Playlist;
import model.playlists.PlaylistAleatoria;
import view.bases.CampoEntidade;
import view.bases.CriarEntidadeBaseScreen;

public class CriarPlaylistsAleatoriaScreen extends CriarEntidadeBaseScreen<Playlist, Utilizador> {

    public CriarPlaylistsAleatoriaScreen(Utilizador utilizador) {
        super("Criar Playlist", utilizador);
    }

    @Override
    protected Playlist novaEntidade() {
        return new PlaylistAleatoria();
    }

    @Override
    protected List<CampoEntidade<Playlist>> obterCampos() {
        return Arrays.asList(
            new CampoEntidade<>("Nome", input -> input, (playlist, titulo) -> playlist.setNome(titulo))
        );
    }

    @Override
    protected void processarEntidade(Playlist entidade, Utilizador utilizador) {
        System.out.println("Quer tornar a playlist pública? (s/n)");
        String resposta = scanner.nextLine().trim().toLowerCase();
        System.out.println(controller.adicionarPlaylistAleatoria(utilizador, entidade, resposta));
        this.esperarEnter();
        setNextScreen(getPrevious());
    }
    

}
