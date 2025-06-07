package model;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import exceptions.ColecaoDeMusicaVaziaException;
import exceptions.MusicaJaExisteException;
import exceptions.MusicaNaoExisteException;
import model.musica.Musica;

public class AlbumTest {

    @Test
    public void testGetNomeEArtista() {
        Album album = new Album("Álbum de Teste", "Artista X", List.of());
        assertEquals("Álbum de Teste", album.getNome());
        assertEquals("Artista X", album.getArtista());
    }

    @Test
    public void testGetMusicaPorNome() {
        Musica m = new Musica("Música c", "Artista", "Editora1", "letra", "notas", "Genero", 200);
        Album album = new Album("Top", "Artista", List.of(m));
        assertEquals("Música c", album.getMusica("Música c").getNome());
    }

    @Test
    public void testGetMusicaPorIndice() throws ColecaoDeMusicaVaziaException {
        Musica m = new Musica("Música A", "Artista", "Editora1", "letra", "notas", "Genero", 200);
        Album album = new Album("Rock", "Artista", List.of(m));
        assertEquals("Música A", album.getMusicaPorIndice(0).getNome());
    }

    @Test
    public void testGetMusicaPorIndiceAlbumVazio() {
        Album album = new Album();
        assertThrows(ColecaoDeMusicaVaziaException.class, () -> {
            album.getMusicaPorIndice(0);
        });
    }

    @Test
    public void testMusicaMaisReproduzida() {
        Musica m1 = new Musica("Música B", "Artista", "Editora1", "letra", "notas", "Genero", 200);
        Musica m2 = new Musica("Música E", "Artista", "Editora1", "letra", "notas", "Genero", 200);
        m1.reproduzir(); // 1 vez
        m2.reproduzir(); m2.reproduzir(); // 2 vezes
        Album album = new Album("Mix", "A", List.of(m1, m2));
        assertEquals("Música E", album.musicaMaisReproduzida().getNome());
    }

    @Test
    public void testMusicaMaisReproduzidaVazia() {
        Album album = new Album();
        assertNull(album.musicaMaisReproduzida());
    }

    @Test
    public void testGetAndSetNome() {
        Album album = new Album();
        album.setNome("Novo Álbum");
        assertEquals("Novo Álbum", album.getNome());
    }

    @Test
    public void testGetAndSetArtista() {
        Album album = new Album();
        album.setArtista("Novo Artista");
        assertEquals("Novo Artista", album.getArtista());
    }


    @Test
public void testConstrutorClonaMusicas() {
    Musica m = new Musica("Original", "Artista", "Editora", "Letra", "Notas", "Genero", 180);
    List<Musica> originais = List.of(m);
    Album album = new Album("Álbum", "Artista", originais);

    // Alterar a música original
    m.setNome("Alterada");

    // Verifica que no álbum o nome continua "Original"
    assertEquals("Original", album.getMusica("Original").getNome());
}

@Test
public void testGetMusicasRetornaClone() {
    Musica m = new Musica("Track 1", "Artista", "Editora", "Letra", "Notas", "Genero", 180);
    Album album = new Album("Álbum", "Artista", List.of(m));

    List<Musica> musicas = album.getMusicas();
    musicas.get(0).setNome("Modificada");

    // Verifica que o nome da música original no álbum **não** mudou
    assertEquals("Track 1", album.getMusica("Track 1").getNome());
}

@Test
public void testAdicionaMusicaClonaEntrada() throws MusicaJaExisteException {
    Musica m = new Musica("Nova", "Artista", "Editora", "Letra", "Notas", "Genero", 180);
    Album album = new Album();
    album.adicionaMusica(m);

    // Modifica a música original
    m.setNome("Alterada");

    // Verifica que a música no álbum continua com o nome antigo
    assertEquals("Nova", album.getMusica("Nova").getNome());
}

@Test
public void testMusicaMaisReproduzidaEhClone() {
    Musica m = new Musica("Mais Tocada", "Artista", "Editora", "Letra", "Notas", "Genero", 180);
    m.reproduzir(); m.reproduzir();
    Album album = new Album("Hits", "DJ", List.of(m));

    Musica mais = album.musicaMaisReproduzida();
    mais.setNome("Modificada");

    // A música original no álbum continua igual
    assertEquals("Mais Tocada", album.getMusica("Mais Tocada").getNome());
}

@Test
public void testGetMusicasOriginaisNaoClona() {
    Musica m = new Musica("Original", "Artista", "Editora", "Letra", "Notas", "Genero", 180);
    Album album = new Album("Álbum", "Artista", List.of(m));

    List<Musica> originais = album.getMusicasOriginais();
    originais.get(0).setNome("Alterada");

    // Verifica que a música no álbum também mudou
    assertEquals("Alterada", album.getMusica("Alterada").getNome());
}

@Test
public void testCloneAlbum() {
    Musica m = new Musica("Clone", "Artista", "Editora", "Letra", "Notas", "Genero", 120);
    Album original = new Album("Orig", "Band", List.of(m));
    Album copia = original.clone();

    assertEquals("Orig", copia.getNome());
    assertEquals("Band", copia.getArtista());
    assertEquals(1, copia.getMusicas().size());
    assertNotSame(original, copia);
    assertNotSame(original.getMusicas().get(0), copia.getMusicas().get(0));
}

@Test
public void testConstrutorDeCopia() {
    Musica m = new Musica("CopyTrack", "Artista", "Editora", "Letra", "Notas", "Genero", 150);
    Album original = new Album("Álbum X", "Artista X", List.of(m));
    Album copia = new Album(original);

    assertEquals(original.getNome(), copia.getNome());
    assertEquals(original.getArtista(), copia.getArtista());
    assertNotSame(original.getMusicas().get(0), copia.getMusicas().get(0));
}

@Test
public void testRemoveMusica() throws MusicaNaoExisteException {
    Musica m = new Musica("Para Remover", "Artista", "Editora", "Letra", "Notas", "Genero", 180);
    Album album = new Album("Test", "Artista", List.of(m));

    // Remove com objeto idêntico
    album.removerMusica(m);
    assertEquals(0, album.getTamanho());
}

@Test
public void testGetTipoDeColecao() {
    Album album = new Album();
    assertEquals("Album", album.getTipoDeColecao());
}

@Test
public void testGetMusicasMantemEncapsulamento() {
    Musica m = new Musica("Inalterável", "Artista", "Editora", "Letra", "Notas", "Genero", 200);
    Album album = new Album("Título", "Artista", List.of(m));
    List<Musica> musicas = album.getMusicas();

    // Tentar modificar a lista retornada
    musicas.get(0).setNome("Modificado");

    // Verificar que o estado interno do álbum não foi alterado
    assertEquals("Inalterável", album.getMusica("Inalterável").getNome());
}

@Test
public void testAdicionaMusicaRepetidaLancaExcecao() throws MusicaJaExisteException {
    Musica m = new Musica("Repetida", "Artista", "Editora", "Letra", "Notas", "Genero", 200);
    Album album = new Album();
    album.adicionaMusica(m);

    // Tentar adicionar novamente deve lançar exceção
    assertThrows(MusicaJaExisteException.class, () -> album.adicionaMusica(m));
}

@Test
public void testRemoveMusicaInexistenteLancaExcecao() {
    Musica m = new Musica("Inexistente", "Artista", "Editora", "Letra", "Notas", "Genero", 200);
    Album album = new Album();

    // Tentar remover deve lançar exceção
    assertThrows(MusicaNaoExisteException.class, () -> album.removerMusica(m));
}



}
