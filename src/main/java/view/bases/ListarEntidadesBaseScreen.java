package view.bases;

import java.util.List;

public abstract class ListarEntidadesBaseScreen<T> extends BaseScreen {

    public ListarEntidadesBaseScreen(String titulo) {
        super(titulo);
    }

    @Override
    public void run() {
        List<T> entidades = getEntidades();

        if (entidades == null || entidades.isEmpty()) {
            System.out.println("Nenhuma entidade encontrada.");
            esperarEnter();

            setNextScreen(getPrevious());
        }

        for (int i = 0; i < entidades.size(); i++) {
            System.out.println((i + 1) + " - " + resumoEntidade(entidades.get(i)));
        }

        System.out.print("Escolha uma entidade pelo número: ");
        int opcao = scanner.nextInt();
        scanner.nextLine(); // consumir newline

        if (opcao < 1 || opcao > entidades.size()) {
            System.out.println("Opção inválida.");
        } else {
            T entidadeSelecionada = entidades.get(opcao - 1);
            aoSelecionarEntidade(entidadeSelecionada);
        }

        esperarEnter();
    }

    protected abstract List<T> getEntidades();

    protected abstract String resumoEntidade(T entidade);

    protected abstract void aoSelecionarEntidade(T entidade);
}
