package view.gestao.criarEntidades;

import java.util.Arrays;
import java.util.List;

import model.Utilizador;
import model.playlists.Playlist;
import model.playlists.PlaylistPersonalizada;
import view.bases.CampoEntidade;
import view.bases.CriarEntidadeBaseScreen;
import view.gestao.listagens.ListagemTodasMusicasScreen;

public class CriarPlaylistPersonalizada extends CriarEntidadeBaseScreen<Playlist, Utilizador> {

    public CriarPlaylistPersonalizada(Utilizador alvo) {
        super("Criar Playlist Personalizada", alvo);
    }

    @Override
    protected Playlist novaEntidade() {
        return new PlaylistPersonalizada();
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
        this.esperarEnter();
        setNextScreen(new ListagemTodasMusicasScreen(entidade, alvo));}
    
}
