package cskaoyan.java11prj.domain;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/15
 * Time: 上午 9:47
 * Detail requirement:
 * Method:
 */
public class OrderItem {
    int itemid;
    String oid;
    String pid;
    int buynum;
    Order order;
    Product product;

    public OrderItem() {
    }

    public OrderItem(int itemid, String oid, String pid, int buynum, Order order, Product product) {
        this.itemid = itemid;
        this.oid = oid;
        this.pid = pid;
        this.buynum = buynum;
        this.order = order;
        this.product = product;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getBuynum() {
        return buynum;
    }

    public void setBuynum(int buynum) {
        this.buynum = buynum;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}