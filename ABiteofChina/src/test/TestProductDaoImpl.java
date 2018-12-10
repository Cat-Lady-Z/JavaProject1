package test;

import cskaoyan.java11prj.dao.impl.ProductDaoImpl;
import cskaoyan.java11prj.domain.Product;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/09
 * Time: 下午 3:01
 * Detail requirement:
 * Method:
 */
public class TestProductDaoImpl {
    @Test
    public void testAdd(){
        ProductDaoImpl productDao = new ProductDaoImpl();
//        Product product = new Product();
        //int pid, String pname, String estoreprice, String markprice, String pnum, String cid, String imgurl, String desc
        //Product product = new Product(1,"20","3","4","5","14","7","14");

        Boolean isSuccess = false;
        /*try {
            isSuccess = productDao.addProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        Assert.assertTrue(isSuccess);
    }

    @Test
    public void testdelete(){
        ProductDaoImpl productDao = new ProductDaoImpl();
//        Product product = new Product();
        //int pid, String pname, String estoreprice, String markprice, String pnum, String cid, String imgurl, String desc
        /*Product product = new Product(1,"20","3","4","5","14","7","8");

        Boolean isSuccess = false;
        try {
            isSuccess = productDao.deleteProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(isSuccess);*/
    }

    @Test
    public void testUpdate(){
        ProductDaoImpl productDao = new ProductDaoImpl();
        Product product = new Product();
        //int pid, String pname, String estoreprice, String markprice, String pnum, String cid, String imgurl, String desc
        //Product product = new Product(1,"大猪蹄子","3","4","5","14","7","14");

        Boolean isSuccess = false;
        try {
            isSuccess = productDao.updateProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertFalse(isSuccess);
    }

    @Test
    public void testFindOne(){
        ProductDaoImpl productDao = new ProductDaoImpl();
        //String pname="";
        String pname="大猪蹄子";

        Product product = null;
        try {
            product = productDao.findProduct(pname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(product);
    }

    @Test
    public void testFindAll(){
        ProductDaoImpl productDao = new ProductDaoImpl();

        List<Product> productList = null;
        try {
            productList = productDao.findAllProduct();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(productList);
    }
}