package exceptions;

/**
 * Exceção lançada quando um álbum não existe na biblioteca.
 * 
 * Esta exceção é utilizada para indicar que uma operação de busca ou remoção de álbum
 * falhou porque o álbum não está presente na biblioteca.
 */
public class AlbumNaoExisteException extends Exception {

    /**
     * Construtor padrão da exceção lançada quando um álbum não existe na biblioteca.
     * 
     * @param message Mensagem de erro a ser exibida.
     */
    public AlbumNaoExisteException(String message) {
        super(message);
    }
    
}
