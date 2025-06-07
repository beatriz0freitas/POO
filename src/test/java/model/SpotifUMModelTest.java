package  model;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import exceptions.AlbumJaExisteException;
import exceptions.MusicaJaExisteException;
import exceptions.UtilizadorJaExisteException;
import model.musica.Musica;
import model.playlists.PlaylistAleatoria;

public class SpotifUMModelTest {

    @Test
    public void testMusicaMaisReproduzida() throws Exception {
        SpotifUMModel spotifUM = new SpotifUMModel();

        Musica musica = new Musica("Song Title", "Artist Name", "Label", "Lyrics", "Notes", "Genre", 200);
        Musica musica2 = new Musica("Song Title 2", "Artist Name 2", "Label 2", "Lyrics 2", "Notes 2", "Genre 2", 300);
        Musica musica3 = new Musica("Song Title 3", "Artist Name 3", "Label 3", "Lyrics 3", "Notes 3", "Genre 3", 400);
        Musica musica4 = new Musica("Song Title 4", "Artist Name 4", "Label 4", "Lyrics 4", "Notes 4", "Genre 4", 500);
        Musica musica5 = new Musica("Song Title 5", "Artist Name 5", "Label 5", "Lyrics 5", "Notes 5", "Genre 5", 600);


        musica.setNumeroDeReproducoes(1);
        musica2.setNumeroDeReproducoes(4);
        musica3.setNumeroDeReproducoes(3);
        musica4.setNumeroDeReproducoes(2);
        musica5.setNumeroDeReproducoes(5);


        List<Musica> musicas1 = new ArrayList<>();
        List<Musica> musicas2 = new ArrayList<>();
        List<Musica> musicas3 = new ArrayList<>();
        Album album = new Album("Album Title", "Artist Name", musicas1);
        album.adicionaMusica(musica);
        album.adicionaMusica(musica2);
        Album album2 = new Album("Album Title 2", "Artist Name 2", musicas2);
        album2.adicionaMusica(musica3);
        album2.adicionaMusica(musica5);
        Album album3 = new Album("Album Title 3", "Artist Name 3", musicas3);
        album3.adicionaMusica(musica4);


        //testa a função musicaMaisReproduzida
        spotifUM.adicionarAlbum(album);
        spotifUM.adicionarAlbum(album2);
        spotifUM.adicionarAlbum(album3);

        Musica maisReproduzida = spotifUM.musicaMaisReproduzida();

        assertEquals(musica5, maisReproduzida, "A música mais reproduzida deve ser 'musica5'");
        assertEquals(5, maisReproduzida.getNumeroDeReproducoes(), "O número de reproduções deve ser 5");
    }

    @Test
    public void testUtilizadorMaisPontos() throws UtilizadorJaExisteException {
        SpotifUMModel spotifUM = new SpotifUMModel();

        Utilizador utilizador1 = new Utilizador();
        utilizador1.setEmail("utilizador1@gmail.com");
        Utilizador utilizador2 = new Utilizador();
        utilizador2.setPontosAtuais(10);    
        utilizador2.setEmail("utilizador2@gmail.com");

        Utilizador utilizador3 = new Utilizador();
        utilizador3.setPontosAtuais(5);
        utilizador3.setEmail("utilizador3@gmail.com");


        spotifUM.adicionarUtilizador(utilizador1);
        spotifUM.adicionarUtilizador(utilizador2);  
        spotifUM.adicionarUtilizador(utilizador3);

        Utilizador utilizadorMaisPontos = spotifUM.utilizadorComMaisPontos();
        assertEquals(utilizador2.getEmail(), utilizadorMaisPontos.getEmail(), "O utilizador com mais pontos deve ser 'utilizador2'");
        assertEquals(10, utilizadorMaisPontos.getPontosAtuais(), "O número de pontos deve ser 10");
    }

    @Test
    public void testGeneroMaisReproduzido() throws Exception {
        SpotifUMModel spotifUM = new SpotifUMModel();
    
        Musica rock1 = new Musica("Rock1", "Artist1", "Label1", "Lyrics1", "Notes1", "Rock", 180);
        Musica rock2 = new Musica("Rock2", "Artist2", "Label2", "Lyrics2", "Notes2", "Rock", 200);
        Musica pop1 = new Musica("Pop1", "Artist3", "Label3", "Lyrics3", "Notes3", "Pop", 210);
        Musica jazz1 = new Musica("Jazz1", "Artist4", "Label4", "Lyrics4", "Notes4", "Jazz", 190);
    
        rock1.setNumeroDeReproducoes(3);
        rock2.setNumeroDeReproducoes(2);
        pop1.setNumeroDeReproducoes(4);
        jazz1.setNumeroDeReproducoes(1);
    
        Album album1 = new Album("Album1", "Artist1", new ArrayList<>());
        album1.adicionaMusica(rock1);
        album1.adicionaMusica(rock2);
    
        Album album2 = new Album("Album2", "Artist2", new ArrayList<>());
        album2.adicionaMusica(pop1);
        album2.adicionaMusica(jazz1);
    
        spotifUM.adicionarAlbum(album1);
        spotifUM.adicionarAlbum(album2);
    
        String generoMaisReproduzido = spotifUM.generoMaisReproduzido();
    
        assertEquals("Rock", generoMaisReproduzido, "O género mais reproduzido deve ser Rock (3+2 = 5)");
    }

    @Test
    public void testInterpreteMaisOuvido() throws AlbumJaExisteException, MusicaJaExisteException {
        SpotifUMModel spotifUM = new SpotifUMModel();

        Musica m1 = new Musica("Song1", "Interprete1", "Label1", "Lyrics1", "Notes1", "Pop", 200);
        Musica m2 = new Musica("Song2", "Interprete2", "Label2", "Lyrics2", "Notes2", "Rock", 180);
        Musica m3 = new Musica("Song3", "Interprete1", "Label3", "Lyrics3", "Notes3", "Pop", 210);
        Musica m4 = new Musica("Song4", "Interprete3", "Label4", "Lyrics4", "Notes4", "Jazz", 190);

        m1.setNumeroDeReproducoes(3);  // Interprete1
        m2.setNumeroDeReproducoes(2);  // Interprete2
        m3.setNumeroDeReproducoes(5);  // Interprete1
        m4.setNumeroDeReproducoes(1);  // Interprete3

        Album album1 = new Album("Album1", "Artista1", new ArrayList<>());
        album1.adicionaMusica(m1);
        album1.adicionaMusica(m2);

        Album album2 = new Album("Album2", "Artista2", new ArrayList<>());
        album2.adicionaMusica(m3);
        album2.adicionaMusica(m4);

        spotifUM.adicionarAlbum(album1);
        spotifUM.adicionarAlbum(album2);

        String interpreteMaisOuvido = spotifUM.interpreteMaisOuvido();

        assertEquals("Interprete1", interpreteMaisOuvido, "O intérprete mais ouvido deve ser Interprete1 (3+5 = 8 reproduções)");
    }
    /* 
    @Test

    public void testUtilizadorMaisOuviuNumPeriodo() {
        SpotifUMModel spotifUM = new SpotifUMModel();
        // Criar utilizadores
        Utilizador u1 = new Utilizador("Alice", null, null);
        Utilizador u2 = new Utilizador("Bob", null, null);
        Utilizador u3 = new Utilizador("Carol", null, null);

        Musica m1 = new Musica("Song1", "Interprete1", "Label1", "Lyrics1", "Notes1", "Pop", 200);
        Musica m2 = new Musica("Song2", "Interprete2", "Label2", "Lyrics2", "Notes2", "Rock", 180);
        Musica m3 = new Musica("Song3", "Interprete1", "Label3", "Lyrics3", "Notes3", "Pop", 210);
        Musica m4 = new Musica("Song4", "Interprete3", "Label4", "Lyrics4", "Notes4", "Jazz", 190);

        // Datas de referência
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime ontem = agora.minusDays(1);
        LocalDateTime doisDiasAtras = agora.minusDays(2);
        LocalDateTime foraDoPeriodo = agora.minusDays(10);

        // Registos dentro do período (ontem até hoje)
        u1.adicionarRegistoDeReproducaoAoHistorico(new RegistoDeReproducao(new Reproducao(m1)));
        u1.adicionarRegistoDeReproducaoAoHistorico(new RegistoDeReproducao(new Reproducao(m2)));
        u2.adicionarRegistoDeReproducaoAoHistorico(new RegistoDeReproducao(new Reproducao(m3)));

        // Fora do período
        RegistoDeReproducao antigo = new RegistoDeReproducao(new Reproducao(m4));
        antigo.setData(foraDoPeriodo);
        u3.adicionarRegistoDeReproducaoAoHistorico(antigo); // fora do intervalo

        List<Utilizador> utilizadores = Arrays.asList(u1, u2, u3);
        Utilizador resultado = spotifUM.utilizadorMaisOuviuNumPeriodo(utilizadores, ontem, agora);

        assertNotNull(resultado);
        assertEquals("Alice", resultado.getNome());
    }
        */

    @Test
    public void testUtilizadorComMaisPlaylists() throws UtilizadorJaExisteException {
        SpotifUMModel spotifUM = new SpotifUMModel();
    
        Utilizador u1 = new Utilizador("User1", "morada1", "email1");
        Utilizador u2 = new Utilizador("User2", "morada2", "email2");
        Utilizador u3 = new Utilizador("User3", "morada3", "email3");
    
        Musica m1 = new Musica("Song1", "Interprete1", "Label1", "Lyrics1", "Notes1", "Pop", 200);
        Musica m2 = new Musica("Song2", "Interprete2", "Label2", "Lyrics2", "Notes2", "Rock", 180);
        Musica m3 = new Musica("Song3", "Interprete1", "Label3", "Lyrics3", "Notes3", "Pop", 210);
        Musica m4 = new Musica("Song4", "Interprete3", "Label4", "Lyrics4", "Notes4", "Jazz", 190);

        PlaylistAleatoria p1 = new PlaylistAleatoria("PlaylistTeste1",Arrays.asList(m1, m2, m3));
        PlaylistAleatoria p2 = new PlaylistAleatoria("PlaylistTeste1",Arrays.asList(m1, m2));
        PlaylistAleatoria p3 = new PlaylistAleatoria("PlaylistTeste1",Arrays.asList(m1, m2, m4));

        Album a1 = new Album("Album1", "Artista1", Arrays.asList(m1));
        Album a2 = new Album("Album2", "Artista2", Arrays.asList(m3));

        u1.adicionarPlaylistABiblioteca(p1);
        u1.adicionarPlaylistABiblioteca(p2);
        u1.adicionarAlbumABiblioteca(a1);
        u1.adicionarAlbumABiblioteca(a2);
    
        u2.adicionarPlaylistABiblioteca(p1);
        u2.adicionarAlbumABiblioteca(a1);
    
        u3.adicionarPlaylistABiblioteca(p1);
        u3.adicionarPlaylistABiblioteca(p2);
        u3.adicionarPlaylistABiblioteca(p3);

        spotifUM.adicionarUtilizador(u1);
        spotifUM.adicionarUtilizador(u2);  
        spotifUM.adicionarUtilizador(u3);

        Utilizador maisPlaylists = spotifUM.utilizadorComMaisPlaylists(Arrays.asList(u1, u2, u3));
    
        assertEquals(u3, maisPlaylists, "O utilizador com mais playlists públicas deve ser User3 (3 playlists)");
    }
}
