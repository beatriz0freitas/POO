package model.playlists;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

import model.musica.Musica;

/**
 * Representa uma playlist criada a partir de uma {@link PlaylistListaDeFavoritos},
 * mas limitada por um tempo máximo de duração total.
 * As músicas são adicionadas da lista de favoritos até que o tempo máximo seja atingido.
 * Estende {@link PlaylistListaDeFavoritos}.
 */
/**
 * Classe que representa uma playlist de músicas com base em preferências do utilizador e um tempo máximo.
 * 
 * Esta classe estende a classe PlaylistListaDeFavoritos e implementa métodos específicos para
 * filtrar músicas com base em um tempo máximo.
 */
public class PlaylistPreferenciasComTempoMaximo extends PlaylistListaDeFavoritos {

    private int tempoMaxEmSegundos;

    /**
     * Construtor vazio que inicializa a playlist com um tempo máximo de 0 segundos.
     */
    public PlaylistPreferenciasComTempoMaximo() {
        super();
        this.tempoMaxEmSegundos = 0;
    }

    /**
     * Construtor que inicializa a playlist com um nome, tempo máximo e uma lista de músicas favoritas.
     * @param nome Nome da playlist.
     * @param tempoMaximo Tempo máximo em segundos.
     * @param favoritos Lista de músicas favoritas.
     */
    public PlaylistPreferenciasComTempoMaximo(String nome, int tempoMaximo, PlaylistListaDeFavoritos favoritos) {
        super(nome, getMusicasAteMaximo(tempoMaximo, favoritos.getMusicas()));
        this.tempoMaxEmSegundos = tempoMaximo;
    }

    /**
     * Construtor de cópia.
     * Cria uma nova playlist de preferências com tempo máximo baseada em outra existente.
     *
     * @param original A playlist de preferências com tempo máximo a ser copiada.
     */
    /**
     * Construtor de cópia que cria uma nova playlist a partir de uma existente.
     * 
     * @param original Playlist a ser copiada.
     */
    public PlaylistPreferenciasComTempoMaximo(PlaylistPreferenciasComTempoMaximo original) {
        super(original.getNome(), original.getMusicas()); // Usa getMusicas para obter clones
        this.tempoMaxEmSegundos = original.tempoMaxEmSegundos;
    }

    /**
     * Seleciona músicas de uma lista até que a soma de suas durações atinja o tempo máximo.
     * As músicas selecionadas são clonadas.
     *
     * @param tempoMaximo O tempo máximo acumulado permitido (em segundos).
     * @param musicas A lista de músicas de onde selecionar.
     * @return Uma nova lista contendo clones das músicas selecionadas.
     */
    /**
     * Método que retorna músicas até a duração máxima especificada.
     * 
     * @param tempoMaximo Tempo máximo em segundos.
     * @param musicas Lista de músicas a serem filtradas.
     * @return Lista de músicas filtradas dentro da duração máxima.
     */
    private static List<Musica> getMusicasAteMaximo(int tempoMaximo, List<Musica> musicas) {
        List<Musica> selecionadas = new ArrayList<>();
        int tempoAcumulado = 0;

        for (Musica m : musicas) { // musicas aqui são clones da lista de favoritos
            int duracao = m.getDuracaoEmSegundos();
            if (tempoAcumulado + duracao <= tempoMaximo) {
                selecionadas.add(m.clone()); // Clona novamente para garantir independência
                tempoAcumulado += duracao;
            }
        }
        return selecionadas;
    }



    /**
     * Cria e retorna uma cópia desta playlist de preferências com tempo máximo.
     *
     * @return Um clone desta playlist.
     */
    /**
     * Método que retorna o tempo máximo em segundos.
     * 
     * @return Tempo máximo em segundos.
     */
    public int getTempoMaxEmSegundos() {
        return tempoMaxEmSegundos;
    }

    /**
     * Método que define o tempo máximo em segundos.
     * 
     * @param tempoMaxEmSegundos Tempo máximo em segundos.
     */
    public void setTempoMaxEmSegundos(int tempoMaxEmSegundos) {
        this.tempoMaxEmSegundos = tempoMaxEmSegundos;
    }

    /**
     * Método que clona a playlist.
     * 
     * @return Uma nova instância da playlist com as mesmas propriedades.
     */
    @Override
    public Playlist clone() {
        return new PlaylistPreferenciasComTempoMaximo(this);
    }

    /**
     * Retorna uma representação em String da playlist de preferências com tempo máximo.
     * Inclui a duração máxima, além das informações da playlist base (Lista de Favoritos).
     *
     * @return Uma String formatada com os detalhes da playlist.
     */
    /**
     * Método que retorna uma representação em string da playlist.
     * 
     * @return String representando a playlist.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Playlist com tempo maximo:\n");
        sb.append("Duração Máxima: ").append(this.tempoMaxEmSegundos).append(" segundos\n");
        // Chama o toString da classe PlaylistListaDeFavoritos
        // que por sua vez chamará o toString da classe Playlist
        sb.append(super.toString());
        return sb.toString();
    }
}