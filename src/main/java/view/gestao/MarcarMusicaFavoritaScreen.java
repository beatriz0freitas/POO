package view.gestao;

import java.util.List;

import model.Utilizador;
import model.musica.Musica;
import view.bases.MenuEntidadeBaseScreen;
import view.bases.MenuItem;

public class MarcarMusicaFavoritaScreen extends MenuEntidadeBaseScreen<Musica> {

    private Utilizador utilizador;

    public MarcarMusicaFavoritaScreen(Musica musica, Utilizador utilizador) {
        super("Marcar Musica como Favorita", musica);
        this.utilizador = utilizador;
    }

    @Override
    protected String resumoEntidade(Musica musica) {
        return "Deseja adicionar esta música à sua lista de favoritos?";
    }

    @Override
    public List<MenuItem> configurarMenu() {
        return List.of(
            new MenuItem(1, "Sim", () -> {System.out.println( controller.marcarComoFavorita(getEntidade(), utilizador));
                esperarEnter();
                setNextScreen(getPrevious());
            }),
            new MenuItem(2, "Não", () -> setNextScreen(getPrevious()))
        );
    }
}
