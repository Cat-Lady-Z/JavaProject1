package cskaoyan.java11prj.dao.impl;

import cskaoyan.java11prj.dao.AdminDao;
import cskaoyan.java11prj.domain.Admin;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import cskaoyan.java11prj.util.C3P0Utils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/11
 * Time: 下午 10:48
 * Detail requirement:
 * Method:
 */
public class AdminDaoImpl implements AdminDao {
    @Override
    public boolean addAdmin(Admin admin) throws SQLException {
        if ("".equals(admin.getUsername()) || "null".equals(admin.getUsername()+"") || admin == null)
            return false;

        String username = admin.getUsername();
        String password = admin.getPassword();
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        int isSuccess = queryRunner.update("insert into `admin` (username,password) values (?,?);", username,password);
        if (isSuccess > 0)
            return true;

        return false;
    }

    @Override
    public Boolean deleteAdmin(int aid) throws SQLException {
        if (aid <= 0)
            return false;

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        //先判断cid在表里是否存在
        Admin query = queryRunner.query("select *from `admin` where `aid`=?;", new BeanHandler<Admin>(Admin.class), aid);
        if (query == null)
            return false;

        int isSuccess = queryRunner.update("delete from `admin` where aid=?;",aid);
        if (isSuccess > 0)
            return true;

        return false;
    }

    public Boolean updateAdmin(Admin admin) throws SQLException {
        if (admin == null)
            return false;

        int aid = admin.getAid();
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        Boolean isDeleteSuccess = deleteAdmin(aid);
        if (isDeleteSuccess){
            Boolean isSuccess = addAdmin(admin);
            return isSuccess;
        }

        return false;
    }

    @Override
    public List<Admin> findAllAdmin() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        List<Admin> adminList = queryRunner.query("select *from `admin`;",
                new BeanListHandler<Admin>(Admin.class));

        return adminList;
    }

    @Override
    public int findTotalNumber() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        Long query = (Long)queryRunner.query("select count(*) from `admin`;",
                new ScalarHandler());

        return query.intValue();
    }

    @Override
    public List<Admin> findOnePageAdmin(int limit, int offset) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        List<Admin> adminList = queryRunner.query("select *from `admin` limit ? offset ?;",
                new BeanListHandler<Admin>(Admin.class), limit, offset);
        if (adminList == null || adminList.size() == 0)
            return null;

        return adminList;
    }

    @Override
    public Admin findOneAdmin(String username, String password) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        Admin admin = queryRunner.query("select *from `admin` where `username`=? and `password`=?;", new BeanHandler<Admin>(Admin.class), username, password);
        return admin;
    }
}