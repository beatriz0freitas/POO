package  view;

import  controller.SpotifUMController;
import view.bases.BaseScreen;

public class SpotifUMView {

    private SpotifUMController controller;
    private BaseScreen currentScreen;

    public SpotifUMView(SpotifUMController controller) {
        this.controller = controller;
    }

    public void setScreen(BaseScreen screen) {
        try {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            this.currentScreen = screen;
            this.currentScreen.render(); 
        } catch (Exception e) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Ocorreu um erro inexperado, por favor contactar suporte: " + e.getMessage());
            System.out.println("Pressione qualquer tecla para continuar...");
            screen.esperarEnter();
            this.init();
            }

    }

    public void init(){
        BaseScreen mainMenu = new MenuModoDeAppScreen();
        mainMenu.setController(this.controller);
        mainMenu.setView(this);
        this.setScreen(mainMenu);
    }

}
