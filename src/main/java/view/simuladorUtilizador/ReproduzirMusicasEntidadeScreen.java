package view.simuladorUtilizador;

import java.util.ArrayList;
import java.util.List;

import model.Utilizador;
import view.bases.MenuEntidadeBaseScreen;
import view.bases.MenuItem;

public class ReproduzirMusicasEntidadeScreen extends MenuEntidadeBaseScreen<Utilizador> {

    public ReproduzirMusicasEntidadeScreen(Utilizador utilizador) {
        super("Reprodução de Músicas", utilizador);
    }

    @Override
    protected String resumoEntidade(Utilizador entidade) {
        return String.format("Utilizador: %s\nEmail: %s", entidade.getNome(), entidade.getEmail());
    }

    @Override
    public List<MenuItem> configurarMenu() {
        List<MenuItem> items = new ArrayList<>();

        items.add(new MenuItem(1, "<< Reproduzir música individual", () -> reproduzirMusicasIndividuais()));
        items.add(new MenuItem(2, "<< Reproduzir da biblioteca", () -> setNextScreen(new ListagemColecoesDeMusicasDaBiblioteca(getEntidade())) ));
        items.add(new MenuItem(0, ">> Voltar", () -> setNextScreen(new UtilizadorMenuEntidadeScreen(this.getEntidade()))));

        return items;
    }

    //rever isto de ter que pedir o nome do album
    private void reproduzirMusicasIndividuais() {
        System.out.println("Que música deseja reproduzir?");
        String musica = scanner.nextLine();
        System.out.println("A que album pertence?");
        String album = scanner.nextLine();
        
        System.out.println(controller.reproduzirMusicaIndividual(musica, album)); // Chamar o método do controller para reproduzir a música
        esperarEnter(); //mudar isto depois
        setNextScreen(this); // Fica nesta mesma screen após ação
    }
    
}
