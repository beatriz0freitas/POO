package view.gestao.menus;

import java.util.List;

import view.bases.MenuBaseScreen;
import view.bases.MenuItem;
import view.gestao.listagens.ListagemUtilizadoresScreen;

public class MenuListagensUtilizadoresScreen extends MenuBaseScreen {

    public MenuListagensUtilizadoresScreen() {
        super("Listagens de Utilizadores");
    }

    @Override
    public List<MenuItem> configurarMenu() {
        return List.of(
            new MenuItem(1, ">> Listar todos os utilizadores", () -> this.setNextScreen(new ListagemUtilizadoresScreen("Todos os utilizadores"))),
            new MenuItem(2,"<< Listar utilizadores com plano premium", () -> System.out.println("Não implementado")),
            new MenuItem(3, "<< Listar utilizadores com plano free", () -> System.out.println("Não implementado")),
            new MenuItem(0, ">> Voltar", () -> this.setNextScreen(new MenuGestaoUtilizadoresScreen()))
        );
    }
     
}
