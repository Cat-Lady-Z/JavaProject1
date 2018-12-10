package cskaoyan.java11prj.domain;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/15
 * Time: 上午 10:30
 * Detail requirement: 购物车列表项
 * Method:
 */
public class Shoppingitem {
    int itemid;
    int sid;
    String  pid;
    int snum;
    Product product;

    public Shoppingitem() {
    }

    public Shoppingitem(int itemid, int sid, String pid, int snum, Product product) {
        this.itemid = itemid;
        this.sid = sid;
        this.pid = pid;
        this.snum = snum;
        this.product = product;
    }

    @Override
    public String toString() {
        return "Shoppingitem{" +
                "itemid=" + itemid +
                ", sid=" + sid +
                ", pid='" + pid + '\'' +
                ", snum=" + snum +
                ", product=" + product +
                '}';
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getSnum() {
        return snum;
    }

    public void setSnum(int snum) {
        this.snum = snum;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}