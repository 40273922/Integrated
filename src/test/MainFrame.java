package test;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Zhai Jinpei
 */
public class MainFrame extends JFrame{
    Circle circle = new Circle();

    public static void main(String[] args){
        new MainFrame();
    }

    public MainFrame(){
        SwingUtilities.invokeLater(()->{
            try{
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
            }catch(UnsupportedLookAndFeelException e){
                throw new RuntimeException(e);
            }
            setTitle("定积分计算圆面积演示");
            setSize(900,800);
            setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            JPanel circlePanel = new JPanel(){
                @Override
                protected void paintComponent(Graphics g){
                    super.paintComponent(g);
                    for(int i = 0;i <= circle.N;i++){
                        g.drawOval((int)(20 + circle.dR * 120 * i),(int)(20 + circle.dR * 120 * i),2 * (int)(100 - circle.dR * 120 * i),2 * (int)(100 - circle.dR * 120 * i));
                    }
                }
            };
            JPanel argsPanel = new JPanel();
            JPanel areaPanel = new JPanel(){
                @Override
                protected void paintComponent(Graphics g){
                    super.paintComponent(g);
                    g.drawLine(50,10,50,(int)Circle.C * 120 + 40);
                    g.drawLine(50,(int)Circle.C * 120 + 10,170,(int)Circle.C * 120 + 10);
                    g.drawLine(170,10,170,(int)Circle.C * 120 + 10);
                    for(int i = 0;i <= circle.N;i++){
                        g.drawLine((int)(50 + circle.dR * i * 120),(int)(Circle.C * 120 + 10) - (int)(circle.dC * 120 * i),(int)(50 + circle.dR * (i+1) * 120),(int)(Circle.C * 120 + 10) - (int)(circle.dC * 120 * i));
                        g.drawRect((int)(50 + circle.dR * i * 120),(int)(Circle.C * 120 + 10) - (int)(circle.dC * 120 * i),(int)circle.dR * 120,(int)circle.dC * 120 * i);
                    }
                    System.out.println("===========================");
                }
            };
            JLabel N = new JLabel("N=");
            JTextField Nfield = new JTextField();
            JLabel AREALabel = new JLabel("近似面积=");
            JTextField AREA = new JTextField();
            JLabel R = new JLabel("半径=");
            JTextField Rfield = new JTextField();
            Rfield.setText("1");
            Rfield.setEditable(false);
            JLabel EA = new JLabel("准确面积=");
            JTextField EAfield = new JTextField("3.1415926535897932");
            EAfield.setEditable(false);
            JLabel dr = new JLabel("dr=");
            JTextField drfield = new JTextField(String.valueOf(circle.dR));
            JLabel ex = new JLabel("误差=");
            JTextField exfield = new JTextField();
            Nfield.setText("2147483647");
            Nfield.addActionListener(e->{
                double Num = Double.parseDouble(Nfield.getText());
                if(Num < 900000){
                    AREA.setText(String.valueOf(circle.setN(Num).AREAR));
                    exfield.setText(String.valueOf(Double.parseDouble(EAfield.getText()) - Double.parseDouble(AREA.getText())));
                    drfield.setText(String.valueOf(circle.dR));
                    circlePanel.repaint();
                    areaPanel.repaint();
                }else{
                    AREA.setText(String.valueOf(circle.setN(Num).AREAR));
                    exfield.setText(String.valueOf(Double.parseDouble(EAfield.getText()) - Double.parseDouble(AREA.getText())));
                    drfield.setText(String.valueOf(circle.dR));
                    JOptionPane.showMessageDialog(this,"你输入的N过大，可能导致渲染时间过长！！！\n但仍可看到计算结果");
                }
            });
            JMenuBar jMenuBar = new JMenuBar();
            JMenuBar jMenuBar2 = new JMenuBar();
            JMenu jMenu = new JMenu("选项");
            JMenu jMenu2 = new JMenu("关于");
            JMenuItem jMenuItem1 = new JMenuItem("自动演示");
            jMenuItem1.addActionListener(e->{
                for(int i = 1;i<80000;i++){
                    Nfield.setText(String.valueOf(i));
                    AREA.setText(String.valueOf(circle.setN(i).AREAR));
                    exfield.setText(String.valueOf(Double.parseDouble(EAfield.getText()) - Double.parseDouble(AREA.getText())));
                    drfield.setText(String.valueOf(circle.dR));
                    circlePanel.repaint();
                    areaPanel.repaint();
                }
            });
            JMenuItem jMenuItem2 = new JMenuItem("退出界面");
            JMenuItem jMenuItem3 = new JMenuItem("作者");
            jMenuItem3.addActionListener(e->JOptionPane.showMessageDialog(this,"软件2211 翟金培"));
            JMenuItem jMenuItem4 = new JMenuItem("小组成员");
            jMenuItem4.addActionListener(e->{
                JOptionPane.showMessageDialog(this,"软件2211 翟金培\n软件2211 陈也\n软件2211 殷文喧\n软件2211 仇星\n软件2211 蔡吴江\n软件2211 李方圆");
            });
            JMenuItem jMenuItem5 = new JMenuItem("源代码链接");
            jMenuItem5.addActionListener(e->{
                Desktop desktop = Desktop.getDesktop();
                try{
                    //todo:uri
                    URI uri = new URI("");
                    desktop.browse(uri);
                }catch(URISyntaxException | IOException exc){
                    throw new RuntimeException(exc);
                }
            });
            JMenuItem jMenuItem6 = new JMenuItem("个人博客链接");
            jMenuItem6.addActionListener(e->{
                Desktop desktop = Desktop.getDesktop();
                try{
                    URI uri = new URI("https://blog.csdn.net/m0_73469755?type=collect");
                    desktop.browse(uri);
                }catch(URISyntaxException | IOException exc){
                    throw new RuntimeException(exc);
                }
            });
            jMenuItem2.addActionListener(e->System.exit(0));
            jMenu.add(jMenuItem1);
            jMenu.add(jMenuItem2);
            jMenu2.add(jMenuItem3);
            jMenu2.add(jMenuItem4);
            jMenu2.add(jMenuItem5);
            jMenu2.add(jMenuItem6);
            jMenuBar.add(jMenu);
            jMenuBar.add(jMenu2);
            argsPanel.setLayout(new GridLayout(7,2));
            argsPanel.add(jMenuBar);
            argsPanel.add(jMenuBar2);
            argsPanel.add(N);
            argsPanel.add(Nfield);
            argsPanel.add(dr);
            argsPanel.add(drfield);
            argsPanel.add(R);
            argsPanel.add(Rfield);
            argsPanel.add(EA);
            argsPanel.add(EAfield);
            argsPanel.add(AREALabel);
            argsPanel.add(AREA);
            argsPanel.add(ex);
            argsPanel.add(exfield);
            JLabel y = new JLabel("Y:2\u03c0R        ");
            JLabel x = new JLabel("X:R");
            JLabel e = new JLabel("  Y轴最大误差:>-0.003");
            y.setForeground(Color.red);
            x.setForeground(Color.red);
            e.setForeground(Color.red);
            areaPanel.add(y);
            areaPanel.add(x);
            areaPanel.add(e);
            getContentPane().setLayout(new GridLayout(1,3));
            getContentPane().add(circlePanel);
            getContentPane().add(argsPanel);
            getContentPane().add(areaPanel);
            circlePanel.setVisible(true);
            argsPanel.setVisible(true);
            areaPanel.setVisible(true);
        });
    }
}
