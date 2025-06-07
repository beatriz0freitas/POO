package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.musica.Musica;

public class HistoricoTest {

    private Historico historico;
    private RegistoDeReproducao registo;

    @BeforeEach
    public void setUp() {
        Musica musica = new Musica("Som", "Artista", "Editora", "Letra", "Notas", "Gênero", 180);
        Reproducao reproducao = new Reproducao(musica);
        registo = new RegistoDeReproducao(reproducao);
        historico = new Historico();
    }

    @Test
    public void testAdicionarRegisto() {
        historico.adicionarRegistoDeReproducaoAoHistorico(registo);
        assertEquals(1, historico.getHistorico().size());
    }

    @Test
    public void testUpdate() {
        Musica musica = new Musica("Som", "Artista", "Editora", "Letra", "Notas", "Gênero", 180);
        Reproducao reproducao = new Reproducao(musica);
        historico.update(reproducao);
        assertEquals(1, historico.getHistorico().size());
        String nome = historico.getHistorico().get(0).getMusicaReproduzida(); 
        assertEquals("Som", nome);
    }
}
