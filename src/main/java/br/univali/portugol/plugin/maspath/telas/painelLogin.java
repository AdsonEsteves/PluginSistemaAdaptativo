/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.plugin.maspath.telas;

import br.univali.portugol.plugin.maspath.ControladorDeJanelas;
import br.univali.portugol.plugin.maspath.conexao.InterfaceComunicacao;
import br.univali.portugol.plugin.maspath.dataentities.Student;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import java.awt.Color;

/**
 *
 * @author Space Today
 */
public class painelLogin extends javax.swing.JPanel implements Themeable {

    /**
     * Creates new form painelLogin
     */
    public painelLogin() {
        initComponents();
        configurarCores();
        this.CampoLogin.setText("oJGgQOqvB8");
        this.campoSenha.setText("9JZ8racZBO");
    }

    @Override
    public void configurarCores() {
        this.setBackground(ColorController.COR_PRINCIPAL);
        labelTitulo.setForeground(ColorController.COR_LETRA_TITULO);
        labelCriarConta.setForeground(ColorController.COR_LETRA_TITULO);
        if (WeblafUtils.weblafEstaInstalado()) {
            WeblafUtils.configurarBotao(botaoLogin, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA,
                    ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 2, true);
            WeblafUtils.configuraWebLaf(CampoLogin);
            WeblafUtils.configuraWebLaf(campoSenha);
        }
    }

    public Student fazerLogin() throws Exception {
        String nome = this.CampoLogin.getText();
        String senha = new String(this.campoSenha.getPassword());
        String dados = "{" + "\"usuario\":\"" + nome + "\"," + "\"senha\": \"" + senha + "\"" + "}";
        return InterfaceComunicacao.getInstance().fazerLogin(dados);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        painelbotaoLogin = new javax.swing.JPanel();
        botaoLogin = new com.alee.laf.button.WebButton();
        labelCriarConta = new javax.swing.JLabel();
        paineltitulo = new javax.swing.JPanel();
        labelTitulo = new javax.swing.JLabel();
        painelcamposlogin = new javax.swing.JPanel();
        CampoLogin = new javax.swing.JTextField();
        campoSenha = new javax.swing.JPasswordField();

        setMinimumSize(new java.awt.Dimension(350, 380));
        setPreferredSize(new java.awt.Dimension(350, 380));
        setLayout(new java.awt.GridBagLayout());

        painelbotaoLogin.setMinimumSize(new java.awt.Dimension(300, 140));
        painelbotaoLogin.setOpaque(false);
        painelbotaoLogin.setPreferredSize(new java.awt.Dimension(300, 140));
        painelbotaoLogin.setLayout(new java.awt.GridBagLayout());

        botaoLogin.setText("Login");
        botaoLogin.setPreferredSize(new java.awt.Dimension(120, 50));
        botaoLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoLoginActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 6, 0);
        painelbotaoLogin.add(botaoLogin, gridBagConstraints);

        labelCriarConta.setText("Não tem uma Conta? Crie uma aqui!");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(18, 15, 10, 12);
        painelbotaoLogin.add(labelCriarConta, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        add(painelbotaoLogin, gridBagConstraints);

        paineltitulo.setMinimumSize(new java.awt.Dimension(300, 100));
        paineltitulo.setOpaque(false);
        paineltitulo.setPreferredSize(new java.awt.Dimension(300, 100));
        paineltitulo.setLayout(new java.awt.GridBagLayout());

        labelTitulo.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        labelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTitulo.setText("<html><body style='text-align:center'>Entre no sistema com sua conta</body></html>");
        labelTitulo.setMinimumSize(new java.awt.Dimension(300, 70));
        labelTitulo.setPreferredSize(new java.awt.Dimension(300, 70));
        paineltitulo.add(labelTitulo, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        add(paineltitulo, gridBagConstraints);

        painelcamposlogin.setMinimumSize(new java.awt.Dimension(300, 140));
        painelcamposlogin.setOpaque(false);
        painelcamposlogin.setPreferredSize(new java.awt.Dimension(300, 140));
        painelcamposlogin.setLayout(new java.awt.GridBagLayout());

        CampoLogin.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        CampoLogin.setText("Usuário");
        CampoLogin.setToolTipText("");
        CampoLogin.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        CampoLogin.setMinimumSize(new java.awt.Dimension(100, 24));
        CampoLogin.setPreferredSize(new java.awt.Dimension(150, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        painelcamposlogin.add(CampoLogin, gridBagConstraints);

        campoSenha.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        campoSenha.setText("Senha");
        campoSenha.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        campoSenha.setMinimumSize(new java.awt.Dimension(100, 24));
        campoSenha.setPreferredSize(new java.awt.Dimension(150, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 10);
        painelcamposlogin.add(campoSenha, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        add(painelcamposlogin, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoLoginActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_botaoLoginActionPerformed
        try {
            Student estudante  = fazerLogin();
            //ControladorDeJanelas.getInstance().closejanelaPrincipal();
            ControladorDeJanelas.getInstance().showJanelaMenuPrincipal(estudante);
        } catch (Exception e) {            
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(e);
        }
    }//GEN-LAST:event_botaoLoginActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CampoLogin;
    private com.alee.laf.button.WebButton botaoLogin;
    private javax.swing.JPasswordField campoSenha;
    private javax.swing.JLabel labelCriarConta;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JPanel painelbotaoLogin;
    private javax.swing.JPanel painelcamposlogin;
    private javax.swing.JPanel paineltitulo;
    // End of variables declaration//GEN-END:variables

    
}
