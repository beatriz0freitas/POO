package model.musica;

/**
 * Representa uma música com conteúdo explícito.
 * 
 * Esta classe herda de {@link Musica} e implementa {@link MusicaExplicitaInterface}
 * para sinalizar que se trata de conteúdo explicito.
 */
public class MusicaExplicita extends Musica implements MusicaExplicitaInterface {

    /**
     * Construtor da música explícita.
     * 
     * @param nome              Nome da música
     * @param interprete        Intérprete da música
     * @param editora           Editora da música
     * @param letra             Letra da música
     * @param notasMusicais     Notas musicais
     * @param genero            Género musical
     * @param duracaoEmSegundos Duração da música em segundos
     */
    public MusicaExplicita(String nome, String interprete, String editora, String letra,
                           String notasMusicais, String genero, int duracaoEmSegundos) {
        super(nome, interprete, editora, letra, notasMusicais, genero, duracaoEmSegundos);
    }

    /**
     * Cria e retorna uma cópia desta música explícita.
     * 
     * @return Nova instância de {@code MusicaExplicita}
     */
    @Override
    public Musica clone() {
        return new MusicaExplicita(getNome(), getInterprete(), getEditora(),
                                   getLetra(), getNotasMusicais(), getGenero(), getDuracaoEmSegundos());
    }
}
