package view.gestao.menus;

import java.util.List;

import view.MenuModoDeAppScreen;
import view.bases.MenuBaseScreen;
import view.bases.MenuItem;
import view.gestao.criarEntidades.CriarAlbumScreen;
import view.gestao.listagens.ListagemAlbumsScreen;

public class MenuGestaoDeAlbunsScreen extends MenuBaseScreen {



    public MenuGestaoDeAlbunsScreen() {
        super("Gestão de Álbuns");
    }

    @Override
    public  List<MenuItem> configurarMenu() {
        return List.of(
            new MenuItem(1, ">> Criar Álbum", () -> setNextScreen(new CriarAlbumScreen())),
            new MenuItem(2, ">> Listar Álbuns", () -> setNextScreen(new ListagemAlbumsScreen("Listagem Albuns"))),
            new MenuItem(0, ">> Voltar", () -> setNextScreen(new MenuModoDeAppScreen()))
        );
    }

}
