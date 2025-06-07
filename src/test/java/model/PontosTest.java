package  model;

import  static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import model.planosSubscricao.PlanoSubscricaoPremiumBase;
import model.planosSubscricao.PlanoSubscricaoPremiumTop;

public class PontosTest {

    // Testa os pontos do plano top
    @Test
    public void testPontosPorMusica() {
        PlanoSubscricaoPremiumTop plano = new PlanoSubscricaoPremiumTop();
        int pontosAtuais = 1000;
        int pontosEsperados = 25;
        assertEquals(pontosEsperados, plano.pontosPorMusica(pontosAtuais));
    }

    // Testa os pontos do plano base
    @Test
    public void testPontosPorMusicaBase() {
        PlanoSubscricaoPremiumBase plano = new PlanoSubscricaoPremiumBase();
        int pontosAtuais = 1000;
        int pontosEsperados = 10;
        assertEquals(pontosEsperados, plano.pontosPorMusica(pontosAtuais));
    }
}