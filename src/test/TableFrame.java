package test;

import test.dao.DAOImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

/**
 * @author Zhai Jinpei
 */
public class TableFrame extends JFrame{
    JScrollPane jPanel = new JScrollPane();
    JTable jTable = new JTable();
    DAOImpl dao = new DAOImpl();

    public TableFrame() throws SQLException{
        SwingUtilities.invokeLater(()->{
            setTitle("data recording table");
            setSize(Toolkit.getDefaultToolkit().getScreenSize());
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setVisible(true);
            jPanel.setToolTipText("数据记录表");
            jPanel.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            String[] tip = new String[]{"序号","N","dr","近似面积","误差"};
            try{
                jTable.setModel(new DefaultTableModel(dao.selectALL(dao.selectRawsCount()),tip));
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
            jPanel.add(jTable);
            jPanel.setViewportView(jTable);
            getContentPane().add(jPanel);
            jPanel.setVisible(true);
        });

    }
}
