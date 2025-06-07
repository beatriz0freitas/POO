package view.gestao.menus;

import java.util.List;

import model.Album;
import view.bases.MenuEntidadeBaseScreen;
import view.bases.MenuItem;
import view.gestao.apagarEntidades.ApagarAlbumScreen;
import view.gestao.criarEntidades.CriarMusicaScreen;
import view.gestao.listagens.ListagemMusicasScreen;

public class MenuAlbum extends MenuEntidadeBaseScreen<Album> {

    public MenuAlbum(Album entidade) {
        super("Gestão de albuns", entidade);
    }

    @Override
    protected String resumoEntidade(Album entidade) {
        return String.format("%s (%s)", entidade.getNome(), entidade.getArtista());
    }

    @Override
    public List<MenuItem> configurarMenu() {
        return List.of(
            new MenuItem(1, ">> Mostrar Informacão", () -> printInformacao(getEntidade())),
            new MenuItem(2, ">> Adicionar Música", () -> setNextScreen(new CriarMusicaScreen(getEntidade()))),
            new MenuItem(3, ">> Listar Musicas Album", () -> this.setNextScreen(new ListagemMusicasScreen(getEntidade()))),
            new MenuItem(4, ">> Apagar Álbum", () -> setNextScreen(new ApagarAlbumScreen(getEntidade()))),
            new MenuItem(0, ">> Voltar", () -> this.setNextScreen(new MenuPrincipalScreen()))
        );    
    }

    public void printInformacao(Album entidade) {
        System.out.println("Album selecionado: " + entidade.getNome());
        System.out.println("Artista: " + entidade.getArtista());

        esperarEnter();
        setNextScreen(this);
    }
    
}
