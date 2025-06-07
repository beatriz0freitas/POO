package model.Enunciado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import exceptions.AlbumJaExisteException;
import exceptions.AlbumNaoExisteException;
import model.Album;
import model.SpotifUMModel;

import java.util.ArrayList;

public class GestaoDeAlbunsTest {

    private SpotifUMModel model;

    @BeforeEach
    void setUp() {
        model = new SpotifUMModel();
    }

    @Test
    void testAdicionarAlbumComSucesso() throws AlbumNaoExisteException, AlbumJaExisteException {
        // Enunciado (p1, 1): "músicas estas poderão estar também organizadas... por álbuns"
        Album novoAlbum = new Album("Greatest Hits", "The Best Band", new ArrayList<>());
        model.adicionarAlbum(novoAlbum);
        assertEquals(1, model.getAlbums().size());
        Album recuperado = model.getAlbum("Greatest Hits");
        assertEquals("Greatest Hits", recuperado.getNome());
        assertEquals("The Best Band", recuperado.getArtista());
    }

    @Test
    void testAdicionarAlbumDuplicadoLancaExcecao() throws AlbumJaExisteException {
        Album album1 = new Album("Album Unico", "Artista X", new ArrayList<>());
        model.adicionarAlbum(album1);
        Album album1Dup = new Album("Album Unico", "Artista Y", new ArrayList<>()); // Mesmo nome
        assertThrows(AlbumJaExisteException.class, () -> model.adicionarAlbum(album1Dup));
    }

    @Test
    void testRemoverAlbumExistente() throws AlbumJaExisteException, AlbumNaoExisteException {
        Album album1 = new Album("Para Remover Album", "Artista R", new ArrayList<>());
        model.adicionarAlbum(album1);
        assertEquals(1, model.getAlbums().size());
        model.removerAlbum(album1);
        assertEquals(0, model.getAlbums().size());
        assertThrows(AlbumNaoExisteException.class, () -> model.getAlbum("Para Remover Album"));
    }

    @Test
    void testRemoverAlbumNaoExistenteLancaExcecao() {
        Album albumNaoExistente = new Album("Album Fantasma", "Artista Fantasma", new ArrayList<>());
        assertThrows(AlbumNaoExisteException.class, () -> model.removerAlbum(albumNaoExistente));
    }

    @Test
    void testGetAlbumExistente() throws AlbumJaExisteException, AlbumNaoExisteException {
        Album album1 = new Album("Album Get Test", "Artista G", new ArrayList<>());
        model.adicionarAlbum(album1);
        Album a = model.getAlbum("Album Get Test");
        assertNotNull(a);
        assertEquals("Artista G", a.getArtista());
    }

    @Test
    void testGetAlbumNaoExistenteLancaExcecao() {
        assertThrows(AlbumNaoExisteException.class, () -> model.getAlbum("Album Inexistente"));
    }
}