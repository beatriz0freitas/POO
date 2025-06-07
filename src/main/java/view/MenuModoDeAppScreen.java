package view;

import java.util.List;

import utils.EstadodaAPP;
import view.bases.MenuBaseScreen;
import view.bases.MenuItem;
import view.gestao.menus.MenuPrincipalScreen;
import view.simuladorUtilizador.SimularUtilizadorScreen;

public class MenuModoDeAppScreen extends MenuBaseScreen {


    public MenuModoDeAppScreen() {
        super("Modo de App");
    }

    @Override
    public List<MenuItem> configurarMenu() {
        return List.of(
            new MenuItem(1, ">> Modo Gestão", () -> this.setNextScreen(new MenuPrincipalScreen())),
            new MenuItem(2, ">> Simulador de utilizador", () -> this.setNextScreen(new SimularUtilizadorScreen())),
            new MenuItem(0, ">> Sair", () -> {
    System.out.println("A guardar os dados...");    

    // Garante que a pasta data existe
    new java.io.File("data").mkdirs();

    controller.salvarDados();

    System.out.println("Dados guardados com sucesso. A sair da aplicação...");
    System.exit(0);
})
            
        );
    }
}
