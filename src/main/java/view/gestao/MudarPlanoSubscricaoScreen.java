package view.gestao;

import java.util.List;

import model.Utilizador;

import view.bases.MenuEntidadeBaseScreen;
import view.bases.MenuItem;
import view.gestao.menus.MenuGestaoUtilizadoresScreen;
import view.gestao.menus.MenuUtilizador;



public class MudarPlanoSubscricaoScreen extends MenuEntidadeBaseScreen<Utilizador> {

    public MudarPlanoSubscricaoScreen(Utilizador entidade) {
        super("Mudar Plano de Subscrição", entidade);
    }
  
    @Override
    protected String resumoEntidade(Utilizador entidade) {
        return String.format("%s (%s)", entidade.getNome(), entidade.getEmail());
    }

    @Override
    public List<MenuItem> configurarMenu(){
        return List.of(
            new MenuItem(1, ">> Plano Free", () -> {
                System.out.println(controller.mudarPlano(getEntidade(), "Free")); esperarEnter();
                this.setNextScreen(new MenuGestaoUtilizadoresScreen());
            }),
            new MenuItem(2, "<< Plano Premium Base", () -> {
                System.out.println(controller.mudarPlano(getEntidade(), "Premium Base")); esperarEnter();
                this.setNextScreen(new MenuGestaoUtilizadoresScreen());
            }),
            new MenuItem(3, "<< Plano Premium Top", () -> {
                System.out.println(controller.mudarPlano(getEntidade(), "Premium Top")); esperarEnter();
                this.setNextScreen(new MenuGestaoUtilizadoresScreen());
            }),
            new MenuItem(0, ">> Voltar", () -> this.setNextScreen(new MenuUtilizador(getEntidade())))
        );    
    }
}


