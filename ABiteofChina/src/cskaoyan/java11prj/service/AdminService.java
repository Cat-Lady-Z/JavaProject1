package cskaoyan.java11prj.service;

import cskaoyan.java11prj.domain.Admin;
import cskaoyan.java11prj.util.Page;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/11
 * Time: 下午 10:55
 * Detail requirement:
 * Method:
 */
public interface AdminService {
    Admin login(String username, String password) throws SQLException;   //用于管理员登录

    boolean addAdmin(Admin admin) throws SQLException;

    Boolean deleteAdmin(int aid) throws SQLException;

    Boolean updateAdmin(Admin admin) throws SQLException;

    List<Admin> findAllAdmin() throws SQLException;

    int findTotalNumber() throws SQLException;

    Page<Admin> findPageAdmin(String num) throws SQLException;


    Admin findAdmin(String username, String password) throws SQLException;
}
