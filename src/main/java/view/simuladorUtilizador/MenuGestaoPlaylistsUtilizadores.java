package view.simuladorUtilizador;

import java.util.ArrayList;
import java.util.List;

import model.Utilizador;
import view.bases.MenuEntidadeBaseScreen;
import view.bases.MenuItem;
import view.gestao.criarEntidades.*;

public class MenuGestaoPlaylistsUtilizadores extends MenuEntidadeBaseScreen<Utilizador> {

    public MenuGestaoPlaylistsUtilizadores(Utilizador utilizador) {
        super("Gestao de Playlists", utilizador);
    }

    @Override
    protected String resumoEntidade(Utilizador entidade) {
        return String.format("Utilizador: %s\nEmail: %s", entidade.getNome(), entidade.getEmail());
    }

    @Override
    public List<MenuItem> configurarMenu() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(1, "<< Playlist Aleatória", () -> setNextScreen(new CriarPlaylistsAleatoriaScreen(getEntidade()))));
        menuItems.add(new MenuItem(2,"<< Playlist Por Genero e Tempo", () -> setNextScreen(new CriarPlaylistPorGeneroETempo(getEntidade()))));
        menuItems.add(new MenuItem(3, "<< Playlist Personalizada", () -> setNextScreen(new CriarPlaylistPersonalizada(getEntidade()))));
        menuItems.add(new MenuItem(4, "<< Playlist Lista de Favoritos com Tempo Máximo", () -> setNextScreen(new CriarPlaylistPreferênciasComTempoMaximo(getEntidade())))); 
        menuItems.add(new MenuItem(5, "<< Playlist Lista de Favoritos com Musicas Explicitas", () -> setNextScreen(new CriarPlaylistPreferênciasExplicitas(getEntidade()))));
        menuItems.add(new MenuItem(0, ">> Voltar", () -> setNextScreen(new UtilizadorMenuEntidadeScreen(getEntidade()))));

        return menuItems;    
    }
    
    
}
