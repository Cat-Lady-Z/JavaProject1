package test;

import cskaoyan.java11prj.dao.impl.UserDaoImpl;
import cskaoyan.java11prj.domain.User;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/13
 * Time: 下午 6:00
 * Detail requirement:
 * Method:
 */
public class TestUserDao {
    UserDaoImpl userDao = new UserDaoImpl();

    @Test
    public void testAdd() throws SQLException {

        User user = new User();
        boolean isSuccess = userDao.addUser(user);
        Assert.assertFalse(isSuccess);
    }

    @Test
    public void testUpdate() throws SQLException {
        User user = new User();

        Boolean aBoolean = userDao.updateUser(null);
        Assert.assertFalse(aBoolean);
    }

    @Test
    public void testFindByName() throws SQLException {
        String name = "";
        String name1 = "ggg";

        User userByName = userDao.findUserByName(null);

        Assert.assertNull(userByName);
    }

    @Test
    public void testFindById() throws SQLException {
        int uid = 0;
        int uid1;

        User userByName = userDao.findUserByUid(0);

        Assert.assertNull(userByName);
    }

    @Test
    public void testFindBye() throws SQLException {
        String E = "";
        String E1 = "111";

        User userByE = userDao.findUserByEmail(E1);

        Assert.assertNull(userByE);
    }
}