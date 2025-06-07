package model.Enunciado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import exceptions.AlbumJaExisteException;
import exceptions.AlbumNaoExisteException;
import exceptions.MusicaJaExisteException;
import exceptions.MusicaNaoExisteException;
import model.Album;
import model.musica.Musica;
import model.SpotifUMModel;
import model.Utilizador;
import model.planosSubscricao.PlanoSubscricaoPremiumBase;
import model.playlists.Playlist;
import model.playlists.PlaylistAleatoria;
import model.playlists.PlaylistPersonalizada;

import java.util.ArrayList;


public class EstatisticasGeraisTest {

    private SpotifUMModel model;
    private Musica m1, m2, m3, m4;
    private Album albumA, albumB;

    @BeforeEach
    void setUp() throws AlbumJaExisteException, MusicaJaExisteException, MusicaNaoExisteException, AlbumNaoExisteException {
        model = new SpotifUMModel();

        // Enunciado (p3, 3.2): Estatísticas
        m1 = new Musica("Song 1 Stat", "Interprete Alpha", "L1", "Ly1", "N1", "Pop", 200);
        m2 = new Musica("Song 2 Stat", "Interprete Beta", "L2", "Ly2", "N2", "Rock", 300);
        m3 = new Musica("Song 3 Stat", "Interprete Alpha", "L3", "Ly3", "N3", "Pop", 400);
        m4 = new Musica("Song 4 Stat", "Interprete Gamma", "L4", "Ly4", "N4", "Jazz", 250);


        

        albumA = new Album("AlbumStatsGenA", "Interprete Alpha", new ArrayList<>());
        albumA.adicionaMusica(m1);
        albumA.adicionaMusica(m3); 
        model.adicionarAlbum(albumA);

        albumB = new Album("AlbumStatsGenB", "Interprete Beta", new ArrayList<>());
        albumB.adicionaMusica(m2); 
        albumB.adicionaMusica(m4); 
        model.adicionarAlbum(albumB);

        model.reproduzirMusica("Song 1 Stat","AlbumStatsGenA");
        model.reproduzirMusica("Song 1 Stat","AlbumStatsGenA");

        model.reproduzirMusica("Song 3 Stat","AlbumStatsGenA");
        model.reproduzirMusica("Song 3 Stat","AlbumStatsGenA");
        model.reproduzirMusica("Song 3 Stat","AlbumStatsGenA");


        model.reproduzirMusica("Song 2 Stat","AlbumStatsGenB");
        model.reproduzirMusica("Song 2 Stat","AlbumStatsGenB");
        model.reproduzirMusica("Song 2 Stat","AlbumStatsGenB");
        model.reproduzirMusica("Song 2 Stat","AlbumStatsGenB");
        model.reproduzirMusica("Song 2 Stat","AlbumStatsGenB");
        model.reproduzirMusica("Song 2 Stat","AlbumStatsGenB");

        model.reproduzirMusica("Song 4 Stat","AlbumStatsGenB");
        
    }

    @Test
    void testMusicaMaisReproduzida() throws AlbumNaoExisteException, MusicaNaoExisteException {
        // Requisito 3.2.1
        // Setar reproduções diretamente nas músicas do modelo
        Musica maisReproduzida = model.musicaMaisReproduzida();
        assertNotNull(maisReproduzida);
        assertEquals("Song 2 Stat", maisReproduzida.getNome());
        assertEquals(6, maisReproduzida.getNumeroDeReproducoes());
    }
    
    @Test
    void testMusicaMaisReproduzidaComModeloVazioOuSemMusicas() {
        SpotifUMModel model = new SpotifUMModel();
        Musica maisReproduzida = model.musicaMaisReproduzida();
        assertNull(maisReproduzida);
    }

    @Test
    void testGeneroMaisReproduzido() throws AlbumNaoExisteException, MusicaNaoExisteException {
        // Requisito 3.2.5: "qual o tipo de música mais reproduzida" (assumindo tipo=género)
        String genero = model.generoMaisReproduzido();
        assertEquals("Rock", genero);
    }
    
    @Test
    void testGeneroMaisReproduzidoComModeloVazioOuSemMusicas() {
        SpotifUMModel modelVazio = new SpotifUMModel();
        assertEquals("Nenhum género encontrado", modelVazio.generoMaisReproduzido());
    }

    @Test
    void testInterpreteMaisEscutado() throws AlbumNaoExisteException, MusicaNaoExisteException {
        // Requisito 3.2.2
        String interprete = model.interpreteMaisOuvido();
        assertEquals("Interprete Beta", interprete);
    }
    
    @Test
    void testInterpreteMaisEscutadoComModeloVazioOuSemMusicas() {
        SpotifUMModel modelVazio = new SpotifUMModel();
        assertEquals("Nenhum intérprete encontrado", modelVazio.interpreteMaisOuvido());
    }
    
    @Test
    void testQuantasPlaylistsPublicasExistem() throws Exception {
        // Requisito 3.2.6
        assertEquals(0, model.quantasPlaylistsPublicasExistem());
        
        Utilizador u = new Utilizador("TestUserPlStats", "Addr", "tpls@mail.com");
        u.setPlanoSubscricao(new PlanoSubscricaoPremiumBase()); // Premium para poder tornar pública
        model.adicionarUtilizador(u);
        
        Playlist p1 = new PlaylistAleatoria("PublicaStatsFile9_1", new ArrayList<>());
        model.adicionarPlaylistAoUtilizador(u.getEmail(), p1);
        model.tornarPlaylistPublica(u.getEmail(), p1.getNome());
        assertEquals(1, model.quantasPlaylistsPublicasExistem());

        Playlist p2 = new PlaylistPersonalizada("PublicaStatsFile9_2", new ArrayList<>());
        model.adicionarPlaylistAoUtilizador(u.getEmail(), p2);
        model.tornarPlaylistPublica(u.getEmail(), p2.getNome());
        assertEquals(2, model.quantasPlaylistsPublicasExistem());
    }
}