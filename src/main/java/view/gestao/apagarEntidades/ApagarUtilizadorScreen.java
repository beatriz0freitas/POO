package view.gestao.apagarEntidades;

import java.util.List;

import model.Utilizador;
import view.bases.MenuEntidadeBaseScreen;
import view.bases.MenuItem;
import view.gestao.menus.MenuGestaoUtilizadoresScreen;

public class ApagarUtilizadorScreen extends MenuEntidadeBaseScreen<Utilizador> {

    public ApagarUtilizadorScreen(Utilizador entidade) {
        super("Eliminar Utilizador", entidade);
    }

    @Override
    protected String resumoEntidade(Utilizador entidade) {
        return String.format("Tem a certeza que quer eliminar o utilizador %s (%s)?", entidade.getNome(), entidade.getEmail());

    }

    @Override
    public List<MenuItem> configurarMenu() {
        return List.of(
            new MenuItem(1, "Sim", () -> { 
                System.out.println(controller.removerUtilizador(getEntidade()));
                this.esperarEnter();
                this.setNextScreen(new MenuGestaoUtilizadoresScreen());}),
            new MenuItem(2, "Não", () -> this.setNextScreen(getPrevious()))
        );
    }
}
