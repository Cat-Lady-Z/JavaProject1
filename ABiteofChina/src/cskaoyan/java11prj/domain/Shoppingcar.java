package cskaoyan.java11prj.domain;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/15
 * Time: 上午 10:29
 * Detail requirement:
 * Method:
 */
public class Shoppingcar {
    int sid;
    int uid;
    List<Shoppingitem> shoppingitems;

    public Shoppingcar() {
    }

    public Shoppingcar(int sid, int uid, List<Shoppingitem> shoppingitems) {
        this.sid = sid;
        this.uid = uid;
        this.shoppingitems = shoppingitems;
    }

    @Override
    public String toString() {
        return "Shoppingcar{" +
                "sid=" + sid +
                ", uid=" + uid +
                ", shoppingitems=" + shoppingitems +
                '}';
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public List<Shoppingitem> getShoppingitems() {
        return shoppingitems;
    }

    public void setShoppingitems(List<Shoppingitem> shoppingitems) {
        this.shoppingitems = shoppingitems;
    }
}