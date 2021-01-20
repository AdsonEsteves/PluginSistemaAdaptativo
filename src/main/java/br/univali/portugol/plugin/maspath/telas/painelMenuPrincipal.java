/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.plugin.maspath.telas;

import br.univali.portugol.plugin.maspath.ControladorDeJanelas;
import br.univali.portugol.plugin.maspath.conexao.InterfaceComunicacao;
import br.univali.portugol.plugin.maspath.dataentities.Student;
import br.univali.portugol.plugin.maspath.drawer.GraphDrawer;
import br.univali.portugol.plugin.maspath.utils.ImageWorker;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import com.alee.extended.image.DisplayType;
import com.alee.extended.image.WebImage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Space Today
 */
public class painelMenuPrincipal extends javax.swing.JPanel implements Themeable {

    /**
     * Creates new form painelMenuPrincipal
     */

    GraphDrawer drawer = new GraphDrawer();
    PainelTrilha trilha = new PainelTrilha();
    Student estudante;

    public painelMenuPrincipal() {
        initComponents();
        configurarCores();
        configurarAcoes();
    }

    public void configuraPainel(Student estudante) {
        // setImage(drawer.drawGraph());
        this.estudante = estudante;
        SwingUtilities.invokeLater(() -> {
            painelAmostragem.removeAll();
            try 
            {
                ImageWorker img = new ImageWorker(new URL(estudante.getAvatar()), labelPicUsuario, 100, 100);
                img.execute();
            } catch (MalformedURLException e) {
                System.out.println("Não achou imagem de usuario");
            }
            labelNomeUsuario.setText(estudante.getName());
            trilha.carregarComponentes();
            painelAmostragem.add(trilha);
            revalidate();
            repaint();
        });
    }
    private void configurarAcoes(){
        this.botaoLogout.setAction(new AbstractAction("",new javax.swing.ImageIcon(getClass().getResource("/logout.png"))){
            @Override
            public void actionPerformed(ActionEvent e) {
                String resposta = InterfaceComunicacao.getInstance().deslogar();
                if(resposta.equals("SUCESSO")){
                    ControladorDeJanelas.getInstance().closeJanelaMenuPrincipal();
                    ControladorDeJanelas.getInstance().showjanelaPrincipal();
                }
                else{
                    System.out.println(resposta);
                }
            }
        });
    }

    @Override
    public void configurarCores() {
        this.setBackground(ColorController.FUNDO_CLARO);
        painelUsuário.setBackground(ColorController.COR_DESTAQUE);
        labelPicUsuario.setForeground(ColorController.COR_LETRA);
        labelNomeUsuario.setForeground(ColorController.COR_LETRA);
        if (WeblafUtils.weblafEstaInstalado()) {
            WeblafUtils.configurarBotao(botaoAjuda, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA,
                    ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 10, true);
            WeblafUtils.configurarBotao(botaoConfiguracoes, ColorController.COR_PRINCIPAL, ColorController.COR_LETRA,
                    ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 10, true);
            WeblafUtils.configurarBotao(botaoLogout, ColorController.COR_PRINCIPAL, ColorController.COR_LETRA,
                    ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 10, true);
            WeblafUtils.configurarBotao(botaoPerguntar, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA,
                    ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 10, true);
            WeblafUtils.configurarBotao(botaoSelecionarConteudo, ColorController.FUNDO_ESCURO,
                    ColorController.COR_LETRA, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 10, true);
            WeblafUtils.configurarBotao(botaoVerTrilhas, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA,
                    ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 10, true);

        }
    }

    private void setImage(Image image) {
        SwingUtilities.invokeLater(() -> {
            painelAmostragem.removeAll();
            WebImage webImage = new WebImage(image);
            webImage.setDisplayType(DisplayType.fitComponent);
            painelAmostragem.add(webImage);
            revalidate();
            repaint();
        });
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

        selecaoConteudo1 = new br.univali.portugol.plugin.maspath.telas.SelecaoConteudo();
        painelMenu = new javax.swing.JPanel();
        botaoVerTrilhas = new com.alee.laf.button.WebButton();
        botaoSelecionarConteudo = new com.alee.laf.button.WebButton();
        botaoPerguntar = new com.alee.laf.button.WebButton();
        botaoAjuda = new com.alee.laf.button.WebButton();
        painelUsuário = new javax.swing.JPanel();
        labelPicUsuario = new javax.swing.JLabel();
        labelNomeUsuario = new javax.swing.JLabel();
        botaoLogout = new com.alee.laf.button.WebButton();
        botaoConfiguracoes = new com.alee.laf.button.WebButton();
        painelAmostragem = new javax.swing.JPanel();

        setMinimumSize(new java.awt.Dimension(810, 480));
        setPreferredSize(new java.awt.Dimension(810, 480));
        setLayout(new java.awt.GridBagLayout());

        painelMenu.setMinimumSize(new java.awt.Dimension(200, 480));
        painelMenu.setOpaque(false);
        painelMenu.setPreferredSize(new java.awt.Dimension(200, 480));
        painelMenu.setLayout(new java.awt.GridBagLayout());

        botaoVerTrilhas.setText("<html><div style=\"text-align:center\">Ver Trilhas");
        botaoVerTrilhas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        botaoVerTrilhas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoVerTrilhas.setPreferredSize(new java.awt.Dimension(120, 50));
        botaoVerTrilhas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoVerTrilhasActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        painelMenu.add(botaoVerTrilhas, gridBagConstraints);

        botaoSelecionarConteudo.setText("<html><div style=\"text-align:center\">Selecionar Conteúdo");
        botaoSelecionarConteudo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        botaoSelecionarConteudo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoSelecionarConteudo.setPreferredSize(new java.awt.Dimension(120, 50));
        botaoSelecionarConteudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSelecionarConteudoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        painelMenu.add(botaoSelecionarConteudo, gridBagConstraints);

        botaoPerguntar.setText("<html><div style=\"text-align:center\">Pergunte ao Sistema");
        botaoPerguntar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        botaoPerguntar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoPerguntar.setPreferredSize(new java.awt.Dimension(120, 50));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        painelMenu.add(botaoPerguntar, gridBagConstraints);

        botaoAjuda.setText("<html><div style=\"text-align:center\">Ajuda");
        botaoAjuda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        botaoAjuda.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoAjuda.setPreferredSize(new java.awt.Dimension(120, 50));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        painelMenu.add(botaoAjuda, gridBagConstraints);

        painelUsuário.setMinimumSize(new java.awt.Dimension(100, 120));
        painelUsuário.setOpaque(false);
        painelUsuário.setPreferredSize(new java.awt.Dimension(100, 120));
        painelUsuário.setLayout(new java.awt.GridBagLayout());

        labelPicUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelPicUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userpic100.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        painelUsuário.add(labelPicUsuario, gridBagConstraints);

        labelNomeUsuario.setText("Nome do Usuário");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        painelUsuário.add(labelNomeUsuario, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(15, 40, 15, 40);
        painelMenu.add(painelUsuário, gridBagConstraints);

        botaoLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logout.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        painelMenu.add(botaoLogout, gridBagConstraints);

        botaoConfiguracoes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gear_in.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        painelMenu.add(botaoConfiguracoes, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(painelMenu, gridBagConstraints);

        painelAmostragem.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 4));
        painelAmostragem.setMaximumSize(new java.awt.Dimension(610, 480));
        painelAmostragem.setMinimumSize(new java.awt.Dimension(610, 480));
        painelAmostragem.setOpaque(false);
        painelAmostragem.setPreferredSize(new java.awt.Dimension(610, 480));
        painelAmostragem.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(painelAmostragem, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoSelecionarConteudoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_botaoSelecionarConteudoActionPerformed
        SwingUtilities.invokeLater(() -> {
            
            try {
                this.painelAmostragem.removeAll();
                this.painelAmostragem.add(selecaoConteudo1);
                this.selecaoConteudo1.setVisible(true);
                // this.selecaoConteudo1.addFakeContents();
                String conteudos = InterfaceComunicacao.getInstance().requisitaConteudos();
                ObjectMapper mapper = new ObjectMapper();
                this.selecaoConteudo1.addContents(mapper.readTree(conteudos).get(0));
            } catch (JsonMappingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            this.revalidate();
            this.repaint();
        });
    }//GEN-LAST:event_botaoSelecionarConteudoActionPerformed

    private void botaoVerTrilhasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoVerTrilhasActionPerformed
        configuraPainel(this.estudante);
    }//GEN-LAST:event_botaoVerTrilhasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.laf.button.WebButton botaoAjuda;
    private com.alee.laf.button.WebButton botaoConfiguracoes;
    private com.alee.laf.button.WebButton botaoLogout;
    private com.alee.laf.button.WebButton botaoPerguntar;
    private com.alee.laf.button.WebButton botaoSelecionarConteudo;
    private com.alee.laf.button.WebButton botaoVerTrilhas;
    private javax.swing.JLabel labelNomeUsuario;
    private javax.swing.JLabel labelPicUsuario;
    private javax.swing.JPanel painelAmostragem;
    private javax.swing.JPanel painelMenu;
    private javax.swing.JPanel painelUsuário;
    private br.univali.portugol.plugin.maspath.telas.SelecaoConteudo selecaoConteudo1;
    // End of variables declaration//GEN-END:variables
}
