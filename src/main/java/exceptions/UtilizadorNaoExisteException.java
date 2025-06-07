package exceptions;

/**
 * Exceção lançada quando um utilizador não existe.
 * 
 * Esta exceção é utilizada para indicar que uma operação que requer a existência de um
 * utilizador falhou porque o utilizador não está presente.
 */
public class UtilizadorNaoExisteException extends Exception {
    /**
     * Construtor padrão da exceção lançada quando um utilizador não existe.
     * 
     * @param mensagem Mensagem de erro a ser exibida.
     */
    public UtilizadorNaoExisteException(String mensagem) {
        super(mensagem);
    }
}

