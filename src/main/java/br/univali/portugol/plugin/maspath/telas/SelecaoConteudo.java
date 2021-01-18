/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.plugin.maspath.telas;

import br.univali.portugol.plugin.maspath.dataentities.Content;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.utils.FabricaDicasInterface;
import com.alee.extended.layout.WrapFlowLayout;
import com.alee.laf.button.WebButton;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

/**
 *
 * @author Space Today
 */
public class SelecaoConteudo extends javax.swing.JPanel implements Themeable{
    
    List<Content> conteudos = new ArrayList<>();
    
    /**
     * Creates new form SelecaoConteudo
     */
    public SelecaoConteudo() {
        initComponents();
        configurarCores();
        painelItensBuscados.setLayout(new WrapFlowLayout(false, 2, 2));
    }

    @Override
    public void configurarCores() {
        this.setBackground(ColorController.FUNDO_MEDIO);
        this.labelDificuldade.setForeground(ColorController.COR_LETRA_TITULO);
        this.labelNivel.setForeground(ColorController.COR_LETRA);
        this.labelTaxonomia.setForeground(ColorController.COR_LETRA);
        this.labelTemas.setForeground(ColorController.COR_LETRA);
        this.labelTopico.setForeground(ColorController.COR_LETRA);
        
        this.campoDeBusca.setBackground(ColorController.FUNDO_CLARO);
        this.jScrollPane1.getViewport().setBackground(ColorController.FUNDO_CLARO);
        
        
        if(WeblafUtils.weblafEstaInstalado())
        {
            WeblafUtils.configurarBotao(botaoBuscar, ColorController.COR_LETRA, ColorController.COR_PRINCIPAL,
                                        ColorController.COR_LETRA, ColorController.COR_DESTAQUE, 1, true);
            WeblafUtils.configuraWebLaf(checkBoxExercicio);
            WeblafUtils.configuraWebLaf(jScrollPane1);
        }
    }
    
    public void addContents(JsonNode json)
    {   
        painelItensBuscados.removeAll();
        ObjectMapper mapper = new ObjectMapper();
        for (JsonNode jsonNode : json) {
            try 
            {
                Content conteudo = mapper.treeToValue(jsonNode, Content.class);
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
                WeblafUtils.configurarBotao(button,ColorController.TRANSPARENTE, ColorController.COR_LETRA_TITULO, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 5);
                FabricaDicasInterface.criarTooltip(button, conteudo.getTopic());
                painelItensBuscados.add(button);
                conteudos.add(conteudo);
                ImageWorker img = new ImageWorker(new URL(conteudo.getImageLink()), button);
                img.execute();
                
            } catch (JsonProcessingException ex) {
                Logger.getLogger(SelecaoConteudo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(SelecaoConteudo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void addFakeContents()
    {
        painelItensBuscados.removeAll();
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
                ImageWorker img = new ImageWorker(new URL("https://i.4cdn.org/vg/1610038753760.jpg"), button);
                img.execute();
            }
        } catch (Exception ex) {
            Logger.getLogger(SelecaoConteudo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public class ImageWorker extends SwingWorker<ImageIcon, Void>{

        URL imageURL;
        ImageIcon brandImage;
        WebButton device;

        public ImageWorker(URL imageURL, WebButton device){
            this.imageURL = imageURL;
            this.device = device;
        }

        @Override
        protected ImageIcon doInBackground() throws Exception {
            brandImage = new ImageIcon(imageURL);
            Image rawBrandImage = brandImage.getImage();
            Image newimg = rawBrandImage.getScaledInstance(103, 103,  java.awt.Image.SCALE_SMOOTH);
            brandImage = new ImageIcon(newimg);
            return brandImage;
        }

        @Override
        protected void done() {
            device.setIcon(brandImage);
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

        painelBusca = new javax.swing.JPanel();
        campoDeBusca = new javax.swing.JTextField();
        botaoBuscar = new com.alee.laf.button.WebButton();
        comboBoxTags = new javax.swing.JComboBox<>();
        labelDificuldade = new javax.swing.JLabel();
        labelTaxonomia = new javax.swing.JLabel();
        labelNivel = new javax.swing.JLabel();
        labelTopico = new javax.swing.JLabel();
        comboBoxTopico = new javax.swing.JComboBox<>();
        comboBoxTaxonomia = new javax.swing.JComboBox<>();
        comboBoxNivel = new javax.swing.JComboBox<>();
        comboBoxDificuldade = new javax.swing.JComboBox<>();
        checkBoxExercicio = new javax.swing.JCheckBox();
        painelScrollTags = new javax.swing.JScrollPane();
        labelTemas = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        painelItensBuscados = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(610, 480));
        setMinimumSize(new java.awt.Dimension(610, 480));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(610, 480));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        painelBusca.setMaximumSize(new java.awt.Dimension(610, 120));
        painelBusca.setOpaque(false);
        painelBusca.setPreferredSize(new java.awt.Dimension(610, 120));

        campoDeBusca.setText("digite um nome");

        botaoBuscar.setText("Buscar");

        comboBoxTags.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        labelDificuldade.setText("Dificuldade");

        labelTaxonomia.setText("Taxonomia");

        labelNivel.setText("Nivel");

        labelTopico.setText("Tópico");

        comboBoxTopico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        comboBoxTaxonomia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        comboBoxNivel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        comboBoxDificuldade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        checkBoxExercicio.setText("exercicio");
        checkBoxExercicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxExercicioActionPerformed(evt);
            }
        });

        labelTemas.setText("Temas");

        javax.swing.GroupLayout painelBuscaLayout = new javax.swing.GroupLayout(painelBusca);
        painelBusca.setLayout(painelBuscaLayout);
        painelBuscaLayout.setHorizontalGroup(
            painelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelBuscaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelBuscaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(campoDeBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(botaoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painelBuscaLayout.createSequentialGroup()
                        .addGroup(painelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTopico)
                            .addComponent(labelNivel))
                        .addGap(18, 18, 18)
                        .addGroup(painelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelBuscaLayout.createSequentialGroup()
                                .addComponent(comboBoxTopico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelTaxonomia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(comboBoxTaxonomia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(painelBuscaLayout.createSequentialGroup()
                                .addComponent(comboBoxNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelDificuldade)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(comboBoxDificuldade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(painelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelBuscaLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(labelTemas)
                                .addGap(18, 18, 18)
                                .addComponent(comboBoxTags, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(painelBuscaLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(checkBoxExercicio)))
                        .addGap(18, 18, 18)
                        .addComponent(painelScrollTags)))
                .addContainerGap())
        );
        painelBuscaLayout.setVerticalGroup(
            painelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelBuscaLayout.createSequentialGroup()
                .addGroup(painelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelBuscaLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(campoDeBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelBuscaLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(painelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(painelScrollTags)
                            .addGroup(painelBuscaLayout.createSequentialGroup()
                                .addGroup(painelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelTaxonomia)
                                    .addComponent(comboBoxTopico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboBoxTaxonomia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelTopico)
                                    .addComponent(labelTemas)
                                    .addComponent(comboBoxTags))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(painelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(painelBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(comboBoxNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelDificuldade)
                                        .addComponent(comboBoxDificuldade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(checkBoxExercicio))
                                    .addComponent(labelNivel))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botaoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(373, 373, 373))
        );

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

    private void checkBoxExercicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxExercicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkBoxExercicioActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.laf.button.WebButton botaoBuscar;
    private javax.swing.JTextField campoDeBusca;
    private javax.swing.JCheckBox checkBoxExercicio;
    private javax.swing.JComboBox<String> comboBoxDificuldade;
    private javax.swing.JComboBox<String> comboBoxNivel;
    private javax.swing.JComboBox<String> comboBoxTags;
    private javax.swing.JComboBox<String> comboBoxTaxonomia;
    private javax.swing.JComboBox<String> comboBoxTopico;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelDificuldade;
    private javax.swing.JLabel labelNivel;
    private javax.swing.JLabel labelTaxonomia;
    private javax.swing.JLabel labelTemas;
    private javax.swing.JLabel labelTopico;
    private javax.swing.JPanel painelBusca;
    private javax.swing.JPanel painelItensBuscados;
    private javax.swing.JScrollPane painelScrollTags;
    // End of variables declaration//GEN-END:variables
}
