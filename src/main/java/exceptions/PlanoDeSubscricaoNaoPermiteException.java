package exceptions;

/**
 * Exceção lançada quando um plano de subscrição não permite uma operação específica.
 * 
 * Esta exceção é utilizada para indicar que uma operação não é permitida devido às
 * restrições do plano de subscrição atual.
 */
public class PlanoDeSubscricaoNaoPermiteException extends Exception {
    /**
     * Construtor padrão da exceção lançada quando um plano de subscrição não permite
     * uma operação específica.
     * 
     * @param message Mensagem de erro a ser exibida.
     */
    public PlanoDeSubscricaoNaoPermiteException(String message) {
        super(message);
    }
    
}
