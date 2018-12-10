package cskaoyan.java11prj.service.impl;

import cskaoyan.java11prj.dao.impl.UserDaoImpl;
import cskaoyan.java11prj.domain.User;
import cskaoyan.java11prj.service.UserService;
import cskaoyan.java11prj.util.MD5Util;

import java.sql.SQLException;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/13
 * Time: 下午 8:06
 * Detail requirement:
 * Method:
 */
public class UserServiceImpl implements UserService {
    UserDaoImpl userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) throws SQLException {
        //判断用户名是否存在，password是否与数据库存在的对应的用户密码是否一致
        if (username == null || "".equals(username) || password == null || "".equals(password))
            return null;
        String activecode = userDao.getActivecodeByUsername(username);
        String passwordParam = password + username + activecode;
        password = MD5Util.encrypt(passwordParam);
        User userByName = userDao.findUserByName(username);
        if (userByName != null){
            if (password.equals(userByName.getPassword()))
                return userByName;
        }
        return null;
    }

    @Override
    public Boolean regist(User user) throws SQLException {
        String username = user.getUsername();
        String password = user.getPassword();
        if (username == null || "".equals(username) || password == null || "".equals(password))
            return false;

        return userDao.addUser(user);
    }

    @Override
    public Boolean updateUser(User user) throws SQLException {
        String username = user.getUsername();
        String password = user.getPassword();

        UUID uuid = UUID.randomUUID();
        String activecode = uuid.toString();
        String passwordParam = password + username + activecode;
        password = MD5Util.encrypt(passwordParam);

        user.setPassword(password);
        user.setActivecode(activecode);

        if (username == null || "".equals(username) || password == null || "".equals(password))
            return false;
        return userDao.updateUser(user);
    }

    @Override
    public boolean isUserUsernameAvailable(String username) throws SQLException {
        if (username != null && !"".equals(username)){
            User userByName = userDao.findUserByName(username);
            if (userByName == null){
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isUserEmailAvailable(String email) throws SQLException {
        if (email != null && !"".equals(email)){
            User userByEmail = userDao.findUserByEmail(email);
            if (userByEmail == null){
                return true;
            }
        }

        return false;
    }

    @Override
    public User findUserByName(String username) throws SQLException {
        return userDao.findUserByName(username);
    }

    @Override
    public User isActivecodeExist(String activecode) throws SQLException {
        return userDao.findUserByActivecode(activecode);
    }
}
