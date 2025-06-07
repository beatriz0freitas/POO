package model.Enunciado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import exceptions.*;
import model.planosSubscricao.PlanoSubscricaoPremiumBase;
import model.planosSubscricao.PlanoSubscricaoPremiumTop;
import model.playlists.Playlist;
import model.playlists.PlaylistAleatoria;
import model.playlists.PlaylistPersonalizada;
import model.playlists.PlaylistPorGeneroETempo;
import model.Album;
import model.musica.Musica;
import model.SpotifUMModel;
import model.Utilizador;
import model.planosSubscricao.PlanoSubscricaoFree;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections; // Adicionado para Collections.singletonList

public class PermissoesPlaylistsTest {

    private SpotifUMModel model;
    private Utilizador userFree;
    private Utilizador userPremiumBase;
    private Utilizador userPremiumTop;
    private Musica musicaQualquer; // Música para adicionar a playlists

    @BeforeEach
    void setUp() throws UtilizadorJaExisteException {
        model = new SpotifUMModel();
        
        userFree = new Utilizador("UserFreePL", "Rua FreePL", "freepl@example.com");
        userFree.setPlanoSubscricao(new PlanoSubscricaoFree()); // Plano default, mas explícito aqui
        model.adicionarUtilizador(userFree);

        userPremiumBase = new Utilizador("UserPremiumBasePL", "Rua PBPL", "pbpl@example.com");
        userPremiumBase.setPlanoSubscricao(new PlanoSubscricaoPremiumBase());
        model.adicionarUtilizador(userPremiumBase);
        
        userPremiumTop = new Utilizador("UserPremiumTopPL", "Rua PTPL", "ptpl@example.com");
        userPremiumTop.setPlanoSubscricao(new PlanoSubscricaoPremiumTop());
        model.adicionarUtilizador(userPremiumTop); // Pontos de adesão são testados em UserManagement

        musicaQualquer = new Musica("Sample Song", "Any Artist", "Any Label", "Lyrics", "Notes", "Genre", 180);
        // Para adicionar músicas a playlists, elas normalmente viriam de um álbum já existente no sistema.
        // Vamos simular isso adicionando a música a um álbum genérico no modelo.
        Album albumDeOrigem = new Album("Album Para Musicas Playlist", "Varios", Collections.singletonList(musicaQualquer));
        try {
            model.adicionarAlbum(albumDeOrigem);
        } catch (AlbumJaExisteException e) { /* ignorar se já existir de outro teste, idealmente isolar */ }
    }

    // --- Testes de Criação/Adição de Playlists à Biblioteca ---

    @Test
    void testUtilizadorPremiumBasePodeAdicionarPlaylistPersonalizadaASuaBiblioteca() throws UtilizadorNaoExisteException, PlanoDeSubscricaoNaoPermiteException {
        // Enunciado (p2, 1.3): "Premium Base, que permite ... criarem playlists"
        PlaylistPersonalizada playlist = new PlaylistPersonalizada("Minha Playlist PB", new ArrayList<>(Collections.singletonList(musicaQualquer)));
        model.adicionarPlaylistAoUtilizador(userPremiumBase.getEmail(), playlist);
        
        Utilizador u = model.getUtilizadorPorEmail(userPremiumBase.getEmail());
        assertTrue(u.getBiblioteca().getPlaylists().stream()
                    .anyMatch(p -> p.getNome().equals("Minha Playlist PB") && p instanceof PlaylistPersonalizada));
    }

    @Test
    void testUtilizadorPremiumTopPodeAdicionarPlaylistPersonalizadaASuaBiblioteca() throws UtilizadorNaoExisteException, PlanoDeSubscricaoNaoPermiteException {
        // Enunciado (p2-3, 1.3): "Premium Top que permite ... as funcionalidades do Premium Base"
        PlaylistPersonalizada playlist = new PlaylistPersonalizada("Minha Playlist PT", new ArrayList<>(Collections.singletonList(musicaQualquer)));
        model.adicionarPlaylistAoUtilizador(userPremiumTop.getEmail(), playlist);
        
        Utilizador u = model.getUtilizadorPorEmail(userPremiumTop.getEmail());
        assertTrue(u.getBiblioteca().getPlaylists().stream()
                    .anyMatch(p -> p.getNome().equals("Minha Playlist PT") && p instanceof PlaylistPersonalizada));
    }
    
    @Test
    void testUtilizadorFreeNaoPodeAdicionarPlaylistPersonalizadaASuaBiblioteca() throws UtilizadorNaoExisteException {
        // Enunciado (p2, 1.2): "Só os utilizadores premium é que podem construir playlists."
        // E (p2, 1.3) "Free... apenas permite reproduzir músicas aleatórias que o SpotifUM determina"
        PlaylistPersonalizada playlist = new PlaylistPersonalizada("Playlist Free Tentativa", new ArrayList<>());
        assertThrows(PlanoDeSubscricaoNaoPermiteException.class,() -> model.adicionarPlaylistAoUtilizador(userFree.getEmail(), playlist));
        
    }


    // --- Testes de Tornar Playlist Pública ---

    @Test
    void testUtilizadorPremiumBasePodeTornarSuaPlaylistPublica() throws Exception {
        // Enunciado (p3, 1.3): "Os utilizadores com planos Premium Base e Premium Top poderão criar playlists e torná-las públicas"
        PlaylistPersonalizada playlist = new PlaylistPersonalizada("Playlist Publica PB", new ArrayList<>());
        model.adicionarPlaylistAoUtilizador(userPremiumBase.getEmail(), playlist);
        
        model.tornarPlaylistPublica(userPremiumBase.getEmail(), "Playlist Publica PB");
        
        assertTrue(model.getPlaylistsPublicas().stream().anyMatch(p -> p.getNome().equals("Playlist Publica PB")));
    }

    @Test
    void testUtilizadorPremiumTopPodeTornarSuaPlaylistPublica() throws Exception {
        PlaylistPersonalizada playlist = new PlaylistPersonalizada("Playlist Publica PT", new ArrayList<>());
        model.adicionarPlaylistAoUtilizador(userPremiumTop.getEmail(), playlist);
        
        model.tornarPlaylistPublica(userPremiumTop.getEmail(), "Playlist Publica PT");
        
        assertTrue(model.getPlaylistsPublicas().stream().anyMatch(p -> p.getNome().equals("Playlist Publica PT")));
    }

    @Test
    void testUtilizadorFreeNaoPodeTornarPlaylistPublicaLancaExcecao() throws Exception {
        // Enunciado não menciona Free users tornando playlists públicas 

         Playlist playlist = new PlaylistAleatoria("Playlist Tentativa Publica Free", new ArrayList<>());
        model.adicionarPlaylistAleatoriaAoUtilizador(userFree.getEmail(), playlist);
        
        // Este teste VAI FALHAR com a implementação atual de PlanoSubscricaoFree, pois ele permite.
        // O teste é escrito CONFORME O ENUNCIADO (que infere restrição).
        assertThrows(PlanoDeSubscricaoNaoPermiteException.class,
                () -> model.tornarPlaylistPublica(userFree.getEmail(), "Playlist Tentativa Publica Free"));
    }

    @Test
    void testTornarPlaylistInexistenteDoUtilizadorPublicaLancaExcecao() {
        assertThrows(ColecaoNaoExisteNaBibliotecaException.class,
                () -> model.tornarPlaylistPublica(userPremiumBase.getEmail(), "Playlist Fantasma"));
    }

    @Test
    void testGetPlaylistsPublicasAposAdicao() throws Exception {
        PlaylistPersonalizada p1 = new PlaylistPersonalizada("PublicaUm", new ArrayList<>());
        PlaylistAleatoria p2 = new PlaylistAleatoria("PublicaDois", new ArrayList<>()); // Premium também pode criar aleatórias para si, se desejar
        
        model.adicionarPlaylistAoUtilizador(userPremiumBase.getEmail(), p1);
        model.tornarPlaylistPublica(userPremiumBase.getEmail(), "PublicaUm");
        
        model.adicionarPlaylistAoUtilizador(userPremiumTop.getEmail(), p2);
        model.tornarPlaylistPublica(userPremiumTop.getEmail(), "PublicaDois");

        List<Playlist> publicas = model.getPlaylistsPublicas();
        assertEquals(2, publicas.size());
        assertTrue(publicas.stream().anyMatch(p -> p.getNome().equals("PublicaUm")));
        assertTrue(publicas.stream().anyMatch(p -> p.getNome().equals("PublicaDois")));
    }
    

    
    @Test
    void testUtilizadorPremiumPodeCriarPlaylistPorGeneroETempo() throws Exception {
        // Enunciado (p2, 1.2): "Um outro tipo de playlist é aquele que podem ser definidas com base
        // num tempo máximo de um determinado género músical"
        // Assumimos que utilizadores Premium podem criar este tipo de playlist.
        Musica rockCurta = new Musica("Rock Breve", "Roqueiro", "R", "L", "N", "Rock", 120); // 2 min
        Musica rockLonga = new Musica("Rock Extenso", "Roqueiro", "R", "L", "N", "Rock", 300); // 5 min
        Album albumRock = new Album("Album De Rock Para Playlist", "Roqueiro", Arrays.asList(rockCurta, rockLonga));
        model.adicionarAlbum(albumRock);

        // A PlaylistPorGeneroETempo (no seu código) filtra as músicas no construtor.
        // Para o teste, precisamos de uma lista de *todas* as músicas disponíveis no sistema
        // ou de um subconjunto relevante para que a playlist possa filtrar.
        // O SpotifUMModel não tem um getTodasAsMusicas(). Vamos passar as do álbum criado.
        List<Musica> todasAsMusicasDoAlbum = albumRock.getMusicas();
        
        PlaylistPorGeneroETempo playlistRock6min = new PlaylistPorGeneroETempo(
            "Rock Ate 6 Minutos", "Rock", 360, todasAsMusicasDoAlbum);
            
        // A playlist é criada, agora adicionamos ao utilizador
        model.adicionarPlaylistAoUtilizador(userPremiumBase.getEmail(), playlistRock6min);
        
        Utilizador u = model.getUtilizadorPorEmail(userPremiumBase.getEmail());
        Playlist recuperada = u.getBiblioteca().getPlaylists().stream()
            .filter(p -> p.getNome().equals("Rock Ate 6 Minutos"))
            .findFirst().orElse(null);
            
        assertNotNull(recuperada);
        assertTrue(recuperada instanceof PlaylistPorGeneroETempo);
        // Verificar se as músicas foram filtradas corretamente (rockCurta + rockLonga = 420s > 360s)
        // A lógica de PlaylistPorGeneroETempo.filtrarPorGeneroETempo é crucial aqui.
        // Se o filtro for acumulativo: rockCurta (120s) entra. Acumulado = 120.
        // rockLonga (300s). 120 + 300 = 420. Se duracaoMax é 360, rockLonga não entra.
        assertEquals(1, recuperada.getMusicas().size(), "Apenas a música rockCurta deveria caber.");
        assertEquals("Rock Breve", recuperada.getMusicas().get(0).getNome());
    }

}