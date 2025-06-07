package exceptions;

/**
 * Exceção lançada quando uma música não existe na biblioteca.
 * 
 * Esta exceção é utilizada para indicar que uma operação de busca ou remoção de
 * música falhou porque a música não está presente na biblioteca.
 */
public class MusicaNaoExisteException extends Exception {
    /**
     * Construtor padrão da exceção lançada quando uma música não existe na
     * biblioteca.
     * 
     * @param message Mensagem de erro a ser exibida.
     */
    public MusicaNaoExisteException(String message) {
        super(message);
    }

}
