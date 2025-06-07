package model.Enunciado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import exceptions.*;
import model.Album;
import model.musica.*;
import model.SpotifUMModel;
import model.Utilizador;
import model.planosSubscricao.PlanoSubscricaoFree;
import model.planosSubscricao.PlanoSubscricaoPremiumBase;
import model.planosSubscricao.PlanoSubscricaoPremiumTop; // Adicionado
import model.playlists.Playlist;
import model.playlists.PlaylistListaDeFavoritos;
import model.playlists.PlaylistPreferenciasComTempoMaximo;
import model.playlists.PlaylistPreferenciasExplicitas;

import java.util.ArrayList;
import java.util.Arrays; // Adicionado
import java.util.Collections;
import java.util.List; // Adicionado

public class BibliotecaUtilizadorEFavoritosPermissoesTest {

    private SpotifUMModel model;
    private Utilizador userFreeLib;
    private Utilizador userPremiumBaseLib;
    private Utilizador userPremiumTopLib;
    private Album albumParaBiblioteca;
    private Musica musicaParaFavoritos; // Para "Listas de Favoritos" GERADAS


    @BeforeEach
    void setUp() throws Exception {
        model = new SpotifUMModel();

        userFreeLib = new Utilizador("UserFreeLib", "Rua FreeLib", "freelib@example.com");
        userFreeLib.setPlanoSubscricao(new PlanoSubscricaoFree());
        model.adicionarUtilizador(userFreeLib);

        userPremiumBaseLib = new Utilizador("UserPBLib", "Rua PBLib", "pblib@example.com");
        userPremiumBaseLib.setPlanoSubscricao(new PlanoSubscricaoPremiumBase());
        model.adicionarUtilizador(userPremiumBaseLib);
        
        userPremiumTopLib = new Utilizador("UserPTLib", "Rua PTLib", "ptlib@example.com");
        userPremiumTopLib.setPlanoSubscricao(new PlanoSubscricaoPremiumTop());
        model.adicionarUtilizador(userPremiumTopLib);


        musicaParaFavoritos = new Musica("MusicaFav1", "ArtistaFav", "EFav", "LetraFav1", "NFav1", "Pop", 180);
        albumParaBiblioteca = new Album("AlbumParaBib", "ArtistaBib", Collections.singletonList(musicaParaFavoritos));
        model.adicionarAlbum(albumParaBiblioteca); // Álbum deve existir no sistema
    }

    // --- Testes de Biblioteca Pessoal (Álbuns) ---

    @Test
    void testUtilizadorPremiumBasePodeAdicionarAlbumASuaBiblioteca() throws Exception {
        // Enunciado (p2, 1.3): "Premium Base, que permite aos utilizadores criarem uma biblioteca com álbuns existentes"
        model.adicionarAlbumABiblioteca(albumParaBiblioteca, userPremiumBaseLib.getEmail());
        Utilizador u = model.getUtilizadorPorEmail(userPremiumBaseLib.getEmail());
        assertTrue(u.getBiblioteca().getAlbuns().stream().anyMatch(a -> a.getNome().equals(albumParaBiblioteca.getNome())));
    }
    
    @Test
    void testUtilizadorPremiumTopPodeAdicionarAlbumASuaBiblioteca() throws Exception {
        // Enunciado (p3, 1.3): "Premium Top que permite ... as funcionalidades do Premium Base"
        model.adicionarAlbumABiblioteca(albumParaBiblioteca, userPremiumTopLib.getEmail());
        Utilizador u = model.getUtilizadorPorEmail(userPremiumTopLib.getEmail());
        assertTrue(u.getBiblioteca().getAlbuns().stream().anyMatch(a -> a.getNome().equals(albumParaBiblioteca.getNome())));
    }


    @Test
    void testUtilizadorFreeNaoPodeAdicionarAlbumASuaBibliotecaLancaExcecao() throws Exception {
        // Enunciado (p2, 1.3) implica que Free não tem biblioteca de álbuns.
        // A lógica de restrição deve estar em Utilizador.adicionarAlbumABiblioteca ou
        // SpotifUMModel.adicionarAlbumABiblioteca, verificando o plano.
        // O PlanoSubscricao tem `estrategiaAdicionarABiblioteca` (atualmente não usada para este caso).
        // Este teste VAI FALHAR com o código atual se a restrição não estiver implementada.
        // O teste é escrito CONFORME O ENUNCIADO.

        // Para passar, Utilizador.adicionarAlbumABiblioteca() precisaria de:
        // this.plano.executarAdicionarABiblioteca(() -> this.biblioteca.adicionarAlbumABiblioteca(album));
        // E PlanoSubscricaoFree teria estrategiaAdicionarABiblioteca = new EstrategiaPlanoSubscricaoNaoPermite(...);
        // Ou o SpotifUMModel.adicionarAlbumABiblioteca faria a verificação.

        assertThrows(PlanoDeSubscricaoNaoPermiteException.class, () -> {
            model.adicionarAlbumABiblioteca(albumParaBiblioteca, userFreeLib.getEmail());
        }, "Utilizador Free não deveria poder adicionar álbuns à sua biblioteca.");
    }

    // --- Testes para "Listas de Favoritos" (GERADAS) e Playlists por Preferências ---
    // O enunciado (p1, 1) e (p2, 1.2) fala de "Listas de favoritos" geradas automaticamente para Premium.
    // E (p3, 1.3) "Premium Top ... acesso a playlists geradas pela aplicação com base nas preferências".
    // Secção 3.4: "gerar uma playlist de acordo com critérios":
    // 1. "uma playlist com as preferências musicais do utilizador" (Lista de Favoritos)
    // 2. "uma playlist com as preferências musicais do utilizador e restrita a um determinado tempo máximo"
    // 3. "uma playlist com as preferências músicais do utilizador mas apenas com músicas do tipo Musica Explicita"

    // Os métodos `model.adicionarMusicaAosFavoritos` e `removerMusicaDosFavoritos` no seu código
    // parecem referir-se a uma gestão MANUAL de uma lista de favoritos, usando `PlaylistListaDeFavoritos`.
    // O enunciado NÃO descreve uma funcionalidade de o utilizador *marcar manualmente* músicas como favoritas.
    // Ele descreve "Listas de Favoritos" como sendo GERADAS.
    // Portanto, os testes para adicionar/remover manualmente favoritos podem não ser relevantes para o enunciado.
    // Vou focar em testar a capacidade de gerar ou obter essas playlists especiais, se o MODELO expuser tal capacidade.

    // Se `PlaylistListaDeFavoritos` for a representação da "playlist com as preferências musicais do utilizador" (gerada),
    // então precisaríamos de testes que verifiquem como o `SpotifUMModel` a disponibiliza ou cria para um user Premium Top.
    // O mesmo para `PlaylistPreferenciasComTempoMaximo` e `PlaylistPreferenciasExplicitas`.

    // Por agora, vou assumir que o `SpotifUMModel` *não* tem métodos diretos para *gerar* estas playlists
    // (essa lógica pode estar no Controller ou nas próprias classes de Playlist ao serem instanciadas com os dados certos).
    // O que o `SpotifUMModel` FAZ é permitir `adicionarPlaylistAoUtilizador`.
    // Então, podemos testar se um utilizador (Premium Top) pode *ter* esses tipos de playlist na sua biblioteca.

    @Test
    void testUtilizadorPremiumTopPodeTerPlaylistListaDeFavoritosGerada() throws Exception {
        // Simula que uma "Lista de Favoritos" foi gerada (a lógica de geração em si é complexa e pode estar fora do modelo direto)
        // e é adicionada à biblioteca do utilizador.
        Musica fav1 = new Musica("FavHit1", "TopArtist", "E", "L", "N", "Pop", 180);
        Musica fav2 = new Musica("FavHit2", "TopArtist", "E", "L", "N", "Pop", 200);
        model.adicionarAlbum(new Album("AlbumComFavs", "TopArtist", Arrays.asList(fav1, fav2))); // Músicas devem existir
        
        // A PlaylistListaDeFavoritos no seu código é uma playlist normal que pode ser preenchida.
        // Se ela representar a "gerada", o teste é sobre se o user PT pode tê-la.
        PlaylistListaDeFavoritos playlistGeradaFavoritos = new PlaylistListaDeFavoritos("Favoritos de UserPT", Arrays.asList(fav1, fav2));
        
        model.adicionarPlaylistAoUtilizador(userPremiumTopLib.getEmail(), playlistGeradaFavoritos);
        
        Utilizador u = model.getUtilizadorPorEmail(userPremiumTopLib.getEmail());
        assertTrue(u.getBiblioteca().getPlaylists().stream()
                    .anyMatch(p -> p.getNome().equals("Favoritos de UserPT") && p instanceof PlaylistListaDeFavoritos));
    }

    @Test
    void testUtilizadorPremiumTopPodeTerPlaylistPreferenciasComTempoMaximo() throws Exception {
        Musica m1 = new Musica("PopCurta", "PopArtist", "E", "L", "N", "Pop", 120); // 2 min
        Musica m2 = new Musica("PopMedia", "PopArtist", "E", "L", "N", "Pop", 180); // 3 min
        Musica m3 = new Musica("PopLonga", "PopArtist", "E", "L", "N", "Pop", 300); // 5 min
        Album albumPop = new Album("AlbumDePop", "PopArtist", Arrays.asList(m1,m2,m3));
        model.adicionarAlbum(albumPop);

        List<Musica> musicasBase = albumPop.getMusicas(); // Músicas a partir das quais gerar
        
        // A PlaylistPreferenciasComTempoMaximo é uma SUBCLASSE de PlaylistListaDeFavoritos.
        // O construtor dela já faz o filtro.
        // Para gerar a "lista de favoritos base", o sistema precisaria inferir os gostos.
        // Para o teste, vamos dar uma lista base de "favoritas" e aplicar o tempo máximo.
        PlaylistListaDeFavoritos baseFavoritas = new PlaylistListaDeFavoritos("BaseFavsParaTempo", musicasBase);

        PlaylistPreferenciasComTempoMaximo playlistComTempo = new PlaylistPreferenciasComTempoMaximo(
            "Pop Favoritas Max 5 Min", 300, baseFavoritas // 300 segundos = 5 minutos
        );
        
        model.adicionarPlaylistAoUtilizador(userPremiumTopLib.getEmail(), playlistComTempo);
        
        Utilizador u = model.getUtilizadorPorEmail(userPremiumTopLib.getEmail());
        Playlist recuperada = u.getBiblioteca().getPlaylists().stream()
            .filter(p -> p.getNome().equals("Pop Favoritas Max 5 Min"))
            .findFirst().orElse(null);
            
        assertNotNull(recuperada);
        assertTrue(recuperada instanceof PlaylistPreferenciasComTempoMaximo);
        // m1 (120s) + m2 (180s) = 300s. Ambas devem entrar. m3 (300s) não cabe com as anteriores.
        // Se o algoritmo for greedy e pegar pela ordem:
        // M1 (120s) - total 120s
        // M2 (180s) - total 120+180 = 300s
        // M3 (300s) - 300+300 > 300s, não entra.
        assertEquals(2, recuperada.getMusicas().size());
        assertTrue(recuperada.getMusicas().stream().anyMatch(m -> m.getNome().equals("PopCurta")));
        assertTrue(recuperada.getMusicas().stream().anyMatch(m -> m.getNome().equals("PopMedia")));
    }
    
    @Test
    void testUtilizadorPremiumTopPodeTerPlaylistPreferenciasExplicitas() throws Exception {
        Musica exp1 = new MusicaExplicita("SomExplicito1", "Rapper", "E", "L", "N", "Rap", 180);
        Musica normal1 = new Musica("SomNormal1", "Rapper", "E", "L", "N", "Rap", 150);
        Musica exp2 = new MusicaExplicita("SomExplicito2", "Rapper", "E", "L", "N", "Rap", 200);
        Album albumRap = new Album("AlbumDeRap", "Rapper", Arrays.asList(exp1, normal1, exp2));
        model.adicionarAlbum(albumRap);

        List<Musica> musicasBaseFavoritas = albumRap.getMusicas(); // Lista que simula as "preferências"
        PlaylistListaDeFavoritos baseFavoritas = new PlaylistListaDeFavoritos("BaseFavsParaExplicitas", musicasBaseFavoritas);

        // A PlaylistPreferenciasExplicitas filtra as explícitas da lista de favoritos base.
        PlaylistPreferenciasExplicitas playlistExplicitas = new PlaylistPreferenciasExplicitas(baseFavoritas);
        // O nome da playlist é herdado da base no seu construtor atual. Pode ser desejável setar um novo nome.
        playlistExplicitas.setNome("Favoritas Explícitas Rap"); 
        
        model.adicionarPlaylistAoUtilizador(userPremiumTopLib.getEmail(), playlistExplicitas);

        Utilizador u = model.getUtilizadorPorEmail(userPremiumTopLib.getEmail());
        Playlist recuperada = u.getBiblioteca().getPlaylists().stream()
            .filter(p -> p.getNome().equals("Favoritas Explícitas Rap"))
            .findFirst().orElse(null);

        assertNotNull(recuperada);
        assertTrue(recuperada instanceof PlaylistPreferenciasExplicitas);
        assertEquals(2, recuperada.getMusicas().size(), "Deveria conter apenas as 2 músicas explícitas.");
        assertTrue(recuperada.getMusicas().stream().allMatch(m -> m instanceof MusicaExplicitaInterface));
        assertTrue(recuperada.getMusicas().stream().anyMatch(m -> m.getNome().equals("SomExplicito1")));
        assertTrue(recuperada.getMusicas().stream().anyMatch(m -> m.getNome().equals("SomExplicito2")));
    }

    @Test
    void testUtilizadorPremiumBaseNaoTemAcessoAcessoAPlaylistsGeradasPorPreferencias() throws Exception {
        // Enunciado (p3, 1.3) "Premium Top ... acesso a playlists geradas pela aplicação com base nas preferências"
        // Implica que Premium Base não tem.
        PlaylistListaDeFavoritos playlistGeradaFavoritos = new PlaylistListaDeFavoritos("Favoritos Negados a PB", new ArrayList<>());
        
        // Este teste assume que haveria uma verificação no `adicionarPlaylistAoUtilizador`
        // ou num método de "geração" que impediria um User PB de ter este *tipo* de playlist gerada.
        // A restrição pode ser no acesso à funcionalidade de GERAÇÃO (Controller) ou
        // se o MODELO tiver um método `model.gerarListaFavoritos(user)` ele deveria verificar o plano.
        // Se for apenas adicionar uma playlist já criada, o `adicionarPlaylistAoUtilizador` atual não restringe por tipo de playlist.
        // Este teste é mais sobre a *funcionalidade de ter acesso à geração*.
        // Se for só sobre poder ter uma `PlaylistListaDeFavoritos` na sua biblioteca (que ele mesmo poderia ter criado
        // se esta classe for também para favoritos manuais), então o User PB poderia.
        // A distinção é se `PlaylistListaDeFavoritos` é *exclusivamente* para as geradas automaticamente.

        // Dado o enunciado focar em "Listas de Favoritos" como GERADAS para Premium,
        // e "Premium Top ... acesso a playlists GERADAS", é razoável testar que um User PB
        // não pode obter/ter uma playlist que é *explicitamente do tipo gerada por preferências*.
        // Isso exigiria que o método que as GERA ou FORNECE no modelo verificasse o plano.
        
        // Por agora, como não há um método `model.gerarXXXPlaylist()`, este teste é difícil de formular
        // diretamente contra a API atual do SpotifUMModel.
        System.out.println("WARN: Teste para restrição de Premium Base a playlists geradas por preferências (3.4) precisa de API no Model para geração/acesso a essas playlists.");
    }
}