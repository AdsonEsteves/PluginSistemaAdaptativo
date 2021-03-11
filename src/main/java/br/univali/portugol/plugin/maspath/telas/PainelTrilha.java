package br.univali.portugol.plugin.maspath.telas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
import java.net.URL;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputAdapter;

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
import br.univali.ps.ui.telas.TelaCustomBorder;
import br.univali.ps.ui.utils.FabricaDicasInterface;


public class PainelTrilha extends JPanel{

    List<Content> conteudosTrilha = new ArrayList<>();
    List<WebButton> botoesConteudo = new ArrayList<>();
    painelInfoConteudo painel= new painelInfoConteudo();
    JFrame frame = new JFrame();
    

    public PainelTrilha() {
        frame.setUndecorated(true);        
        frame.add(painel);
    }

    public void configurarFrame()
    {   
        TelaCustomBorder janela = ControladorDeJanelas.getInstance().getJanelaAtual();
        frame.setLocation(janela.getX()+janela.getWidth()+15, janela.getY()+40);
        frame.setPreferredSize(new Dimension(150, 450));
        frame.setMaximumSize(new Dimension(150, 450));
        frame.setMinimumSize(new Dimension(150, 450));
        //frame.setVisible(true);
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
        this.botoesConteudo.clear();
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
                        //abrirConteudo(conteudo);
                        //ControladorDeJanelas.getInstance().hideJanelaAtual();                        
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            });

            button.addMouseListener(new MouseInputAdapter(){
                @Override
                public void mouseEntered(MouseEvent e) {                    
                    configurarFrame();
                    painel.updateData(conteudo);
                    frame.setAlwaysOnTop(true);
                    frame.setVisible(true);
                    painel.revalidate();
                    painel.repaint();
                    frame.revalidate();
                    frame.repaint();
                    frame.pack();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    frame.setVisible(false);
                }
            });
            
            if(conteudo.getName().length()>4){
                button.setText(conteudo.getName().substring(0, 6)+"...");
            }else{
                button.setText(conteudo.getName());
            }
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
            button.setBounds(x, y, 80, 80);
        }
    }

    private void abrirConteudo(Content conteudo){
        AbaCodigoFonte aba = (AbaCodigoFonte) PortugolStudio.getInstancia().getTelaPrincipal().getPainelTabulado().getAbaSelecionada();
        aba.getEditor().setCodigo("Codigo Exercicio"+conteudo.getName());
        aba.getEditor().getTextArea().setText("Codigo Exercicio"+conteudo.getName());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < this.botoesConteudo.size()-1; i++) {
            WebButton button1 = this.botoesConteudo.get(i);
            WebButton button2 = this.botoesConteudo.get(i+1);

            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    
            g.setColor(ColorController.COR_LETRA);
            ((Graphics2D)g).setStroke(new BasicStroke(3.0f));
            Point p1 = button1.getLocation();
            p1.x += button1.getWidth() / 2;
            p1.y += button1.getHeight() / 2;
            Point p2 = button2.getLocation();
            p2.x += button2.getWidth() / 2;
            p2.y += button2.getHeight() / 2;
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
            //drawArrowHead((Graphics2D)g, p2, p1);
        }
    }

    private void drawArrowHead(Graphics2D g2, Point tip, Point tail)
    {
        double phi = Math.toRadians(15);
        int barb= 20;
        double dy = tip.y - tail.y;
        double dx = tip.x - tail.x;
        double theta = Math.atan2(dy, dx);
        //System.out.println("theta = " + Math.toDegrees(theta));
        double x, y, rho = theta + phi;
        
        double iniX = tip.x - (70/2) * Math.cos(theta);
        double iniY = tip.y - (70/2) * Math.sin(theta);
        
        for(int j = 0; j < 2; j++)
        {
            x = iniX - barb * Math.cos(rho);
            y = iniY - barb * Math.sin(rho);
            g2.draw(new Line2D.Double(iniX, iniY, x, y));
            rho = theta - phi;
        }
    }


}
