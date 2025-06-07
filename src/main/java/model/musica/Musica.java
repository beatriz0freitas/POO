package model.musica;

import java.io.Serializable;
import java.util.Objects;

/**
 * Representa uma música com várias informações como nome, intérprete, editora,
 * letra, notas musicais, género e duração.
 * 
 *
 */
public class Musica implements Serializable {

    private String nome;
    private String interprete;
    private String editora;
    private String letra;
    private String notasMusicais; 
    private String genero;
    private int duracaoEmSegundos; 
    private int numeroDeReproducoes;

    /**
     * Construtor completo para inicializar uma música com todos os seus atributos.
     * 
     * @param nome               Nome da música
     * @param interprete         Intérprete da música
     * @param editora            Editora responsável
     * @param letra              Letra da música
     * @param notasMusicais      Notas musicais
     * @param genero             Género musical
     * @param duracaoEmSegundos  Duração em segundos
     */
    public Musica(String nome, String interprete, String editora, String letra, String notasMusicais, String genero, int duracaoEmSegundos) {
        this.nome = nome;
        this.interprete = interprete;
        this.editora = editora;
        this.letra = letra;
        this.notasMusicais = notasMusicais;
        this.genero = genero;
        this.duracaoEmSegundos = duracaoEmSegundos;
        this.numeroDeReproducoes = 0;
    }

    /**
     * Construtor por omissão. Inicializa todos os campos com valores padrão.
     */
    public Musica() {
        this.nome = "";
        this.interprete = "";
        this.editora = "";
        this.letra = "";
        this.notasMusicais = "";
        this.genero = "";
        this.duracaoEmSegundos = 0;
        this.numeroDeReproducoes = 0;
    }

    /**
     * Construtor de cópia.
     * 
     * @param m Música a ser copiada
     */
    public Musica(Musica m){
        this.nome = m.getNome();
        this.interprete = m.getInterprete();
        this.editora = m.getEditora();
        this.letra = m.getLetra();
        this.notasMusicais = m.getNotasMusicais();
        this.genero = m.getGenero();
        this.duracaoEmSegundos = m.getDuracaoEmSegundos();
        this.numeroDeReproducoes = m.getNumeroDeReproducoes();
    }

    // Getters e Setters

    /** @return Nome da música */
    public String getNome() {
        return nome;
    }

    /** @param nome Nome da música */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** @return Intérprete da música */
    public String getInterprete() {
        return interprete;
    }

    /** @param interprete Intérprete da música */
    public void setInterprete(String interprete) {
        this.interprete = interprete;
    }

    /** @return Editora da música */
    public String getEditora() {
        return editora;
    }

    /** @param editora Editora da música */
    public void setEditora(String editora) {
        this.editora = editora;
    }

    /** @return Letra da música */
    public String getLetra() {
        return letra;
    }

    /** @param letra Letra da música */
    public void setLetra(String letra) {
        this.letra = letra;
    }

    /** @return Notas musicais da música */
    public String getNotasMusicais() {
        return notasMusicais;
    }

    /** @param notasMusicais Notas musicais */
    public void setNotasMusicais(String notasMusicais) {
        this.notasMusicais = notasMusicais;
    }

    /** @return Género musical */
    public String getGenero() {
        return genero;
    }

    /** @param genero Género musical */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /** @return Duração da música em segundos */
    public int getDuracaoEmSegundos() {
        return duracaoEmSegundos;
    }

    /** @param duracaoEmSegundos Duração da música em segundos */
    public void setDuracaoEmSegundos(int duracaoEmSegundos) {
        this.duracaoEmSegundos = duracaoEmSegundos;
    }

    /** @return Número total de reproduções da música */
    public int getNumeroDeReproducoes() {
        return numeroDeReproducoes;
    }

    /** @param numeroDeReproducoes Número total de reproduções */
    public void setNumeroDeReproducoes(int numeroDeReproducoes) {
        this.numeroDeReproducoes = numeroDeReproducoes;
    }

    /**
     * Incrementa o número de reproduções da música em 1.
     */
    public void incrementaNumeroDeReproducoes() {
        this.numeroDeReproducoes++;
    }

    /**
     * Cria e retorna uma cópia da música.
     * 
     * @return Nova instância de {@code Musica} com os mesmos dados
     */
    public Musica clone(){
        return new Musica(this);
    }
    
    /**
     * Verifica a igualdade entre esta música e outra.
     * 
     * @param o Objeto a comparar
     * @return {@code true} se os objetos forem iguais, {@code false} caso contrário
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Musica)) return false;
        Musica musica = (Musica) o;
        return this.nome.equals(musica.getNome()) && this.interprete.equals(musica.getInterprete())
                && this.editora.equals(musica.getEditora()) && this.letra.equals(musica.getLetra())
                && this.notasMusicais.equals(musica.getNotasMusicais()) && this.genero.equals(musica.getGenero())
                && this.duracaoEmSegundos == musica.getDuracaoEmSegundos();
    }

    /**
     * Retorna o hash code da música.
     * 
     * @return Hash code da música
     */
    @Override
    public int hashCode() {
        return Objects.hash(nome, interprete, editora, letra, notasMusicais, genero, duracaoEmSegundos, numeroDeReproducoes);
    }

    /**
     * Compara esta música com outra com base no nome.
     * 
     * @param outra Música a ser comparada
     * @return Valor negativo se esta música for menor, zero se forem iguais e
     *         positivo se esta música for maior
     */
    public int compareTo(Musica outra) {
        return this.nome.compareTo(outra.nome);
    }
    /**
     * Retorna uma representação em String da música.
     * 
     * @return String formatada com os dados da música
     */
    public String reproduzir() {
        incrementaNumeroDeReproducoes();
        return this.getLetra();
    }

}
