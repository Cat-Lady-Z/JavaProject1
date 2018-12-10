package cskaoyan.java11prj.dao;

import cskaoyan.java11prj.domain.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/13
 * Time: 下午 5:18
 * Detail requirement:
 * Method:
 */
public interface UserDao {
    boolean addUser(User user) throws SQLException;

    Boolean updateUser(User user) throws SQLException;

    List<User> findAllUser() throws SQLException;

    int findTotalNumber() throws SQLException;

    List<User> findOnePageUser(int limit, int offset);

    User findUserByName(String username) throws SQLException;

    User findUserByEmail(String email) throws SQLException;

    User findUserByUid(int uid) throws SQLException;

    User findUserByActivecode(String activecode) throws SQLException;

    String getActivecodeByUsername(String username) throws SQLException;
}
