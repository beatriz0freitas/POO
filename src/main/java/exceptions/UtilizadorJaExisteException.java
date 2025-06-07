package exceptions;

/**
 * Exceção lançada quando um utilizador já existe.
 * 
 * Esta exceção é utilizada para indicar que uma operação de adição de utilizador falhou
 * porque o utilizador já está presente na coleção de utilizadores.
 */
public class UtilizadorJaExisteException extends Exception  {
    /**
     * Construtor padrão da exceção lançada quando um utilizador já existe.
     * 
     * @param mensagem Mensagem de erro a ser exibida.
     */
    public UtilizadorJaExisteException(String mensagem) {
        super(mensagem);
    }


}
