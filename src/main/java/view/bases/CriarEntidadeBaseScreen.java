package view.bases;

import java.util.*;

public abstract class CriarEntidadeBaseScreen<T, O> extends BaseScreen {

    private final O alvo;

    public CriarEntidadeBaseScreen(String titulo, O alvo) {
        super(titulo);
        this.alvo = alvo;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        T entidade = novaEntidade();

        for (CampoEntidade<T> campo : obterCampos()) {
            campo.preencherCampo(entidade, scanner);
        }

        processarEntidade(entidade, alvo);
    }

    protected abstract T novaEntidade();
    protected abstract List<CampoEntidade<T>> obterCampos();
    protected abstract void processarEntidade(T entidade, O alvo);

    public O getAlvo() {
        return alvo;
    }
}
