package test;

import cskaoyan.java11prj.dao.impl.AdminDaoImpl;
import cskaoyan.java11prj.domain.Admin;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/12
 * Time: 上午 10:42
 * Detail requirement:
 * Method:
 */
public class TestAdminDao {
    AdminDaoImpl adminDao = new AdminDaoImpl();

    @Test
    public void testadd() throws SQLException {
        //Admin admin = new Admin();
        Admin admin = new Admin(3,"lalala","76");

        boolean isOk = adminDao.addAdmin(admin);
        Assert.assertFalse(isOk);
    }

    @Test
    public void testDelete() throws SQLException {
        //Admin admin = new Admin();
        //Admin admin = new Admin(3,"lalala","76");

        boolean isOk = adminDao.deleteAdmin(0);
        Assert.assertFalse(isOk);
    }

    @Test
    public void testupdate() throws SQLException {
       // Admin admin = new Admin();
        //Admin admin = new Admin(3,"lalala","76");
        Admin admin = new Admin(3,null,"");

        boolean isOk = adminDao.updateAdmin(admin);
        Assert.assertFalse(isOk);
    }
    @Test
    public void testFindAllAdmin() throws SQLException {
        // Admin admin = new Admin();
        //Admin admin = new Admin(3,"lalala","76");
        Admin admin = new Admin(3,null,"");

        List<Admin> adminList = adminDao.findAllAdmin();
        Assert.assertNotNull(adminList);
    }

    @Test
    public void testFindTotalNumber() throws SQLException {
        // Admin admin = new Admin();
        //Admin admin = new Admin(3,"lalala","76");
        Admin admin = new Admin(3,null,"");

        int totalNumber = adminDao.findTotalNumber();
        Assert.assertNotNull(totalNumber);
    }

    @Test
    public void testfindOnePageAdmin() throws SQLException {
        int limit = 10;
        int offset = 1;

        List<Admin> onePageAdmin = adminDao.findOnePageAdmin(limit, offset);
        Assert.assertNotNull(onePageAdmin);
    }
}