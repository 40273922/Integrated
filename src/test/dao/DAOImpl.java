package test.dao;

import test.CircleEntity;
import test.util.DbConn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Zhai Jinpei
 */
@SuppressWarnings("all")
public class DAOImpl implements MainDAO{
    private final Lock lock = new ReentrantLock();
    DbConn conn = new DbConn();
    @Override
    public boolean checklogin() throws SQLException{
        return conn.getConn(conn.getUrl())!=null;
    }

    @Override
    public void insert(CircleEntity circleEntity) throws SQLException{
        lock.lock();
        try{
            if(checklogin()){
                PreparedStatement preparedStatement = conn.getPs("insert into pirsrc values(?,?,?,?)");
                preparedStatement.setObject(1,circleEntity.getN());
                preparedStatement.setObject(2,circleEntity.getDr());
                preparedStatement.setObject(3,circleEntity.getArea());
                preparedStatement.setObject(4,circleEntity.getMis());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
        }finally{
            lock.unlock();
        }
    }

    @Override
    public Object[][] selectALL() throws SQLException{
        lock.lock();
        try{
            if(checklogin()){
                int i = 0;
                Object[][] objects = new Object[selectRawsCount()][5];
                PreparedStatement preparedStatement = conn.getPs("select *from pirsrc");
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()) {
                    objects[i][0]=i;
                    objects[i][1]=resultSet.getString("N");
                    objects[i][2]=resultSet.getString("dr");
                    objects[i][3]=resultSet.getString("area");
                    objects[i++][4]=resultSet.getString("mis");
                }
                resultSet.close();
                preparedStatement.close();
                return objects;
            }
        }finally{
            lock.unlock();
        }
        return null;
    }

    @Override
    public int selectRawsCount() throws SQLException{
        lock.lock();
        try{
            if(checklogin()){
                int count = 0;
                PreparedStatement preparedStatement = conn.getPs("select count(*) from pirsrc");
                ResultSet resultSet = preparedStatement.executeQuery();
                int r =0;
                while(resultSet.next())r = resultSet.getInt(1);
                resultSet.close();
                preparedStatement.close();
                return r;
            }
        }finally{
            lock.unlock();
        }
        return 0;
    }

    @Override
    public void deleteAll(){
        lock.lock();
        try{
            if(checklogin()){
                PreparedStatement preparedStatement = conn.getPs("delete from pirsrc p");
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }finally{
            lock.unlock();
        }
    }
}