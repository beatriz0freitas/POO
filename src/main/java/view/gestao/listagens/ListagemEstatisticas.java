package view.gestao.listagens;

import java.time.LocalDateTime;
import java.util.List;

import model.Utilizador;
import model.musica.Musica;
import view.bases.ListarEntidadesBaseScreen;
import view.gestao.menus.MenuPrincipalScreen;

public class ListagemEstatisticas extends ListarEntidadesBaseScreen<String> {

    public ListagemEstatisticas() {
        super("Estatísticas do Sistema");
    }

    @Override
protected List<String> getEntidades() {
    return List.of(
        "Voltar",  // "Voltar" agora é a primeira opção
        "Música Mais Ouvida", 
        "Intérprete Mais Escutado", 
        "Utilizador Que Mais Ouviu Música no Último Mês", 
        "Utilizador Com Mais Pontos", 
        "Género Musical Mais Reproduzido", 
        "Quantas Playlists Públicas Existem", 
        "Utilizador Com Mais Playlists Públicas"
    );
}

@Override
protected String resumoEntidade(String entidade) {
    // Ajusta apenas a exibição de "Voltar" para mostrar "0 - Voltar"
    if (entidade.equals("Voltar")) {
        return "0 - Voltar";
    }
    // Para as outras opções, exibe como está
    return entidade;
}

@Override
protected void aoSelecionarEntidade(String entidade) {
    switch (entidade) {
        case "Voltar" -> setNextScreen(new MenuPrincipalScreen());
        case "Música Mais Ouvida" -> mostrarMusicaMaisOuvida();
        case "Intérprete Mais Escutado" -> mostrarInterpreteMaisEscutado();
        case "Utilizador Que Mais Ouviu Música no Último Mês" -> mostrarUtilizadorMaisOuviu();
        case "Utilizador Com Mais Pontos" -> mostrarUtilizadorMaisPontos();
        case "Género Musical Mais Reproduzido" -> mostrarGeneroMaisReproduzido();
        case "Quantas Playlists Públicas Existem" -> mostrarQuantasPlaylists();
        case "Utilizador Com Mais Playlists Públicas" -> mostrarUtilizadorComMaisPlaylists();
        default -> System.out.println("Estatística desconhecida.");
    }
    esperarEnter();
    setNextScreen(new ListagemEstatisticas());
}


    private void mostrarMusicaMaisOuvida() {
        Musica m = controller.musicaMaisReproduzida();
        if (m != null)
            System.out.println("\n🎵 Música mais ouvida: " + m.getNome() + " por " + m.getInterprete() + " (" + m.getNumeroDeReproducoes() + " reproduções)");
        else
            System.out.println("\nNenhuma música encontrada.");
    }

    private void mostrarInterpreteMaisEscutado() {
        String interprete = controller.interpreteMaisOuvido();
        System.out.println("\n🎤 Intérprete mais escutado: " + interprete);
    }

    private void mostrarUtilizadorMaisOuviu() {
        LocalDateTime fim = LocalDateTime.now();
        LocalDateTime inicio = fim.minusMonths(1);
        Utilizador u = controller.utilizadorMaisOuviuNumPeriodo(inicio, fim);
        if (u != null)
            System.out.println("\nUtilizador que mais ouviu música no último mês: " + u.getNome());
        else
            System.out.println("\nNenhum utilizador encontrado.");
    }

    private void mostrarUtilizadorMaisPontos() {
        Utilizador u = controller.utilizadorComMaisPontos();
        System.out.println("\nUtilizador com mais pontos: " + u.getNome() + " (" + u.getPontosAtuais() + " pontos)");
    }

    private void mostrarGeneroMaisReproduzido() {
        String genero = controller.generoMaisReproduzido();
        System.out.println("\nGénero mais reproduzido: " + genero);
    }

    private void mostrarQuantasPlaylists() {
        int total = controller.quantasPlaylistsPublicasExistem();
        System.out.println("\nNúmero de playlists públicas: " + total);
    }

    private void mostrarUtilizadorComMaisPlaylists() {
        Utilizador u = controller.utilizadorComMaisPlaylists();
        System.out.println("\nUtilizador com mais playlists públicas: " + u.getNome() + " (" + u.getBiblioteca().getPlaylists().size() + ")");
    }
}
