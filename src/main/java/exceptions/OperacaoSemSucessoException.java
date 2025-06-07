package exceptions;

/**
 * Exceção lançada quando uma operação não é bem-sucedida.
 * 
 * Esta exceção é utilizada para indicar que uma operação falhou por algum motivo
 * específico.
 */
public class OperacaoSemSucessoException extends Exception{
    /**
     * Construtor padrão da exceção lançada quando uma operação não é bem-sucedida.
     * 
     * @param message Mensagem de erro a ser exibida.
     */
    public OperacaoSemSucessoException(String message) {
        super(message);
    }
}
