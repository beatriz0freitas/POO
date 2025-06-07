package  model;

import  exceptions.ColecaoDeMusicaVaziaException;
import exceptions.MusicaJaExisteException;
import exceptions.OperacaoSemSucessoException;
import model.musica.Musica;
import model.musica.MusicaExplicita;
import model.musica.MusicaExplicitaInterface;
import model.playlists.Playlist;
import model.playlists.PlaylistAleatoria;
import model.playlists.PlaylistListaDeFavoritos;
import model.playlists.PlaylistPersonalizada;
import model.playlists.PlaylistPorGeneroETempo;
import model.playlists.PlaylistPreferenciasComTempoMaximo;
import model.playlists.PlaylistPreferenciasExplicitas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest {

    private Musica m1, m2, m3;
    private Playlist playlist;
    private Musica musica1, musica2;

    @BeforeEach
    public void setUp() {
        m1 = new Musica("Song1", "Artist1", "Editora1", "Letra1", "Notas1", "Pop", 180);
        m2 = new Musica("Song2", "Artist2", "Editora2", "Letra2", "Notas2", "Rock", 150);
        m3 = new Musica("Song3", "Artist3", "Editora3", "Letra3", "Notas3", "Pop", 200);
        m1.setNumeroDeReproducoes(10); // Para PlaylistListaDeFavoritos

        musica1 = new Musica("Song 1", "Artist 1", "Label 1", "Lyrics 1", "Notes 1", "Genre 1", 180);
        musica2 = new Musica("Song 2", "Artist 2", "Label 2", "Lyrics 2", "Notes 2", "Genre 2", 200);
        playlist = new Playlist("My Playlist", Arrays.asList(musica1, musica2)) {
            @Override
            public Playlist clone() {
                return null;
            }
        };
    }

    @Test
    public void testMusicaCloneAndEquals() {
        Musica copia = m1.clone();
        assertEquals(m1, copia);
        assertNotSame(m1, copia);
    }

    @Test
    public void testPlaylistAleatoria() {
        PlaylistAleatoria p = new PlaylistAleatoria("PlaylistTeste1",Arrays.asList(m1, m2, m3));
        assertDoesNotThrow(() -> {
            Musica m = p.getMusicaPorIndice(0);
            assertNotNull(m);
        });
    }

    @Test
    public void testPlaylistFavoritos() throws ColecaoDeMusicaVaziaException, OperacaoSemSucessoException, MusicaJaExisteException {
        PlaylistListaDeFavoritos p = new PlaylistListaDeFavoritos();
        
        // Adiciona apenas as músicas que o utilizador marcou como favoritas
        p.adicionarMusica(m1);
        p.adicionarMusica(m3);
    
        assertFalse(p.estaVazia());
    
        // Garante que conseguimos aceder a ela na playlist
        Musica m = p.getMusicaPorIndice(0);
        assertNotNull(m);
   
    }

    @Test
    public void testPlaylistPorGeneroETempo() throws ColecaoDeMusicaVaziaException {
        PlaylistPorGeneroETempo p = new PlaylistPorGeneroETempo("nome","Pop", 400, Arrays.asList(m1, m2, m3));
        assertFalse(p.estaVazia());
        Musica reproduzida = p.getMusicaPorIndice(0);
        assertEquals("Pop", reproduzida.getGenero());
    }

    @Test
    public void testPlaylistPersonalizadaSequencial() throws ColecaoDeMusicaVaziaException {
        PlaylistPersonalizada p = new PlaylistPersonalizada("PlaylistTeste2",Arrays.asList(m1, m2, m3));
        ReprodutorDeMusica reprodutor = new ReprodutorDeMusica();
        reprodutor.insereColecao(p);

        Reproducao r1 = reprodutor.reproduzProximaMusica();
        Reproducao r2 = reprodutor.reproduzProximaMusica();

        assertEquals(m1.getNome(), r1.getMusica().getNome());
        assertEquals(m2.getNome(), r2.getMusica().getNome());
    }

    @Test
    public void testPlaylistPersonalizadaAleatoriaToggle() {
        PlaylistPersonalizada p = new PlaylistPersonalizada("PlaylistTeste3",Arrays.asList(m1, m2, m3));
        ReprodutorDeMusica reprodutor = new ReprodutorDeMusica();
        reprodutor.insereColecao(p);
        reprodutor.setShuffle(true);
        assertDoesNotThrow(() -> reprodutor.reproduzProximaMusica());
        reprodutor.setShuffle(false);
        assertDoesNotThrow(() -> reprodutor.reproduzProximaMusica());
    }

    @Test
    public void testCriacaoPlaylistComTempoMaximo() throws OperacaoSemSucessoException, MusicaJaExisteException {
        Musica m1 = new Musica("Song1", "Artista1", "Editora1", "Letra1", "Notas1", "Pop", 200); // 3m20s
        Musica m2 = new Musica("Song2", "Artista2", "Editora2", "Letra2", "Notas2", "Rock", 250); // 4m10s
        Musica m3 = new Musica("Song3", "Artista3", "Editora3", "Letra3", "Notas3", "Jazz", 300); // 5m

        PlaylistListaDeFavoritos favoritos = new PlaylistListaDeFavoritos();
        favoritos.adicionarMusica(m1);
        favoritos.adicionarMusica(m2);
        favoritos.adicionarMusica(m3);

        //8 minutos (480 segundos)
        PlaylistPreferenciasComTempoMaximo playlist = new PlaylistPreferenciasComTempoMaximo("nome", 480, favoritos);

        assertNotNull(playlist);
        assertFalse(playlist.estaVazia());

        int duracaoTotal = playlist.getMusicas().stream()
                                    .mapToInt(Musica::getDuracaoEmSegundos)
                                    .sum();

        assertTrue(duracaoTotal <= 480, "A duração total deve estar dentro do limite");
        assertTrue(playlist.getMusicas().size() >= 1);
    }

    @Test
    public void testPlaylistFavoritosExplicitas() throws OperacaoSemSucessoException {
        Musica m1 = new Musica("Normal1", "Artista1", "Editora1", "Letra", "Notas", "Pop", 120);
        MusicaExplicita me1 = new MusicaExplicita("Explícita1", "Artista2", "Editora2", "Letra", "Notas", "Rock", 180);
        MusicaExplicita me2 = new MusicaExplicita("Explícita2", "Artista3", "Editora3", "Letra", "Notas", "Hip-hop", 200);

    
        PlaylistListaDeFavoritos favoritas = new PlaylistListaDeFavoritos("favorits",List.of(m1, me1, me2));

        PlaylistPreferenciasExplicitas playlist = new PlaylistPreferenciasExplicitas(favoritas);

        assertEquals(2, playlist.getMusicas().size());
        for (Musica m : playlist.getMusicas()) {
            assertTrue(m instanceof MusicaExplicitaInterface);
        }
    }

    @Test
    public void testGetNome() {
        assertEquals("My Playlist", playlist.getNome());
    }

    @Test
    public void testSetNome() {
        playlist.setNome("New Playlist Name");
        assertEquals("New Playlist Name", playlist.getNome());
    }

    @Test
    public void testGetMusicas() {
        List<Musica> musicas = playlist.getMusicas();
        assertEquals(2, musicas.size());
        assertEquals(musica1.getNome(), musicas.get(0).getNome());
        assertEquals(musica2.getNome(), musicas.get(1).getNome());
    }

    @Test
    public void testAdicionarMusica() throws MusicaJaExisteException {
        Musica musica3 = new Musica("Song 3", "Artist 3", "Label 3", "Lyrics 3", "Notes 3", "Genre 3", 220);
        playlist.adicionarMusica(musica3);
        assertEquals(3, playlist.getMusicas().size());
        assertEquals("Song 3", playlist.getMusicas().get(2).getNome());
    }

    @Test
    public void testEstaVazia() {
        assertFalse(playlist.estaVazia());
        playlist = new Playlist("Empty Playlist", new ArrayList<>()) {
            @Override
            public Playlist clone() {
                return null;
            }
        };
        assertTrue(playlist.estaVazia());
    }

    @Test
    public void testDuracaoTotal() {
        assertEquals(380, playlist.duracaoTotal());
    }

    @Test
    public void testGetMusicaPorIndice() throws ColecaoDeMusicaVaziaException {
        assertEquals(musica1, playlist.getMusicaPorIndice(0));
        assertEquals(musica2, playlist.getMusicaPorIndice(1));
    }

    @Test
    public void testGetMusicaPorIndiceThrowsException() {
        playlist = new Playlist("Empty Playlist", new ArrayList<>()) {
            @Override
            public Playlist clone() {
                return null;
            }
        };
        assertThrows(ColecaoDeMusicaVaziaException.class, () -> playlist.getMusicaPorIndice(0));
    }

    @Test
    public void testToString() {
        assertTrue(playlist.toString().contains("My Playlist"));
    }

}
