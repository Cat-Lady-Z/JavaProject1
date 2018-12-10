package cskaoyan.java11prj.service;

import cskaoyan.java11prj.domain.Product;
import cskaoyan.java11prj.domain.Shoppingitem;
import cskaoyan.java11prj.util.Page;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/09
 * Time: 下午 4:16
 * Detail requirement:
 * Method:
 */
public interface ProductService {
    Boolean addProduct(Product product) throws SQLException; //增加商品
    Boolean deleteProduct(Product product) throws SQLException; //修改商品
    Boolean updateProduct(Product product) throws SQLException;  //删除商品
    Product findProduct(String name) throws SQLException; //根据商品名称查询商品
    List<Product> findAllProduct(String num) throws SQLException; //查询所有商品
    Page<Product> findPageProduct(String num) throws SQLException;

    Product findProductByPid(String pid) throws SQLException;

    Boolean deleteProductByPid(String pid) throws SQLException;

    Page<Product> multiConditionSearchProduct(String pid, String cid, String pname, String minprice, String maxprice) throws SQLException;

    List<Product> findTopProducts() throws SQLException;

    List<Product> findHotProducts() throws SQLException;

    Page<Product> findProductsByName(String pname,String num) throws SQLException,NumberFormatException;

    Page<Product> findProductByCid(String cid,String num) throws SQLException,NumberFormatException;

    boolean reducePnumByPid(List<Shoppingitem> shoppingitems) throws SQLException;
}
