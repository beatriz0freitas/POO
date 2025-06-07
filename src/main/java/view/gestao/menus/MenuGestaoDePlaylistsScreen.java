package view.gestao.menus;

import java.util.List;

import view.bases.MenuBaseScreen;
import view.bases.MenuItem;
import view.gestao.listagens.ListagemPlaylistScreen;

public class MenuGestaoDePlaylistsScreen extends MenuBaseScreen {

    public MenuGestaoDePlaylistsScreen() {
        super("Gestão de Playlists");
    }

    @Override
    public List<MenuItem> configurarMenu() {
        return List.of(
            new MenuItem(1, "<< Listar Playlist", () -> this.setNextScreen(new ListagemPlaylistScreen(getTitulo()))),
            new MenuItem(0, ">> Voltar", () -> this.setNextScreen(new MenuPrincipalScreen()))
        );
    }
}
        
