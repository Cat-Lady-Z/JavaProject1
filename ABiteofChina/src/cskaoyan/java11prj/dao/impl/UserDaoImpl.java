package cskaoyan.java11prj.dao.impl;

import cskaoyan.java11prj.dao.UserDao;
import cskaoyan.java11prj.domain.User;
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
 * Date: 2018/11/13
 * Time: 下午 5:24
 * Detail requirement:
 * Method:
 */
public class UserDaoImpl implements UserDao {
    @Override
    public boolean addUser(User user) throws SQLException {
        String username = user.getUsername();
        if (user == null || username == null || "".equals(username))
            return false;

        String password = user.getPassword();
        String nickname = user.getNickname();
        String email = user.getEmail();
        String updatetime = user.getUpdatetime();
        String birthday = user.getBirthday();
        String activecode = user.getActivecode();

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        int isSuccess = queryRunner.update("insert into `user` values (null,?,?,?,?,?,?,0,?);", username,
                nickname,password,email,birthday,updatetime,activecode);
        if (isSuccess > 0)
            return true;

        return false;
    }

    @Override
    public Boolean updateUser(User user) throws SQLException {
        if (user == null)
            return false;

        int uid = user.getUid();
        String password = user.getPassword();
        String nickname = user.getNickname();
        String email = user.getEmail();
        String birthday = user.getBirthday();
        String activecode = user.getActivecode();

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        int isSuccess = queryRunner.update("update `user` set `nickname`=?,`password`=?,`email`=?,`birthday`=?," +
                        "`activecode`=?  where `uid`=?;", nickname,password,email,birthday,activecode, uid);
        if (isSuccess > 0)
            return true;

        return false;
    }

    @Override
    public List<User> findAllUser() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        List<User> users = queryRunner.query("select *from `user`;", new BeanListHandler<User>(User.class));
        return users;
    }

    @Override
    public int findTotalNumber() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        Long query = (Long)queryRunner.query("select count(*) from `user`;",
                new ScalarHandler());
        return query.intValue();
    }

    @Override
    public List<User> findOnePageUser(int limit, int offset) {
        return null;
    }

    @Override
    public User findUserByName(String username) throws SQLException {
        if (username == null || "".equals(username))
            return null;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        User user = queryRunner.query("select *from `user` where `username`=?;", new BeanHandler<User>(User.class),username);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws SQLException {
        if (email == null || "".equals(email))
            return null;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        User user = queryRunner.query("select *from `user` where `email`=?;", new BeanHandler<User>(User.class),email);
        return user;
    }

    @Override
    public User findUserByUid(int uid) throws SQLException {
        if (uid <= 0 )
            return null;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        User user = queryRunner.query("select *from `user` where `uid`=?;", new BeanHandler<User>(User.class),uid);
        return user;
    }

    @Override
    public User findUserByActivecode(String activecode) throws SQLException {
        if (activecode == null || "".equals(activecode) )
            return null;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        User user = queryRunner.query("select *from `user` where `activecode`=?;", new BeanHandler<User>(User.class),activecode);
        return user;
    }

    @Override
    public String getActivecodeByUsername(String username) throws SQLException {
        if (username == null || "".equals(username) )
            return null;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        User user = queryRunner.query("select *from `user` where `username`=?;", new BeanHandler<User>(User.class),username);
        return user.getActivecode();
    }
}