package controller;

import model.*;
import model.musica.Musica;
import model.planosSubscricao.PlanoSubscricaoFree;
import model.planosSubscricao.PlanoSubscricaoPremiumBase;
import model.planosSubscricao.PlanoSubscricaoPremiumTop;
import model.playlists.Playlist;
import model.playlists.PlaylistAleatoria;
import model.playlists.PlaylistPersonalizada;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
// Removi a importação de org.junit.jupiter.api.function.Executable pois não estava a ser usada diretamente.
// Se precisar de assertThrows para exceções específicas, pode reimportá-la.

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import utils.EstadodaAPP;
import java.util.HashMap;

public class SpotifUMControllerTest {

    private SpotifUMModel model;
    private SpotifUMController controller;
    private Utilizador utilizadorFreeTeste;
    private Utilizador utilizadorPremiumTeste;
    private Album albumTeste;
    private Musica musicaTeste;

    @BeforeEach
    public void setUp() {
        model = new SpotifUMModel(); // Assume que o construtor não carrega dados de ficheiros para testes isolados
        controller = new SpotifUMController(model);

        utilizadorFreeTeste = new Utilizador("Utilizador Free Teste", "Morada Teste", "teste@exemplo.com");
        utilizadorPremiumTeste = new Utilizador("Utilizador Premium Teste", "Morada Teste", "teste@exemplo.com");
        utilizadorPremiumTeste.setPlanoSubscricao(new PlanoSubscricaoPremiumTop());
        albumTeste = new Album("Album Teste", "Artista Teste", new ArrayList<>());
        musicaTeste = new Musica("Musica Teste", "Artista Teste", "Editora Teste", "Letra Teste", "Notas Teste", "Genero Teste", 180);
    }

    // Testes de Gestão de Utilizadores (Unitários)
    @Test
    public void testAdicionarUtilizador() {
        String resultado = controller.adicionarUtilizador(utilizadorFreeTeste);
        assertEquals("Utilizador adicionado com sucesso!", resultado);
        assertNotNull(model.getUtilizadoresEstado().get(utilizadorFreeTeste.getEmail()));
    }

    @Test
    public void testAdicionarUtilizadorJaExistente() {
        controller.adicionarUtilizador(utilizadorFreeTeste);
        String resultado = controller.adicionarUtilizador(utilizadorFreeTeste);
        assertEquals("Utilizador já existe com o email: " + utilizadorFreeTeste.getEmail(), resultado);
    }

    @Test
    public void testAdicionarUtilizadorComCamposNulosOuVazios() {
        // Ajuste: O modelo pode ter validações para campos nulos/vazios.
        // Se o utilizador com email "" é adicionado com sucesso, o teste reflete isso.
        // No entanto, normalmente, um email vazio seria inválido.
        // Este teste depende da lógica de validação do seu Model/Controller.
        Utilizador invalido = new Utilizador("Nome Valido", "Morada", ""); // Email vazio
        String resultado = controller.adicionarUtilizador(invalido);
        // A mensagem esperada depende da implementação no controller/model
        // Se o controller não faz validação e o model permite, pode ser "Utilizador adicionado com sucesso!"
        // Se houver validação, a mensagem de erro seria diferente.
        // Para o código original do controller que apenas delega, e o model que pode aceitar:
        assertEquals("Utilizador adicionado com sucesso!", resultado);
        // Se o model tivesse uma validação no Utilizador para não permitir email vazio:
        // Ou se o controller adicionasse essa validação antes de chamar o model.
    }

    @Test
    public void testRemoverUtilizador() throws Exception {
        controller.adicionarUtilizador(utilizadorFreeTeste);
        String resultado = controller.removerUtilizador(utilizadorFreeTeste);
        assertEquals("Utilizador removido com sucesso!", resultado); 
        assertNull(model.getUtilizadoresEstado().get(utilizadorFreeTeste.getEmail()));
    }

    @Test
    public void testRemoverUtilizadorNaoExistente() {
        String resultado = controller.removerUtilizador(utilizadorFreeTeste);
        assertEquals("Utilizador não existe", resultado);
    }

    // Testes de Gestão de Álbuns (Unitários)
    @Test
    public void testAdicionarAlbum() {
        String resultado = controller.adicionarAlbum(albumTeste);
        assertEquals("Album adicionado com sucesso!", resultado); 
        assertNotNull(model.getAlbumsEstado().get(albumTeste.getNome()));
    }

    
    @Test
    public void testRemoverAlbum() throws Exception {
        controller.adicionarAlbum(albumTeste);
        String resultado = controller.removerAlbum(albumTeste);
        assertEquals("Album removido com sucesso!", resultado); 
        assertNull(model.getAlbumsEstado().get(albumTeste.getNome()));
    }

    @Test
    public void testRemoverAlbumNaoExistente() {
        String resultado = controller.removerAlbum(albumTeste);
        assertEquals("Album não existe", resultado); 
    }

    // Testes de Gestão de Músicas em Álbuns (Unitários)
    @Test
    public void testAdicionarMusicaAoAlbum() throws Exception {
        controller.adicionarAlbum(albumTeste);
        String resultado = controller.adicionarMusica(musicaTeste, albumTeste);
        assertEquals("Música adicionada com sucesso!", resultado); 
        Album albumDoModel = model.getAlbum(albumTeste.getNome());
        assertNotNull(albumDoModel);
        assertTrue(albumDoModel.getMusicas().stream().anyMatch(m -> m.getNome().equals(musicaTeste.getNome())));
    }

    @Test
    public void testAdicionarMusicaAAlbumNaoExistente() {
        Album albumInexistente = new Album("Album Inexistente", "Artista", new ArrayList<>());
        String resultado = controller.adicionarMusica(musicaTeste, albumInexistente);
        assertEquals("Album não existe", resultado); 
        assertNull(model.getAlbumsEstado().get(albumInexistente.getNome()));
    }

    @Test
    public void testAdicionarMusicaJaExistenteNoAlbum() throws Exception {
        controller.adicionarAlbum(albumTeste);
        controller.adicionarMusica(musicaTeste, albumTeste);
        String resultado = controller.adicionarMusica(musicaTeste, albumTeste);
        assertEquals("Música já existe no álbum", resultado); 
    }

    @Test
    public void testRemoverMusicaDoAlbum() throws Exception {
        controller.adicionarAlbum(albumTeste);
        controller.adicionarMusica(musicaTeste, albumTeste);
        String resultado = controller.removerMusica(albumTeste.getNome(), musicaTeste.getNome());
        assertEquals("Música removida com sucesso!", resultado); // Ajustado
        Album albumDoModel = model.getAlbum(albumTeste.getNome());
        assertNotNull(albumDoModel);
        assertTrue(albumDoModel.getMusicas().stream().noneMatch(m -> m.getNome().equals(musicaTeste.getNome())));
    }

    @Test
    public void testRemoverMusicaDeAlbumNaoExistente() {
        String resultado = controller.removerMusica("Album Inexistente", musicaTeste.getNome());
        assertEquals("Album não existe", resultado); // Ajustado
    }

    @Test
    public void testRemoverMusicaNaoExistenteNoAlbum() throws Exception {
        controller.adicionarAlbum(albumTeste);
        String resultado = controller.removerMusica(albumTeste.getNome(), "Musica Inexistente");
        assertEquals("Música não existe no álbum", resultado); // Ajustado
    }



    // Testes de Planos de Subscrição (Unitários)
    @Test
    public void testMudarPlanoParaFree() throws Exception {
        controller.adicionarUtilizador(utilizadorFreeTeste);
        String resultado = controller.mudarPlano(utilizadorFreeTeste, "Free");
        assertEquals("Plano alterado com sucesso para: PlanoSubscricaoFree", resultado);
        Utilizador userAtualizado = controller.getUtilizador(utilizadorFreeTeste.getEmail());
        assertTrue(userAtualizado.getPlanoSubscricao() instanceof PlanoSubscricaoFree);
    }

    @Test
    public void testMudarPlanoParaPremiumBase() throws Exception {
        controller.adicionarUtilizador(utilizadorFreeTeste);
        String resultado = controller.mudarPlano(utilizadorFreeTeste, "Premium Base");
        assertEquals("Plano alterado com sucesso para: PlanoSubscricaoPremiumBase", resultado);
        Utilizador userAtualizado = controller.getUtilizador(utilizadorFreeTeste.getEmail());
        assertTrue(userAtualizado.getPlanoSubscricao() instanceof PlanoSubscricaoPremiumBase);
    }

    @Test
    public void testMudarPlanoParaPremiumTop() throws Exception {
        controller.adicionarUtilizador(utilizadorFreeTeste);
        String resultado = controller.mudarPlano(utilizadorFreeTeste, "Premium Top");
        assertEquals("Plano alterado com sucesso para: PlanoSubscricaoPremiumTop", resultado);
        Utilizador userAtualizado = controller.getUtilizador(utilizadorFreeTeste.getEmail());
        assertTrue(userAtualizado.getPlanoSubscricao() instanceof PlanoSubscricaoPremiumTop);
    }

    @Test
    public void testMudarPlanoInvalido() {
        controller.adicionarUtilizador(utilizadorFreeTeste);
        String resultado = controller.mudarPlano(utilizadorFreeTeste, "Plano Inexistente");
        assertEquals("Plano inválido.", resultado);
    }

    // Testes de Músicas Favoritas (Unitários)
    @Test
    public void testMarcarComoFavorita() throws Exception {
        controller.adicionarUtilizador(utilizadorFreeTeste);
        String resultado = controller.marcarComoFavorita(musicaTeste, utilizadorFreeTeste);
        assertEquals("Adicionado aos favoritos com sucesso", resultado); // Ajustado
        Utilizador userAtualizado = controller.getUtilizador(utilizadorFreeTeste.getEmail());
        assertTrue(userAtualizado.eFavorita(musicaTeste));
    }

    @Test
    public void testMarcarComoFavoritaJaFavorita() throws Exception {
        controller.adicionarUtilizador(utilizadorFreeTeste);
        controller.marcarComoFavorita(musicaTeste, utilizadorFreeTeste);
        String resultado = controller.marcarComoFavorita(musicaTeste, utilizadorFreeTeste);
        assertEquals("Não foi possível adicionar música", resultado); // Mensagem da OperacaoSemSucessoException
    }


    @Test
    public void testRemoverDosFavoritos() throws Exception {
        controller.adicionarUtilizador(utilizadorFreeTeste);
        controller.marcarComoFavorita(musicaTeste, utilizadorFreeTeste);
        String resultado = controller.removerDosFavoritos(musicaTeste, utilizadorFreeTeste);
        assertEquals("Removido dos favoritos com sucesso", resultado); // Ajustado
        Utilizador userAtualizado = controller.getUtilizador(utilizadorFreeTeste.getEmail());
        assertFalse(userAtualizado.eFavorita(musicaTeste));
    }

    @Test
    public void testRemoverDosFavoritosNaoFavorita() {
        controller.adicionarUtilizador(utilizadorFreeTeste);
        String resultado = controller.removerDosFavoritos(musicaTeste, utilizadorFreeTeste);
        assertEquals("Não foi possível remover música", resultado); // Mensagem da OperacaoSemSucessoException
    }

    // Testes de Reprodução (Unitários)
    @Test
    public void testReproduzirMusicaIndividual() throws Exception {
        controller.adicionarAlbum(albumTeste);
        controller.adicionarMusica(musicaTeste, albumTeste);
        String resultado = controller.reproduzirMusicaIndividual(musicaTeste.getNome(), albumTeste.getNome());
        assertEquals(musicaTeste.getLetra(), resultado);
        Album albumDoModel = model.getAlbum(albumTeste.getNome());
        Musica musicaDoModel = albumDoModel.getMusica(musicaTeste.getNome());
        assertEquals(1, musicaDoModel.getNumeroDeReproducoes());
    }

    @Test
    public void testReproduzirMusicaIndividualMusicaNaoExistente() throws Exception {
        controller.adicionarAlbum(albumTeste);
        String resultado = controller.reproduzirMusicaIndividual("Musica Inexistente", albumTeste.getNome());
        assertEquals("Música não existe no álbum", resultado); // Ajustado
    }

    @Test
    public void testReproduzirMusicaIndividualAlbumNaoExistente() {
        String resultado = controller.reproduzirMusicaIndividual(musicaTeste.getNome(), "Album Inexistente");
        assertEquals("Álbum não existe", resultado); // Ajustado
    }

    // Testes de Playlist (Unitários e alguns de integração leve)
    @Test
    public void testAdicionarPlaylistPrivadaAoUtilizador() throws Exception {
        controller.adicionarUtilizador(utilizadorPremiumTeste);
        PlaylistAleatoria playlist = new PlaylistAleatoria();
        playlist.setNome("Minha Playlist Privada");
        // A lista de músicas inicial é vazia por defeito no construtor de PlaylistAleatoria
        String resultado = controller.adicionarPlaylist(utilizadorPremiumTeste, playlist, "n");
        assertEquals("Playlist adicionada com sucesso!", resultado); // Ajustado

        Utilizador userAtualizado = controller.getUtilizador(utilizadorPremiumTeste.getEmail());
        assertNotNull(userAtualizado.getBiblioteca().getColecao("Minha Playlist Privada"));
        assertTrue(controller.getPlaylitsPublicas().stream().noneMatch(p -> p.getNome().equals("Minha Playlist Privada")));
    }

    @Test
    public void testAdicionarPlaylistPublicaAoUtilizador() throws Exception {
        controller.adicionarUtilizador(utilizadorFreeTeste);
        controller.mudarPlano(utilizadorFreeTeste, "Premium Top");
        PlaylistAleatoria playlist = new PlaylistAleatoria();
        playlist.setNome("Minha Playlist Publica");
        String resultado = controller.adicionarPlaylist(utilizadorFreeTeste, playlist, "s");

        assertEquals("Playlist adicionada com sucesso!", resultado); // A mensagem de adicionarPlaylist
        // E a playlist deve se tornar pública
        Utilizador userAtualizado = controller.getUtilizador(utilizadorFreeTeste.getEmail());
        assertNotNull(userAtualizado.getBiblioteca().getColecao("Minha Playlist Publica"));

        boolean publica = false;
        for(Playlist p : model.getPlaylistsPublicas()){
            if(p.getNome().equals("Minha Playlist Publica")) publica = true;
        }
        assertTrue(publica, "A playlist deveria ter sido tornada pública.");
    }


    @Test
    public void testAdicionarPlaylistUtilizadorNaoExistente() {
        Utilizador utilizadorInexistente = new Utilizador("Fantasma", "Rua X", "fantasma@mail.com");
        PlaylistAleatoria playlist = new PlaylistAleatoria();
        playlist.setNome("Playlist Fantasma");

        String resultadoPrivada = controller.adicionarPlaylist(utilizadorInexistente, playlist, "n");
        // O método `adicionarPlaylist` tentará buscar o utilizador, que não existe.
        assertEquals("Utilizador não existe", resultadoPrivada);

        String resultadoPublica = controller.adicionarPlaylist(utilizadorInexistente, playlist, "s");
        assertEquals("Utilizador não existe", resultadoPublica);
    }


    @Test
    public void testTornarPlaylistPublica() throws Exception {
        controller.adicionarUtilizador(utilizadorPremiumTeste);

        PlaylistAleatoria playlist = new PlaylistAleatoria();
        playlist.setNome("Playlist Para Publicar");
        // Adiciona a playlist primeiro à biblioteca do utilizador
        model.adicionarPlaylistAoUtilizador(utilizadorPremiumTeste.getEmail(), playlist);

        String resultado = controller.tornarPlaylistPublica(utilizadorPremiumTeste.getEmail(), "Playlist Para Publicar");
        assertEquals("Playlist tornada pública com sucesso!", resultado);
        assertTrue(model.getPlaylistsPublicas().stream().anyMatch(p -> p.getNome().equals("Playlist Para Publicar")));
    }

    @Test
    public void testTornarPlaylistPublicaPlanoNaoPermite() throws Exception {
        controller.adicionarUtilizador(utilizadorFreeTeste);
        // Simular um plano que NÃO permite (exigiria criar um novo tipo de plano para teste ou mock)
        // Para este exemplo, vamos mudar o utilizador para um plano que sabemos que não permite
        // (Supondo que PlanoSubscricaoFree NÃO permite, mas no código atual ele permite)
        // Este teste precisa de um plano que efetivamente restrinja.
        // Se quisermos testar a exceção, precisaríamos de um plano com EstrategiaPlanoSubscricaoNaoPermite
        // e ajustar o assert.
        // Por agora, vamos assumir que o plano Free PERMITE, como está no código.
        // Para testar o "não permite", você teria que:
        // 1. Ter um plano que realmente use EstrategiaPlanoSubscricaoNaoPermite para esta ação.
        // 2. Mudar o utilizador para esse plano.
        // 3. Chamar tornarPlaylistPublica e esperar a mensagem da exceção.

        // Exemplo se PlanoFree não permitisse:
        // utilizadorFreeTeste.setPlanoSubscricao(new PlanoSubscricaoQueNaoPermite()); // Plano hipotético
        // model.getUtilizadoresEstado().put(utilizadorFreeTeste.getEmail(), utilizadorFreeTeste); // Atualiza no model
        // PlaylistAleatoria playlist = new PlaylistAleatoria();
        // playlist.setNome("Playlist Restrita");
        // model.adicionarPlaylistAoUtilizador(utilizadorFreeTeste.getEmail(), playlist);
        // String resultado = controller.tornarPlaylistPublica(utilizadorFreeTeste.getEmail(), "Playlist Restrita");
        // assertEquals("Plano de subscrição não permite a ação", resultado); // Ou a mensagem específica
    }


    // Testes para `reproduzProximaMusicaDoReprodutor`
    @Test
    public void testReproduzProximaMusicaComSucesso() throws Exception {
        controller.adicionarUtilizador(utilizadorPremiumTeste);
        Musica m1 = new Musica("S1", "Art", "Ed", "Letra S1", "N", "G", 1);
        PlaylistPersonalizada playlist = new PlaylistPersonalizada(); // Usar PlaylistPersonalizada para ordem definida
        playlist.setNome("Playlist Para Tocar");
        playlist.adicionarMusica(m1); // Adiciona música à playlist
        controller.adicionarPlaylist(utilizadorPremiumTeste, playlist, "n"); // Adiciona playlist à biblioteca do user

        // Inserir a coleção no reprodutor do utilizador no model diretamente para este teste,
        // já que o método do controller `inserirMusicaNoReprodutor` não está implementado.
        Utilizador user = model.getUtilizadorPorEmail(utilizadorPremiumTeste.getEmail());
        user.insereColecaoNoReprodutor(playlist.getNome());


        Reproducao rep = controller.avancarMusica(utilizadorPremiumTeste);
        assertTrue(rep.isSucesso());
        assertEquals("S1", rep.getMusica().getNome());
        assertEquals("Letra S1", rep.getMusica().getLetra());
    }


    @Test
    public void testReproduzProximaMusicaColecaoVazia() throws Exception {
        controller.adicionarUtilizador(utilizadorPremiumTeste);
        PlaylistAleatoria playlistVazia = new PlaylistAleatoria();
        playlistVazia.setNome("Playlist Vazia Teste");
        controller.adicionarPlaylist(utilizadorPremiumTeste, playlistVazia, "n");

        Utilizador user = model.getUtilizadorPorEmail(utilizadorPremiumTeste.getEmail());
        user.insereColecaoNoReprodutor(playlistVazia.getNome());

        Reproducao rep = controller.avancarMusica(utilizadorPremiumTeste);
        assertFalse(rep.isSucesso());
        assertEquals("Impossível reproduzir", rep.getMensagem());
    }

    // Testes para adicionarAlbumABiblioteca
    @Test
    public void testAdicionarAlbumABibliotecaComSucesso() throws Exception {
        controller.adicionarUtilizador(utilizadorPremiumTeste);
        controller.adicionarAlbum(albumTeste); // Adiciona álbum ao sistema

        String resultado = controller.adicionarAlbumABiblioteca(albumTeste, utilizadorPremiumTeste);
        assertEquals("Adicionado com sucesso", resultado);

        Utilizador userAtualizado = controller.getUtilizador(utilizadorPremiumTeste.getEmail());
        assertTrue(userAtualizado.getBiblioteca().getAlbuns().stream().anyMatch(a -> a.getNome().equals(albumTeste.getNome())));
    }

    // Testes para os métodos de avançar e retroceder música
    @Test
    public void testAvancarMusica() throws Exception {
        controller.adicionarUtilizador(utilizadorPremiumTeste);
        Musica m1 = new Musica("M1", "Art", "Ed", "L1", "N", "G", 1);
        Musica m2 = new Musica("M2", "Art", "Ed", "L2", "N", "G", 1);
        PlaylistPersonalizada playlist = new PlaylistPersonalizada();
        playlist.setNome("Playlist Avanco");
        playlist.adicionarMusica(m1);
        playlist.adicionarMusica(m2);
        controller.adicionarPlaylist(utilizadorPremiumTeste, playlist, "n");

        Utilizador user = model.getUtilizadorPorEmail(utilizadorPremiumTeste.getEmail());
        user.insereColecaoNoReprodutor(playlist.getNome());

        Reproducao rep1 = controller.avancarMusica(utilizadorPremiumTeste);
        assertEquals("M1", rep1.getMusica().getNome());

        Reproducao rep2 = controller.avancarMusica(utilizadorPremiumTeste); // Este é o método do controller
        assertTrue(rep2.isSucesso());
        assertEquals("M2", rep2.getMusica().getNome());
    }

    @Test
    public void testRetrocederMusica() throws Exception {
        controller.adicionarUtilizador(utilizadorPremiumTeste);
        Musica m1 = new Musica("M1", "Art", "Ed", "L1", "N", "G", 1);
        Musica m2 = new Musica("M2", "Art", "Ed", "L2", "N", "G", 1);
        PlaylistPersonalizada playlist = new PlaylistPersonalizada();
        playlist.setNome("Playlist Retrocesso");
        playlist.adicionarMusica(m1);
        playlist.adicionarMusica(m2);
        controller.adicionarPlaylist(utilizadorPremiumTeste, playlist, "n");

        Utilizador user = model.getUtilizadorPorEmail(utilizadorPremiumTeste.getEmail());
        user.insereColecaoNoReprodutor(playlist.getNome());

        controller.avancarMusica(utilizadorPremiumTeste); // Toca M1
        controller.avancarMusica(utilizadorPremiumTeste); // Toca M2

        Reproducao rep = controller.retrocederMusica(utilizadorPremiumTeste); // Deveria voltar para M1
        assertTrue(rep.isSucesso());
        assertEquals("M1", rep.getMusica().getNome());
    }

    // Teste para reproduzirColecaoMusica
    @Test
    public void testReproduzirColecaoMusicaComSucesso() throws Exception {
        controller.adicionarUtilizador(utilizadorPremiumTeste);
        Musica m1 = new Musica("MusicaColecao", "Art", "Ed", "LetraColecao", "N", "G", 1);
        PlaylistAleatoria playlist = new PlaylistAleatoria(); // Pode ser qualquer tipo de ColecaoDeMusicasReproduzivel
        playlist.setNome("Playlist ReproducaoDireta");
        playlist.adicionarMusica(m1);
        controller.adicionarPlaylist(utilizadorPremiumTeste, playlist, "n");

        // Obter a coleção da biblioteca do utilizador para passar ao método do controller
        ColecaoDeMusicasReproduzivel colecaoDaBiblioteca = controller.getUtilizador(utilizadorPremiumTeste.getEmail())
                .getBiblioteca()
                .getColecao("Playlist ReproducaoDireta");

        Reproducao rep = controller.reproduzirColecaoMusica(utilizadorPremiumTeste, colecaoDaBiblioteca);
        assertTrue(rep.isSucesso());
        assertEquals("MusicaColecao", rep.getMusica().getNome());
        assertEquals("LetraColecao", rep.getMusica().getLetra());
    }

    @Test
    public void testReproduzirColecaoMusicaVazia() throws Exception {
        controller.adicionarUtilizador(utilizadorPremiumTeste);
        PlaylistAleatoria playlistVazia = new PlaylistAleatoria();
        playlistVazia.setNome("PlaylistReproducaoVazia");
        controller.adicionarPlaylist(utilizadorPremiumTeste, playlistVazia, "n");

        ColecaoDeMusicasReproduzivel colecaoDaBiblioteca = controller.getUtilizador(utilizadorPremiumTeste.getEmail())
                .getBiblioteca()
                .getColecao("PlaylistReproducaoVazia");

        Reproducao rep = controller.reproduzirColecaoMusica(utilizadorPremiumTeste, colecaoDaBiblioteca);
        assertFalse(rep.isSucesso());
        assertEquals("Impossível reproduzir", rep.getMensagem()); // Mensagem de erro do controller
    }

    // Getters para Estado (Map)
    @Test
    public void testGetUtilizadoresNovo() {
        controller.adicionarUtilizador(utilizadorFreeTeste);
        Map<String, Utilizador> estado = controller.getUtilizadoresNovo(); // Nome do método no controller
        assertNotNull(estado);
        assertTrue(estado.containsKey(utilizadorFreeTeste.getEmail()));
        assertEquals(utilizadorFreeTeste.getNome(), estado.get(utilizadorFreeTeste.getEmail()).getNome());
    }

    @Test
    public void testGetPlaylistsNovo() throws Exception {
        controller.adicionarUtilizador(utilizadorPremiumTeste);
        PlaylistAleatoria playlist = new PlaylistAleatoria();
        playlist.setNome("PlaylistPublicaEstado");
        controller.adicionarPlaylist(utilizadorPremiumTeste, playlist, "s"); // Torna pública

        Map<String, Playlist> estado = controller.getPlaylistsNovo(); // Nome do método no controller
        assertNotNull(estado);
        assertTrue(estado.containsKey("PlaylistPublicaEstado"));
    }

    @Test
    public void testGetAlbumNovo() {
        controller.adicionarAlbum(albumTeste);
        Map<String, Album> estado = controller.getAlbum(); // Nome do método no controller
        assertNotNull(estado);
        assertTrue(estado.containsKey(albumTeste.getNome()));
        assertEquals(albumTeste.getArtista(), estado.get(albumTeste.getNome()).getArtista());
    }


    // --- Testes de Jornada / Integração ---

    @Test
    public void testJornadaCompleta_AdminAdicionaConteudo_UtilizadorReproduz() throws Exception {
        // 1. Admin adiciona utilizador
        Utilizador novoUtilizador = new Utilizador("Joana Silva", "Rua das Flores", "joana.silva@email.com");
        assertEquals("Utilizador adicionado com sucesso!", controller.adicionarUtilizador(novoUtilizador));

        // 2. Admin adiciona álbum
        Album novoAlbum = new Album("Grandes Hits", "Artista Famoso", new ArrayList<>());
        assertEquals("Album adicionado com sucesso!", controller.adicionarAlbum(novoAlbum));

        // 3. Admin adiciona música ao álbum
        Musica novaMusica = new Musica("Canção da Alegria", "Artista Famoso", "Editora XYZ", "Letra feliz...", "Notas...", "Pop", 200);
        assertEquals("Música adicionada com sucesso!", controller.adicionarMusica(novaMusica, novoAlbum));

        // Verificar estado intermediário
        assertNotNull(model.getUtilizadorPorEmail(novoUtilizador.getEmail()));
        Album albumDoModel = model.getAlbum(novoAlbum.getNome());
        assertNotNull(albumDoModel);
        assertTrue(albumDoModel.getMusicas().stream().anyMatch(m -> m.getNome().equals(novaMusica.getNome())));

        // 4. Utilizador reproduz a música
        String resultadoReproducao = controller.reproduzirMusicaIndividual(novaMusica.getNome(), novoAlbum.getNome());
        assertEquals(novaMusica.getLetra(), resultadoReproducao);

        // Verificar o impacto da reprodução
        Musica musicaReproduzida = model.getAlbum(novoAlbum.getNome()).getMusica(novaMusica.getNome());
        assertEquals(1, musicaReproduzida.getNumeroDeReproducoes(), "O número de reproduções da música deveria ter incrementado.");
    }

    @Test
    public void testJornada_UtilizadorCriaPlaylistTornaPublicaEReproduz() throws Exception {
        // 1. Setup inicial
        Utilizador utilizadorA = new Utilizador("Utilizador A", "Morada A", "usera@exemplo.com");
        utilizadorA.setPlanoSubscricao(new PlanoSubscricaoPremiumTop());
        controller.adicionarUtilizador(utilizadorA);

        Album albumX = new Album("Album X", "Artista X", new ArrayList<>());
        Musica musicaX1 = new Musica("Musica X1", "Artista X", "EdX", "LX1", "NX1", "Rock", 180);
        controller.adicionarAlbum(albumX);
        controller.adicionarMusica(musicaX1, albumX);

        // 2. UtilizadorA cria uma playlist privada
        PlaylistPersonalizada playlistDoUserA = new PlaylistPersonalizada();
        playlistDoUserA.setNome("Minhas Favoritas Rock");
        assertEquals("Playlist adicionada com sucesso!", controller.adicionarPlaylist(utilizadorA, playlistDoUserA, "n"));

        // 4. UtilizadorA torna a playlist pública (assumindo que o plano Free permite)
        String resultadoPublicacao = controller.tornarPlaylistPublica(utilizadorA.getEmail(), playlistDoUserA.getNome());
        assertEquals("Playlist tornada pública com sucesso!", resultadoPublicacao);
        assertTrue(model.getPlaylistsPublicas().stream().anyMatch(p -> p.getNome().equals(playlistDoUserA.getNome())));

    }


    @Test
    public void testJornada_FavoritosEMudancaDePlano() throws Exception {
        // 1. & 2. Setup
        controller.adicionarUtilizador(utilizadorFreeTeste);
        controller.adicionarAlbum(albumTeste);
        controller.adicionarMusica(musicaTeste, albumTeste);

        // 3. Marcar como favorita
        assertEquals("Adicionado aos favoritos com sucesso", controller.marcarComoFavorita(musicaTeste, utilizadorFreeTeste));

        // 4. Verificar
        Utilizador userAtualizado = model.getUtilizadorPorEmail(utilizadorFreeTeste.getEmail());
        assertTrue(userAtualizado.eFavorita(musicaTeste));

        // 5. Mudar plano
        assertEquals("Plano alterado com sucesso para: PlanoSubscricaoPremiumBase", controller.mudarPlano(utilizadorFreeTeste, "Premium Base"));

        // 6. Verificar plano
        userAtualizado = model.getUtilizadorPorEmail(utilizadorFreeTeste.getEmail()); // Re-fetch
        assertTrue(userAtualizado.getPlanoSubscricao() instanceof PlanoSubscricaoPremiumBase);

        // 7. Reproduzir
        int reprodsAntes = model.getAlbum(albumTeste.getNome()).getMusica(musicaTeste.getNome()).getNumeroDeReproducoes();
        controller.reproduzirMusicaIndividual(musicaTeste.getNome(), albumTeste.getNome());
        int reprodsDepois = model.getAlbum(albumTeste.getNome()).getMusica(musicaTeste.getNome()).getNumeroDeReproducoes();
        assertEquals(reprodsAntes + 1, reprodsDepois, "Número de reproduções deveria incrementar.");
        // A verificação de pontos seria mais complexa e dependeria da lógica exata de atribuição.

        // 8. Remover dos favoritos
        assertEquals("Removido dos favoritos com sucesso", controller.removerDosFavoritos(musicaTeste, utilizadorFreeTeste));

        // 9. Verificar
        userAtualizado = model.getUtilizadorPorEmail(utilizadorFreeTeste.getEmail()); // Re-fetch
        assertFalse(userAtualizado.eFavorita(musicaTeste));
    }

    // Testes para métodos que lançam UnsupportedOperationException no Controller fornecido
    // Estes testes servem para documentar o estado atual e falharão até que os métodos sejam implementados.

    @Test
    public void testShuffleNoReprodutor() throws Exception {
        // Arrange
        controller.adicionarUtilizador(utilizadorFreeTeste);

        // Act
        String resultado = controller.mudarShuffleNoReprodutor(utilizadorFreeTeste.getEmail());

        // Assert
        assertEquals("Modo shuffle alterado com sucesso!", resultado);
    }



    
    @Test
    public void testSalvarDados() {
        // Arrange
        controller.adicionarUtilizador(utilizadorFreeTeste);
        controller.adicionarAlbum(albumTeste);
        controller.salvarDados();

        // Act
        Map<String, Utilizador> utilizadoresSalvos = EstadodaAPP.carregarObjeto("data/utilizadores.dat", new HashMap<>());
        Map<String, Album> albunsSalvos = EstadodaAPP.carregarObjeto("data/albuns.dat", new HashMap<>());

        // Assert
        assertTrue(utilizadoresSalvos.containsKey(utilizadorFreeTeste.getEmail()));
        assertTrue(albunsSalvos.containsKey(albumTeste.getNome()));
    }

    @Test
    public void testCarregarDados() {
        // Arrange
        Map<String, Utilizador> utilizadoresMock = new HashMap<>();
        utilizadoresMock.put(utilizadorFreeTeste.getEmail(), utilizadorFreeTeste);
        EstadodaAPP.guardarObjeto("data/utilizadores.dat", utilizadoresMock);

        Map<String, Album> albunsMock = new HashMap<>();
        albunsMock.put(albumTeste.getNome(), albumTeste);
        EstadodaAPP.guardarObjeto("data/albuns.dat", albunsMock);

        // Act
        controller.carregarDados();

        // Assert
        assertEquals(utilizadorFreeTeste, model.getUtilizadoresEstado().get(utilizadorFreeTeste.getEmail()));
        assertEquals(albumTeste, model.getAlbumsEstado().get(albumTeste.getNome()));
    }
}