package view.gestao.criarEntidades;

import java.util.Arrays;
import java.util.List;

import model.Album;
import view.bases.CampoEntidade;
import view.bases.CriarEntidadeBaseScreen;
import view.gestao.menus.MenuGestaoDeAlbunsScreen;

public class CriarAlbumScreen extends CriarEntidadeBaseScreen<Album, Void> {

    public CriarAlbumScreen() {
        super("Criar Álbum", null);
    }

    @Override
    protected Album novaEntidade() {
        return new Album();
    }

    @Override
    protected List<CampoEntidade<Album>> obterCampos() {
        return Arrays.asList(
            new CampoEntidade<>("Titulo", input -> input, (album, titulo) -> album.setNome(titulo)),
            new CampoEntidade<>("Artista", input -> input, (album, artista) -> album.setArtista(artista))
        );
    }

    @Override
    protected void processarEntidade(Album entidade, Void ignored) {
        System.out.println(controller.adicionarAlbum(entidade));
        this.esperarEnter();
        this.setNextScreen(new MenuGestaoDeAlbunsScreen());
    }

}
