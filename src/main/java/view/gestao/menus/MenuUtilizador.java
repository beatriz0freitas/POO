package view.gestao.menus;

import java.util.List;

import model.Utilizador;
import view.bases.MenuEntidadeBaseScreen;
import view.bases.MenuItem;
import view.gestao.MudarPlanoSubscricaoScreen;
import view.gestao.apagarEntidades.ApagarUtilizadorScreen;

public class MenuUtilizador extends MenuEntidadeBaseScreen<Utilizador> {

    public MenuUtilizador(Utilizador entidade) {
        super("Gestão de Utilizador",entidade);
    }

    @Override
    protected String resumoEntidade(Utilizador entidade) {
        return String.format("%s (%s)", entidade.getNome(), entidade.getEmail());
    }

    @Override
    public List<MenuItem> configurarMenu() {
        return List.of(
            new MenuItem(1, ">> Mostrar Informacão", () -> printInformacao(getEntidade())),
            new MenuItem(2,"<< Alterar Plano", () -> this.setNextScreen(new MudarPlanoSubscricaoScreen(getEntidade()))),
            new MenuItem(3,"<< Eliminar Utilizador", () -> this.setNextScreen(new ApagarUtilizadorScreen(getEntidade()))),
            new MenuItem(0, ">> Voltar", () -> this.setNextScreen(new MenuGestaoUtilizadoresScreen()))
        );    
    }

    public void printInformacao(Utilizador entidade) {
        System.out.println("Utilizador selecionado: " + entidade.getNome());
        System.out.println("Email: " + entidade.getEmail());
        System.out.println("Morada " + entidade.getMorada());
        System.out.println("Tipo de plano de subscrição: " + entidade.getPlanoSubscricao().toString());
        System.out.println("Total de pontos: " + entidade.getPontosAtuais());
        esperarEnter();
        setNextScreen(this);
    }
    
}


