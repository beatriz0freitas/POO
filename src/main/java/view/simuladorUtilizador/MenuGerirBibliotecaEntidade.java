package view.simuladorUtilizador;

import java.util.List;

import model.Utilizador;
import view.bases.MenuEntidadeBaseScreen;
import view.bases.MenuItem;
import view.simuladorUtilizador.selecionadores.SelecionarAlbum;
import view.simuladorUtilizador.selecionadores.SelecionarPlaylist;

public class MenuGerirBibliotecaEntidade extends MenuEntidadeBaseScreen<Utilizador>{

    public MenuGerirBibliotecaEntidade(Utilizador entidade) {
        super("Biblioteca", entidade);
    }

    @Override
    protected String resumoEntidade(Utilizador entidade) {
        return String.format("Utilizador: %s\nEmail: %s", entidade.getNome(), entidade.getEmail());
    }

    @Override
    public List<MenuItem> configurarMenu() {
        return List.of(new MenuItem(1, "Adicionar Album", () -> {
            setNextScreen(new SelecionarAlbum(
                album -> controller.adicionarAlbumABiblioteca(album, getEntidade()),
                new UtilizadorMenuEntidadeScreen(getEntidade())
            ));
        }),
        new MenuItem(2, "Adicionar Playlist", () -> {
            setNextScreen(new SelecionarPlaylist(
                playlist -> controller.adicionarPlaylistABiblioteca(playlist, getEntidade()),
                new UtilizadorMenuEntidadeScreen(getEntidade())
            ));
        }),
        new MenuItem(3, "Consultar Biblioteca", () -> {
            setNextScreen(new ListagemColecoesDeMusicasDaBiblioteca(getEntidade()));
        }),
        new MenuItem(0, "Voltar", () -> setNextScreen(new UtilizadorMenuEntidadeScreen(getEntidade()))));
    }



}
