package test;

import javax.swing.*;
import java.awt.*;

/**
 * @author Zhai Jinpei
 */
public class ProcessBarWindow extends JWindow{
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
