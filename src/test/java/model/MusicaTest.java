package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.musica.Musica;
import model.musica.MusicaExplicita;
import model.musica.MusicaExplicitaInterface;
import model.musica.MusicaMultimedia;
import model.musica.MusicaMultimediaExplicita;

import static org.junit.jupiter.api.Assertions.*;

public class MusicaTest {

    private Musica musica;

    @BeforeEach
    public void setUp() {
        musica = new Musica("Song1", "Artist1", "Label1", "Lyrics1", "Notes1", "Pop", 200);
    }

    @Test
    public void testIncrementarReproducoes() {
        musica.incrementaNumeroDeReproducoes();
        musica.incrementaNumeroDeReproducoes();
        assertEquals(2, musica.getNumeroDeReproducoes());
    }

    @Test
    public void testReproduzir() {
        String letra = musica.reproduzir();
        assertEquals("Lyrics1", letra);
        assertEquals(1, musica.getNumeroDeReproducoes());
    }


    @Test
    public void testCloneMusicaExplicita() {
        MusicaExplicita m = new MusicaExplicita("Nome", "Int", "Ed", "Letra", "Notas", "Rock", 150);
        Musica c = m.clone();
        assertEquals(m.getNome(), c.getNome());
        assertTrue(c instanceof MusicaExplicitaInterface);
    }

    @Test
    public void testGetSetUrlVideo() {
        MusicaMultimedia m = new MusicaMultimedia("Nome", "Int", "Ed", "Letra", "Notas", "Jazz", 120, "http://video");
        assertEquals("http://video", m.getUrlVideo());

        m.setUrlVideo("http://newvideo");
        assertEquals("http://newvideo", m.getUrlVideo());
    }

    @Test
    public void testCloneMusicaMultimedia() {
        MusicaMultimedia m = new MusicaMultimedia("Nome", "Int", "Ed", "Letra", "Notas", "Jazz", 120, "http://video");
        Musica c = m.clone();
        assertTrue(c instanceof MusicaMultimedia);
    }

    @Test
    public void testCloneMusicaMultimediaExplicita() {
        MusicaMultimediaExplicita m = new MusicaMultimediaExplicita("Nome", "Int", "Ed", "Letra", "Notas", "Jazz", 180, "http://video");
        Musica c = m.clone();
        assertTrue(c instanceof MusicaMultimediaExplicita);
        assertTrue(c instanceof MusicaExplicitaInterface);
    }

}
