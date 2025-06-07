package exceptions;

/**
 * Exceção lançada quando o histórico de músicas está vazio.
 * 
 * Esta exceção é utilizada para indicar que uma operação que requer um histórico de
 * músicas falhou porque o histórico está vazio.
 */
public class HistoricoVazioException extends Exception {
    /**
     * Construtor padrão da exceção lançada quando o histórico de músicas está vazio.
     * 
     * @param message Mensagem de erro a ser exibida.
     */
    public HistoricoVazioException(String message) {
        super(message);
    }
}
