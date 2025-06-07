package model;

import exceptions.ColecaoDeMusicaVaziaException;
import exceptions.MusicaJaExisteException;
import model.musica.Musica;
import model.playlists.PlaylistPersonalizada;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReprodutorDeMusicaTest {

    private ReprodutorDeMusica reprodutor;
    private PlaylistPersonalizada playlist;

    private Musica m1;
    private Musica m2;
    private Musica m3;

    @BeforeEach
    public void setUp() throws MusicaJaExisteException {
        m1 = new Musica("musica1", "artista1", "editora", "letra1", "notasmusicais", "pop", 100);
        m2 = new Musica("musica2", "artista1", "editora", "letra1", "notasmusicais", "pop", 100);
        m3 = new Musica("musica3", "artista1", "editora", "letra1", "notasmusicais", "pop", 100);

        playlist = new PlaylistPersonalizada("MinhaPlaylist", List.of());
        playlist.adicionarMusica(m1);
        playlist.adicionarMusica(m2);
        playlist.adicionarMusica(m3);

        reprodutor = new ReprodutorDeMusica();
        reprodutor.insereColecao(playlist);
    }

    @Test
    public void testReproduzProximaMusicaModoSequencial() throws ColecaoDeMusicaVaziaException {
        reprodutor.setShuffle(false);

        Reproducao r1 = reprodutor.reproduzProximaMusica();
        assertEquals("musica1", r1.getMusica().getNome());

        Reproducao r2 = reprodutor.reproduzProximaMusica();
        assertEquals("musica2", r2.getMusica().getNome());

        Reproducao r3 = reprodutor.reproduzProximaMusica();
        assertEquals("musica3", r3.getMusica().getNome());

        // Após a última música, deve voltar ao início
        Reproducao r4 = reprodutor.reproduzProximaMusica();
        assertEquals("musica1", r4.getMusica().getNome());
    }

    @Test
    public void testReproduzMusicaAnteriorModoSequencial() throws ColecaoDeMusicaVaziaException {
        reprodutor.setShuffle(false);

        reprodutor.reproduzProximaMusica(); // m1
        reprodutor.reproduzProximaMusica(); // m2

        // Voltar para m1
        Reproducao anterior1 = reprodutor.reproduzMusicaAnterior();
        assertEquals("musica1", anterior1.getMusica().getNome());

        // Voltar para m3 (loop para o fim da lista)
        Reproducao anterior2 = reprodutor.reproduzMusicaAnterior();
        assertEquals("musica3", anterior2.getMusica().getNome());
    }

    @Test
    public void testModoShuffleNaoLancaExcecao() {
        reprodutor.setShuffle(true);

        assertDoesNotThrow(() -> {
            // Reproduz várias vezes no modo aleatório
            for (int i = 0; i < 10; i++) {
                reprodutor.reproduzProximaMusica();
            }
        });
    }
}
