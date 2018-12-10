package cskaoyan.java11prj.service;

import cskaoyan.java11prj.domain.User;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/13
 * Time: 下午 8:05
 * Detail requirement:
 * Method:
 */
public interface UserService {
    User login(String username, String password) throws SQLException; // 用于用户登录
    Boolean regist(User user) throws SQLException;  //用于用户注册
    Boolean updateUser(User user) throws SQLException;   //用于用户信息修改

    boolean isUserUsernameAvailable(String username) throws SQLException;

    boolean isUserEmailAvailable(String email) throws SQLException;

    User findUserByName(String username) throws SQLException;

    User isActivecodeExist(String activecode) throws SQLException;

    //TODO
}
