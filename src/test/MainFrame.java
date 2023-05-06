package test;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubDarkContrastIJTheme;
import test.dao.DAOImpl;
import test.dao.MainDAO;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Zhai Jinpei
 */
public class MainFrame extends JFrame{
    static class Circle{
        static final double PI = Math.PI;
        static final double R = 1;
        static final double C = 2 * PI * R;
        double N = 1;
        double dR = R / N;
        double dS = 2 * PI * dR * dR;
        double dC = 2 * PI * dR;
        double AREAR = dS * (1 + N) * N / 2;

        Circle setN(double n){
            N = n;
            dR = R / N;
            dC = 2 * PI * dR;
            dS = 2 * PI * dR * dR;
            AREAR = dS * (1 + N) * N / 2;
            return this;
        }
    }

    Circle circle = new Circle();
    MainDAO dao = new DAOImpl();
    public static final double pi = 3.1415926535897932;
    private static final List<CircleEntity> circleEntity = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws SQLException{
        new MainFrame();
    }

    public MainFrame() throws SQLException{
        SwingUtilities.invokeLater(()->{
            try{
                UIManager.setLookAndFeel(new FlatGitHubDarkContrastIJTheme());
            }catch(UnsupportedLookAndFeelException e){
                throw new RuntimeException(e);
            }
            setTitle("定积分计算圆面积小组项目演示");
            setSize(Toolkit.getDefaultToolkit().getScreenSize());
            setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            JPanel circlePanel = new JPanel(){
                @Override
                protected void paintComponent(Graphics g){
                    super.paintComponent(g);
                    g.setColor(Color.cyan);
                    g.drawLine(10,(int)(150 + 100 * 2.4),(int)(100 * 4.8 + 10),(int)(150 + 100 * 2.4));
                    g.drawLine((int)(10 + 100 * 2.4),(int)(150),(int)(10 + 100 * 2.4),(int)(150 + 100 * 4.8));
                    g.setColor(Color.red);
                    for(int i = 0;i <= circle.N;i++)
                        g.drawOval((int)(10 + circle.dR * 120 * i * 2.4),(int)(150 + circle.dR * 120 * i * 2.4),2 * (int)(100 * 2.4 - circle.dR * 120 * i * 2.4),2 * (int)(100 * 2.4 - circle.dR * 120 * i * 2.4));
                }
            };
            JPanel argsPanel = new JPanel();
            JPanel areaPanel = new JPanel(){
                @Override
                protected void paintComponent(Graphics g){
                    super.paintComponent(g);
                    g.drawLine(50,30,50,(int)Circle.C * 120 + 50);
                    g.drawLine(50,(int)Circle.C * 120 + 50,(int)(50 + 120 * 3.8),(int)Circle.C * 120 + 50);
                    g.drawLine((int)(50 + 120 * 3.8),30,(int)(50 + 120 * 3.8),(int)Circle.C * 120 + 50);
                    g.setColor(Color.cyan);
                    g.drawLine(50,(int)Circle.C * 120 + 50,(int)(50 + 120 * 3.8),30);
                    for(int i = 0;i <= circle.N;i++){
                        if(i == 0) g.setColor(Color.red);
                        else g.setColor(Color.WHITE);
                        g.drawLine((int)(50 + circle.dR * i * 120 * 3.8),(int)(Circle.C * 120) - (int)(circle.dC * 120 * i) + 20,(int)(50 + circle.dR * (i + 1) * 120 * 3.8),(int)(Circle.C * 120) - (int)(circle.dC * 120 * i) + 20);
                        g.drawRect((int)(50 + circle.dR * i * 120 * 3.8),(int)(Circle.C * 120) - (int)(circle.dC * 120 * i) + 20,(int)(circle.dR * 120 * 3.8),(int)(circle.dC * 120 * i) - 3);
                    }
                }
            };
            JLabel N = new JLabel("请滑动N值标尺 或 按下END键(右方向键)=");
            JSlider jSlider = new JSlider(0,800);
            jSlider.setPaintTicks(true);
            jSlider.setMajorTickSpacing(80);
            jSlider.setMinorTickSpacing(8);
            jSlider.setPaintTrack(true);
            jSlider.setPaintLabels(true);
            jSlider.setOrientation(SwingConstants.HORIZONTAL);
            JLabel tip = new JLabel("重要参数列表");
            JTextField textField = new JTextField("对参数求值的结果");
            textField.setEditable(false);
            JLabel NN = new JLabel("N =");
            JTextField NNt = new JTextField();
            NNt.setEditable(false);
            JLabel AREALabel = new JLabel("积分后的近似面积=");
            JTextField AREA = new JTextField();
            AREA.setEditable(false);
            JLabel R = new JLabel("单位圆半径R=");
            JTextField Rfield = new JTextField();
            Rfield.setText("1");
            Rfield.setEditable(false);
            JLabel EA = new JLabel("单位圆的准确面积(πR^2)=");
            JTextField EAfield = new JTextField(String.valueOf(pi));
            EAfield.setEditable(false);
            JLabel dr = new JLabel("dR(R的微分)=R/N=");
            JTextField drfield = new JTextField(String.valueOf(circle.dR));
            drfield.setEditable(false);
            JLabel ex = new JLabel("相对面积误差=");
            JTextField exfield = new JTextField();
            exfield.setEditable(false);
            jSlider.addChangeListener(e->{
                int n = jSlider.getValue();
                double mis = circle.setN(n).AREAR - pi;
                if(n == 0) circleEntity.add(new CircleEntity(n,0.0d,0.0d,0.0d));
                else circleEntity.add(new CircleEntity(n,circle.dR,circle.AREAR,mis));
                NNt.setText(String.valueOf(n));
                AREA.setText(String.valueOf(circle.AREAR));
                exfield.setText(String.valueOf(mis));
                drfield.setText(String.valueOf(circle.dR));
                circlePanel.repaint();
                areaPanel.repaint();
            });
            jSlider.addKeyListener(new KeyListener(){
                @Override
                public void keyTyped(KeyEvent e){
                }

                @Override
                public void keyPressed(KeyEvent e){
                    if(e.getKeyCode() == 35) jSlider.setValue(jSlider.getValue() + 1);
                    if(e.getKeyCode() == 36) jSlider.setValue(jSlider.getValue() - 1);
                }

                @Override
                public void keyReleased(KeyEvent e){
                }
            });
            JMenuBar jMenuBar = new JMenuBar();
            JMenuBar jMenuBar2 = new JMenuBar();
            JMenu jMenu = new JMenu("选项");
            JMenu jMenu2 = new JMenu("关于");
            JMenu jMenu3 = new JMenu("数据");
            JMenuItem jMenuItem = new JMenuItem("数据导入mysql");
            JMenuItem jMenuItem1 = new JMenuItem("查看数据表格");
            jMenuItem1.addActionListener(e->{
                try{
                    new TableFrame();
                }catch(SQLException exc){
                    throw new RuntimeException(exc);
                }
            });
            JMenuItem jMenuItem7 = new JMenuItem("清空表数据");
            jMenuItem7.addActionListener(e->{
                dao.deleteAll();
                JOptionPane.showMessageDialog(this,"已清空!");
            });
            jMenu3.add(jMenuItem1);
            jMenu3.add(jMenuItem7);
            jMenuItem.addActionListener(e->new Thread(()->{
                AtomicInteger i = new AtomicInteger();
                ProcessBarWindow processBarWindow = new ProcessBarWindow();
                circleEntity.forEach(Ent->{
                    String info = "N:" + Ent.getN() + ",getNdr:" + Ent.getDr() + ",Area:" + Ent.getArea() + ",Mismatch:" + Ent.getMis();
                    System.out.println(info);
                    i.getAndIncrement();
                    processBarWindow.jProgressBar.setValue(800 * i.intValue() / circleEntity.size());
                    try{
                        dao.insert(Ent);
                    }catch(SQLException exc){
                        throw new RuntimeException(exc);
                    }
                });
                processBarWindow.dispose();
                JOptionPane.showMessageDialog(this,"导入成功!");
            }).start());
            JMenuItem jMenuItem2 = new JMenuItem("退出界面");
            JMenuItem jMenuItem3 = new JMenuItem("作者");
            jMenuItem3.addActionListener(e->JOptionPane.showMessageDialog(this," "));
            JMenuItem jMenuItem4 = new JMenuItem("小组成员");
            jMenuItem4.addActionListener(e->JOptionPane.showMessageDialog(this," "));
            JMenuItem jMenuItem5 = new JMenuItem("源代码链接");
            jMenuItem5.addActionListener(e->{
                Desktop desktop = Desktop.getDesktop();
                try{
                    URI uri = new URI("https://github.com/40273922/Integrated/tree/master/src/test");
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
            jMenu.add(jMenuItem);
            jMenu.add(jMenuItem2);
            jMenu2.add(jMenuItem3);
            jMenu2.add(jMenuItem4);
            jMenu2.add(jMenuItem5);
            jMenu2.add(jMenuItem6);
            jMenuBar.add(jMenu);
            jMenuBar.add(jMenu2);
            jMenuBar.add(jMenu3);
            argsPanel.setLayout(new GridLayout(10,2));
            argsPanel.add(jMenuBar);
            argsPanel.add(jMenuBar2);
            argsPanel.add(N);
            argsPanel.add(jSlider);
            argsPanel.add(tip);
            argsPanel.add(textField);
            argsPanel.add(NN);
            argsPanel.add(NNt);
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
            JLabel y = new JLabel("Y:2πR ");
            JLabel x = new JLabel("X:R");
            JLabel e = new JLabel(" Y轴误差<πE-3");
            y.setForeground(Color.red);
            x.setForeground(Color.red);
            e.setForeground(Color.red);
            areaPanel.add(y);
            areaPanel.add(x);
            areaPanel.add(e);
            argsPanel.setBorder(new TitledBorder(new EtchedBorder(),"参数显示面板"));
            circlePanel.setBorder(new TitledBorder(new EtchedBorder(),"单位圆对半径进行微分示意图"));
            areaPanel.setBorder(new TitledBorder(new EtchedBorder(),"微元法积分坐标轴直观图"));
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

