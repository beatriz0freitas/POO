package model.estrategias;

import exceptions.PlanoDeSubscricaoNaoPermiteException;

/**
 * Estratégia de plano de subscrição que permite a execução da ação.
 * 
 * Implementa {@link EstrategiaPlanoSubscricao}, executando diretamente a ação fornecida.
 */
public class EstrategiaPlanoSubscricaoPermite implements EstrategiaPlanoSubscricao {

    /**
     * Executa a ação fornecida, sem lançar exceções.
     *
     * @param acao A ação a ser executada.
     * @throws PlanoDeSubscricaoNaoPermiteException nunca é lançada nesta implementação.
     */
    @Override
    public void executar(Runnable acao) throws PlanoDeSubscricaoNaoPermiteException {
        acao.run();
    }
}
