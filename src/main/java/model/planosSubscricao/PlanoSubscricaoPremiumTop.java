package model.planosSubscricao;

import model.estrategias.*;

/**
 * Plano de subscrição Premium Top com permissões completas.
 * 
 * Permite todas as ações do Premium Base e também gerar playlists personalizadas pelo sistema.
 * Oferece bônus de adesão e pontos por música baseados em uma fórmula percentual.
 */
public class PlanoSubscricaoPremiumTop extends PlanoSubscricaoPremiumBase {

    /**
     * Construtor que configura as estratégias de permissão para o plano Premium Top.
     */
    public PlanoSubscricaoPremiumTop() {
        this.estrategiaAdicionarPlaylistAleatoria = new EstrategiaPlanoSubscricaoPermite();
        this.estrategiaTornarPlaylistPublica = new EstrategiaPlanoSubscricaoPermite();
        this.estrategiaAdicionarABiblioteca = new EstrategiaPlanoSubscricaoPermite();
        this.estrategiaCriarPlaylist = new EstrategiaPlanoSubscricaoPermite();
        this.estrategiaGerarPlaylistPersonalizadaPeloSistema = new EstrategiaPlanoSubscricaoPermite();
        this.estrategiaGerarPlaylistListaDeFavoritos = new EstrategiaPlanoSubscricaoPermite();
    }

    /**
     * Método que retorna os pontos por Música reproduzida de um utilizador com plano Premium Top.
     * 
     * @param pontosAtuais Pontos atuais do utilizador.
     * @return Pontos por Música reproduzida.
     */
    @Override
    public int pontosPorMusica(int pontosAtuais) {
        return (int) Math.round(pontosAtuais * 0.025);
    }

    /**
     * Método que atribui ao utilizador um bônus de adesão de 100 pontos.
     * 
     * @return Valor do bonus de adesão.
     */
    @Override
    public int bonusAdesao() {
        return 100;
    }

    /**
     * Método que clona o plano de subscrição Premium Top.
     * 
     * @return Uma nova instância do plano de subscrição Premium Top.
     */
    @Override
    public PlanoSubscricao clone() {
        return new PlanoSubscricaoPremiumTop();
    }
}
