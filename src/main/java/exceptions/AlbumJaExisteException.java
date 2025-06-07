package exceptions;

/**
 * Exceção lançada quando um álbum já existe na biblioteca.
 * 
 * Esta exceção é utilizada para indicar que uma operação de adição de álbum falhou
 * porque o álbum já está presente na biblioteca.
 */
public class AlbumJaExisteException extends Exception {

    /**
     * Construtor padrão da exceção lançada quando um album já nao existe na biblioteca.
     * 
     * @param mensagem Mensagem de erro a ser exibida.
     */
    public AlbumJaExisteException(String mensagem) {
        super(mensagem);
    }
}