package  model;

import java.util.List;

import exceptions.ColecaoDeMusicaVaziaException;
import model.musica.Musica;

/**
 * Interface que define os métodos necessários para uma coleção de músicas
 * que pode ser reproduzida.
 * 
 * Esta interface é implementada por classes como Album e Playlist, permitindo
 * a manipulação e reprodução de coleções de músicas.
 */
public interface ColecaoDeMusicasReproduzivel {

    /**
     * Método que devolve o tipo de coleção de músicas.
     * @return Tipo de coleção (ex: "Album", "Playlist")
     */
    public String getTipoDeColecao();

    /**
     * Método que devolve o nome da coleção de músicas.
     * @return Nome da coleção
     */
    public String getNome();

    /**
     * Método que devolve a lista de músicas da coleção.
     * @return Lista de músicas
     */
    public List<Musica> getMusicas();

    /**
     * Método que devolve a lista de músicas originais da coleção.
     * @return Lista de músicas originais
     */
    public List<Musica> getMusicasOriginais();

    /**
     * Método que devolve o tamanho da coleção de músicas.
     * @return Tamanho da coleção
     */
    public int getTamanho();

    /**
     * Método que devolve a música associada ao índice especificado
     * @param indiceAtual Índice da música a ser devolvida
     * @return Música associada ao índice
     * @throws ColecaoDeMusicaVaziaException Caso a coleção de músicas esteja vazia
     */
    public Musica getMusicaPorIndice(int indiceAtual) throws ColecaoDeMusicaVaziaException;

    /**
     * Método que clona a coleção de músicas reproduzível.
     * 
     * @return Uma nova instância da coleção de músicas reproduzível.
     */
    public ColecaoDeMusicasReproduzivel clone();

}
