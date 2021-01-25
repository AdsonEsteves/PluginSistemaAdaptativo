/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.plugin.maspath.telas;

import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

import com.alee.extended.layout.WrapFlowLayout;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.univali.portugol.plugin.maspath.ControladorDeJanelas;
import br.univali.portugol.plugin.maspath.conexao.InterfaceComunicacao;
import br.univali.portugol.plugin.maspath.dataentities.Content;
import br.univali.portugol.plugin.maspath.utils.ImageWorker;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.PSScrollBarUI;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.telas.TelaCustomBorder;
import br.univali.ps.ui.utils.FabricaDicasInterface;

/**
 *
 * @author Space Today
 */
public class SelecaoConteudo extends javax.swing.JPanel implements Themeable{
    
    List<Content> conteudos = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    List<String> tags = new ArrayList<>();
    
    /**
     * Creates new form SelecaoConteudo
     */
    public SelecaoConteudo() {
        initComponents();
        configurarSeletores();
        configurarAcaoBuscar();
        configurarCores();
        painelItensBuscados.setLayout(new WrapFlowLayout(false, 2, 2));
    }

    private void configurarAcaoBuscar(){
        this.botaoBuscar.setAction(new AbstractAction("Buscar"){
            @Override
            public void actionPerformed(ActionEvent e) {
                ObjectNode infoBusca = mapper.createObjectNode();

                if (!campoDeBusca.getText().equals(""))
                    infoBusca.put("nome", campoDeBusca.getText());

                if (!((String) comboBoxTopico.getSelectedItem()).equals("Qualquer"))
                    infoBusca.put("topico", (String) comboBoxTopico.getSelectedItem());

                if (!((String) comboBoxTaxonomia.getSelectedItem()).equals("Qualquer"))
                    infoBusca.put("taxonomia", (String) comboBoxTaxonomia.getSelectedItem());

                if (!((String) comboBoxNivel.getSelectedItem()).equals("Qualquer"))
                    infoBusca.put("nivel", (String) comboBoxNivel.getSelectedItem());

                if (!tags.isEmpty())
                    infoBusca.set("tags", mapper.valueToTree(tags));

                if (!((String) comboBoxTipo.getSelectedItem()).equals("Qualquer"))
                    infoBusca.put("exercicio", ((String) comboBoxTipo.getSelectedItem()).equals("Exercicio"));

                String busca = InterfaceComunicacao.getInstance().requisitaBuscaConteudos(infoBusca);
                try {
                    addContents(mapper.readTree(busca));
                } catch (Exception e1) {
                    //TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        painelTags.setLayout(new BoxLayout(painelTags, BoxLayout.X_AXIS));
        this.comboBoxTags.setAction(new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                WebButton botao = new WebButton();
                String tag = (String)((WebComboBox)e.getSource()).getSelectedItem();
                botao.setAction(new AbstractAction(tag){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        WebButton botao = (WebButton) e.getSource();
                        tags.remove(botao.getActionCommand());
                        painelTags.remove(botao);
                        painelTags.revalidate();
                        painelTags.repaint();
                    }
                });
                tags.add(tag);
                painelTags.add(botao);                
                botao.setVisible(true);
                painelTags.revalidate();
                painelTags.repaint();
            }
        });
    }

    public static void main(String[] args) {
        SelecaoConteudo jPanel = new SelecaoConteudo();
        TelaCustomBorder border = new TelaCustomBorder(jPanel, "conteudo");
        jPanel.addFakeContents();
        border.setVisible(true);
    }

    private void configurarSeletores(){

        this.comboBoxTaxonomia.removeAllItems();
        this.comboBoxNivel.removeAllItems();
        this.comboBoxTopico.removeAllItems();
        this.comboBoxTags.removeAllItems();
        
        this.campoDeBusca.setText("");

        String info = InterfaceComunicacao.getInstance().requisitaInfo();
        this.comboBoxTaxonomia.addItem("Qualquer");
        this.comboBoxTaxonomia.addItem("Lembrar");
        this.comboBoxTaxonomia.addItem("Compreeder");
        this.comboBoxTaxonomia.addItem("Aplicar");
        this.comboBoxTaxonomia.addItem("Analisar");
        this.comboBoxTaxonomia.addItem("Avaliar");
        this.comboBoxTaxonomia.addItem("Criar");
        this.comboBoxTipo.addItem("Qualquer");
        this.comboBoxTipo.addItem("Exercicio");
        this.comboBoxTipo.addItem("Conteudo");
        this.comboBoxNivel.addItem("Qualquer");
        this.comboBoxTopico.addItem("Qualquer");
        try {
            JsonNode jinfo = mapper.readTree(info);

            for (int i = 1; i <= jinfo.get("MAXlevel").asInt(); i++) {
                this.comboBoxNivel.addItem(i+"");
            }

            for (JsonNode jsonNode : jinfo.get("tags")) {
                this.comboBoxTags.addItem(jsonNode.asText());
            }

            for (JsonNode jsonNode : jinfo.get("topics")) {
                this.comboBoxTopico.addItem(jsonNode.asText());
            }

        } catch (Exception e) {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(e);
        }
        revalidate();
        repaint();
    }

    @Override
    public void configurarCores() {
        this.setBackground(ColorController.FUNDO_MEDIO);
        this.labelNivel.setForeground(ColorController.COR_LETRA);
        this.labelTaxonomia.setForeground(ColorController.COR_LETRA);
        this.labelTemas.setForeground(ColorController.COR_LETRA);
        this.labelTopico.setForeground(ColorController.COR_LETRA);
        this.labelTipo.setForeground(ColorController.COR_LETRA);
        
        this.campoDeBusca.setBackground(ColorController.FUNDO_CLARO);
        this.campoDeBusca.setForeground(ColorController.COR_LETRA);
        this.jScrollPane1.getViewport().setBackground(ColorController.FUNDO_CLARO);

        this.campoDeBusca.setCaretColor(ColorController.COR_LETRA);
        this.painelTags.setBackground(ColorController.COR_PRINCIPAL);
        this.painelScrollTags.setOpaque(false);
        
        if(WeblafUtils.weblafEstaInstalado())
        {
            WeblafUtils.configurarBotao(botaoBuscar, ColorController.COR_LETRA, ColorController.COR_PRINCIPAL,
                                        ColorController.COR_LETRA, ColorController.COR_DESTAQUE, 1, true);
            WeblafUtils.configuraWebLaf(jScrollPane1);
            WeblafUtils.configuraWebLaf(comboBoxTaxonomia);
            WeblafUtils.configuraWebLaf(comboBoxNivel);
            WeblafUtils.configuraWebLaf(comboBoxTags);
            WeblafUtils.configuraWebLaf(comboBoxTopico);
            WeblafUtils.configuraWebLaf(comboBoxTipo);
        }
        revalidate();
        repaint();
    }
    
    public void addContents(JsonNode json)
    {   
        painelItensBuscados.removeAll();
          
        for (JsonNode jsonNode : json) {
            try 
            {
                Content conteudo = mapper.treeToValue(jsonNode, Content.class);
                WebButton button = new WebButton(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        abrirConteudo(conteudo);
                        ControladorDeJanelas.getInstance().hideJanelaAtual();
                    }
                });
                
                if(conteudo.getName().length()>15){
                    button.setText(conteudo.getName().substring(0, 11)+"...");
                }else{
                    button.setText(conteudo.getName());
                }                
                button.setHorizontalAlignment(SwingConstants.CENTER);
                button.setVerticalAlignment(SwingConstants.CENTER);
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                button.setVerticalTextPosition(SwingConstants.BOTTOM);
                WeblafUtils.configurarBotao(button,ColorController.TRANSPARENTE, ColorController.COR_LETRA_TITULO, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 5);
                FabricaDicasInterface.criarTooltip(button, conteudo.getTopic());
                painelItensBuscados.add(button);
                conteudos.add(conteudo);
                ImageWorker img = new ImageWorker(new URL(conteudo.getImageLink()), button, 103, 103);
                img.execute();
                
            } catch (JsonProcessingException ex) {
                Logger.getLogger(SelecaoConteudo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(SelecaoConteudo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        revalidate();
        repaint();
    }

    private void abrirConteudo(Content conteudo){
        AbaCodigoFonte aba = (AbaCodigoFonte) PortugolStudio.getInstancia().getTelaPrincipal().getPainelTabulado().getAbaSelecionada();
        aba.getEditor().setCodigo("Codigo Exercicio"+conteudo.getName());
        aba.getEditor().getTextArea().setText("Codigo Exercicio"+conteudo.getName());
        System.out.println("Abriu "+conteudo.getName());
    }
    
    public void addFakeContents()
    {
        painelItensBuscados.removeAll();
        this.comboBoxTaxonomia.removeAllItems();
        this.comboBoxNivel.removeAllItems();
        this.comboBoxTopico.removeAllItems();
        this.comboBoxTags.removeAllItems();
        this.comboBoxTaxonomia.addItem("Qualquer");
        this.comboBoxTaxonomia.addItem("Lembrar");
        this.comboBoxTaxonomia.addItem("Compreeder");
        this.comboBoxTaxonomia.addItem("Aplicar");
        this.comboBoxTaxonomia.addItem("Analisar");
        this.comboBoxTaxonomia.addItem("Avaliar");
        this.comboBoxTaxonomia.addItem("Criar");
        this.comboBoxTipo.addItem("Qualquer");
        this.comboBoxTipo.addItem("Exercicio");
        this.comboBoxTipo.addItem("Conteudo");
        this.comboBoxNivel.addItem("Qualquer");
        this.comboBoxTopico.addItem("Qualquer");
        this.comboBoxTags.addItem("Qualquer");
        this.comboBoxTags.addItem("safdasf");
        try {
            for (int i = 0; i < 30; i++) {
                Content conteudo = new Content("nome", "descricao", i%5, "Tópico", "Complexidade", true, i%5, new ArrayList<String>(), "Link", "https://i.4cdn.org/vg/1610038753760.jpg");
                WebButton button = new WebButton(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //abrirConteudo
                    }
                });
                if(conteudo.getName().length()>15){
                    button.setText(conteudo.getName().substring(0, 11)+"...");
                }else{
                    button.setText(conteudo.getName());
                }
                
                button.setHorizontalAlignment(SwingConstants.CENTER);
                button.setVerticalAlignment(SwingConstants.CENTER);
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                button.setVerticalTextPosition(SwingConstants.BOTTOM);
                WeblafUtils.configurarBotao(button,ColorController.TRANSPARENTE, ColorController.COR_LETRA_TITULO, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 2);
                FabricaDicasInterface.criarTooltip(button, conteudo.getTopic());
                painelItensBuscados.add(button);
                conteudos.add(conteudo);
                ImageWorker img = new ImageWorker(new URL(conteudo.getImageLink()), button, 103, 103);
                img.execute();
            }
        } catch (Exception ex) {
            Logger.getLogger(SelecaoConteudo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        webComboBox1 = new com.alee.laf.combobox.WebComboBox();
        painelBusca = new javax.swing.JPanel();
        painelAtributos = new javax.swing.JPanel();
        painelTopico = new javax.swing.JPanel();
        labelTopico = new javax.swing.JLabel();
        comboBoxTopico = new com.alee.laf.combobox.WebComboBox();
        painelTipo = new javax.swing.JPanel();
        labelTipo = new javax.swing.JLabel();
        comboBoxTipo = new com.alee.laf.combobox.WebComboBox();
        painelNivel = new javax.swing.JPanel();
        labelNivel = new javax.swing.JLabel();
        comboBoxNivel = new com.alee.laf.combobox.WebComboBox();
        paineTax = new javax.swing.JPanel();
        labelTaxonomia = new javax.swing.JLabel();
        comboBoxTaxonomia = new com.alee.laf.combobox.WebComboBox();
        painelTemas = new javax.swing.JPanel();
        painelSeletorTemas = new javax.swing.JPanel();
        labelTemas = new javax.swing.JLabel();
        comboBoxTags = new com.alee.laf.combobox.WebComboBox();
        painelScrollTags = new javax.swing.JScrollPane();
        painelTags = new javax.swing.JPanel();
        painelBuscar = new javax.swing.JPanel();
        campoDeBusca = new javax.swing.JTextField();
        botaoBuscar = new com.alee.laf.button.WebButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        painelItensBuscados = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(610, 480));
        setMinimumSize(new java.awt.Dimension(610, 480));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(610, 480));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        painelBusca.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 5));
        painelBusca.setMaximumSize(new java.awt.Dimension(610, 120));
        painelBusca.setOpaque(false);
        painelBusca.setPreferredSize(new java.awt.Dimension(610, 120));
        painelBusca.setLayout(new java.awt.GridLayout(3, 0));

        painelAtributos.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painelAtributos.setMaximumSize(new java.awt.Dimension(600, 40));
        painelAtributos.setMinimumSize(new java.awt.Dimension(600, 40));
        painelAtributos.setOpaque(false);
        painelAtributos.setPreferredSize(new java.awt.Dimension(600, 40));
        painelAtributos.setLayout(new javax.swing.BoxLayout(painelAtributos, javax.swing.BoxLayout.X_AXIS));

        painelTopico.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 15));
        painelTopico.setOpaque(false);
        painelTopico.setLayout(new javax.swing.BoxLayout(painelTopico, javax.swing.BoxLayout.LINE_AXIS));

        labelTopico.setText("Tópico:");
        labelTopico.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 5));
        painelTopico.add(labelTopico);
        painelTopico.add(comboBoxTopico);

        painelAtributos.add(painelTopico);

        painelTipo.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 15));
        painelTipo.setOpaque(false);
        painelTipo.setLayout(new javax.swing.BoxLayout(painelTipo, javax.swing.BoxLayout.LINE_AXIS));

        labelTipo.setText("Tipo:");
        labelTipo.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 5));
        painelTipo.add(labelTipo);
        painelTipo.add(comboBoxTipo);

        painelAtributos.add(painelTipo);

        painelNivel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 15));
        painelNivel.setOpaque(false);
        painelNivel.setLayout(new javax.swing.BoxLayout(painelNivel, javax.swing.BoxLayout.LINE_AXIS));

        labelNivel.setText("Nivel:");
        labelNivel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 5));
        painelNivel.add(labelNivel);
        painelNivel.add(comboBoxNivel);

        painelAtributos.add(painelNivel);

        paineTax.setOpaque(false);
        paineTax.setLayout(new javax.swing.BoxLayout(paineTax, javax.swing.BoxLayout.LINE_AXIS));

        labelTaxonomia.setText("Taxonomia:");
        labelTaxonomia.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 5));
        paineTax.add(labelTaxonomia);
        paineTax.add(comboBoxTaxonomia);

        painelAtributos.add(paineTax);

        painelBusca.add(painelAtributos);

        painelTemas.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painelTemas.setMaximumSize(new java.awt.Dimension(610, 40));
        painelTemas.setMinimumSize(new java.awt.Dimension(610, 40));
        painelTemas.setOpaque(false);
        painelTemas.setPreferredSize(new java.awt.Dimension(610, 40));
        painelTemas.setLayout(new javax.swing.BoxLayout(painelTemas, javax.swing.BoxLayout.LINE_AXIS));

        painelSeletorTemas.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 15));
        painelSeletorTemas.setOpaque(false);
        painelSeletorTemas.setLayout(new javax.swing.BoxLayout(painelSeletorTemas, javax.swing.BoxLayout.LINE_AXIS));

        labelTemas.setText("Temas:");
        labelTemas.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 5));
        painelSeletorTemas.add(labelTemas);

        comboBoxTags.setMaximumSize(new java.awt.Dimension(100, 100));
        comboBoxTags.setMinimumSize(new java.awt.Dimension(100, 100));
        comboBoxTags.setPreferredSize(new java.awt.Dimension(100, 100));
        painelSeletorTemas.add(comboBoxTags);

        painelTemas.add(painelSeletorTemas);

        painelScrollTags.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        painelScrollTags.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        painelScrollTags.setMaximumSize(null);
        painelScrollTags.setOpaque(false);

        painelTags.setMaximumSize(null);
        painelTags.setOpaque(false);

        javax.swing.GroupLayout painelTagsLayout = new javax.swing.GroupLayout(painelTags);
        painelTags.setLayout(painelTagsLayout);
        painelTagsLayout.setHorizontalGroup(
            painelTagsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 708, Short.MAX_VALUE)
        );
        painelTagsLayout.setVerticalGroup(
            painelTagsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 81, Short.MAX_VALUE)
        );

        painelScrollTags.setViewportView(painelTags);

        painelTemas.add(painelScrollTags);

        painelBusca.add(painelTemas);

        painelBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painelBuscar.setMaximumSize(new java.awt.Dimension(610, 40));
        painelBuscar.setMinimumSize(new java.awt.Dimension(610, 40));
        painelBuscar.setOpaque(false);
        painelBuscar.setPreferredSize(new java.awt.Dimension(610, 40));
        painelBuscar.setLayout(new javax.swing.BoxLayout(painelBuscar, javax.swing.BoxLayout.LINE_AXIS));

        campoDeBusca.setText("digite um nome");
        painelBuscar.add(campoDeBusca);

        botaoBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
        botaoBuscar.setText("Buscar");
        botaoBuscar.setMaximumSize(new java.awt.Dimension(100, 50));
        botaoBuscar.setMinimumSize(new java.awt.Dimension(100, 50));
        botaoBuscar.setPreferredSize(new java.awt.Dimension(100, 50));
        painelBuscar.add(botaoBuscar);

        painelBusca.add(painelBuscar);

        add(painelBusca);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(600, 350));

        painelItensBuscados.setOpaque(false);

        javax.swing.GroupLayout painelItensBuscadosLayout = new javax.swing.GroupLayout(painelItensBuscados);
        painelItensBuscados.setLayout(painelItensBuscadosLayout);
        painelItensBuscadosLayout.setHorizontalGroup(
            painelItensBuscadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 601, Short.MAX_VALUE)
        );
        painelItensBuscadosLayout.setVerticalGroup(
            painelItensBuscadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 468, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(painelItensBuscados);

        add(jScrollPane1);
    }// </editor-fold>//GEN-END:initComponents

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.laf.button.WebButton botaoBuscar;
    private javax.swing.JTextField campoDeBusca;
    private com.alee.laf.combobox.WebComboBox comboBoxNivel;
    private com.alee.laf.combobox.WebComboBox comboBoxTags;
    private com.alee.laf.combobox.WebComboBox comboBoxTaxonomia;
    private com.alee.laf.combobox.WebComboBox comboBoxTipo;
    private com.alee.laf.combobox.WebComboBox comboBoxTopico;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelNivel;
    private javax.swing.JLabel labelTaxonomia;
    private javax.swing.JLabel labelTemas;
    private javax.swing.JLabel labelTipo;
    private javax.swing.JLabel labelTopico;
    private javax.swing.JPanel paineTax;
    private javax.swing.JPanel painelAtributos;
    private javax.swing.JPanel painelBusca;
    private javax.swing.JPanel painelBuscar;
    private javax.swing.JPanel painelItensBuscados;
    private javax.swing.JPanel painelNivel;
    private javax.swing.JScrollPane painelScrollTags;
    private javax.swing.JPanel painelSeletorTemas;
    private javax.swing.JPanel painelTags;
    private javax.swing.JPanel painelTemas;
    private javax.swing.JPanel painelTipo;
    private javax.swing.JPanel painelTopico;
    private com.alee.laf.combobox.WebComboBox webComboBox1;
    // End of variables declaration//GEN-END:variables
}
