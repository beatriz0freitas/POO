package view.gestao.criarEntidades;

import model.Utilizador;
import view.bases.CampoEntidade;
import view.bases.CriarEntidadeBaseScreen;
import view.gestao.menus.MenuGestaoUtilizadoresScreen;

import java.util.Arrays;
import java.util.List;

public class CriarUtilizadorScreen extends CriarEntidadeBaseScreen<Utilizador, Void> {

    public CriarUtilizadorScreen() {
        super("Criar Utilizador", null);
    }

    @Override
    protected Utilizador novaEntidade() {
        return new Utilizador();
    }

    @Override
    protected List<CampoEntidade<Utilizador>> obterCampos() {
        return Arrays.asList(
            new CampoEntidade<>("Nome", input -> input, (u, nome) -> u.setNome(nome)),
            new CampoEntidade<>("Email", input -> input, (u, email) -> u.setEmail(email)),
            new CampoEntidade<>("Morada", input -> input, (u, morada) -> u.setMorada(morada))
        );
    }

    @Override
    protected void processarEntidade(Utilizador entidade, Void ignored) {
        System.out.println(controller.adicionarUtilizador(entidade));
        this.esperarEnter();
        this.setNextScreen(new MenuGestaoUtilizadoresScreen());
    }
}
