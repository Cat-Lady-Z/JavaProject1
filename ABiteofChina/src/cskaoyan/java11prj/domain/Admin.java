package cskaoyan.java11prj.domain;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/11
 * Time: 下午 10:33
 * Detail requirement:
 * Method:
 */
public class Admin {
    int aid;
    String username;
    String  password;

    public Admin() {
    }

    public Admin(int aid, String username, String password) {
        this.aid = aid;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "admin{" +
                "aid=" + aid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}