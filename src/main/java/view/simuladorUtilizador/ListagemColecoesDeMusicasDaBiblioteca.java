package view.simuladorUtilizador;

import java.util.List;

import model.ColecaoDeMusicasReproduzivel;
import model.Reproducao;
import model.Utilizador;
import view.bases.ListarEntidadesBaseScreen;

public class ListagemColecoesDeMusicasDaBiblioteca extends  ListarEntidadesBaseScreen<ColecaoDeMusicasReproduzivel> {

    private Utilizador utilizador;

    public ListagemColecoesDeMusicasDaBiblioteca(Utilizador utilizador) {
        super("Conteúdo da Biblioteca");
        this.utilizador = utilizador;
    }

    //TODO: EXCEPTION CASO ESTEJA VAZIAs
    @Override
    protected List<ColecaoDeMusicasReproduzivel> getEntidades() {
        return controller.getConteudoBiblioteca(utilizador);
    }

    @Override
    protected String resumoEntidade(ColecaoDeMusicasReproduzivel entidade) {
        return String.format("%s (%s)", entidade.getNome(), entidade.getTipoDeColecao());
    }

    @Override
    protected void aoSelecionarEntidade(ColecaoDeMusicasReproduzivel entidade) {
        Reproducao reproducao = controller.reproduzirColecaoMusica(utilizador, entidade);
        setNextScreen(new ReproduzirColecaoEntidadeScreen(reproducao, utilizador));
    }
    
}
