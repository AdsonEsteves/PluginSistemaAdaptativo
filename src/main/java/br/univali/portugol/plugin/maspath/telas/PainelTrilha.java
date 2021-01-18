package br.univali.portugol.plugin.maspath.telas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.plaf.DimensionUIResource;

import com.alee.laf.button.WebButton;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.univali.portugol.plugin.maspath.conexao.InterfaceComunicacao;
import br.univali.portugol.plugin.maspath.dataentities.Content;
import br.univali.ps.nucleo.PortugolStudio;
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
        setLayout(new GridLayout(4, conteudosTrilha.size()/4));
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
        for (Content conteudo : conteudosTrilha) {
            
            WebButton button = new WebButton(new AbstractAction(conteudo.getName()){
                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    String command = ((WebButton) e.getSource()).getActionCommand();
                    System.err.println("Abriu "+command);
                }                
            });
            
            if(conteudo.getName().length()>4){
                button.setText(conteudo.getName().substring(0, 3)+"...");
            }else{
                button.setText(conteudo.getName());
            }
            try {
                ImageIcon gif = new ImageIcon(new URL(conteudo.getImageLink()));
                gif = new ImageIcon(gif.getImage().getScaledInstance(35, 35,  java.awt.Image.SCALE_SMOOTH));
                button.setIcon(gif);                
            } catch (Exception e) {
                System.out.println(e);
            }
            
            button.setHorizontalAlignment(SwingConstants.CENTER);
            button.setVerticalAlignment(SwingConstants.CENTER);
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
            button.setPreferredSize(35, 35);
            button.setMaximumSize(new DimensionUIResource(35, 35));
            WeblafUtils.configurarBotao(button,ColorController.TRANSPARENTE, ColorController.COR_LETRA_TITULO, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 5);
            FabricaDicasInterface.criarTooltip(button, conteudo.getTopic());
            botoesConteudo.add(button);
            this.add(button);
        }
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
