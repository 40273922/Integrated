package test;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Zhai Jinpei
 */
public class ProcessBarWindow extends JWindow{
    public static void main(String[] args){
        new ProcessBarWindow();
    }
    private volatile double length = 800;

    public  double getLength(){
        return length;
    }

    public void setLength(double length){
       this. length = length;
    }

    JProgressBar jProgressBar = new JProgressBar(JProgressBar.HORIZONTAL,0,800);

    public ProcessBarWindow(){
        setBounds(360,400,800,16);
        getContentPane().add(jProgressBar);
        jProgressBar.setValue(500);
        jProgressBar.setStringPainted(true);
        jProgressBar.setPreferredSize(new Dimension(800,16));
        jProgressBar.setBackground(Color.white);
        jProgressBar.setForeground(Color.GREEN);
        jProgressBar.setVisible(true);
        setVisible(true);
    }
}
