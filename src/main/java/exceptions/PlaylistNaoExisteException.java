package exceptions;

public class PlaylistNaoExisteException extends Exception {
    public PlaylistNaoExisteException(String message) {
        super(message);
    }
}
