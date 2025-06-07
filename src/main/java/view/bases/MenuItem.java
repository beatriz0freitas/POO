package view.bases;

import java.util.function.Supplier;

public class MenuItem {
    private final int opcao;
    private final String descricao;
    private final Runnable acao;
    private final Supplier<Boolean> visibilidade;

    public MenuItem(int opcao, String descricao, Runnable acao) {
        this.opcao = opcao;
        this.descricao = descricao;
        this.acao = acao;
        this.visibilidade = () -> true;
    
    }

    public MenuItem(int opcao, String descricao, Runnable acao, Supplier<Boolean> visibilidade) {
        this.opcao = opcao;
        this.descricao = descricao;
        this.acao = acao;
        this.visibilidade = visibilidade;
    
    }

    public int getOpcao() {
        return opcao;
    }

    public String getDescricao() {
        return descricao;
    }

    public Runnable getAcao() {
        return acao;
    }

    public boolean isVisivel() {
        return visibilidade.get();
    }


}
