package cskaoyan.java11prj.dao;

import cskaoyan.java11prj.domain.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/09
 * Time: 上午 10:28
 * Detail requirement:
 * Method:
 */
public interface ProductDao {
    Boolean addProduct(Product product) throws SQLException; //增加商品
    Boolean deleteProduct(Product product) throws SQLException; //修改商品
    Boolean updateProduct(Product product) throws SQLException;  //删除商品
    Product findProduct(String name) throws SQLException; //根据商品名称查询商品
    List<Product> findAllProduct() throws SQLException; //查询所有商品

    int findTotalNumber() throws SQLException;

    List<Product> findOnePageProduct(int limit, int offset) throws SQLException;

    Product findProductByPid(String pid) throws SQLException,NumberFormatException;

    Boolean deleteProductByPid(String pid) throws SQLException;

    List<Product> multiConditionSearchProduct(String pid, int cid, String pname, String minprice, String maxprice) throws SQLException,NumberFormatException;

    List<Product> findTopProducts() throws SQLException;

    List<Product> findHotProducts() throws SQLException;

    List<Product> findProductsByName(String pname,int limit, int offset) throws SQLException;

    List<Product> findProductsByCid(int cid,int limit, int offset) throws SQLException;

    int findTotalNumberByCid(int cid) throws SQLException;

    boolean updateProductPnumByPid(String pid, int snum) throws SQLException;
}
