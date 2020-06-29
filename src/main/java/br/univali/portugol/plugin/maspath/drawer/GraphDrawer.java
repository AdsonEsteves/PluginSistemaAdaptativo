/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.plugin.maspath.drawer;


import br.univali.ps.ui.swing.ColorController;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Dimension2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author lite
 */
public class GraphDrawer {
    //private Grafo graph;
    private int nodeSize = 35;
    private int fontWidth;
    private int fontHeight;
    private double phi = Math.toRadians(15);
    private int barb= 20;
    private String[] listaConteudo = {"C1","C2","C3","C4","C5","C6","C7", "C8", "C9", "C10", "C11"};
    
    private Point size = new Point(610, 480);
    
    public GraphDrawer() {
        //this.graph = graph;
    }
    
    
    public Image drawGraph(){
        Point minvert = new Point();
        Point maxvert = new Point();
        Point centervert = new Point();
        Point maxSize = size;
        Point minSize = new Point(0, 0);
        BufferedImage bufferedImage = new BufferedImage(maxSize.x, maxSize.y, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = bufferedImage.getGraphics();
        Graphics2D gd = (Graphics2D) g;
        int distanceX = size.x/3;
        int distanceY = size.y/6;
        int initialPositionX = distanceX/2;
        int initialPositionY = distanceY/2;
        
        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gd.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        gd.setColor(ColorController.FUNDO_CLARO);
        gd.fillRect(0, 0, maxSize.x, maxSize.y);
        gd.setStroke(new BasicStroke(3.0f));
//        
//        {
//            gd.setColor(new Color(0,239,192));
//            gd.setColor(ColorController.COR_LETRA);
//            
//            Point sw = edge.getVertice1().getPosition();
//            Point ne = edge.getVertice2().getPosition();
//            gd.drawLine(sw.x, sw.y, ne.x, ne.y);
//            drawArrowHead(gd, ne, sw);     
//        }
//        
        
        for (int i=0; i<listaConteudo.length; i++) {
            
            if(i+1<listaConteudo.length)
            {
                gd.setColor(new Color(0,239,192));
                gd.setColor(ColorController.COR_LETRA);

                Point ne = new Point(initialPositionX+(distanceX*((i+1)%3)), initialPositionY+(distanceY*((i+1)/3)));
                Point sw = new Point(initialPositionX+(distanceX*((i)%3))-(fontWidth/2), initialPositionY+(distanceY*((i)/3)));
                gd.drawLine(sw.x, sw.y, ne.x, ne.y);
                drawArrowHead(gd, ne, sw);    
            }
            
            gd.setColor(new Color(69,189,255));            
            gd.fillOval(initialPositionX+(distanceX*(i%3))-nodeSize/2, initialPositionY+(distanceY*(i/3))-nodeSize/2, nodeSize, nodeSize);
            
            gd.setColor(ColorController.COR_LETRA);         
            gd.drawOval(initialPositionX+(distanceX*(i%3))-nodeSize/2, initialPositionY+(distanceY*(i/3))-nodeSize/2, nodeSize, nodeSize);

            gd.setColor(ColorController.FUNDO_MEDIO);
            fontWidth = gd.getFontMetrics().stringWidth(listaConteudo[i]);
            fontHeight = gd.getFontMetrics().getHeight()-gd.getFontMetrics().getDescent();
            gd.drawString(listaConteudo[i],initialPositionX+(distanceX*(i%3))-(fontWidth/2), initialPositionY+(distanceY*(i/3))+(fontHeight/2));            
            
        }
        
        bufferedImage = bufferedImage.getSubimage(minSize.x, minSize.y, maxSize.x, maxSize.y);
        return  bufferedImage;
    }
    
    private void drawArrowHead(Graphics2D g2, Point tip, Point tail)
    {
        double dy = tip.y - tail.y;
        double dx = tip.x - tail.x;
        double theta = Math.atan2(dy, dx);
        //System.out.println("theta = " + Math.toDegrees(theta));
        double x, y, rho = theta + phi;
        
        double iniX = tip.x - (nodeSize/2) * Math.cos(theta);
        double iniY = tip.y - (nodeSize/2) * Math.sin(theta);
        
        for(int j = 0; j < 2; j++)
        {
            x = iniX - barb * Math.cos(rho);
            y = iniY - barb * Math.sin(rho);
            g2.draw(new Line2D.Double(iniX, iniY, x, y));
            rho = theta - phi;
        }
    }
//    private Point getMaxMinPoint(int maxMin){
//        Point point = new Point();
//        point.x = this.graph.getVertice(0).getPosition().x;
//        point.y = this.graph.getVertice(0).getPosition().y;
//        for (Vertice v : this.graph.getVertices()) {
//            if(v.getPosition().x * maxMin > point.x * maxMin){
//                point.x = v.getPosition().x;
//            }
//            if(v.getPosition().y * maxMin > point.y * maxMin){
//                point.y = v.getPosition().y;
//            }
//        }
//        point.x += 50 * maxMin;
//        point.y += 50 * maxMin;
//        if(point.x < 0){
//            point.x = 0;
//        }
//        if(point.y < 0){
//            point.y = 0;
//        }
//        return point;
//    }
}
