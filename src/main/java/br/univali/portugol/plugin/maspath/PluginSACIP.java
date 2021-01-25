package br.univali.portugol.plugin.maspath;

import br.univali.portugol.plugin.maspath.acoes.AcaoPersonalizadaDinamica;
import br.univali.portugol.plugin.maspath.acoes.AcaoPersonalizadaEstatica;
import br.univali.portugol.plugin.maspath.biblioteca.Exemplo;
import br.univali.ps.plugins.base.GerenciadorPlugins;
import br.univali.ps.plugins.base.Plugin;
import br.univali.ps.plugins.base.UtilizadorPlugins;
import br.univali.ps.plugins.base.VisaoPlugin;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class PluginSACIP extends Plugin
{
    private final VisaoPlugin visao = new VisaoPluginExemplo(this);
    
    private UtilizadorPlugins utilizador;
    
    /**
     * Construtor padrão vázio do plugin.
     */
    
    public PluginSACIP()
    {
        
    }

    @Override
    protected void inicializar(UtilizadorPlugins utilizador)
    {
        this.utilizador = utilizador;
        //Aqui você deve instalar todas as ações que seu plugin fará, ou seja, seus botões
        GerenciadorPlugins.getInstance().instalarAcaoPlugin(this, new AcaoPersonalizadaEstatica());
//        GerenciadorPlugins.getInstance().instalarAcaoPlugin(this, new AcaoPersonalizadaEstatica());
//        GerenciadorPlugins.getInstance().instalarAcaoPlugin(this, new AcaoPersonalizadaDinamica());
//        this.utilizador.registrarBiblioteca(Exemplo.class);
    }

    @Override
    public VisaoPlugin getVisao()
    {
        //retorna uma tela simples para o usuário
        return visao;
    }

    public UtilizadorPlugins getUtilizador()
    {
        return utilizador;
    }
}
