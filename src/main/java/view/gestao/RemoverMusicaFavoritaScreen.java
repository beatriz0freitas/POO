package view.gestao;

import java.util.List;

import model.Utilizador;
import model.musica.Musica;
import view.bases.MenuEntidadeBaseScreen;
import view.bases.MenuItem;

public class RemoverMusicaFavoritaScreen extends MenuEntidadeBaseScreen<Musica> {

    private Utilizador utilizador;

    public RemoverMusicaFavoritaScreen(Musica musica, Utilizador utilizador) {
        super("Remover dos Favoritos", musica);
        this.utilizador = utilizador;
    }

    @Override
    protected String resumoEntidade(Musica musica) {
        return "Deseja remover esta música da sua lista de favoritas?";
    }

    @Override
    public List<MenuItem> configurarMenu() {
        return List.of(
            new MenuItem(1, "Sim", () -> {System.out.println(controller.removerDosFavoritos(getEntidade(), utilizador));
                esperarEnter();
                setNextScreen(getPrevious());
            }),
            new MenuItem(2, "Não", () -> setNextScreen(getPrevious()))
        );
    }
}
