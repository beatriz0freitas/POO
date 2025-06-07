package view.gestao.criarEntidades;

import java.util.Arrays;
import java.util.List;

import model.Utilizador;
import model.playlists.Playlist;
import model.playlists.PlaylistPorGeneroETempo;
import model.playlists.PlaylistPreferenciasComTempoMaximo;
import view.bases.CampoEntidade;
import view.bases.CriarEntidadeBaseScreen;

public class CriarPlaylistPorGeneroETempo extends CriarEntidadeBaseScreen<Playlist, Utilizador> {


    
    public CriarPlaylistPorGeneroETempo(Utilizador alvo) {
        super("Criar Playlist Por Genero e Tempo", alvo);

    }

    @Override
    protected Playlist novaEntidade() {
        return new PlaylistPorGeneroETempo();
    }

    @Override
    protected List<CampoEntidade<Playlist>> obterCampos() {
        return Arrays.asList(
            new CampoEntidade<>("Nome", input -> input, (playlist, titulo) -> playlist.setNome(titulo)),
            new CampoEntidade<>("Genero", input -> input, (playlist, genero) -> {
                if (playlist instanceof PlaylistPorGeneroETempo) {
                    ((PlaylistPorGeneroETempo) playlist).setGenero(genero);
                }
            }),
            new CampoEntidade<>("Duração Máxima (segundos)", Integer::parseInt, (playlist, duracao) -> {
                if (playlist instanceof PlaylistPorGeneroETempo) {
                    ((PlaylistPorGeneroETempo) playlist).setDuracaoMax(duracao);
                }
            })
        );
    }

    @Override
    protected void processarEntidade(Playlist entidade, Utilizador alvo) {

        String resultado = controller.gerarPlaylistPorTempoeGenero(alvo.getEmail(), entidade.getNome(), ((PlaylistPorGeneroETempo) entidade).getDuracaoMax(), ((PlaylistPorGeneroETempo) entidade).getGenero());
        System.out.println(resultado);
        this.esperarEnter();
        setNextScreen(getPrevious());
    }
    
}
