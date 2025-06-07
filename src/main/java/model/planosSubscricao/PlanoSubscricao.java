package model.planosSubscricao;

import exceptions.PlanoDeSubscricaoNaoPermiteException;
import model.estrategias.*;
import java.util.Objects;

/**
 * Classe abstrata que representa um plano de subscrição genérico.
 * Define as estratégias para várias ações e fornece métodos para executar essas ações
 * com base nas permissões do plano.
 */
public abstract class PlanoSubscricao {

    protected EstrategiaPlanoSubscricao estrategiaAdicionarPlaylistAleatoria;
    protected EstrategiaPlanoSubscricao estrategiaTornarPlaylistPublica;
    protected EstrategiaPlanoSubscricao estrategiaAdicionarABiblioteca;
    protected EstrategiaPlanoSubscricao estrategiaCriarPlaylist;
    protected EstrategiaPlanoSubscricao estrategiaGerarPlaylistPersonalizadaPeloSistema;
    protected EstrategiaPlanoSubscricao estrategiaGerarPlaylistListaDeFavoritos;

    /**
     * Calcula os pontos ganhos por música com base nos pontos atuais.
     *
     * @param pontosAtuais Pontos atuais do usuário.
     * @return Quantidade de pontos concedidos por música.
     */
    public abstract int pontosPorMusica(int pontosAtuais);

    /**
     * Retorna o bônus de adesão para este plano.
     * O padrão é zero, mas pode ser sobrescrito por subclasses.
     *
     * @return Bônus de adesão em pontos.
     */
    public int bonusAdesao() {
        return 0;
    }

    /**
     * Retorna o nome simples do plano, baseado no nome da classe.
     *
     * @return Nome do plano.
     */
    public String getNomePlano() {
        return this.getClass().getSimpleName();
    }

    /**
     * Clona o plano de subscrição.
     *
     * @return Uma cópia do plano atual.
     */
    public abstract PlanoSubscricao clone();

    @Override
    public String toString() {
        return getNomePlano();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        return getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass());
    }

    /**
     * Executa a ação de adicionar uma playlist aleatória ao utilizador, se permitida pelo plano.
     *
     * @param metodo A ação a ser executada.
     * @throws PlanoDeSubscricaoNaoPermiteException Se a ação não for permitida pelo plano.
     */
    public void executarAdicionarPlaylistAleatoria(Runnable metodo) throws PlanoDeSubscricaoNaoPermiteException {
        estrategiaAdicionarPlaylistAleatoria.executar(metodo);
    }

    /**
     * Executa a ação de tornar uma playlist pública, se permitida pelo plano.
     *
     * @param metodo A ação a ser executada.
     * @throws PlanoDeSubscricaoNaoPermiteException Se a ação não for permitida pelo plano.
     */
    public void executarTornarPlaylistPublica(Runnable metodo) throws PlanoDeSubscricaoNaoPermiteException {
        estrategiaTornarPlaylistPublica.executar(metodo);
    }

    /**
     * Executa a ação de adicionar uma música à biblioteca, se permitida pelo plano.
     *
     * @param metodo A ação a ser executada.
     * @throws PlanoDeSubscricaoNaoPermiteException Se a ação não for permitida pelo plano.
     */
    public void executarAdicionarABiblioteca(Runnable metodo) throws PlanoDeSubscricaoNaoPermiteException {
        estrategiaAdicionarABiblioteca.executar(metodo);
    }

    /**
     * Executa a ação de criar uma playlist, se permitida pelo plano.
     *
     * @param metodo A ação a ser executada.
     * @throws PlanoDeSubscricaoNaoPermiteException Se a ação não for permitida pelo plano.
     */
    public void executarCriarPlaylist(Runnable metodo) throws PlanoDeSubscricaoNaoPermiteException {
        estrategiaCriarPlaylist.executar(metodo);
    }

    /**
     * Executa a ação de gerar playlist personalizada pelo sistema, se permitida pelo plano.
     *
     * @param metodo A ação a ser executada.
     * @throws PlanoDeSubscricaoNaoPermiteException Se a ação não for permitida pelo plano.
     */
    public void executarGerarPlaylistPersonalizadaPeloSistema(Runnable metodo) throws PlanoDeSubscricaoNaoPermiteException {
        estrategiaGerarPlaylistPersonalizadaPeloSistema.executar(metodo);
    }

    /**
     * Executa a ação de gerar playlist da lista de favoritos, se permitida pelo plano.
     *
     * @param metodo A ação a ser executada.
     * @throws PlanoDeSubscricaoNaoPermiteException Se a ação não for permitida pelo plano.
     */
    public void executarGerarPlaylistListaDeFavoritos(Runnable metodo) throws PlanoDeSubscricaoNaoPermiteException {
        estrategiaGerarPlaylistListaDeFavoritos.executar(metodo);
    }
}
