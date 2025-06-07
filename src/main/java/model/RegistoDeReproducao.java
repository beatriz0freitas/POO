package model;

import java.io.Serializable; // Importa a interface Serializable
import java.time.LocalDateTime;
import java.util.Objects; // Importa a classe Objects para utilitários como equals e hashCode

import model.musica.Musica;

/**
 * Representa um registo individual de uma música que foi reproduzida.
 * Esta classe armazena o nome da música, a data e hora da reprodução,
 * e uma referência ao objeto {@link Reproducao} associado.
 * Implementa {@link Serializable} para permitir que os seus objetos sejam persistidos.
 */
public class RegistoDeReproducao implements Serializable { // Garante que a classe é serializável

    private String musicaReproduzida; // Nome da música que foi reproduzida
    private LocalDateTime data;       // Data e hora em que a reprodução ocorreu
    private Reproducao reproducao;    // Objeto Reproducao associado a este registo

    /**
     * Construtor padrão.
     * Inicializa {@code musicaReproduzida} como uma string vazia,
     * {@code data} com a data e hora atuais, e {@code reproducao}
     * com uma nova instância de {@link Reproducao} baseada na string vazia.
     */
    public RegistoDeReproducao() {
        this.musicaReproduzida = "";
        this.data = LocalDateTime.now();
        this.reproducao = new Reproducao(musicaReproduzida); 
    }

    /**
     * Construtor de cópia.
     * Cria um novo {@code RegistoDeReproducao} como uma cópia de um registo existente.
     * Os campos {@code musicaReproduzida} e {@code data} são copiados diretamente.
     * O campo {@code reproducao} é copiado chamando o construtor de cópia de {@link Reproducao}.
     *
     * @param reg O {@code RegistoDeReproducao} original a ser copiado.
     */
    public RegistoDeReproducao(RegistoDeReproducao reg) {
        this.musicaReproduzida = reg.getMusicaReproduzida();
        this.data = reg.getData();
        this.reproducao = new Reproducao(reg.getReproducao()); 
    }

    /**
     * Construtor que cria um registo a partir de um objeto {@link Reproducao}.
     * O nome da música é obtido a partir do objeto {@link Musica} dentro de {@code r},
     * e a data é definida para o momento atual.
     *
     * @param r O objeto {@link Reproducao} que ocorreu.
     */
    public RegistoDeReproducao(Reproducao r) {

        this.reproducao = r.clone(); 
        this.musicaReproduzida = r.getMusica().getNome();
        this.data = LocalDateTime.now();
    }
    
    /**
     * Retorna o nome da música que foi reproduzida.
     *
     * @return O nome da música.
     */
    public String getMusicaReproduzida() {
        return this.musicaReproduzida;
    }

    /**
     * Retorna a data e hora em que a reprodução ocorreu.
     *
     * @return O {@link LocalDateTime} da reprodução.
     */
    public LocalDateTime getData() {
        return this.data;
    }

    /**
     * Retorna o objeto {@link Reproducao} associado a este registo.
     *
     * @return Uma cópia do objeto {@link Reproducao} para manter o encapsulamento.
     */
    public Reproducao getReproducao() {
        return this.reproducao.clone(); 
    }

    /**
     * Define a data e hora da reprodução.
     *
     * @param data O novo {@link LocalDateTime} para este registo.
     */
    public void setData(LocalDateTime data) {
        // LocalDateTime é imutável, então a atribuição direta é segura.
        // Se fosse um objeto mutável, seria necessário `this.data = new LocalDateTime(data);` (se aplicável).
        this.data = data;
    }

    /**
     * Retorna uma representação em string do objeto {@code RegistoDeReproducao}.
     * Inclui o nome da música, a data e os detalhes do objeto {@link Reproducao}.
     *
     * @return Uma string formatada representando o registo.
     */
    @Override
    public String toString() {
        return "RegistoDeReproducao{" +
                "musicaReproduzida='" + musicaReproduzida + '\'' +
                ", data=" + data +
                ", reproducao=" + reproducao + // Chama o toString() de Reproducao
                '}';
    }

    /**
     * Compara este {@code RegistoDeReproducao} com outro objeto para verificar igualdade.
     * Dois registos são considerados iguais se os seus campos {@code musicaReproduzida},
     * {@code data}, e {@code reproducao} forem todos iguais.
     *
     * @param o O objeto a ser comparado.
     * @return {@code true} se os objetos forem iguais, {@code false} caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegistoDeReproducao that = (RegistoDeReproducao) o;
        // Utiliza Objects.equals para tratar corretamente campos que podem ser nulos (embora não esperado aqui).
        // Assume que Musica, LocalDateTime e Reproducao têm implementações adequadas de equals().
        return Objects.equals(musicaReproduzida, that.musicaReproduzida) &&
               Objects.equals(data, that.data) &&
               Objects.equals(reproducao, that.reproducao);
    }


    /**
     * Cria e retorna uma cópia (clone) deste objeto {@code RegistoDeReproducao}.
     * Utiliza o construtor de cópia para criar a nova instância.
     *
     * @return Um clone deste {@code RegistoDeReproducao}.
     */
    @Override
    public RegistoDeReproducao clone(){
        return new RegistoDeReproducao(this);
    }
}