package view.bases;

public abstract class MenuEntidadeBaseScreen<T> extends MenuBaseScreen {

    private final T entidade;

    public MenuEntidadeBaseScreen(String titulo, T entidade) {
        super(titulo);
        this.entidade = entidade;
    }

    @Override
    public void run() {
        mostrarCabecalhoEntidade(entidade);
        super.run(); 
    }

    protected abstract String resumoEntidade(T entidade);

    private void mostrarCabecalhoEntidade(T entidade) {
        System.out.println("-".repeat(40));
        System.out.println(resumoEntidade(entidade));
        System.out.println("-".repeat(40));
    }

    public T getEntidade() {
        return entidade;
    }
}
