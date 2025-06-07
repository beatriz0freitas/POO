package view.bases;

import java.util.*;

public abstract class MenuBaseScreen extends BaseScreen {

   
    private final Map<Integer, MenuItem> menuItems = new HashMap<>();

    public MenuBaseScreen(String titulo) {
        super(titulo);
    }

    public abstract List<MenuItem> configurarMenu();

    @Override
    public void run() {
        List<MenuItem> items = configurarMenu();
        menuItems.clear();
        for (MenuItem item : items) {
            menuItems.put(item.getOpcao(), item);
        }

        for (MenuItem item : items) {
            if (item.isVisivel()){
                System.out.println(item.getOpcao() + " - " + item.getDescricao());
            }
        }

        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine(); // consome o '\n' no buffer

        MenuItem itemSelecionado = menuItems.get(opcao);
        if (itemSelecionado != null) {
            itemSelecionado.getAcao().run();
        } else {
            System.out.println("Opção inválida.");
            esperarEnter();
            setNextScreen(this);
        }
    }
}
