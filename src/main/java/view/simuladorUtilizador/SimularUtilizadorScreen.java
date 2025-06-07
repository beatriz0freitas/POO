package view.simuladorUtilizador;

import exceptions.UtilizadorNaoExisteException;
import model.Utilizador;
import view.MenuModoDeAppScreen;
import view.bases.BaseScreen;


public class SimularUtilizadorScreen extends BaseScreen {

    public SimularUtilizadorScreen() {
        super("Simular Utilizador");
    }


    @Override
    public void run() {
        System.out.print("Introduza o email do utilizador: ");
        String email = scanner.nextLine();
        try {
            Utilizador utilizador = controller.getUtilizador(email);
            UtilizadorMenuEntidadeScreen utilizadorMenu = new UtilizadorMenuEntidadeScreen(utilizador);
            setNextScreen(utilizadorMenu);

        } catch (UtilizadorNaoExisteException e) {
            System.out.println(e.getMessage());
            esperarEnter();
            setNextScreen(new MenuModoDeAppScreen());
        }
    }

}