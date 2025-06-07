package view.gestao.menus;

import java.util.List;

import model.Utilizador;
import model.musica.Musica;
import view.bases.MenuEntidadeBaseScreen;
import view.bases.MenuItem;
import view.gestao.apagarEntidades.ApagarMusicaScreen;


public class MenuMusica extends MenuEntidadeBaseScreen<Musica>{

    private Utilizador utilizador;

    public MenuMusica(Musica entidade, Utilizador utilizador) {
        super("Gestão de músicas", entidade);
        this.utilizador = utilizador;
    }

    //construtor para a listagem
    public MenuMusica(Musica entidade) {
        super("Gestão de músicas", entidade);
        this.utilizador = null;
    }

    @Override
    protected String resumoEntidade(Musica musica) {
        return String.format("%s - %s", 
            musica.getNome(), 
            musica.getInterprete());
    }

    @Override
    public List<MenuItem> configurarMenu() {
        return List.of(
            new MenuItem(1, ">> Mostrar Informacão Musica", () -> printInformacao(getEntidade())),
            new MenuItem(2,"<< Eliminar Musica", () -> this.setNextScreen(new ApagarMusicaScreen(getEntidade()))),
            new MenuItem(0, ">> Voltar", () -> this.setNextScreen(new MenuGestaoDeAlbunsScreen()))
        );
    }
    
    public void printInformacao(Musica entidade) {
        System.out.println("Musica selecionada: " + entidade.getNome());
        System.out.println("Interprete: " + entidade.getInterprete());
        System.out.println("Genero: " + entidade.getGenero());
        System.out.println("Duração em segundos: " + entidade.getDuracaoEmSegundos());
        System.out.println("Letra " + entidade.getLetra());
        System.out.println("Notas Musicais " + entidade.getNotasMusicais());

        esperarEnter();
        setNextScreen(this);
    }

}    
