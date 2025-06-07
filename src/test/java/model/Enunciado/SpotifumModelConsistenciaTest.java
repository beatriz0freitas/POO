package model.Enunciado;

import model.Album;
import model.musica.Musica;
import model.playlists.Playlist;
import model.playlists.PlaylistPersonalizada;
import model.SpotifUMModel;
import model.Utilizador;
import model.planosSubscricao.PlanoSubscricaoPremiumBase; 

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled; 

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpotifumModelConsistenciaTest {

    private SpotifUMModel model;
    private Utilizador user1;
    private Utilizador user2;
    private Album albumA;
    private Musica musicaA1;
    private Musica musicaA2;
    private Album albumB; 
    private Musica musicaB1;

    @BeforeEach
    void setUp() throws Exception {
        model = new SpotifUMModel();

        user1 = new Utilizador("User One", "Addr1", "user1@example.com");
        user1.setPlanoSubscricao(new PlanoSubscricaoPremiumBase());
        model.adicionarUtilizador(user1);

        user2 = new Utilizador("User Two", "Addr2", "user2@example.com");
        user2.setPlanoSubscricao(new PlanoSubscricaoPremiumBase());
        model.adicionarUtilizador(user2);

        musicaA1 = new Musica("Song A1", "Artist A", "EditorA", "Lyrics A1", "Notes A1", "Pop", 180);
        musicaA2 = new Musica("Song A2", "Artist A", "EditorA", "Lyrics A2", "Notes A2", "Pop", 200);
        
        List<Musica> musicasAlbumA = new ArrayList<>();
        musicasAlbumA.add(musicaA1);
        musicasAlbumA.add(musicaA2);
        albumA = new Album("Album A", "Artist A", musicasAlbumA);
        model.adicionarAlbum(albumA);

        musicaB1 = new Musica("Song B1 Original Name", "Artist B Original", "EditorB", "Lyrics B1", "Notes B1", "Rock", 220);
        List<Musica> musicasAlbumB = new ArrayList<>();
        musicasAlbumB.add(musicaB1);
        albumB = new Album("Album B Original Name", "Artist B Original", musicasAlbumB);
        model.adicionarAlbum(albumB);
    }

    @Test
    void testRemoverAlbumDoSistemaDeveRemoverDasBibliotecasDosUtilizadores() throws Exception {
        model.adicionarAlbumABiblioteca(albumA, user1.getEmail());
        model.adicionarAlbumABiblioteca(albumA, user2.getEmail());

        Utilizador user1Antes = model.getUtilizadorPorEmail(user1.getEmail());
        assertTrue(user1Antes.getBiblioteca().getAlbuns().stream()
                        .anyMatch(a -> a.getNome().equals(albumA.getNome())),
                "Album A deveria estar na biblioteca do User1 antes da remoção.");

        Utilizador user2Antes = model.getUtilizadorPorEmail(user2.getEmail());
        assertTrue(user2Antes.getBiblioteca().getAlbuns().stream()
                        .anyMatch(a -> a.getNome().equals(albumA.getNome())),
                "Album A deveria estar na biblioteca do User2 antes da remoção.");

        model.removerAlbum(albumA);

        Utilizador user1Depois = model.getUtilizadorPorEmail(user1.getEmail());
        assertFalse(user1Depois.getBiblioteca().getAlbuns().stream()
                        .anyMatch(a -> a.getNome().equals(albumA.getNome())),
                "Album A NÃO deveria mais estar na biblioteca do User1 após remoção do sistema.");

        Utilizador user2Depois = model.getUtilizadorPorEmail(user2.getEmail());
        assertFalse(user2Depois.getBiblioteca().getAlbuns().stream()
                        .anyMatch(a -> a.getNome().equals(albumA.getNome())),
                "Album A NÃO deveria mais estar na biblioteca do User2 após remoção do sistema.");
    }

    @Test
    void testRemoverMusicaDoAlbumNoSistemaDeveRemoverDasCopiasDoAlbumNasBibliotecas() throws Exception {
        model.adicionarAlbumABiblioteca(albumA, user1.getEmail());

        Utilizador user1Antes = model.getUtilizadorPorEmail(user1.getEmail());
        Album albumNaBibAntes = user1Antes.getBiblioteca().getAlbuns().stream()
                .filter(a -> a.getNome().equals(albumA.getNome()))
                .findFirst().orElse(null);
        assertNotNull(albumNaBibAntes, "Album A deveria estar na biblioteca do User1.");
        assertTrue(albumNaBibAntes.getMusicas().stream().anyMatch(m -> m.getNome().equals(musicaA1.getNome())),
                "Musica A1 deveria estar no Album A da biblioteca do User1 antes da remoção.");
        assertEquals(2, albumNaBibAntes.getMusicas().size(), "Album A na biblioteca deveria ter 2 músicas antes.");

        model.removerMusicaDoAlbum(albumA.getNome(), musicaA1.getNome());

        Utilizador user1Depois = model.getUtilizadorPorEmail(user1.getEmail());
        Album albumNaBibDepois = user1Depois.getBiblioteca().getAlbuns().stream()
                .filter(a -> a.getNome().equals(albumA.getNome()))
                .findFirst().orElse(null);
        
        assertNotNull(albumNaBibDepois, "Album A ainda deveria estar na biblioteca do User1.");
        assertFalse(albumNaBibDepois.getMusicas().stream().anyMatch(m -> m.getNome().equals(musicaA1.getNome())),
                "Musica A1 NÃO deveria mais estar no Album A da biblioteca do User1.");
        assertTrue(albumNaBibDepois.getMusicas().stream().anyMatch(m -> m.getNome().equals(musicaA2.getNome())),
                "Musica A2 ainda deveria estar no Album A da biblioteca do User1.");
        assertEquals(1, albumNaBibDepois.getMusicas().size(), "Album A na biblioteca deveria ter apenas 1 música depois.");
    }

    @Test
    void testRemoverMusicaDoAlbumNoSistemaDeveRemoverDasPlaylistsDosUtilizadores() throws Exception {
        model.adicionarAlbumABiblioteca(albumA, user1.getEmail());

        PlaylistPersonalizada playlistUser1 = new PlaylistPersonalizada("Minhas Pop Hits", new ArrayList<>());
        Musica musicaA1Original = model.getAlbum(albumA.getNome()).getMusica(musicaA1.getNome());
        Musica musicaA2Original = model.getAlbum(albumA.getNome()).getMusica(musicaA2.getNome());

        playlistUser1.adicionarMusica(musicaA1Original.clone());
        playlistUser1.adicionarMusica(musicaA2Original.clone());
        model.adicionarPlaylistAoUtilizador(user1.getEmail(), playlistUser1);

        Utilizador user1Antes = model.getUtilizadorPorEmail(user1.getEmail());
        Playlist playlistNaBibAntes = user1Antes.getBiblioteca().getPlaylists().stream()
                .filter(p -> p.getNome().equals(playlistUser1.getNome()))
                .findFirst().orElse(null);
        assertNotNull(playlistNaBibAntes, "Playlist deveria estar na biblioteca do User1.");
        assertTrue(playlistNaBibAntes.getMusicas().stream().anyMatch(m -> m.getNome().equals(musicaA1.getNome()) && m.getInterprete().equals(musicaA1.getInterprete())),
                "Musica A1 deveria estar na playlist do User1 antes da remoção.");
        assertEquals(2, playlistNaBibAntes.getMusicas().size(), "Playlist deveria ter 2 músicas antes.");

        model.removerMusicaDoAlbum(albumA.getNome(), musicaA1.getNome());

        Utilizador user1Depois = model.getUtilizadorPorEmail(user1.getEmail());
        Playlist playlistNaBibDepois = user1Depois.getBiblioteca().getPlaylists().stream()
                .filter(p -> p.getNome().equals(playlistUser1.getNome()))
                .findFirst().orElse(null);

        assertNotNull(playlistNaBibDepois, "Playlist ainda deveria estar na biblioteca do User1.");
        assertFalse(playlistNaBibDepois.getMusicas().stream().anyMatch(m -> m.getNome().equals(musicaA1.getNome()) && m.getInterprete().equals(musicaA1.getInterprete())),
                "Musica A1 NÃO deveria mais estar na playlist do User1.");
        assertTrue(playlistNaBibDepois.getMusicas().stream().anyMatch(m -> m.getNome().equals(musicaA2.getNome()) && m.getInterprete().equals(musicaA2.getInterprete())),
                "Musica A2 ainda deveria estar na playlist do User1.");
        assertEquals(1, playlistNaBibDepois.getMusicas().size(), "Playlist deveria ter apenas 1 música depois.");
    }

    // --- Casos de Teste Adicionais ---

    @Test
    void testModificacaoPlaylistPessoalNaoAfetaPlaylistPublica() throws Exception {
        PlaylistPersonalizada p1User = new PlaylistPersonalizada("User1 Pop Time", new ArrayList<>());
        p1User.adicionarMusica(musicaA1.clone());
        model.adicionarPlaylistAoUtilizador(user1.getEmail(), p1User);
        model.tornarPlaylistPublica(user1.getEmail(), p1User.getNome());

        // Verificar playlist pública inicial
        Optional<Playlist> publicaOptAntes = model.getPlaylistsPublicas().stream()
            .filter(p -> p.getNome().equals(p1User.getNome())).findFirst();
        assertTrue(publicaOptAntes.isPresent(), "Playlist deveria ser pública.");
        assertEquals(1, publicaOptAntes.get().getMusicas().size(), "Playlist pública deveria ter 1 música.");
        assertTrue(publicaOptAntes.get().getMusicas().stream().anyMatch(m -> m.getNome().equals(musicaA1.getNome())), "Música A1 deveria estar na playlist pública.");

        // User1 modifica sua playlist pessoal
        Playlist pessoalUser1 = model.getUtilizadorPorEmail(user1.getEmail())
                                   .getBiblioteca().getPlaylists().stream()
                                   .filter(p -> p.getNome().equals(p1User.getNome())).findFirst().get();
        pessoalUser1.adicionarMusica(musicaA2.clone()); // Adiciona musicaA2 à pessoal
        
        // Verificar que a playlist pública não foi alterada
        Optional<Playlist> publicaOptDepois = model.getPlaylistsPublicas().stream()
            .filter(p -> p.getNome().equals(p1User.getNome())).findFirst();
        assertTrue(publicaOptDepois.isPresent(), "Playlist pública ainda deveria existir.");
        assertEquals(1, publicaOptDepois.get().getMusicas().size(), "Playlist pública ainda deveria ter 1 música.");
        assertTrue(publicaOptDepois.get().getMusicas().stream().anyMatch(m -> m.getNome().equals(musicaA1.getNome())), "Música A1 ainda deveria estar na playlist pública.");
        assertFalse(publicaOptDepois.get().getMusicas().stream().anyMatch(m -> m.getNome().equals(musicaA2.getNome())), "Música A2 NÃO deveria estar na playlist pública.");
    }

    @Test
    void testRemocaoUtilizadorNaoRemoveSuasPlaylistsPublicas() throws Exception {
        PlaylistPersonalizada p1User = new PlaylistPersonalizada("PublicaOrfa", new ArrayList<>());
        model.adicionarPlaylistAoUtilizador(user1.getEmail(), p1User);
        model.tornarPlaylistPublica(user1.getEmail(), p1User.getNome());

        assertTrue(model.getPlaylistsPublicas().stream().anyMatch(p->p.getNome().equals("PublicaOrfa")), "Playlist deveria ser pública antes da remoção do user.");

        model.removerUtilizador(user1); // Remove User1

        // Verificar que a playlist pública ainda existe
        assertTrue(model.getPlaylistsPublicas().stream().anyMatch(p->p.getNome().equals("PublicaOrfa")), "Playlist pública deveria permanecer após remoção do user.");
    }
    
    @Test
    void testModificacaoDetalhesAlbumMestreNaoAfetaCloneNaBiblioteca() throws Exception {
        model.adicionarAlbumABiblioteca(albumB, user1.getEmail()); // User1 tem clone de albumB

        // Modificar albumB no sistema (precisa de um método no SpotifUMModel ou acesso direto para o teste)
        Album albumMestre = model.getAlbum(albumB.getNome()); // Pega o "mestre"
        assertNotNull(albumMestre);
        albumMestre.setNome("Album B Nome Alterado");
        albumMestre.setArtista("Artist B Nome Alterado");
        // Nota: Se SpotifUMModel.getAlbum() retorna clone, esta modificação não afetaria o "mestre".
        // Para este teste funcionar como esperado (modificar o mestre), o getAlbum() do model
        // precisaria retornar a referência, ou teríamos um método model.updateAlbumDetails(albumOriginal, novosDetalhes).
        // Assumindo que `model.getAlbumsEstado().get(albumB.getNome())` nos dá a referência ao mestre para alteração no teste.
        model.getAlbumsEstado().get("Album B Original Name").setNome("Album B Nome Alterado Master");
        model.getAlbumsEstado().get("Album B Original Name").setArtista("Artist B Artista Alterado Master");


        Utilizador user1Depois = model.getUtilizadorPorEmail(user1.getEmail());
        Album albumNaBib = user1Depois.getBiblioteca().getAlbuns().stream()
                            .filter(a -> a.getArtista().equals("Artist B Original")) // Procura pelo artista original, pois nome pode ter mudado
                            .findFirst().orElse(null);
        
        assertNotNull(albumNaBib, "Clone do Album B deveria estar na biblioteca do user1.");
        assertEquals("Album B Original Name", albumNaBib.getNome(), "Nome do álbum na biblioteca não deveria mudar.");
        assertEquals("Artist B Original", albumNaBib.getArtista(), "Artista do álbum na biblioteca não deveria mudar.");
    }

    @Test
    void testModificacaoDetalhesMusicaMestreNaoAfetaClones() throws Exception {
        // Adiciona albumB (com musicaB1) à biblioteca e a uma playlist de user1
        model.adicionarAlbumABiblioteca(albumB, user1.getEmail());
        PlaylistPersonalizada playlistUser1 = new PlaylistPersonalizada("Playlist Com B1", new ArrayList<>());
        // Adiciona clone da musicaB1 (do albumB mestre) à playlist
        playlistUser1.adicionarMusica(model.getAlbum(albumB.getNome()).getMusica(musicaB1.getNome()).clone());
        model.adicionarPlaylistAoUtilizador(user1.getEmail(), playlistUser1);

        // Modificar musicaB1 no álbum mestre (albumB)
        // Assumindo acesso para modificar a música no álbum mestre
        Musica musicaMestre = model.getAlbum(albumB.getNome()).getMusica(musicaB1.getNome());
        assertNotNull(musicaMestre);
        musicaMestre.setNome("Song B1 Nome Alterado Master");
        musicaMestre.setInterprete("Artist B Interprete Alterado Master");

        // Verificar clone no álbum da biblioteca
        Utilizador user1Recarregado = model.getUtilizadorPorEmail(user1.getEmail());
        Album albumNaBib = user1Recarregado.getBiblioteca().getAlbuns().stream()
            .filter(a -> a.getNome().equals(albumB.getNome())).findFirst().orElseThrow();
        Musica musicaNoAlbumBib = albumNaBib.getMusicas().stream()
            .filter(m -> m.getInterprete().equals("Artist B Original")) // Procura pelo interprete original
            .findFirst().orElseThrow();
        assertEquals("Song B1 Original Name", musicaNoAlbumBib.getNome());

        // Verificar clone na playlist da biblioteca
        Playlist playlistNaBib = user1Recarregado.getBiblioteca().getPlaylists().stream()
            .filter(p -> p.getNome().equals("Playlist Com B1")).findFirst().orElseThrow();
        Musica musicaNaPlaylistBib = playlistNaBib.getMusicas().stream()
             .filter(m -> m.getInterprete().equals("Artist B Original")) // Procura pelo interprete original
            .findFirst().orElseThrow();
        assertEquals("Song B1 Original Name", musicaNaPlaylistBib.getNome());
    }

    @Test
    @Disabled("Funcionalidade de remoção de música do sistema em cascata para favoritos não implementada.")
    void testRemocaoMusicaSistemaRemoveDosFavoritos() throws Exception {
        model.adicionarMusicaAosFavoritos(user1, musicaA1); // User1 marca musicaA1 como favorita
        assertTrue(model.getUtilizadorPorEmail(user1.getEmail()).eFavorita(musicaA1), "MusicaA1 deveria ser favorita.");

        model.removerMusicaDoAlbum(albumA.getNome(), musicaA1.getNome()); // Remove musicaA1 do sistema

        assertFalse(model.getUtilizadorPorEmail(user1.getEmail()).eFavorita(musicaA1), "MusicaA1 não deveria mais ser favorita após remoção do sistema.");
    }

    @Test
    void testMusicaEmMultiplasPlaylistsSaoClonesIndependentes() throws Exception {
        model.adicionarAlbumABiblioteca(albumA, user1.getEmail()); // Para ter acesso a musicaA1

        PlaylistPersonalizada p1 = new PlaylistPersonalizada("Playlist Alpha", new ArrayList<>());
        PlaylistPersonalizada p2 = new PlaylistPersonalizada("Playlist Beta", new ArrayList<>());

        // Adiciona musicaA1 (ou seu clone) a ambas as playlists
        // A clonagem ocorre dentro de Playlist.adicionarMusica se for implementado assim,
        // ou aqui, se estamos a passar a música "mestre".
        // O model.getAlbum().getMusica() já retorna um clone.
        Musica musicaParaP1 = model.getAlbum(albumA.getNome()).getMusica(musicaA1.getNome());
        Musica musicaParaP2 = model.getAlbum(albumA.getNome()).getMusica(musicaA1.getNome());

        p1.adicionarMusica(musicaParaP1); // Playlist.adicionarMusica armazena o objeto passado (que já é clone do mestre)
        p2.adicionarMusica(musicaParaP2); // ou clona internamente.
                                        // A classe Playlist base não clona em adicionarMusica.
                                        // Se for desejado clones totalmente independentes até na playlist,
                                        // p1.adicionarMusica(musicaParaP1.clone()) seria necessário.
                                        // No entanto, musicaParaP1 e musicaParaP2 já são clones distintos do mestre.
        
        model.adicionarPlaylistAoUtilizador(user1.getEmail(), p1);
        model.adicionarPlaylistAoUtilizador(user1.getEmail(), p2);
        
        Utilizador userRecarregado = model.getUtilizadorPorEmail(user1.getEmail());
        Playlist playlist1Rec = userRecarregado.getBiblioteca().getPlaylists().stream().filter(pl -> pl.getNome().equals("Playlist Alpha")).findFirst().get();
        Playlist playlist2Rec = userRecarregado.getBiblioteca().getPlaylists().stream().filter(pl -> pl.getNome().equals("Playlist Beta")).findFirst().get();

        Musica m1_em_P1 = playlist1Rec.getMusicas().get(0);
        Musica m1_em_P2 = playlist2Rec.getMusicas().get(0);

        assertEquals(m1_em_P1.getNome(), m1_em_P2.getNome()); // Mesmo nome
        assertNotSame(m1_em_P1, m1_em_P2, "As instâncias da música nas duas playlists deveriam ser diferentes (clones).");
        
        // Modificar uma e verificar a outra
        m1_em_P1.setNumeroDeReproducoes(99);
        assertNotEquals(m1_em_P1.getNumeroDeReproducoes(), m1_em_P2.getNumeroDeReproducoes(), "Alterar uma instância não deve afetar a outra se forem clones distintos.");
        assertEquals(0, m1_em_P2.getNumeroDeReproducoes()); // Assumindo que a reprodução default é 0.
    }
}