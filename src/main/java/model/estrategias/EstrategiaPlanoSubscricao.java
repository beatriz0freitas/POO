package model.estrategias;

import exceptions.PlanoDeSubscricaoNaoPermiteException;

/**
 * Interface funcional que define uma estratégia para executar uma ação
 * dependendo das permissões do plano de subscrição.
 *
 * Pode lançar uma exceção se a ação não for permitida.
 */
@FunctionalInterface
public interface EstrategiaPlanoSubscricao {
    
    /**
     * Executa uma ação de acordo com a estratégia definida.
     *
     * @param acao A ação a ser executada como um {@link Runnable}.
     * @throws PlanoDeSubscricaoNaoPermiteException se o plano de subscrição não permitir a execução da ação.
     */
    void executar(Runnable acao) throws PlanoDeSubscricaoNaoPermiteException;
}
