package  view.bases;

import java.util.Scanner;

import controller.SpotifUMController;
import view.SpotifUMView;

public abstract class BaseScreen {

    private BaseScreen previous;
    private final String titulo;
    protected SpotifUMController controller;
    private  SpotifUMView view;
    protected static final Scanner scanner = new Scanner(System.in);

    public BaseScreen(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public BaseScreen getPrevious() {
        return this.previous;
    }

    public void setPrevious(BaseScreen previous) {
        this.previous = previous;
    }

    public void setController(SpotifUMController controller) {
        this.controller = controller;
    }

    public void setView(SpotifUMView view) {
        this.view = view;
    }

    public void setNextScreen(BaseScreen nextScreen) {
        nextScreen.setController(this.controller);
        nextScreen.setView(this.view);
        nextScreen.setPrevious(this);
        view.setScreen(nextScreen);
    }

    public void render(){
        System.out.println("=".repeat(this.titulo.length()));
        System.out.println(this.titulo);
        System.out.println("=".repeat(this.titulo.length()));
        System.out.println();
        this.run();
    }


    public void esperarEnter(){
        System.out.println("Pressione Enter para continuar...");
        scanner.nextLine();
    }


    public abstract void run();
}
