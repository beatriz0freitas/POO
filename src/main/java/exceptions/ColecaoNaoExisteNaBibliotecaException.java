package exceptions;

/**
 * Exceção lançada quando uma coleção não existe na biblioteca.
 * 
 * Esta exceção é utilizada para indicar que uma operação de busca ou remoção de
 * coleção falhou porque a coleção não está presente na biblioteca.
 */
public class ColecaoNaoExisteNaBibliotecaException extends Exception {

    /**
     * Construtor padrão da exceção lançada quando uma coleção não existe na
     * biblioteca.
     * 
     * @param message Mensagem de erro a ser exibida.
     */
    public ColecaoNaoExisteNaBibliotecaException(String message) {
        super(message);
    }

}
