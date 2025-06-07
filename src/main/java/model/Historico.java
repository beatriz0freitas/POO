package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors; // Adicionado para o Collectors.toList()

import exceptions.HistoricoVazioException;

/**
 * Representa o histórico de reproduções de um utilizador.
 * Esta classe armazena uma lista de {@link RegistoDeReproducao} e implementa
 * {@link ReproducaoObserver} para ser notificada sobre novas reproduções,
 * atualizando assim o histórico.
 * É serializável para permitir a persistência do estado.
 */
public class Historico implements Serializable, ReproducaoObserver {

    private List<RegistoDeReproducao> historico;

    /**
     * Construtor padrão. Inicializa uma lista vazia de registos de reprodução.
     */
    public Historico() {
        this.historico = new ArrayList<>();
    }

    /**
     * Construtor que inicializa o histórico com uma lista de registos de reprodução fornecida.
     * A lista fornecida é copiada para garantir o encapsulamento.
     *
     * @param historico Uma lista de {@link RegistoDeReproducao} para inicializar o histórico.
     */
    public Historico(List<RegistoDeReproducao> historico) {
        this.historico = new ArrayList<>(historico); 
    }

    /**
     * Construtor de cópia. Cria um novo objeto {@code Historico} como uma cópia profunda
     * de um histórico existente.
     *
     * @param historico O objeto {@code Historico} a ser copiado.
     */
    public Historico(Historico historico){
        // Chama getHistorico() do objeto original para obter uma lista de clones
        this.historico = new ArrayList<>(historico.getHistorico()); 
    }
    
    /**
     * Retorna uma cópia da lista de registos de reprodução.
     * Cada {@link RegistoDeReproducao} na lista retornada é um clone do original,
     * protegendo o estado interno do histórico.
     *
     * @return Uma nova lista contendo clones dos {@link RegistoDeReproducao} do histórico.
     */
    public List<RegistoDeReproducao> getHistorico() {
        return this.historico.stream()
                              .map(RegistoDeReproducao::clone)
                              .collect(Collectors.toList()); // Alterado para Collectors.toList() para compatibilidade geral
    }

    /**
     * Define o histórico com uma nova lista de registos de reprodução.
     * A lista fornecida é copiada para garantir o encapsulamento.
     *
     * @param historico A nova lista de {@link RegistoDeReproducao} a ser definida.
     */
    public void setHistorico(List<RegistoDeReproducao> historico) {
        this.historico = new ArrayList<>(historico);
    }

    /**
     * Adiciona um novo registo de reprodução ao histórico.
     * O registo é adicionado ao final da lista.
     *
     * @param reg O {@link RegistoDeReproducao} a ser adicionado.
     */
    public void adicionarRegistoDeReproducaoAoHistorico(RegistoDeReproducao reg) {
        this.historico.add(reg);
    }

    /**
     * Método chamado quando uma nova reprodução ocorre.
     * Cria um novo {@link RegistoDeReproducao} baseado na {@link Reproducao}
     * fornecida e adiciona-o ao histórico.
     *
     * @param reproducao O objeto {@link Reproducao} que representa a música reproduzida.
     */
    @Override
    public void update(Reproducao reproducao) {
        adicionarRegistoDeReproducaoAoHistorico(new RegistoDeReproducao(reproducao));
    }

    /**
     * Cria e retorna uma cópia profunda deste objeto {@code Historico}.
     *
     * @return Um clone do objeto {@code Historico}.
     */
    @Override
    public Historico clone(){
        return new Historico(this);
    }

    /**
     * Retorna o último registo de reprodução adicionado ao histórico.
     *
     * @return O último {@link RegistoDeReproducao}.
     * @throws HistoricoVazioException se o histórico estiver vazio.
     */
    public RegistoDeReproducao getUltimoRegisto() throws HistoricoVazioException {
        if (historico.isEmpty()) {
            throw new HistoricoVazioException("Histórico vazio");
        }
        return historico.get(historico.size() - 1).clone();
    }

    /**
     * Compara este objeto {@code Historico} com outro objeto para verificar igualdade.
     * Dois históricos são considerados iguais se as suas listas de registos de reprodução
     * forem iguais.
     *
     * @param o O objeto a ser comparado com este {@code Historico}.
     * @return {@code true} se os objetos forem iguais, {@code false} caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Historico historico1 = (Historico) o;
        // Compara as listas internas de registos de reprodução.
        // Assume que RegistoDeReproducao tem um método equals() adequado.
        return Objects.equals(this.historico, historico1.historico);
    }

    /**
     * Retorna um valor de código hash para este objeto {@code Historico}.
     * O código hash é baseado na lista de registos de reprodução.
     *
     * @return O valor do código hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(historico);
    }
}