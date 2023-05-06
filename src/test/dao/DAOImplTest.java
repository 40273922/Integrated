package test.dao;

import org.junit.jupiter.api.Test;
import test.CircleEntity;

import java.sql.SQLException;

class DAOImplTest{
    private final DAOImpl dao = new DAOImpl();
    @org.junit.jupiter.api.Test
    void checklogin() throws SQLException{
        System.out.println(dao.checklogin());
    }
    @org.junit.jupiter.api.Test
    void insert() throws SQLException{
        dao.insert(new CircleEntity(1,2d,3d,4d));
    }
}