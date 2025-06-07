package model.playlists;

import java.util.ArrayList;
import java.util.List;
import model.musica.Musica;

/**
 * Classe que representa uma playlist gerada a partir de um gênero musical e uma duração máxima.
 * 
 * Esta classe estende a classe Playlist e implementa métodos específicos para
 * filtrar músicas com base no gênero e na duração máxima.
 */
public class PlaylistPorGeneroETempo extends Playlist {

    private String genero;
    private int duracaoMax; // em segundos

    /**
     * Construtor vazio que inicializa a playlist com um nome vazio, gênero vazio e duração máxima de 0.
     */
    public PlaylistPorGeneroETempo() {
        super();
    }

    /**
     * Construtor que inicializa a playlist com um nome, gênero e duração máxima.
     * 
     * @param nome Nome da playlist.
     * @param genero Gênero musical.
     * @param duracaoMax Duração máxima em segundos.
     * @param todasAsMusicas Lista de todas as músicas disponíveis.
     */
    public PlaylistPorGeneroETempo(String nome, String genero, int duracaoMax, List<Musica> todasAsMusicas) {
        super(nome, filtrarPorGeneroETempo(todasAsMusicas, genero, duracaoMax));
        this.genero = genero;
        this.duracaoMax = duracaoMax;
    }

    /**
     * Construtor de cópia que cria uma nova playlist a partir de uma existente.
     * 
     * @param p Playlist a ser copiada.
     */
    public PlaylistPorGeneroETempo(PlaylistPorGeneroETempo p) {
        super(p); // Copia nome e a lista de músicas já filtrada
        this.genero = p.getGenero();
        this.duracaoMax = p.getDuracaoMax();
    }

    /**
     * Método que filtra as músicas com base no gênero e na duração máxima.
     * 
     * @param todas Lista de todas as músicas disponíveis.
     * @param genero Gênero musical.
     * @param duracaoMax Duração máxima em segundos.
     * @return Lista de músicas filtradas.
     */
    private static List<Musica> filtrarPorGeneroETempo(List<Musica> todas, String genero, int duracaoMax) {
        List<Musica> selecionadas = new ArrayList<>();
        int acumulado = 0;
        for (Musica m : todas) {
            if (m.getGenero().equalsIgnoreCase(genero) &&
                acumulado + m.getDuracaoEmSegundos() <= duracaoMax) {
                selecionadas.add(m.clone());
                acumulado += m.getDuracaoEmSegundos();
            }
        }
        return selecionadas;
    }

    /**
     * Método que retorna o gênero musical da playlist.
     * 
     * @return Gênero musical.
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Método que retorna a duração máxima da playlist.
     * 
     * @return Duração máxima em segundos.
     */
    public int getDuracaoMax() {
        return duracaoMax;
    }

    /**
     * Método que define o gênero musical da playlist.
     * 
     * @param genero Gênero a ser definido.
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * Método que define a duração máxima da playlist.
     * 
     * @param duracaoMax Duração máxima a ser definida em segundos.
     */
    public void setDuracaoMax(int duracaoMax) {
        this.duracaoMax = duracaoMax;
    }

    /**
     * Método que retorna uma representação em string da playlist.
     * 
     * @return String representando a playlist.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Playlist Por Gênero e Tempo:\n");
        sb.append("Gênero: ").append(this.genero).append("\n");
        sb.append("Duração Máxima: ").append(this.duracaoMax).append(" segundos\n");
        sb.append(super.toString()); // Chama o toString da classe Playlist (base)
        return sb.toString();
    }

    /**
     * Método que clona a playlist.
     * 
     * @return Uma nova instância da playlist.
     */
    @Override
    public Playlist clone() {
        return new PlaylistPorGeneroETempo(this);
    }
}