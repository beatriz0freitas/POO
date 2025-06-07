package view.gestao.apagarEntidades;

import java.util.List;

import model.Album;
import view.bases.MenuEntidadeBaseScreen;
import view.bases.MenuItem;


public class ApagarAlbumScreen extends MenuEntidadeBaseScreen<Album> {

    public ApagarAlbumScreen(Album entidade) {
        super("Apagar Album", entidade);
    }


    @Override
    protected String resumoEntidade(Album entidade) {
        return String.format("Tem a certeza que deseja apagar o álbum %s (%s)?", entidade.getNome(), entidade.getArtista());
    }

    @Override
    public List<MenuItem> configurarMenu() {
        return List.of(
            new MenuItem(1, "Sim", () -> { 
                System.out.println(controller.removerAlbum(getEntidade()));
                this.esperarEnter();
                this.setNextScreen(getPrevious());}),
            new MenuItem(2, "Não", () -> this.setNextScreen(getPrevious()))
        );
    }



}
