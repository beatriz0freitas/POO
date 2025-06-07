package exceptions;

/**
 * Exceção lançada quando uma coleção de músicas está vazia.
 * 
 * Esta exceção é utilizada para indicar que uma operação que requer uma coleção de
 * músicas falhou porque a coleção está vazia.
 */
public class ColecaoDeMusicaVaziaException extends Exception {

    /**
     * Construtor padrão da exceção lançada quando uma coleção de músicas está vazia.
     * 
     * @param message Mensagem de erro a ser exibida.
     */
    public ColecaoDeMusicaVaziaException(String message) {
        super(message);
    }
    
}
