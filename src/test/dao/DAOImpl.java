package test.dao;

import test.CircleEntity;
import test.util.DbConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Zhai Jinpei
 */
@SuppressWarnings("all")
public class DAOImpl implements MainDAO{
    private final Lock lock = new ReentrantLock();
    private static final DbConn conn = new DbConn();

    public DAOImpl() throws SQLException{
        if(checklogin()) System.out.println("connect");
        else {
            System.out.println("failed");
        }
    }


    @Override
    public boolean checklogin() throws SQLException{
        return conn.getConn(conn.getUrl())!=null;
    }

    @Override
    public void insert(CircleEntity circleEntity) throws SQLException{
        lock.lock();
        try{
                Connection con = conn.getConn(conn.getUrl());
                PreparedStatement preparedStatement = con.prepareStatement("insert into pirsrc values(?,?,?,?)");
                preparedStatement.setObject(1,circleEntity.getN());
                preparedStatement.setObject(2,circleEntity.getDr());
                preparedStatement.setObject(3,circleEntity.getArea());
                preparedStatement.setObject(4,circleEntity.getMis());
                preparedStatement.executeUpdate();
                preparedStatement.close();
                con.close();
        }finally{
            lock.unlock();
        }
    }

    @Override
    public Object[][] selectALL() throws SQLException{
        lock.lock();
        try{
            Connection con = conn.getConn(conn.getUrl());
            int i = 0;
            Object[][] objects = new Object[selectRawsCount()][5];
            System.out.println(selectRawsCount());
            PreparedStatement preparedStatement = con.prepareStatement("select * from pirsrc");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                objects[i][0] = i;
                objects[i][1] = resultSet.getString("N");
                objects[i][2] = resultSet.getString("dr");
                objects[i][3] = resultSet.getString("area");
                objects[i++][4] = resultSet.getString("mis");
            }
            for(int i1 = 0;i1 < objects.length;i1++){
                System.out.println(Arrays.toString(objects[i1]));
            }
            resultSet.close();
            preparedStatement.close();
            con.close();
            return objects;
        }finally{
            lock.unlock();
        }
    }
    @Override
    public int selectRawsCount() throws SQLException{
        lock.lock();
        try{
                Connection con = conn.getConn(conn.getUrl());
                int count = 0;
                int r =0;
                PreparedStatement preparedStatement = con.prepareStatement("select count(*) from pirsrc");
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next())r = resultSet.getInt(1);
                resultSet.close();
                preparedStatement.close();
                con.close();
                return r;
        }finally{
            lock.unlock();
        }
    }

    @Override
    public void deleteAll(){
        lock.lock();
        try{
                Connection con = conn.getConn(conn.getUrl());
                PreparedStatement preparedStatement = con.prepareStatement("delete from pirsrc p");
                preparedStatement.executeUpdate();
                preparedStatement.close();
                con.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }finally{
            lock.unlock();
        }
    }
}