package model;

import model.musica.Musica;
import java.io.Serializable; // Importa a interface Serializable
import java.util.Objects;   // Importa a classe Objects para utilitários como equals e hashCode

/**
 * Representa o resultado de uma tentativa de reprodução de música.
 * Esta classe encapsula se a reprodução foi bem-sucedida, uma mensagem associada
 * (geralmente uma mensagem de erro em caso de falha, ou informação sobre a reprodução),
 * e a {@link Musica} que foi (ou deveria ter sido) reproduzida.
 * Implementa {@link Serializable} para permitir que os seus objetos sejam persistidos.
 */
public class Reproducao implements Serializable { // Garante que a classe é serializável

    private boolean sucesso; // Indica se a reprodução foi bem-sucedida
    private String mensagem;  // Mensagem associada à reprodução (ex: erro, letra da música)
    private Musica musica;    // A música associada a esta reprodução

    /**
     * Construtor padrão.
     * Inicializa a reprodução como bem-sucedida, com uma mensagem vazia
     * e uma nova instância padrão de {@link Musica}.
     */
    public Reproducao() {
        this.sucesso = true;
        this.mensagem = "";
        this.musica = new Musica(); // Cria uma música vazia por defeito
    }

    /**
     * Construtor para uma reprodução bem-sucedida de uma música específica.
     *
     * @param musica A {@link Musica} que foi reproduzida com sucesso. Assume-se que {@code musica} não é nula.
     */
    public Reproducao(Musica musica) {
        this.sucesso = true;
        this.mensagem = ""; // Mensagem vazia para sucesso, ou poderia ser a letra, por exemplo
        this.musica = musica.clone(); // Armazena um clone da música para proteger o estado original
    }

    /**
     * Construtor para uma tentativa de reprodução que falhou.
     *
     * @param mensagemDeErro A mensagem descrevendo o erro que ocorreu durante a tentativa de reprodução.
     */
    public Reproducao(String mensagemDeErro) {
        this.sucesso = false;
        this.mensagem = mensagemDeErro;
        this.musica = new Musica(); // Cria uma música vazia, pois a reprodução falhou
    }

    /**
     * Construtor de cópia.
     * Cria uma nova instância de {@code Reproducao} como uma cópia de uma existente.
     *
     * @param r A {@code Reproducao} original a ser copiada. Assume-se que {@code r} não é nula.
     */
    public Reproducao(Reproducao r) {
        this.sucesso = r.isSucesso();
        this.mensagem = r.getMensagem();
        // Cria um clone da música para garantir encapsulamento e independência
        this.musica = r.getMusica().clone(); 
    }

    /**
     * Define a mensagem associada a esta reprodução.
     *
     * @param mensagem A nova mensagem.
     */
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    /**
     * Retorna a mensagem associada a esta reprodução.
     *
     * @return A mensagem.
     */
    public String getMensagem() {
        return this.mensagem;
    }

    /**
     * Retorna a {@link Musica} associada a esta reprodução.
     *
     * @return Um clone da {@link Musica} para proteger o estado interno.
     */
    public Musica getMusica() {
        // Retorna um clone para proteger o objeto Musica interno de modificações externas.
        return this.musica.clone();
    }

    /**
     * Verifica se a reprodução foi bem-sucedida.
     *
     * @return {@code true} se a reprodução foi bem-sucedida, {@code false} caso contrário.
     */
    public boolean isSucesso() {
        return sucesso;
    }

    /**
     * Retorna uma representação em string do objeto {@code Reproducao}.
     * Se a reprodução foi bem-sucedida, mostra a letra da música.
     * Se falhou, mostra uma mensagem de erro.
     *
     * @return Uma string formatada representando o estado da reprodução.
     */
    @Override
    public String toString() {
        // Assume que `musica.getLetra()` é o comportamento desejado para uma reprodução bem-sucedida.
        // Se `musica` puder ser nula (embora os construtores a inicializem),
        // seria necessário tratamento de nulo aqui.
        return sucesso ? "Letra: " + musica.getLetra() : "Erro: " + mensagem;
    }

    /**
     * Cria e retorna uma cópia (clone) deste objeto {@code Reproducao}.
     * Utiliza o construtor de cópia para criar a nova instância.
     *
     * @return Um clone deste {@code Reproducao}.
     */
    @Override
    public Reproducao clone() {
        return new Reproducao(this);
    }

    /**
     * Compara este objeto {@code Reproducao} com outro para verificar igualdade.
     * Dois objetos {@code Reproducao} são considerados iguais se os seus campos
     * {@code sucesso}, {@code mensagem} e {@code musica} forem todos iguais.
     *
     * @param o O objeto a ser comparado.
     * @return {@code true} se os objetos forem iguais, {@code false} caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reproducao that = (Reproducao) o;
        return sucesso == that.sucesso &&
               Objects.equals(mensagem, that.mensagem) &&
               Objects.equals(musica, that.musica); // Assume que Musica tem um equals() adequado
    }

    /**
     * Retorna um valor de código hash para este objeto {@code Reproducao}.
     * O código hash é gerado com base nos campos {@code sucesso}, {@code mensagem} e {@code musica}.
     *
     * @return O valor do código hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(sucesso, mensagem, musica); // Assume que Musica tem um hashCode() adequado
    }
}