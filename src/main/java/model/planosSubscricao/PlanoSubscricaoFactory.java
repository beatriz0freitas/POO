package model.planosSubscricao;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Fábrica para criação de instâncias de planos de subscrição.
 * Utiliza um mapa de nomes para fornecedores que criam os planos correspondentes.
 */
public class PlanoSubscricaoFactory {

    private static final Map<String, Supplier<PlanoSubscricao>> planos = new HashMap<>();

    static {
        planos.put("Free", PlanoSubscricaoFree::new);
        planos.put("Premium Base", PlanoSubscricaoPremiumBase::new);
        planos.put("Premium Top", PlanoSubscricaoPremiumTop::new);
    }

    /**
     * Cria um plano de subscrição pelo seu nome.
     *
     * @param nome Nome do plano desejado.
     * @return Instância do plano correspondente ou null se o nome não existir.
     */
    public static PlanoSubscricao criarPlano(String nome) {
        Supplier<PlanoSubscricao> fornecedor = planos.get(nome);
        return fornecedor != null ? fornecedor.get() : null;
    }
}
