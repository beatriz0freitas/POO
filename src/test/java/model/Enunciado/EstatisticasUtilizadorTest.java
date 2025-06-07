package model.Enunciado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import exceptions.*;
import model.*;
import model.musica.*;
import model.planosSubscricao.*;
import model.playlists.Playlist;
import model.playlists.PlaylistAleatoria;
import model.playlists.PlaylistPersonalizada;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EstatisticasUtilizadorTest {

    private SpotifUMModel model;
    private Utilizador u1PremiumBaseStats, u2PremiumBaseStats, u3PremiumBaseStats; // Simplificado para PremiumBase
    private Musica musicaStats1, musicaStats2;
    private Album albumStats;

    @BeforeEach
    void setUp() throws Exception {
        model = new SpotifUMModel();

        // Usar PremiumBase para simplificar cenários de reprodução,
        // pois podem ter bibliotecas e escolher o que ouvir.
        u1PremiumBaseStats = new Utilizador("UserPB1", "AddrPB1", "upb1@mail.com");
        u1PremiumBaseStats.setPlanoSubscricao(new PlanoSubscricaoPremiumBase());
        model.adicionarUtilizador(u1PremiumBaseStats);

        u2PremiumBaseStats = new Utilizador("UserPB2", "AddrPB2", "upb2@mail.com");
        u2PremiumBaseStats.setPlanoSubscricao(new PlanoSubscricaoPremiumBase());
        model.adicionarUtilizador(u2PremiumBaseStats);

        u3PremiumBaseStats = new Utilizador("UserPB3", "AddrPB3", "upb3@mail.com");
        u3PremiumBaseStats.setPlanoSubscricao(new PlanoSubscricaoPremiumBase());
        model.adicionarUtilizador(u3PremiumBaseStats);

        musicaStats1 = new Musica("Musica Pop Stat", "Artista Pop", "EPop", "Letra Pop", "Notas Pop", "Pop", 180);
        musicaStats2 = new Musica("Musica Rock Stat", "Artista Rock", "ERock", "Letra Rock", "Notas Rock", "Rock", 200);
        albumStats = new Album("Album Principal Stats", "Varios Artistas", Arrays.asList(musicaStats1, musicaStats2));
        model.adicionarAlbum(albumStats);

        // Utilizadores PremiumBase adicionam o álbum à sua biblioteca
        model.adicionarAlbumABiblioteca(albumStats, u1PremiumBaseStats.getEmail());
        model.adicionarAlbumABiblioteca(albumStats, u2PremiumBaseStats.getEmail());
        model.adicionarAlbumABiblioteca(albumStats, u3PremiumBaseStats.getEmail());
    }

    /**
     * Simula UMA reprodução (a próxima música disponível do álbum especificado)
     * usando os métodos do modelo. A data da reprodução será LocalDateTime.now().
     * Atualiza pontos e histórico automaticamente via observers.
     */
    private Reproducao simularReproducaoUnicaViaModelo(Utilizador user, Album albumDaColecao) throws Exception {
        // Garante que o utilizador pode aceder a esta coleção (já feito no setup para PremiumBase)
        model.insereColecaoNoReprodutor(user.getEmail(), albumDaColecao.getNome());
        Reproducao rep = model.reproduzProximaMusicaDoReprodutor(user.getEmail());

        if (rep == null || !rep.isSucesso()) {
            throw new Exception("Falha ao simular reprodução para " + user.getEmail() + " do álbum " + albumDaColecao.getNome() +
                                (rep != null ? ". Mensagem: " + rep.getMusica().getNome() : ""));
        }
        return rep;
    }

    @Test
    void testUtilizadorMaisPontosAposReproducoes() throws Exception {
        // Requisito 3.2.4
        // Todos são PremiumBase (10 pontos/música). Pontos iniciais = 0.

        // User1 ouve 1 música
        simularReproducaoUnicaViaModelo(u1PremiumBaseStats, albumStats); // u1Stats: 10 pontos (musicaStats1)

        // User2 ouve 2 músicas
        simularReproducaoUnicaViaModelo(u2PremiumBaseStats, albumStats); // u2Stats: 10 pontos (musicaStats1)
        // Para tocar a proxima (musicaStats2), não precisamos reinserir a coleção.
        model.reproduzProximaMusicaDoReprodutor(u2PremiumBaseStats.getEmail()); // u2Stats: 10+10 = 20 pontos (musicaStats2)
        
        // User3 ouve 0 músicas (para este teste de pontos)

        // Verificar pontos:
        assertEquals(10, model.getUtilizadorPorEmail(u1PremiumBaseStats.getEmail()).getPontosAtuais());
        assertEquals(20, model.getUtilizadorPorEmail(u2PremiumBaseStats.getEmail()).getPontosAtuais());
        assertEquals(0, model.getUtilizadorPorEmail(u3PremiumBaseStats.getEmail()).getPontosAtuais());

        Utilizador maisPontos = model.utilizadorComMaisPontos();
        assertNotNull(maisPontos);
        assertEquals(u2PremiumBaseStats.getEmail(), maisPontos.getEmail());
    }

    @Test
    void testUtilizadorMaisPontosComModeloVazio() {
        SpotifUMModel modelVazio = new SpotifUMModel();
        assertThrows(java.util.NoSuchElementException.class, modelVazio::utilizadorComMaisPontos);
    }

    @Test
    void testUtilizadorComMaisPlaylistsNaSuaBiblioteca() throws Exception {
        // Requisito 3.2.7
        // u1PremiumBaseStats, u2PremiumBaseStats, u3PremiumBaseStats são PremiumBase e podem ter playlists.

        Playlist p1 = new PlaylistAleatoria("Stats_P1", new ArrayList<>());
        Playlist p2 = new PlaylistPersonalizada("Stats_P2", new ArrayList<>());

        // u1 tem 1 playlist
        model.adicionarPlaylistAoUtilizador(u1PremiumBaseStats.getEmail(), p1);

        // u2 tem 2 playlists
        model.adicionarPlaylistAoUtilizador(u2PremiumBaseStats.getEmail(), p1);
        model.adicionarPlaylistAoUtilizador(u2PremiumBaseStats.getEmail(), p2);

        // u3 tem 0 playlists (para este teste)

        List<Utilizador> utilizadoresParaEstatistica = Arrays.asList(
            model.getUtilizadorPorEmail(u1PremiumBaseStats.getEmail()),
            model.getUtilizadorPorEmail(u2PremiumBaseStats.getEmail()),
            model.getUtilizadorPorEmail(u3PremiumBaseStats.getEmail())
        );

        Utilizador maisPlaylists = model.utilizadorComMaisPlaylists(utilizadoresParaEstatistica);
        assertNotNull(maisPlaylists);
        assertEquals(u2PremiumBaseStats.getEmail(), maisPlaylists.getEmail());
        assertEquals(2, model.getUtilizadorPorEmail(u2PremiumBaseStats.getEmail()).getBiblioteca().numeroDePlaylist());
        assertEquals(1, model.getUtilizadorPorEmail(u1PremiumBaseStats.getEmail()).getBiblioteca().numeroDePlaylist());
        assertEquals(0, model.getUtilizadorPorEmail(u3PremiumBaseStats.getEmail()).getBiblioteca().numeroDePlaylist());
    }

    @Test
    void testUtilizadorComMaisPlaylistsComModeloVazioOuSemPlaylists() throws UtilizadorJaExisteException {
        SpotifUMModel modelVazio = new SpotifUMModel();
        assertNull(modelVazio.utilizadorComMaisPlaylists(new ArrayList<>()));

        Utilizador uSemPlaylists = new Utilizador("NoPUserStats", "AddrNP", "np_stats@mail.com");
        uSemPlaylists.setPlanoSubscricao(new PlanoSubscricaoPremiumBase());
        modelVazio.adicionarUtilizador(uSemPlaylists);
        
        Utilizador resultado = modelVazio.utilizadorComMaisPlaylists(modelVazio.getUtilizadores());
        assertNotNull(resultado);
        assertEquals(uSemPlaylists.getEmail(), resultado.getEmail());
        assertEquals(0, resultado.getBiblioteca().numeroDePlaylist());
    }

    @Test
    void testUtilizadorMaisOuviuNumPeriodoDefinidoOuDesdeSempre() throws Exception {
        // Requisito 3.2.3
        // Com a simplificação, o controle exato de datas passadas é difícil.
        // As reproduções via `simularUmaReproducaoViaModelo` usarão `LocalDateTime.now()`.
        // Vamos focar em:
        // 1. Verificar se as contagens de reprodução no histórico são corretas após as simulações.
        // 2. Testar "desde sempre" (que deve contar todas as reproduções simuladas).
        // 3. Testar um período muito curto (ex: últimos 5 minutos) que abranja as reproduções do teste.

        LocalDateTime inicioTeste = LocalDateTime.now(); // Marcar o início do teste

        // User1 ouve musicaStats1 (1 vez)
        simularReproducaoUnicaViaModelo(u1PremiumBaseStats, albumStats);

        // User2 ouve musicaStats1 e depois musicaStats2 (2 vezes)
        simularReproducaoUnicaViaModelo(u2PremiumBaseStats, albumStats); // Toca musicaStats1
        model.reproduzProximaMusicaDoReprodutor(u2PremiumBaseStats.getEmail()); // Toca musicaStats2
        
        // User3 ouve musicaStats1 (1 vez)
        simularReproducaoUnicaViaModelo(u3PremiumBaseStats, albumStats);

        LocalDateTime fimTeste = LocalDateTime.now(); // Marcar o fim das reproduções

        // Verificar contagens no histórico (assumindo que o histórico é preenchido corretamente)
        assertEquals(1, model.getUtilizadorPorEmail(u1PremiumBaseStats.getEmail()).getHistorico().getHistorico().size());
        assertEquals(2, model.getUtilizadorPorEmail(u2PremiumBaseStats.getEmail()).getHistorico().getHistorico().size());
        assertEquals(1, model.getUtilizadorPorEmail(u3PremiumBaseStats.getEmail()).getHistorico().getHistorico().size());

        List<Utilizador> todosUtilizadores = model.getUtilizadores();

        // Teste para o período que abrange as reproduções feitas durante o teste
        Utilizador maisOuviuNoPeriodoDoTeste = model.utilizadorMaisOuviuNumPeriodo(todosUtilizadores, inicioTeste.minusSeconds(1), fimTeste.plusSeconds(0));
        assertNotNull(maisOuviuNoPeriodoDoTeste);
        assertEquals(u2PremiumBaseStats.getEmail(), maisOuviuNoPeriodoDoTeste.getEmail(), "User2 ouviu mais (2 musicas) durante o período do teste.");
        
        // Teste para "desde sempre" (usando um início muito antigo)
        // Deve dar o mesmo resultado se estas foram as únicas reproduções.
        Utilizador maisOuviuSempre = model.utilizadorMaisOuviuNumPeriodo(todosUtilizadores, LocalDateTime.MIN, fimTeste.plusSeconds(0));
        assertNotNull(maisOuviuSempre);
        assertEquals(u2PremiumBaseStats.getEmail(), maisOuviuSempre.getEmail(), "User2 ouviu mais (2 musicas) desde sempre.");

        // Teste para um período que NÃO abrange as reproduções (ex: no futuro)
        Utilizador maisOuviuFuturo = model.utilizadorMaisOuviuNumPeriodo(todosUtilizadores, fimTeste.plusMinutes(5), fimTeste.plusMinutes(10));
        assertNull(maisOuviuFuturo, "Ninguém deveria ter ouvido música num período futuro.");
    }
    
    @Test
    void testUtilizadorMaisOuviuNumPeriodoSemUtilizadoresOuSemReproducoesNoPeriodo() throws UtilizadorJaExisteException {
        SpotifUMModel modelVazio = new SpotifUMModel();
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime antes = agora.minusHours(1);
        assertNull(modelVazio.utilizadorMaisOuviuNumPeriodo(new ArrayList<>(), antes, agora));

        Utilizador uSemRep = new Utilizador("UserSemRepEstatistica", "AddrSRS", "usrs_stat@mail.com");
        uSemRep.setPlanoSubscricao(new PlanoSubscricaoPremiumBase());
        modelVazio.adicionarUtilizador(uSemRep);
        assertNull(modelVazio.utilizadorMaisOuviuNumPeriodo(modelVazio.getUtilizadores(), antes, agora));
    }
}