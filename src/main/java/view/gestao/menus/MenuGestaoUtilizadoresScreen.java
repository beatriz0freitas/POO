package view.gestao.menus;

import java.util.List;

import view.bases.MenuBaseScreen;
import view.bases.MenuItem;
import view.gestao.criarEntidades.CriarUtilizadorScreen;
import view.gestao.listagens.ListagemUtilizadoresScreen;

public class MenuGestaoUtilizadoresScreen extends MenuBaseScreen{


    public MenuGestaoUtilizadoresScreen() {
        super("Gestão de Utilizadores");
    }

    @Override
    public List<MenuItem> configurarMenu() {
        return List.of(
            new MenuItem(1, ">> Criar Utilizador", () -> this.setNextScreen(new CriarUtilizadorScreen())),
            new MenuItem(2, ">> Listar Utilizadores", () -> this.setNextScreen(new ListagemUtilizadoresScreen(getTitulo()))),
            new MenuItem(0, ">> Voltar", () -> this.setNextScreen(new MenuPrincipalScreen()))
        );
    }
}
