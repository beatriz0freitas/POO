package model.estrategias;

import exceptions.PlanoDeSubscricaoNaoPermiteException;

/**
 * Estratégia de plano de subscrição que não permite a execução da ação.
 * 
 * Sempre lança uma {@link PlanoDeSubscricaoNaoPermiteException} ao tentar executar a ação.
 */
public class EstrategiaPlanoSubscricaoNaoPermite implements EstrategiaPlanoSubscricao {

    private final String mensagemPersonalizada;

    /**
     * Construtor padrão com uma mensagem de erro genérica.
     */
    public EstrategiaPlanoSubscricaoNaoPermite() {
        this.mensagemPersonalizada = "Plano de subscrição não permite a ação";
    }

    /**
     * Construtor que permite definir uma mensagem de erro personalizada.
     *
     * @param mensagemPersonalizada A mensagem de erro personalizada a ser usada na exceção.
     */
    public EstrategiaPlanoSubscricaoNaoPermite(String mensagemPersonalizada) {
        this.mensagemPersonalizada = mensagemPersonalizada;
    }

    /**
     * Lança uma exceção, indicando que a ação não é permitida.
     *
     * @param acao Ignorada nesta implementação.
     * @throws PlanoDeSubscricaoNaoPermiteException sempre lançada com a mensagem definida.
     */
    @Override
    public void executar(Runnable acao) throws PlanoDeSubscricaoNaoPermiteException {
        throw new PlanoDeSubscricaoNaoPermiteException(mensagemPersonalizada);
    }
}
