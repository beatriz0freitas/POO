package view.gestao.menus;
import java.util.List;

import view.MenuModoDeAppScreen;
import view.bases.MenuBaseScreen;
import view.bases.MenuItem;
import view.gestao.listagens.ListagemEstatisticas;

public class MenuPrincipalScreen extends MenuBaseScreen {


    public MenuPrincipalScreen() {
        super("MENU PRINCIPAL");
    }

    @Override
    public List<MenuItem> configurarMenu() {
        return List.of(
            new MenuItem(1, ">> Gestão de Utilizadores", () -> this.setNextScreen(new MenuGestaoUtilizadoresScreen())),
            new MenuItem(2, ">> Gestão de Álbuns", () -> this.setNextScreen(new MenuGestaoDeAlbunsScreen())),
            new MenuItem(3, ">> Gestão de Playlists", () -> this.setNextScreen(new MenuGestaoDePlaylistsScreen())),
            new MenuItem(4, "<< Estatísticas", () -> this.setNextScreen(new ListagemEstatisticas())),
            new MenuItem(0, "Voltar", () -> this.setNextScreen(new MenuModoDeAppScreen()))
        );
    }
}
