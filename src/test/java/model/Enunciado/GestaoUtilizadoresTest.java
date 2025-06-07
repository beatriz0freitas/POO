package model.Enunciado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import exceptions.UtilizadorJaExisteException;
import exceptions.UtilizadorNaoExisteException;
import model.SpotifUMModel;
import model.Utilizador;
import model.planosSubscricao.PlanoSubscricaoFree;
import model.planosSubscricao.PlanoSubscricaoPremiumBase;
import model.planosSubscricao.PlanoSubscricaoPremiumTop;

public class GestaoUtilizadoresTest {

    private SpotifUMModel model;

    @BeforeEach
    void setUp() {
        model = new SpotifUMModel();
    }

    @Test
    void testAdicionarUtilizadorComSucessoPlanoDefaultFree() throws UtilizadorNaoExisteException, UtilizadorJaExisteException {
        Utilizador novoUser = new Utilizador("Utilizador Free", "Rua F", "free@example.com");
        // O enunciado diz (p2, 1.3): "plano de subscrição Free, que é o plano por omissão"
        // A classe Utilizador no seu código já instancia PlanoSubscricaoFree por defeito.
        model.adicionarUtilizador(novoUser);
        assertEquals(1, model.getUtilizadores().size());
        Utilizador recuperado = model.getUtilizadorPorEmail("free@example.com");
        assertEquals("Utilizador Free", recuperado.getNome());
        assertTrue(recuperado.getPlanoSubscricao() instanceof PlanoSubscricaoFree, "Plano por omissão deve ser Free.");
        assertEquals(0, recuperado.getPontosAtuais(), "Utilizador Free começa com 0 pontos (sem pontos de adesão).");
    }

    @Test
    void testAdicionarUtilizadorPremiumBase() throws UtilizadorNaoExisteException, UtilizadorJaExisteException {
        Utilizador userPB = new Utilizador("Utilizador PB", "Rua PB", "pb@example.com");
        userPB.setPlanoSubscricao(new PlanoSubscricaoPremiumBase());
        model.adicionarUtilizador(userPB);
        Utilizador recuperado = model.getUtilizadorPorEmail("pb@example.com");
        assertTrue(recuperado.getPlanoSubscricao() instanceof PlanoSubscricaoPremiumBase);
        assertEquals(0, recuperado.getPontosAtuais(), "Premium Base não tem pontos de adesão especificados no enunciado.");
    }

    @Test
    void testAdicionarUtilizadorPremiumTopRecebe100PontosAdesao() throws UtilizadorNaoExisteException, UtilizadorJaExisteException {
        // Enunciado (p3, 1.3): "Premium Top recebem 100 pontos pela adesão"
        Utilizador userPT = new Utilizador("Utilizador PT", "Rua PT", "pt@example.com");
        userPT.setPlanoSubscricao(new PlanoSubscricaoPremiumTop());
        
        // Assumindo que a lógica de pontos de adesão é tratada ao adicionar o utilizador
        // ou ao definir o plano, e que o SpotifUMModel.adicionarUtilizador assegura isto.
        // Se a classe PlanoSubscricaoPremiumTop ou Utilizador não gerir isto automaticamente,
        // o SpotifUMModel.adicionarUtilizador teria que explicitamente setar os pontos.
        // Vamos assumir que o construtor de Utilizador ou o setPlanoSubscricao lida com os pontos de adesão
        // ou que o SpotifUMModel o faz.
        // Se a lógica for no PlanoSubscricaoPremiumTop:
        // ((PlanoSubscricaoPremiumTop) userPT.getPlanoSubscricao()).aplicarPontosAdesao(userPT); // Hipotético
        // Se for no SpotifUMModel:
        // model.adicionarUtilizador(userPT); // O método adicionaUtilizador deveria chamar a lógica de pontos de adesão.

        // Testando o resultado esperado após a adição:
        // Para que este teste funcione, o Utilizador deve ter 100 pontos após setPlanoSubscricao(new PlanoSubscricaoPremiumTop())
        // ou o SpotifUMModel.adicionarUtilizador() deve garantir isso.
        // A forma mais simples é o Utilizador ou o PlanoSubscricaoPremiumTop definir isto.
        // No seu código Utilizador.java, os pontos são 0 por defeito.
        // No seu código PlanoSubscricaoPremiumTop.java, há `pontosAdesao()` mas não é usado para setar no utilizador.
        // Este teste irá FALHAR com o código atual, pois os pontos não são automaticamente atribuídos na adesão.
        // O teste é escrito CONFORME O ENUNCIADO.
        
        // SOLUÇÃO HIPOTÉTICA NO MODELO:
        // No SpotifUMModel.java, ao adicionarUtilizador:
        // if (utilizador.getPlanoSubscricao() instanceof PlanoSubscricaoPremiumTop) {
        //     utilizador.setPontosAtuais(utilizador.getPontosAtuais() + ((PlanoSubscricaoPremiumTop) utilizador.getPlanoSubscricao()).pontosAdesao());
        // }
        // Ou, mais elegantemente, o Utilizador.setPlanoSubscricao poderia lidar com isto.

        // Para o teste (assumindo que o modelo/plano será corrigido para aplicar pontos de adesão):
        // Se o plano for definido *antes* de adicionar ao modelo, e o modelo aplicar os pontos:
        model.adicionarUtilizador(userPT); // Este método deve garantir os pontos de adesão.
                                           // Se não o fizer, o teste falha, como esperado.
        Utilizador recuperado = model.getUtilizadorPorEmail("pt@example.com");
        assertTrue(recuperado.getPlanoSubscricao() instanceof PlanoSubscricaoPremiumTop);
        assertEquals(100, recuperado.getPontosAtuais(), "Premium Top deve receber 100 pontos pela adesão.");
    }


    @Test
    void testAdicionarUtilizadorDuplicadoLancaExcecao() throws UtilizadorJaExisteException {
        Utilizador user1 = new Utilizador("Utilizador Um", "Rua Um", "user1@example.com");
        model.adicionarUtilizador(user1);
        Utilizador user1Dup = new Utilizador("Utilizador Um Dup", "Rua Um Dup", "user1@example.com"); // Mesmo email
        assertThrows(UtilizadorJaExisteException.class, () -> model.adicionarUtilizador(user1Dup));
    }

    @Test
    void testRemoverUtilizadorExistente() throws UtilizadorJaExisteException, UtilizadorNaoExisteException {
        Utilizador user1 = new Utilizador("Para Remover", "Rua R", "remover@example.com");
        model.adicionarUtilizador(user1);
        assertEquals(1, model.getUtilizadores().size());
        model.removerUtilizador(user1);
        assertEquals(0, model.getUtilizadores().size());
        assertThrows(UtilizadorNaoExisteException.class, () -> model.getUtilizadorPorEmail("remover@example.com"));
    }

    @Test
    void testRemoverUtilizadorNaoExistenteLancaExcecao() {
        Utilizador userNaoExistente = new Utilizador("Fantasma", "Invisivel", "fantasma@example.com");
        assertThrows(UtilizadorNaoExisteException.class, () -> model.removerUtilizador(userNaoExistente));
    }

    @Test
    void testGetUtilizadorExistente() throws UtilizadorJaExisteException, UtilizadorNaoExisteException {
        Utilizador user1 = new Utilizador("Utilizador Um Get", "Rua G", "get@example.com");
        model.adicionarUtilizador(user1);
        Utilizador u = model.getUtilizadorPorEmail("get@example.com");
        assertNotNull(u);
        assertEquals("Utilizador Um Get", u.getNome());
        assertEquals("get@example.com", u.getEmail());
        assertEquals("Rua G", u.getMorada());
    }

    @Test
    void testGetUtilizadorNaoExistenteLancaExcecao() {
        assertThrows(UtilizadorNaoExisteException.class, () -> model.getUtilizadorPorEmail("naoexiste@example.com"));
    }
}