package view.gestao.apagarEntidades;

import java.util.List;

import model.musica.Musica;
import view.bases.MenuEntidadeBaseScreen;
import view.bases.MenuItem;
import view.gestao.menus.MenuGestaoDeAlbunsScreen;

public class ApagarMusicaScreen extends MenuEntidadeBaseScreen<Musica> {

    public ApagarMusicaScreen(Musica entidade) {
        super("Eliminar Música", entidade);
    }

    @Override
    protected String resumoEntidade(Musica musica) {
        return String.format("Tem a certeza que deseja apagar a musica %s de %s?", 
            musica.getNome(), 
            musica.getInterprete());
    }

    @Override
    public List<MenuItem> configurarMenu() {
        return List.of(
            new MenuItem(1, "Sim", () -> { 
              //  System.out.println(controller.removerMusica(getEntidade()));
                this.esperarEnter();
                this.setNextScreen(new MenuGestaoDeAlbunsScreen());}),
            new MenuItem(2, "Não", () -> this.setNextScreen(getPrevious()))
        );
    }

}