package test.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Zhai Jinpei
 */
@SuppressWarnings("all")
public class DbConn{
    private String url = "jdbc:mysql:///tutorials?user=root&password=40273939zjpzjp";
    public Connection getConn(String url) throws SQLException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            throw new RuntimeException(e);
        }
        return DriverManager.getConnection(url);
    }
    @Deprecated
    public PreparedStatement getPs(String sql) throws SQLException{
        return getConn(url).prepareStatement(sql);
    }
    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url = url;
    }
}