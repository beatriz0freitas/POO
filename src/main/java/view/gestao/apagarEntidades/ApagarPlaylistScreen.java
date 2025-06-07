package view.gestao.apagarEntidades;

import java.util.List;

import model.Utilizador;
import model.playlists.Playlist;
import view.bases.MenuEntidadeBaseScreen;
import view.bases.MenuItem;

import view.simuladorUtilizador.UtilizadorMenuEntidadeScreen;

public class ApagarPlaylistScreen extends MenuEntidadeBaseScreen<Playlist> {

    private Utilizador utilizador;

    public ApagarPlaylistScreen(Playlist playlits, Utilizador utilizador) {
        super("Eliminar Playlist", playlits);
        this.utilizador = utilizador;

    }



    @Override
    protected String resumoEntidade(Playlist entidade) {
        return String.format("Tem a certeza que deseja apagar a Playlist %s?", 
        entidade.getNome());
    }

    @Override
    public List<MenuItem> configurarMenu() {

    return List.of(
            new MenuItem(1, "Sim", () -> { System.out.println(controller.removerPlaylist(this.utilizador, getEntidade()));
                this.esperarEnter();
                this.setNextScreen(new UtilizadorMenuEntidadeScreen(utilizador));}),
            new MenuItem(2, "Não", () -> this.setNextScreen(new UtilizadorMenuEntidadeScreen(utilizador))),
            new MenuItem(3, "Voltar", () -> this.setNextScreen(new UtilizadorMenuEntidadeScreen(utilizador)))

        );    }
    
}
