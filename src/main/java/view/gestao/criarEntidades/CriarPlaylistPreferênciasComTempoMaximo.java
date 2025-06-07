package view.gestao.criarEntidades;

import java.util.Arrays;
import java.util.List;

import model.Utilizador;
import model.playlists.Playlist;
import model.playlists.PlaylistPreferenciasComTempoMaximo;
import view.bases.CampoEntidade;
import view.bases.CriarEntidadeBaseScreen;

public class CriarPlaylistPreferênciasComTempoMaximo extends CriarEntidadeBaseScreen<Playlist, Utilizador> {

    public CriarPlaylistPreferênciasComTempoMaximo(Utilizador alvo) {
        super("Playlist Preferências com Tempo Máximo", alvo);
    }

    @Override
    protected Playlist novaEntidade() {
        return new PlaylistPreferenciasComTempoMaximo();
    }

    @Override
    protected List<CampoEntidade<Playlist>> obterCampos() {
        return Arrays.asList(
            new CampoEntidade<>("Nome", input -> input, (playlist, titulo) -> playlist.setNome(titulo)),
            new CampoEntidade<>("Tempo máximo (segundos)", Integer::parseInt, (playlist, tempoMaximo) -> {
                if (playlist instanceof PlaylistPreferenciasComTempoMaximo) {
                    ((PlaylistPreferenciasComTempoMaximo) playlist).setTempoMaxEmSegundos(tempoMaximo);
                }
            })
        );
    }

    @Override
    protected void processarEntidade(Playlist entidade, Utilizador alvo) {
        System.out.println("Quer tornar a playlist pública? (s/n)");
        String resposta = scanner.nextLine().trim().toLowerCase();
        String resultado = controller.gerarPlaylistPorPreferenciasETempo(alvo.getEmail(), entidade.getNome(), ((PlaylistPreferenciasComTempoMaximo) entidade).getTempoMaxEmSegundos());
        System.out.println(resultado);
        esperarEnter();
        setNextScreen(getPrevious());
    }
}
