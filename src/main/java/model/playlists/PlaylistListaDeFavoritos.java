package model.playlists;

import model.musica.Musica;
import java.util.List;

/**
 * Representa uma playlist que funciona como uma lista de músicas favoritas.
 * Estende a classe {@link Playlist}.
 * Impõe restrições ao adicionar e remover músicas: não permite músicas duplicadas
 * e só permite remover músicas que existem na lista.
 */
public class PlaylistListaDeFavoritos extends Playlist {

    /**
     * Construtor vazio que inicializa a playlist de favoritos com um nome vazio e uma lista vazia de músicas.
     */
    public PlaylistListaDeFavoritos() {
        super();
    }

    /**
     * Construtor que inicializa a playlist de favoritos com um nome e uma lista de músicas.
     * 
     * @param nome Nome da playlist.
     * @param musicas Lista de músicas a serem adicionadas à playlist.
     */
    public PlaylistListaDeFavoritos(String nome, List<Musica> musicas) {
        super(nome, musicas);
    }

    /**
     * Construtor de cópia que cria uma nova playlist de favoritos a partir de uma existente.
     * 
     * @param original Playlist a ser copiada.
     */
    public PlaylistListaDeFavoritos(PlaylistListaDeFavoritos original) {
        super(original);
    }

    public Playlist clone() {
        return new PlaylistListaDeFavoritos(this);
    }

    /**
     * Método que retorna uma representação em string da playlist da lista de favoritos.
     * 
     * @return String representando a playlist da lista de favoritos.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Playlist Lista de Favoritos:\n");
        sb.append(super.toString());
        return sb.toString();
    }
}