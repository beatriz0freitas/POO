package view.bases;

import java.util.function.Consumer;

public abstract class SelecionarEntidadeDeListagemScreen<T> extends ListarEntidadesBaseScreen<T> {

    private final Consumer<T> acao;
    private final BaseScreen nextScreen;

    public SelecionarEntidadeDeListagemScreen(String titulo, Consumer<T> acao, BaseScreen nextScreen) {
        super(titulo);
        this.acao = acao;
        this.nextScreen = nextScreen;
    }

    @Override
    protected void aoSelecionarEntidade(T entidade) {
        acao.accept(entidade);
        esperarEnter();
        setNextScreen(nextScreen);
    }
}