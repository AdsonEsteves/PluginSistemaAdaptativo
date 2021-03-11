package br.univali.portugol.plugin.maspath.telas;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.univali.portugol.plugin.maspath.dataentities.Content;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;

public class painelInfoConteudo extends JPanel implements Themeable{
    
    public painelInfoConteudo(){
        super();
        initComponents();
        configurarCores();
    }

    @Override
    public void configurarCores() {
        labelNome.setForeground(ColorController.COR_LETRA);
        labelDescricao.setForeground(ColorController.COR_LETRA);
        labelTopico.setForeground(ColorController.COR_LETRA);
        labelNivel.setForeground(ColorController.COR_LETRA);
        labelComplexidade.setForeground(ColorController.COR_LETRA);
        labelTaxonomia.setForeground(ColorController.COR_LETRA);
        labelExercicio.setForeground(ColorController.COR_LETRA);
        labelTags.setForeground(ColorController.COR_LETRA);
        this.setBackground(ColorController.FUNDO_CLARO);
    }

    public void updateData(Content conteudo)
    {
        labelNome.setText("<html><body><div>Nome: "+conteudo.getName()+"</div></body></html>");
        labelDescricao.setText("<html><body><div>Descrição: "+conteudo.getDescricao()+"</div></body></html>");
        labelTopico.setText("<html><body><div>Tópico: "+conteudo.getTopic()+"</div></body></html>");
        labelNivel.setText("<html><body><div>Nível: "+conteudo.getLevel()+"</div></body></html>");
        labelComplexidade.setText("<html><body><div>Complexidade: "+conteudo.getComplexity()+"</div></body></html>");
        labelTaxonomia .setText("<html><body><div>Taxonomia: "+conteudo.getTaxonomy()+"</div></body></html>");
        labelExercicio.setText(conteudo.getExercise()?"<html><body><div>Exercicio: Sim"+"</div></body></html>":"<html><body><div>Exercicio: Não"+"</div></body></html>");
        labelTags.setText("<html><body><div>Tags: "+conteudo.getTags()+"</div></body></html>");
    }


    private void initComponents()
    {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(300, 450));
        this.setMaximumSize(new Dimension(300, 450));
        this.setMinimumSize(new Dimension(300, 450));
        labelNome = new JLabel();
        labelDescricao = new JLabel();
        labelTopico = new JLabel();
        labelNivel = new JLabel();
        labelComplexidade = new JLabel();
        labelTaxonomia = new JLabel();
        labelExercicio = new JLabel();
        labelTags = new JLabel();
        this.add(labelNome);
        this.add(labelDescricao);
        this.add(labelTopico);
        this.add(labelNivel);
        this.add(labelComplexidade);
        this.add(labelTaxonomia);
        this.add(labelExercicio);
        this.add(labelTags);
    }

    JLabel labelNome;
    JLabel labelDescricao;
    JLabel labelTopico;
    JLabel labelNivel;
    JLabel labelTags;
    JLabel labelComplexidade;
    JLabel labelExercicio;
    JLabel labelTaxonomia;
    
}
