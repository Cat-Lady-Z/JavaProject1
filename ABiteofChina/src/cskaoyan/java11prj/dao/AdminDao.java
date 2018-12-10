package cskaoyan.java11prj.dao;

import cskaoyan.java11prj.domain.Admin;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/11
 * Time: 下午 10:28
 * Detail requirement:
 * Method:
 */
public interface AdminDao {
    boolean addAdmin(Admin admin) throws SQLException;

    Boolean deleteAdmin(int aid) throws SQLException;

    Boolean updateAdmin(Admin admin) throws SQLException;

    List<Admin> findAllAdmin() throws SQLException;

    int findTotalNumber() throws SQLException;

    List<Admin> findOnePageAdmin(int limit, int offset) throws SQLException;

    Admin findOneAdmin(String username, String password) throws SQLException;
}
