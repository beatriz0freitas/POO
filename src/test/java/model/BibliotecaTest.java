package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import exceptions.ColecaoNaoExisteNaBibliotecaException;
import exceptions.MusicaJaExisteException;
import model.musica.Musica;
import model.playlists.Playlist;
import model.playlists.PlaylistAleatoria;

public class BibliotecaTest {

    private Biblioteca biblioteca;
    private Album album;
    private Playlist playlist;

    @BeforeEach
    public void setUp() throws MusicaJaExisteException {
        biblioteca = new Biblioteca();
        Musica musica = new Musica("Som", "Artista", "Editora", "Letra", "Notas", "Gênero", 180);
        this.album = new Album("Album 1", "Artista", List.of(musica));
        this.playlist = new PlaylistAleatoria("Playlist 1", List.of(musica));
    }

    @Test
    public void testAdicionarAlbum() {
        biblioteca.adicionarAlbumABiblioteca(album);
        List<Album> albuns = biblioteca.getAlbuns();
        assertEquals(1, albuns.size());
        assertEquals("Album 1", albuns.get(0).getNome());
    }

    @Test
    public void testAdicionarPlaylist() {
        biblioteca.adicionarPlaylistABiblioteca(playlist);
        List<Playlist> playlists = biblioteca.getPlaylists();
        assertEquals(1, playlists.size());
        assertEquals("Playlist 1", playlists.get(0).getNome());
    }

    @Test
    public void testGetColecaoExistente() throws ColecaoNaoExisteNaBibliotecaException {
        biblioteca.adicionarAlbumABiblioteca(album);
        ColecaoDeMusicasReproduzivel colecao = biblioteca.getColecao("Album 1");
        assertEquals("Album 1", colecao.getNome());
    }

    @Test
    public void testGetColecaoInexistente() {
        Exception exception = assertThrows(ColecaoNaoExisteNaBibliotecaException.class, () -> {
            biblioteca.getColecao("Inexistente");
        });
        assertTrue(exception.getMessage().contains("Coleção com o nome"));
    }

    @Test
    public void testNumeroDePlaylists() {
        biblioteca.adicionarPlaylistABiblioteca(playlist);
        biblioteca.adicionarPlaylistABiblioteca(playlist);
        assertEquals(2, biblioteca.numeroDePlaylist());
    }

    @Test
    public void testGetConteudo() {
        biblioteca.adicionarAlbumABiblioteca(album);
        biblioteca.adicionarPlaylistABiblioteca(playlist);
        List<ColecaoDeMusicasReproduzivel> conteudo = biblioteca.getConteudo();
        assertEquals(2, conteudo.size());
    }

    @Test
    public void testGetConteudoIncluiFavoritos() throws MusicaJaExisteException {
        Musica musica = new Musica("Favorita", "Artista", "Editora", "Letra", "Notas", "Gênero", 180);
        biblioteca.adicionarAlbumABiblioteca(album);
        biblioteca.adicionarPlaylistABiblioteca(playlist);
        biblioteca.adicionarMusicaAosFavoritos(musica);

        List<ColecaoDeMusicasReproduzivel> conteudo = biblioteca.getConteudo();

        // Deve conter 3: album, playlist e favoritos
        assertEquals(3, conteudo.size());

        boolean encontrouFavoritos = conteudo.stream()
            .anyMatch(c -> c.getNome().equals("Favoritos"));

        assertTrue(encontrouFavoritos, "Deve conter a playlist de favoritos.");
    }


    @Test
    public void testGetAlbunsRetornaClone() {
        biblioteca.adicionarAlbumABiblioteca(album);
        List<Album> albuns = biblioteca.getAlbuns();
    
        // Modifica o nome do álbum retornado
        albuns.get(0).setNome("Modificado");
    
        // Verifica que o álbum na biblioteca continua com o nome original
        assertEquals("Album 1", biblioteca.getAlbuns().get(0).getNome());
    }
    
    @Test
    public void testGetPlaylistsRetornaClone() {
        biblioteca.adicionarPlaylistABiblioteca(playlist);
        List<Playlist> playlists = biblioteca.getPlaylists();
    
        playlists.get(0).setNome("Nova Playlist");
        
        assertEquals("Playlist 1", biblioteca.getPlaylists().get(0).getNome());
    }
    
    
    @Test
    public void testAdicionarRemoverMusicaFavorita() throws Exception {
        Musica musica = new Musica("Favorita", "Artista", "Editora", "Letra", "Notas", "Gênero", 180);
        Biblioteca biblioteca = new Biblioteca();
    
        biblioteca.adicionarMusicaAosFavoritos(musica);
        assertTrue(biblioteca.isFavorita(musica));
    
        biblioteca.removerMusicaDosFavoritos(musica);
        assertFalse(biblioteca.isFavorita(musica));
    }


}
