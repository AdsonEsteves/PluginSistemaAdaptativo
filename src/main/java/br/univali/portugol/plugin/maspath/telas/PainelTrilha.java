package br.univali.portugol.plugin.maspath.telas;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.alee.extended.layout.WrapFlowLayout;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.DimensionUIResource;

import com.alee.laf.button.WebButton;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.univali.portugol.plugin.maspath.ControladorDeJanelas;
import br.univali.portugol.plugin.maspath.conexao.InterfaceComunicacao;
import br.univali.portugol.plugin.maspath.dataentities.Content;
import br.univali.portugol.plugin.maspath.utils.ImageWorker;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.utils.FabricaDicasInterface;


public class PainelTrilha extends JPanel{

    List<Content> conteudosTrilha = new ArrayList<>();
    List<WebButton> botoesConteudo = new ArrayList<>();

    public PainelTrilha() {
        //carregarComponentes();
    }

    public void carregarComponentes(){
        carregarTrilha();        
        removeAll();
        setLayout(null);
        setSize(610, 480);
        setOpaque(false);
        carregarBotoes();
    }

    private void carregarTrilha()
    {
        conteudosTrilha.clear();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String trilhaJson = InterfaceComunicacao.getInstance().requisitaTrilha();
            JsonNode trilha = mapper.readTree(trilhaJson).get(0);
            for (JsonNode jsonNode : trilha) {
                Content conteudo = mapper.treeToValue(jsonNode, Content.class);
                conteudosTrilha.add(conteudo);
            }
            
        } catch (Exception e) {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(e);
        }
    }

    private void carregarBotoes()
    {
        int startx = 10;
        int starty = 10;
        for (int i = 0; i < conteudosTrilha.size(); i++) {
        //for (Content conteudo : conteudosTrilha) {
            Content conteudo = conteudosTrilha.get(i);
            WebButton button = new WebButton(new AbstractAction(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    try {
                        abrirConteudo(conteudo);
                        ControladorDeJanelas.getInstance().hideJanelaAtual();                        
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }                
            });
            
            if(conteudo.getName().length()>4){
                button.setText(conteudo.getName().substring(0, 6)+"...");
            }else{
                button.setText(conteudo.getName());
            }
            //button.setName(i+"");
            try {
                ImageWorker img = new ImageWorker(new URL(conteudo.getImageLink()), button, 35, 35);
                img.execute();               
            } catch (Exception e) {
                PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(e);
            }
            button.setHorizontalAlignment(SwingConstants.CENTER);
            button.setVerticalAlignment(SwingConstants.CENTER);
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
            
            
            if(WeblafUtils.weblafEstaInstalado())
            WeblafUtils.configurarBotao(button,ColorController.TRANSPARENTE, ColorController.COR_LETRA_TITULO, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 5);
            FabricaDicasInterface.criarTooltip(button, conteudosTrilha.get(i).getTopic());
            botoesConteudo.add(button);
            this.add(button);
            
            int x = startx + ((i*150)%600);
            int y = starty + 100* (((i*150)/600));
            // button.setLocation(x, y);
            // button.setPreferredSize(100, 100);
            // button.setMaximumSize(new DimensionUIResource(100, 100));
            // button.setMinimumSize(new DimensionUIResource(100, 100));
            button.setBounds(x, y, 80, 80);
        }
    }

    private void abrirConteudo(Content conteudo){
        AbaCodigoFonte aba = (AbaCodigoFonte) PortugolStudio.getInstancia().getTelaPrincipal().getPainelTabulado().getAbaSelecionada();
        aba.getEditor().setCodigo("Codigo Exercicio"+conteudo.getName());
        aba.getEditor().getTextArea().setText("Codigo Exercicio"+conteudo.getName());
        System.out.println("Abriu "+conteudo.getName());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < this.botoesConteudo.size()-1; i++) {
            WebButton button1 = this.botoesConteudo.get(i);
            WebButton button2 = this.botoesConteudo.get(i+1);
    
            g.setColor(Color.RED);
            Point p1 = button1.getLocation();
            p1.x += button1.getWidth() / 2;
            p1.y += button1.getHeight() / 2;
            Point p2 = button2.getLocation();
            p2.x += button2.getWidth() / 2;
            p2.y += button2.getHeight() / 2;
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }


}
