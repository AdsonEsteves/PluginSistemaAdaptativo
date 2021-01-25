package br.univali.portugol.plugin.maspath.utils;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

import com.alee.laf.button.WebButton;

public class ImageWorker extends SwingWorker<ImageIcon, Void> {

    URL imageURL;
    ImageIcon brandImage;
    WebButton device;
    JLabel label;
    int w;
    int h;

    public ImageWorker(URL imageURL, WebButton device, int w, int h){
        this.imageURL = imageURL;
        this.device = device;
        this.w = w;
        this.h = h;
    }

    public ImageWorker(URL imageURL, JLabel label, int w, int h){
        this.imageURL = imageURL;
        this.label = label;
        this.w = w;
        this.h = h;
    }

    @Override
    protected ImageIcon doInBackground() throws Exception {
        brandImage = new ImageIcon(imageURL);
        Image rawBrandImage = brandImage.getImage();
        Image newimg = rawBrandImage.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH);
        brandImage = new ImageIcon(newimg);
        return brandImage;
    }

    @Override
    protected void done() {
        if(device!=null)
        {
            device.setIcon(brandImage);
            device.revalidate();
            device.repaint();
        }
        if(label!=null){
            label.setIcon(brandImage);
            label.revalidate();
            label.repaint();
        }

      }
    }
