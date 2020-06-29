/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.plugin.maspath;

import br.univali.portugol.plugin.maspath.telas.SelecaoConteudo;
import br.univali.portugol.plugin.maspath.telas.painelLogin;
import br.univali.portugol.plugin.maspath.telas.painelMenuPrincipal;
import br.univali.ps.ui.telas.TelaCustomBorder;
import javax.swing.SwingUtilities;

/**
 *
 * @author Adson
 */
public class ControladorDeJanelas {
    TelaCustomBorder janelaPrincipal;
    TelaCustomBorder janelaLogin;
    TelaCustomBorder janelaMenuPrincipal;
    SelecaoConteudo painelConteudos;
    painelLogin painelLogin;
    painelMenuPrincipal painelMenuPrincipa1;
    public static ControladorDeJanelas controladorDeJanelas;

    public ControladorDeJanelas() {
        painelConteudos = new SelecaoConteudo();
        painelLogin = new painelLogin();
        painelMenuPrincipa1 = new painelMenuPrincipal();
        janelaLogin = new TelaCustomBorder(painelLogin, "Faça seu Login");
        janelaMenuPrincipal = new TelaCustomBorder(painelMenuPrincipa1, "Menu Principal");
        janelaPrincipal = janelaLogin;
    }
    
    public static ControladorDeJanelas getInstance()
    {
        if(controladorDeJanelas == null)
        {
            controladorDeJanelas = new ControladorDeJanelas();
        }
        
        return controladorDeJanelas;
    }
    
    public void showjanelaPrincipal()
    {
        janelaPrincipal.setVisible(true);
        janelaPrincipal.repaint();
        janelaPrincipal.revalidate();
        janelaPrincipal.pack();
        
    }
    
    public void closejanelaPrincipal()
    {
        janelaPrincipal.setVisible(false);
        
    }
    
    public void showJanelaMenuPrincipal()
    {
        janelaMenuPrincipal.setVisible(true);
        janelaMenuPrincipal.repaint();
        janelaMenuPrincipal.revalidate();
        janelaMenuPrincipal.pack();        
    }
    
    public void closeJanelaMenuPrincipal()
    {
        janelaMenuPrincipal.setVisible(false);      
    }
    
}
