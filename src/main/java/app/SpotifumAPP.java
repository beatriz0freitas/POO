package app;

import controller.SpotifUMController;
import model.SpotifUMModel;
import view.SpotifUMView;

/**
 * Classe principal da aplicação SpotifUM.
 * Esta classe inicializa o modelo, controlador e a interface gráfica.
 */
public class SpotifumAPP {

    /**
     * Método principal que inicia a aplicação.
     * Cria uma instância do modelo, controlador e view.
     * Adiciona um hook para salvar o estado da aplicação ao fechar.
     * @param args Argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        SpotifUMModel model = new SpotifUMModel();
        SpotifUMController controller = new SpotifUMController(model);
        SpotifUMView view = new SpotifUMView(controller);
        
        controller.carregarDados();

        // Hook para guardar estado ao fechar (Ctrl+C ou encerramento normal)
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nA guardar os dados antes de sair...");
            controller.salvarDados();
        }));

        view.init();
    }
}
