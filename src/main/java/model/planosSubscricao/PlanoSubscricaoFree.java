package model.planosSubscricao;

import java.io.Serializable;
import model.estrategias.*;

/**
 * Plano de subscrição Free, com permissões limitadas.
 * 
 * Permite tornar playlists públicas, mas não permite adicionar músicas à biblioteca,
 * criar playlists nem gerar playlists personalizadas.
 */
public class PlanoSubscricaoFree extends PlanoSubscricao implements Serializable {

    /**
     * Construtor que configura as estratégias de permissão para o plano Free.
     */
    public PlanoSubscricaoFree() {
        this.estrategiaAdicionarPlaylistAleatoria = new EstrategiaPlanoSubscricaoPermite();
        this.estrategiaTornarPlaylistPublica = new EstrategiaPlanoSubscricaoNaoPermite();
        this.estrategiaAdicionarABiblioteca = new EstrategiaPlanoSubscricaoNaoPermite();
        this.estrategiaCriarPlaylist = new EstrategiaPlanoSubscricaoNaoPermite();
        this.estrategiaGerarPlaylistPersonalizadaPeloSistema = new EstrategiaPlanoSubscricaoNaoPermite();
        this.estrategiaGerarPlaylistListaDeFavoritos = new EstrategiaPlanoSubscricaoNaoPermite();
    }
    
    /**
     * Método que retorna os pontos por Música reproduzida de um utilizador com plano Free.
     * 
     * @param pontosAtuais Pontos atuais do utilizador.
     * @return Pontos por Música reproduzida.
     */
    @Override
    public int pontosPorMusica(int pontosAtuais) {
        return 5;
    }

    /**
     * Método que clona o plano de subscrição Free.
     * 
     * @return Uma nova instância do plano de subscrição Free.
     */
    @Override
    public PlanoSubscricao clone() {
        return new PlanoSubscricaoFree();
    }
}
