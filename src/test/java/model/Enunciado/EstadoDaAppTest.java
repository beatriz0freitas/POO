package model.Enunciado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import exceptions.AlbumJaExisteException;
import exceptions.AlbumNaoExisteException;
import exceptions.UtilizadorJaExisteException;
import exceptions.UtilizadorNaoExisteException;
import model.Album;
import model.SpotifUMModel;
import model.Utilizador;
import model.planosSubscricao.PlanoSubscricaoPremiumBase; // Usado para tornar playlist pública
import model.playlists.Playlist;
import model.playlists.PlaylistPersonalizada;

import java.util.ArrayList;
import java.util.Map;

public class EstadoDaAppTest {

    private SpotifUMModel model;
    private Utilizador utilizadorParaEstado;
    private Album albumParaEstado;

    @BeforeEach
    void setUp() throws UtilizadorJaExisteException, AlbumJaExisteException {
        model = new SpotifUMModel();
        
        utilizadorParaEstado = new Utilizador("User Para Estado", "Rua Estado", "estado@example.com");
        utilizadorParaEstado.setPlanoSubscricao(new PlanoSubscricaoPremiumBase()); // Para poder ter playlists públicas
        model.adicionarUtilizador(utilizadorParaEstado);
        
        albumParaEstado = new Album("Album Para Estado", "Artista Estado", new ArrayList<>());
        model.adicionarAlbum(albumParaEstado);
    }

    @Test
    void testGetUtilizadoresEstadoContemUtilizadoresAdicionadosESaoClones() {
        // Enunciado (p3, 3.1): "efectuar a salvaguarda e a leitura do estado"
        // Os métodos get...Estado() preparam os dados para a salvaguarda.
        Map<String, Utilizador> estadoUtilizadores = model.getUtilizadoresEstado();
        assertEquals(1, estadoUtilizadores.size());
        assertTrue(estadoUtilizadores.containsKey("estado@example.com"));
        
        Utilizador originalNoModel = null;
        try {
            originalNoModel = model.getUtilizadorPorEmail("estado@example.com"); // Pega um clone
        } catch (UtilizadorNaoExisteException e) { fail("Utilizador deveria existir"); }
        
        Utilizador doEstado = estadoUtilizadores.get("estado@example.com");
        assertNotSame(originalNoModel, doEstado, "Utilizador no estado deve ser um clone.");
        // A asserção acima pode falhar se o getUtilizadorPorEmail já retorna o mesmo clone que vai para o estado.
        // O importante é que modificar o 'doEstado' não modifique o que está no 'model.utilizadores'
        assertEquals(utilizadorParaEstado.getNome(), doEstado.getNome());
    }

    @Test
    void testGetAlbumsEstadoContemAlbunsAdicionadosESaoClones() {
        Map<String, Album> estadoAlbuns = model.getAlbumsEstado();
        assertEquals(1, estadoAlbuns.size());
        assertTrue(estadoAlbuns.containsKey("Album Para Estado"));

        Album originalNoModel = null;
        try {
            originalNoModel = model.getAlbum("Album Para Estado"); // Pega um clone
        } catch (AlbumNaoExisteException e) { fail("Álbum deveria existir"); }

        Album doEstado = estadoAlbuns.get("Album Para Estado");
        assertNotSame(originalNoModel, doEstado, "Álbum no estado deve ser um clone.");
        assertEquals(albumParaEstado.getArtista(), doEstado.getArtista());
    }

    @Test
    void testGetPlaylistEstadoContemPlaylistsPublicasESaoClones() throws Exception {
        PlaylistPersonalizada p = new PlaylistPersonalizada("Publica Para Estado", new ArrayList<>());
        model.adicionarPlaylistAoUtilizador(utilizadorParaEstado.getEmail(), p);
        model.tornarPlaylistPublica(utilizadorParaEstado.getEmail(), "Publica Para Estado");

        Map<String, Playlist> estadoPlaylists = model.getPlaylistEstado();
        assertEquals(1, estadoPlaylists.size());
        assertTrue(estadoPlaylists.containsKey("Publica Para Estado"));
        
        Playlist doEstado = estadoPlaylists.get("Publica Para Estado");
        // Para verificar se é clone da playlist que está no map de playlists públicas do modelo:
        // Não temos acesso direto ao map interno de playlists públicas do modelo para comparar referências.
        // Mas podemos verificar que não é a mesma referência que o objeto 'p' original.
        assertNotSame(p, doEstado, "Playlist no estado deve ser um clone da original 'p'.");
        assertEquals(p.getNome(), doEstado.getNome());
    }

    @Test
    void testGetUtilizadoresEstadoComModeloVazio() {
        SpotifUMModel modelVazio = new SpotifUMModel();
        Map<String, Utilizador> estado = modelVazio.getUtilizadoresEstado();
        assertTrue(estado.isEmpty());
    }

    @Test
    void testGetAlbumsEstadoComModeloVazio() {
        SpotifUMModel modelVazio = new SpotifUMModel();
        Map<String, Album> estado = modelVazio.getAlbumsEstado();
        assertTrue(estado.isEmpty());
    }

    @Test
    void testGetPlaylistEstadoComModeloVazio() {
        SpotifUMModel modelVazio = new SpotifUMModel();
        Map<String, Playlist> estado = modelVazio.getPlaylistEstado();
        assertTrue(estado.isEmpty());
    }
}