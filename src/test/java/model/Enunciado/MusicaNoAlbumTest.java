package model.Enunciado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import exceptions.AlbumJaExisteException;
import exceptions.AlbumNaoExisteException;
import exceptions.MusicaJaExisteException;
import exceptions.MusicaNaoExisteException;
import model.Album;
import model.musica.*;
import model.SpotifUMModel;

import java.util.ArrayList;

public class MusicaNoAlbumTest {

    private SpotifUMModel model;
    private Album albumPrincipal;
    private Musica musicaNormal;
    private MusicaExplicita musicaExplicita;
    private MusicaMultimedia musicaMultimedia;


    @BeforeEach
    void setUp() throws AlbumJaExisteException, MusicaJaExisteException, AlbumNaoExisteException {
        model = new SpotifUMModel();
        
        musicaNormal = new Musica("Musica Normal", "Artista Comum", "Editora C", "Letra N", "Notas N", "Pop", 180);
        // Enunciado (p2, 1.1): "música aparece sempre no contexto de um álbum"
        albumPrincipal = new Album("Album Principal Teste", "Artista Comum", new ArrayList<>());
        model.adicionarAlbum(albumPrincipal); // Adiciona álbum ao modelo
        // Adiciona musicaNormal ao albumPrincipal através do modelo para consistência
        model.adicionarMusicaAoAlbum(albumPrincipal, musicaNormal);


        // Enunciado (p2, 1.1) e (p4, 3.3) referem MusicaExplicita e MusicaMultimedia
        musicaExplicita = new MusicaExplicita("Som Explícito", "Artista Ousado", "Heavy Records", "Letra forte...", "Notas E", "Rap", 200);
        musicaMultimedia = new MusicaMultimedia("Video Clip Hit", "Pop Star", "Visual Media", "Letra V", "Notas V", "Eletrónica", 220, "http://video.url");
    }

    @Test
    void testAdicionarNovaMusicaAoAlbumComSucesso() throws AlbumNaoExisteException, MusicaJaExisteException {
        Musica novaMusicaNoTeste = new Musica("Outra Musica", "Artista Comum", "Editora C", "Letra O", "Notas O", "Rock", 150);
        
        // Obtém o álbum do modelo para garantir que estamos a operar na instância gerida
        Album albumDoModel = model.getAlbum(albumPrincipal.getNome());
        model.adicionarMusicaAoAlbum(albumDoModel, novaMusicaNoTeste);

        Album albumAtualizado = model.getAlbum(albumPrincipal.getNome());
        assertTrue(albumAtualizado.getMusicas().stream().anyMatch(m -> m.getNome().equals("Outra Musica")));
        assertEquals(2, albumAtualizado.getMusicas().size(), "Deveria ter a musicaNormal do setup e a novaMusicaNoTeste.");
    }
    
    @Test
    void testAdicionarMusicaExplicitaAoAlbum() throws AlbumNaoExisteException, MusicaJaExisteException {
        Album albumDoModel = model.getAlbum(albumPrincipal.getNome());
        model.adicionarMusicaAoAlbum(albumDoModel, musicaExplicita);
        Album albumAtualizado = model.getAlbum(albumPrincipal.getNome());
        assertTrue(albumAtualizado.getMusicas().stream().anyMatch(m -> m.getNome().equals("Som Explícito") && m instanceof MusicaExplicita));
    }

    @Test
    void testAdicionarMusicaMultimediaAoAlbum() throws AlbumNaoExisteException, MusicaJaExisteException {
        Album albumDoModel = model.getAlbum(albumPrincipal.getNome());
        model.adicionarMusicaAoAlbum(albumDoModel, musicaMultimedia);
        Album albumAtualizado = model.getAlbum(albumPrincipal.getNome());

        Musica recuperada = albumAtualizado.getMusicas().stream()
            .filter(m -> m.getNome().equals("Video Clip Hit") && m instanceof MusicaMultimedia)
            .findFirst().orElse(null);
        assertNotNull(recuperada);
        assertEquals("http://video.url", ((MusicaMultimedia) recuperada).getUrlVideo());
    }


    @Test
    void testAdicionarMusicaDuplicadaAoAlbumLancaExcecao() throws AlbumNaoExisteException {
        // musicaNormal já existe no albumPrincipal (adicionada no setup)
        Album albumDoModel = model.getAlbum(albumPrincipal.getNome());
        assertThrows(MusicaJaExisteException.class, () -> model.adicionarMusicaAoAlbum(albumDoModel, musicaNormal));
    }
    
    @Test
    void testAdicionarMusicaAAlbumNaoExistenteNoModelLancaExcecao() {
        // Este teste verifica se o SpotifUMModel.adicionarMusicaAoAlbum
        // lida com o caso em que o *nome do álbum do objeto Album passado* não corresponde a um álbum no seu map.
        Album albumNaoGeridoPeloModel = new Album("NaoEstaNoModel", "Fantasma", new ArrayList<>());
        Musica musicaQualquer = new Musica();
        assertThrows(AlbumNaoExisteException.class, () -> model.adicionarMusicaAoAlbum(albumNaoGeridoPeloModel, musicaQualquer));
    }


    @Test
    void testRemoverMusicaDeAlbumExistente() throws AlbumNaoExisteException, MusicaNaoExisteException {
        // musicaNormal foi adicionada no setup
        assertEquals(1, model.getAlbum(albumPrincipal.getNome()).getMusicas().size());
        model.removerMusicaDoAlbum(albumPrincipal.getNome(), musicaNormal.getNome());
        
        Album albumAtualizado = model.getAlbum(albumPrincipal.getNome());
        assertFalse(albumAtualizado.getMusicas().stream().anyMatch(m -> m.getNome().equals(musicaNormal.getNome())));
        assertTrue(albumAtualizado.getMusicas().isEmpty());
    }

    @Test
    void testRemoverMusicaNaoExistenteDeAlbumLancaExcecao() {
        assertThrows(MusicaNaoExisteException.class, () -> model.removerMusicaDoAlbum(albumPrincipal.getNome(), "Musica Fantasma"));
    }

    @Test
    void testRemoverMusicaDeAlbumNaoExistenteLancaExcecao() {
        assertThrows(AlbumNaoExisteException.class, () -> model.removerMusicaDoAlbum("Album Fantasma", musicaNormal.getNome()));
    }
}