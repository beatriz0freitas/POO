package model.planosSubscricao;

import model.estrategias.*;

/**
 * Plano de subscrição Premium Base com permissões ampliadas.
 * 
 * Permite adicionar músicas à biblioteca, criar playlists e tornar playlists públicas,
 * mas não permite gerar playlists personalizadas pelo sistema.
 */
public class PlanoSubscricaoPremiumBase extends PlanoSubscricaoFree {

    /**
     * Construtor que configura as estratégias de permissão para o plano Premium Base.
     */
    public PlanoSubscricaoPremiumBase() {
        this.estrategiaAdicionarPlaylistAleatoria = new EstrategiaPlanoSubscricaoPermite();
        this.estrategiaTornarPlaylistPublica = new EstrategiaPlanoSubscricaoPermite();
        this.estrategiaAdicionarABiblioteca = new EstrategiaPlanoSubscricaoPermite();
        this.estrategiaCriarPlaylist = new EstrategiaPlanoSubscricaoPermite();
        this.estrategiaGerarPlaylistPersonalizadaPeloSistema = new EstrategiaPlanoSubscricaoNaoPermite("Plano Base não permite gerar playlist personalizadas.");
        this.estrategiaGerarPlaylistListaDeFavoritos = new EstrategiaPlanoSubscricaoPermite();
    }

    /**
     * Método que retorna os pontos por Música reproduzida de um utilizador com plano Premium Base.
     * 
     * @param pontosAtuais Pontos atuais do utilizador.
     * @return Pontos por Música reproduzida.
     */
    @Override
    public int pontosPorMusica(int pontosAtuais) {
        return 10;
    }

    /**
     * Método que clona o plano de subscrição Premium Base.
     * 
     * @return Uma nova instância do plano de subscrição Premium Base.
     */
    @Override
    public PlanoSubscricao clone() {
        return new PlanoSubscricaoPremiumBase();
    }
}
