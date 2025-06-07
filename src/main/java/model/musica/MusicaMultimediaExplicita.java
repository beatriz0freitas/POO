package model.musica;

/**
 * Representa uma música multimédia com conteúdo explícito.
 * 
 * Herda de {@link MusicaMultimedia} e implementa {@link MusicaExplicitaInterface}
 * para marcar que possui conteúdo explicito.
 */
public class MusicaMultimediaExplicita extends MusicaMultimedia implements MusicaExplicitaInterface {

    /**
     * Construtor principal.
     */
    public MusicaMultimediaExplicita(String nome, String interprete, String editora, String letra,
                                     String notasMusicais, String genero, int duracaoEmSegundos, String urlVideo) {
        super(nome, interprete, editora, letra, notasMusicais, genero, duracaoEmSegundos, urlVideo);
    }

    /**
     * Construtor de cópia.
     */
    public MusicaMultimediaExplicita(MusicaMultimediaExplicita m) {
        super(m); // usa o construtor da superclasse
    }

    /**
     * Cria e retorna uma cópia desta música multimédia explícita.
     */
    @Override
    public Musica clone() {
        return new MusicaMultimediaExplicita(this);
    }
}
