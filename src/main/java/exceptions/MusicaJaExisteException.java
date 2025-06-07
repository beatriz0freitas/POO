package exceptions;

/**
 * Exceção lançada quando uma música já existe na biblioteca.
 * 
 * Esta exceção é utilizada para indicar que uma operação de adição de música falhou
 * porque a música já está presente na biblioteca.
 */
public class MusicaJaExisteException extends Exception {
    /**
     * Construtor padrão da exceção lançada quando uma música já existe na biblioteca.
     * 
     * @param message Mensagem de erro a ser exibida.
     */
    public MusicaJaExisteException(String message) {
        super(message);
    }

}
