package test.dao;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import test.CircleEntity;

import java.sql.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Zhai Jinpei
 */
public class DAOImpl{
    private final Lock lock = new ReentrantLock();
    private static Session session = null;
    private static Connection connection = null;

    public static Connection sd(){
        try{
            JSch jsch = new JSch();
            String detionnation_name = "root";
            String detionnation_host = "8.130.86.187";
            int detionnation_port = 22;
            session = jsch.getSession(detionnation_name,detionnation_host,detionnation_port);
            String detionnation_pwd = "111111aA";
            session.setPassword(detionnation_pwd);
            session.setConfig("StrictHostKeyChecking","no");
            session.connect();
            int forward_mysql_port = 22;
            String mysql_host = "127.0.0.1";
            int mysql_port = 3306;
            int bindPort = session.setPortForwardingL("localhost",forward_mysql_port,mysql_host,mysql_port);
            String mysql_database = "tutorials";
            String mysql_user = "root";
            String mysql_pwd = "admin";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:" + bindPort + "/" + mysql_database + "?useSSL=false&autoReconnect=true",mysql_user,mysql_pwd);
            if(connection.isValid(3)){
                System.out.println("************数据库连接成功***********");
                return connection;
            }else{
                System.out.println("************数据库连接失败***********");
                return connection;
            }
        }catch(SQLException | JSchException e){
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void insert(CircleEntity circleEntity) throws SQLException{
        lock.lock();
        try{
            Connection con = sd();
            PreparedStatement preparedStatement = con.prepareStatement("insert into pirsrc values(?,?,?,?)");
            preparedStatement.setObject(1,circleEntity.getN());
            preparedStatement.setObject(2,circleEntity.getDr());
            preparedStatement.setObject(3,circleEntity.getArea());
            preparedStatement.setObject(4,circleEntity.getMis());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();
            session.disconnect();
        }finally{
            lock.unlock();
        }
    }

    public Object[][] selectALL(int n) throws SQLException{
        lock.lock();
        try{
            Connection con = sd();
            int i = 0;
            Object[][] objects = new Object[n][5];
            PreparedStatement preparedStatement = con.prepareStatement("select *from pirsrc p");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                objects[i][0] = i;
                objects[i][1] = resultSet.getString("N");
                objects[i][2] = resultSet.getString("dr");
                objects[i][3] = resultSet.getString("area");
                objects[i++][4] = resultSet.getString("mis");
            }
            resultSet.close();
            preparedStatement.close();
            con.close();
            session.disconnect();
            return objects;
        }finally{
            lock.unlock();
        }
    }

    public int selectRawsCount() throws SQLException{
        lock.lock();
        try{
            Connection con = sd();
            int r = 0;
            PreparedStatement preparedStatement = con.prepareStatement("select count(*) from pirsrc p ");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) r = resultSet.getInt(1);
            resultSet.close();
            preparedStatement.close();
            con.close();
            session.disconnect();
            return r;
        }finally{
            lock.unlock();
        }
    }

    public void deleteAll(){
        lock.lock();
        try{
            Connection con = sd();
            PreparedStatement preparedStatement = con.prepareStatement("truncate table tutorials.pirsrc");
            preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();
            session.disconnect();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }finally{
            lock.unlock();
        }
    }
}