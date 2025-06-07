package view.simuladorUtilizador;

import java.util.ArrayList;
import java.util.List;

import exceptions.UtilizadorNaoExisteException;
import model.Utilizador;
import view.MenuModoDeAppScreen;
import view.bases.MenuEntidadeBaseScreen;
import view.bases.MenuItem;
import view.gestao.listagens.ListagemPlaylistRemoveScreen;

public class UtilizadorMenuEntidadeScreen extends MenuEntidadeBaseScreen<Utilizador> {

    public UtilizadorMenuEntidadeScreen(Utilizador utilizador) {
        super("App do Utilizador", utilizador);
    }

    @Override
    public Utilizador getEntidade() {
        try {
            return controller.getUtilizador(super.getEntidade().getEmail());
        } catch (UtilizadorNaoExisteException e) {
            return super.getEntidade();
        }
    }

    @Override
    protected String resumoEntidade(Utilizador entidade) {
        return String.format("Utilizador: %s\nEmail: %s", entidade.getNome(), entidade.getEmail());
    }

    @Override
    public List<MenuItem> configurarMenu() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(1, "<< Reproduzir Músicas", () -> 
            setNextScreen(new ReproduzirMusicasEntidadeScreen(this.getEntidade()))
        ));
        menuItems.add(new MenuItem(2, "<< Criar Playlist", () -> 
            setNextScreen(new MenuGestaoPlaylistsUtilizadores(getEntidade()))
        ));
        menuItems.add(new MenuItem(3, "<< Apagar Playlists", () -> 
            setNextScreen(new ListagemPlaylistRemoveScreen("Apagar Playlist", getEntidade()))
        ));
        menuItems.add(new MenuItem(4, "<< Gerir Biblioteca", () -> 
            setNextScreen(new MenuGerirBibliotecaEntidade(getEntidade()))
        ));
        menuItems.add(new MenuItem(0, ">> Voltar", () -> 
            setNextScreen(new MenuModoDeAppScreen())
        ));
        return menuItems;
    }

}
