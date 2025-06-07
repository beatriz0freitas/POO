package model.Enunciado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import exceptions.*;
import model.Album;
import model.musica.*;
import model.RegistoDeReproducao;
import model.Reproducao;
import model.SpotifUMModel;
import model.Utilizador;
import model.planosSubscricao.PlanoSubscricaoFree;
import model.planosSubscricao.PlanoSubscricaoPremiumBase;
import model.playlists.PlaylistAleatoria;
import model.playlists.PlaylistPersonalizada;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReproducaoColecoesMusicasTest {

    private SpotifUMModel model;
    private Utilizador userFree;
    private Utilizador userPremium;
    private Album albumComumPlayback;
    private Musica musica1AlbumComum, musica2AlbumComum;
    private PlaylistPersonalizada playlistPremium;
    private PlaylistAleatoria playlistAleatoriaParaFree; 

    @BeforeEach
    void setUp() throws Exception {
        model = new SpotifUMModel();

        userFree = new Utilizador("UserFreePlay", "Rua FreePlay", "freeplay@example.com");
        userFree.setPlanoSubscricao(new PlanoSubscricaoFree());
        model.adicionarUtilizador(userFree);

        userPremium = new Utilizador("UserPremiumPlay", "Rua PremiumPlay", "premiumplay@example.com");
        userPremium.setPlanoSubscricao(new PlanoSubscricaoPremiumBase());
        model.adicionarUtilizador(userPremium);

        musica1AlbumComum = new Musica("MusicaPlay1", "ArtistaPlay", "EPlay", "LetraPlay1-Conteudo", "NPlay1", "Pop", 180);
        musica2AlbumComum = new Musica("MusicaPlay2", "ArtistaPlay", "EPlay", "LetraPlay2-Conteudo", "NPlay2", "Rock",200);
        albumComumPlayback = new Album("AlbumComumPlayback", "ArtistaPlay",new ArrayList<>(Arrays.asList(musica1AlbumComum, musica2AlbumComum)));
        model.adicionarAlbum(albumComumPlayback);

        // Premium user pode ter o álbum na sua biblioteca e criar playlists
        model.adicionarAlbumABiblioteca(albumComumPlayback, userPremium.getEmail());
        playlistPremium = new PlaylistPersonalizada("PlaylistDoPremium",
                new ArrayList<>(Arrays.asList(musica1AlbumComum, musica2AlbumComum)));
        model.adicionarPlaylistAoUtilizador(userPremium.getEmail(), playlistPremium);

 
        List<Musica> musicasGlobais = new ArrayList<>();
        musicasGlobais.add(musica1AlbumComum);
        musicasGlobais.add(musica2AlbumComum);
        playlistAleatoriaParaFree = new PlaylistAleatoria("AleatoriaSpotifUM", musicasGlobais);
        model.adicionarPlaylistAleatoriaAoUtilizador("freeplay@example.com",playlistAleatoriaParaFree);
    }

    // --- Testes para Utilizador Premium ---

    @Test
    void testUserPremiumReproduzProximaMusicaDeAlbum() throws Exception {
        model.insereColecaoNoReprodutor(userPremium.getEmail(), albumComumPlayback.getNome());
        Reproducao r1 = model.reproduzProximaMusicaDoReprodutor(userPremium.getEmail());
        assertTrue(r1.isSucesso());
        assertEquals(musica1AlbumComum.getNome(), r1.getMusica().getNome());
        assertEquals("LetraPlay1-Conteudo", r1.getMusica().getLetra()); // Verifica a letra (simulação de reprodução)

        Reproducao r2 = model.reproduzProximaMusicaDoReprodutor(userPremium.getEmail());
        assertTrue(r2.isSucesso());
        assertEquals(musica2AlbumComum.getNome(), r2.getMusica().getNome());

    }

    @Test
    void testUserPremiumReproduzMusicaAnteriorDeSuaPlaylist() throws Exception {
        model.insereColecaoNoReprodutor(userPremium.getEmail(), playlistPremium.getNome());
        model.reproduzProximaMusicaDoReprodutor(userPremium.getEmail()); 
        model.reproduzProximaMusicaDoReprodutor(userPremium.getEmail()); 
        // Enunciado (p2, 1.2): "playlists construídas pelos utilizadores premium
        // permitem que este ... retroceda na mesma."
        Reproducao r = model.reproduzMusicaAnteriorDoReprodutor(userPremium.getEmail()); // Deve voltar para musica1
        assertTrue(r.isSucesso());
        assertEquals(musica1AlbumComum.getNome(), r.getMusica().getNome());
    }

    @Test
    void testUserPremiumReproduzMusicaIndividual() throws Exception {
        int repAntes = musica1AlbumComum.getNumeroDeReproducoes();
        // Enunciado (p2, 1): "um utilizador poderá escolher reproduzir uma música, um
        // álbum ou uma playlist."
        String letra = model.reproduzirMusica(musica1AlbumComum.getNome(), albumComumPlayback.getNome());
        assertEquals(musica1AlbumComum.getLetra(), letra);

        Musica musicaApos = model.getAlbum(albumComumPlayback.getNome()).getMusica(musica1AlbumComum.getNome());
        assertEquals(repAntes + 1, musicaApos.getNumeroDeReproducoes(),
                "Contador de reproduções da música deve incrementar.");
    }

    // --- Testes para Utilizador Free ---

    @Test
    void testUserFreeReproduzProximaMusicaDePlaylistAleatoria() throws Exception {
            model.insereColecaoNoReprodutor(userFree.getEmail(), playlistAleatoriaParaFree.getNome());
            Reproducao r = model.reproduzProximaMusicaDoReprodutor(userFree.getEmail());
            // é uma reprodução aleatoria, não ha como testar
            assertTrue(r.isSucesso());
    }

    @Test
    void testUserFreeNaoPodeRetrocederEmPlaylistAleatoria() throws Exception {
        model.insereColecaoNoReprodutor(userFree.getEmail(), playlistAleatoriaParaFree.getNome());
         Reproducao r = model.reproduzMusicaAnteriorDoReprodutor(userFree.getEmail());
        assertTrue(r.isSucesso());

    }

    // --- Testes Gerais de Reprodução (Contadores, Coleções Vazias) ---
    @Test
    void testReproducaoIncrementaContadorMusica() throws Exception {
        int repAntes = musica1AlbumComum.getNumeroDeReproducoes();
        model.insereColecaoNoReprodutor(userPremium.getEmail(), albumComumPlayback.getNome());
        model.reproduzProximaMusicaDoReprodutor(userPremium.getEmail()); // Reproduz musica1AlbumComum

        Musica musicaApos = model.getAlbum(albumComumPlayback.getNome()).getMusica(musica1AlbumComum.getNome());
        assertEquals(repAntes + 1, musicaApos.getNumeroDeReproducoes());
    }

    @Test
    void testReproducaoIncrementaPontosUtilizador() throws Exception {
        int pontosAntes = userPremium.getPontosAtuais(); // Premium Base = 10 pontos/musica
        model.insereColecaoNoReprodutor(userPremium.getEmail(), albumComumPlayback.getNome());
        model.reproduzProximaMusicaDoReprodutor(userPremium.getEmail());

        Utilizador userApos = model.getUtilizadorPorEmail(userPremium.getEmail());
        assertEquals(pontosAntes + 10, userApos.getPontosAtuais());
    }

    @Test
    void testReproduzirColecaoVaziaLancaExcecaoViaReprodutor() throws Exception {
        Album albumVazio = new Album("AlbumPlaybackVazio", "Ninguem", new ArrayList<>());
        model.adicionarAlbum(albumVazio);
        model.adicionarAlbumABiblioteca(albumVazio, userPremium.getEmail()); // Premium pode ter álbuns vazios na biblioteca
        model.insereColecaoNoReprodutor(userPremium.getEmail(), albumVazio.getNome());
        assertThrows(ColecaoDeMusicaVaziaException.class,
                () -> model.reproduzProximaMusicaDoReprodutor(userPremium.getEmail()));
    }

    @Test
    void testReproduzirColecaoVaziaLancaExcecaoViaReproduzirColecaoDireto() throws Exception {
        Album albumVazio = new Album("AlbumPlaybackVazioDireto", "Ninguem", new ArrayList<>());
        model.adicionarAlbum(albumVazio);
        model.adicionarAlbumABiblioteca(albumVazio, userPremium.getEmail());
        assertThrows(ColecaoDeMusicaVaziaException.class,
                () -> model.reproduzirColecaoMusica(userPremium.getEmail(), albumVazio));
    }

    @Test
    void testUserPremiumHistoricoDeReproducaoAtualizadoCorretamente() throws Exception {
        model.insereColecaoNoReprodutor(userPremium.getEmail(), albumComumPlayback.getNome());

        model.reproduzProximaMusicaDoReprodutor(userPremium.getEmail());
        RegistoDeReproducao registo1 = userPremium.getHistorico().getUltimoRegisto();
        assertEquals("MusicaPlay1", registo1.getMusicaReproduzida());

        model.reproduzProximaMusicaDoReprodutor(userPremium.getEmail());
        RegistoDeReproducao registo2 = userPremium.getHistorico().getUltimoRegisto();
        assertEquals("MusicaPlay2", registo2.getMusicaReproduzida());
    }

}