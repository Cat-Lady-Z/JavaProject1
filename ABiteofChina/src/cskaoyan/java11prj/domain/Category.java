package cskaoyan.java11prj.domain;

/**
 * Created with IntelliJ IDEA.
 * Description: 商品类别的类
 * User:  张娅迪
 * Date: 2018/11/07
 * Time: 下午 7:45
 * Detail requirement:
 * Method:
 */
public class Category {
    int cid;
    String cname;

    public Category() {
    }

    public Category(int cid, String cname) {
        this.cid = cid;
        this.cname = cname;
    }

    @Override
    public String toString() {
        return "Category{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                '}';
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}