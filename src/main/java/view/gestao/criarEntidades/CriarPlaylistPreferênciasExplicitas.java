
package view.gestao.criarEntidades;

import java.util.Arrays;
import java.util.List;

import model.Utilizador;
import model.playlists.Playlist;
import model.playlists.PlaylistPreferenciasExplicitas;
import view.bases.CampoEntidade;
import view.bases.CriarEntidadeBaseScreen;

public class CriarPlaylistPreferênciasExplicitas extends CriarEntidadeBaseScreen<Playlist, Utilizador> {

    public CriarPlaylistPreferênciasExplicitas(Utilizador alvo) {
        super("Playlist Favoritos Explícitos", alvo);
    }

    @Override
    protected Playlist novaEntidade() {
        return new PlaylistPreferenciasExplicitas(controller.getFavoritosDoUtilizador(getAlvo()));
    }


    @Override
    protected List<CampoEntidade<Playlist>> obterCampos() {
        return Arrays.asList(
            new CampoEntidade<>("Nome", input -> input, (playlist, titulo) -> playlist.setNome(titulo))
        );
    }
   
    @Override
    protected void processarEntidade(Playlist entidade, Utilizador alvo) {
        System.out.println("Quer tornar a playlist pública? (s/n)");
        String resposta = scanner.nextLine().trim().toLowerCase();
        System.out.println(controller.adicionarPlaylist(alvo, entidade, resposta));
        esperarEnter();
        setNextScreen(getPrevious());
    }

}
