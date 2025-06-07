package model.musica;

/**
 * Representa uma música que possui conteúdo multimédia adicional, como um vídeo.
 * 
 * Esta classe estende {@link Musica} e adiciona o atributo {@code urlVideo}.
 */
public class MusicaMultimedia extends Musica {
    private String urlVideo;

    /**
     * Construtor principal da música multimédia.
     * 
     * @param nome              Nome da música
     * @param interprete        Intérprete da música
     * @param editora           Editora da música
     * @param letra             Letra da música
     * @param notasMusicais     Notas musicais
     * @param genero            Género musical
     * @param duracaoEmSegundos Duração da música
     * @param urlVideo          URL de vídeo associado à música
     */
    public MusicaMultimedia(String nome, String interprete, String editora, String letra,
                            String notasMusicais, String genero, int duracaoEmSegundos, String urlVideo) {
        super(nome, interprete, editora, letra, notasMusicais, genero, duracaoEmSegundos);
        this.urlVideo = urlVideo;
    }

    /**
     * Construtor de cópia.
     * 
     * @param m Instância de {@code MusicaMultimedia} a ser copiada
     */
    public MusicaMultimedia(MusicaMultimedia m) {
        super(m);
        this.urlVideo = m.getUrlVideo();
    }

    /**
     * Retorna a URL do vídeo associado.
     * 
     * @return URL do vídeo
     */
    public String getUrlVideo() {
        return urlVideo;
    }

    /**
     * Define a URL do vídeo associado.
     * 
     * @param urlVideo URL do vídeo
     */
    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    /**
     * Cria e retorna uma cópia desta música multimédia.
     * 
     * @return Nova instância de {@code MusicaMultimedia}
     */
    @Override
    public Musica clone() {
        return new MusicaMultimedia(this);
    }
}
