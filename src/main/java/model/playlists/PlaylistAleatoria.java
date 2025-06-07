package model.playlists;

import java.util.List;
import java.util.Random;
import exceptions.ColecaoDeMusicaVaziaException;
import model.musica.Musica;

/**
 * Classe que representa uma playlist aleatória de músicas.
 * Esta classe estende a classe Playlist e implementa a lógica para
 * selecionar músicas aleatoriamente a partir de uma lista de músicas.
 */
public class PlaylistAleatoria extends Playlist  {

    private final Random random = new Random();

    /**
     * Construtor vazio que inicializa a playlist aleatória com um nome vazio e uma lista vazia de músicas.
     */
    public PlaylistAleatoria() {
        super();
    }

    /**
     * Construtor que inicializa a playlist aleatória com um nome e uma lista de músicas.
     * 
     * @param nome Nome da playlist.
     * @param musicas Lista de músicas a serem adicionadas à playlist.
     */
    public PlaylistAleatoria(String nome, List<Musica> musicas) {
        super(nome, musicas);
    }

    /**
     * Construtor de cópia que cria uma nova playlist aleatória a partir de uma existente.
     * 
     * @param p Playlist a ser copiada.
     */
    public PlaylistAleatoria(PlaylistAleatoria p) {
        super(p);
    }

    /**
     * Método que retorna uma música aleatória da playlist.
     * 
     * @return Música aleatória da playlist.
     * @throws ColecaoDeMusicaVaziaException Se a playlist estiver vazia.
     */
    @Override
    public Musica getMusicaPorIndice(int indice) throws ColecaoDeMusicaVaziaException {
        if (this.estaVazia()) {
            throw new ColecaoDeMusicaVaziaException("A playlist está vazia.");
        }
        // Retorna uma música da lista original de forma aleatória
        return super.getMusicasOriginais().get(random.nextInt(this.getTamanho()));
    }

    /**
     * Método que retorna uma representação em string da playlist aleatória.
     * 
     * @return String representando a playlist aleatória.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Playlist Aleatória:\n");
        sb.append(super.toString());
        return sb.toString();
    }

    /**
     * Método que clona a playlist aleatória.
     * 
     * @return Uma nova instância da playlist aleatória.
     */
    @Override
    public Playlist clone() {
        return new PlaylistAleatoria(this);
    }
}