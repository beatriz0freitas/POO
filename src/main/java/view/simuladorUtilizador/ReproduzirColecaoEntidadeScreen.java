package view.simuladorUtilizador;

import java.util.List;

import model.Reproducao;
import model.Utilizador;
import view.bases.MenuEntidadeBaseScreen;
import view.bases.MenuItem;
import view.gestao.MarcarMusicaFavoritaScreen;
import view.gestao.RemoverMusicaFavoritaScreen;

public class ReproduzirColecaoEntidadeScreen extends MenuEntidadeBaseScreen<Reproducao> {

    Utilizador utilizador;

    public ReproduzirColecaoEntidadeScreen(Reproducao entidade, Utilizador utilizador) {
        super("A reproduzir", entidade);
        this.utilizador = utilizador;
    }

    @Override
    protected String resumoEntidade(Reproducao entidade) {
        return entidade.getMusica().getLetra();
    }

    @Override
    public List<MenuItem> configurarMenu() {
        return List.of(new MenuItem(1, "<< Avançar para a próxima música", () -> setNextScreen(new ReproduzirColecaoEntidadeScreen(controller.avancarMusica(utilizador), utilizador))),
                       new MenuItem(2, "<< Retroceder para a música anterior", () -> setNextScreen(new ReproduzirColecaoEntidadeScreen(controller.retrocederMusica(utilizador), utilizador))),
                       new MenuItem(3, "<< Marcar como favorita", () -> setNextScreen(new MarcarMusicaFavoritaScreen(getEntidade().getMusica(), utilizador)), () -> !utilizador.eFavorita(getEntidade().getMusica())),
                       new MenuItem(4, "<< Remover música dos favoritos", () -> setNextScreen(new RemoverMusicaFavoritaScreen(getEntidade().getMusica(), utilizador)), () -> utilizador.eFavorita(getEntidade().getMusica())),
                       new MenuItem(5, "<< Modo Shuffle", () -> {
                        controller.mudarShuffleNoReprodutor(utilizador.getEmail());
                        setNextScreen(new ReproduzirColecaoEntidadeScreen(getEntidade(), utilizador));
                    }),                       new MenuItem(0, ">> Voltar", () -> setNextScreen(new UtilizadorMenuEntidadeScreen(utilizador))));

    }
  
} 