package test.dao;

import test.CircleEntity;

import java.sql.SQLException;

/**
 * @author Zhai Jinpei
 */
public interface MainDAO {
    boolean checklogin() throws SQLException;
    void insert(CircleEntity circle) throws SQLException;
    Object[][] selectALL() throws SQLException;
    int selectRawsCount() throws SQLException;
    void deleteAll();
}
