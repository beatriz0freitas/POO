package view.gestao.listagens;

import java.util.List;

import model.Album;
import view.bases.ListarEntidadesBaseScreen;
import view.gestao.menus.MenuAlbum;

public class ListagemAlbumsScreen extends ListarEntidadesBaseScreen<Album> {

    public ListagemAlbumsScreen(String titulo) {
        super(titulo);
    }

    @Override
    protected List<Album> getEntidades() {
        return this.controller.getAlbums();
    }

    @Override
    protected String resumoEntidade(Album entidade) {
        return String.format("%s (%s)", entidade.getNome(), entidade.getArtista());
    }

    @Override
    protected void aoSelecionarEntidade(Album entidade) {
        System.out.println("Album selecionado: " + entidade.getNome());

        esperarEnter();

        setNextScreen(new MenuAlbum(entidade));
    }
    
}
