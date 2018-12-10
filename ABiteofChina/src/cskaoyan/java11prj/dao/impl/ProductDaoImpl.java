package cskaoyan.java11prj.dao.impl;

import cskaoyan.java11prj.dao.ProductDao;
import cskaoyan.java11prj.domain.Category;
import cskaoyan.java11prj.domain.Product;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import cskaoyan.java11prj.util.C3P0Utils;

import java.sql.SQLException;
import java.util.ArrayList;
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
public class ProductDaoImpl implements ProductDao {
    @Override
    public Boolean addProduct(Product product) throws SQLException {
        //参数判断
        if (product == null)
            return false;

        String  pid = product.getPid();
        String pname = product.getPname();
        double estoreprice = product.getEstoreprice();
        double markprice = product.getMarkprice();
        int pnum = product.getPnum();
        int cid = product.getCid();
        String imgurl = product.getImgurl();
        String desc = product.getDesc();

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        int isSuccess = queryRunner.update("insert into product values (?,?,?,?,?,?,?,?);", pid,pname,estoreprice, markprice,pnum,cid,imgurl,desc);
        if (isSuccess > 0)
            return true;
        return false;
    }

    @Override
    public Boolean deleteProduct(Product product) throws SQLException {
        if (product == null)
            return false;

        String  pid = product.getPid();
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        int isSuccess = queryRunner.update("delete from product where `pid`=?;",pid);
        if (isSuccess > 0)
            return true;

        return false;
    }

    @Override
    public Boolean updateProduct(Product product) throws SQLException {
        if (product == null)
            return false;

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        Boolean isDeleteSuccess = deleteProduct(product);
        if (isDeleteSuccess){
            Boolean isSuccess = addProduct(product);
            return isSuccess;
        }

        return false;
    }

    @Override
    public Product findProduct(String name) throws SQLException {
        if (name == null || "".equals(name))
            return null;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        Product product = queryRunner.query("select *from product where `pname`=?;", new BeanHandler<Product>(Product.class),name);
        if (product != null){
            Category category = queryRunner.query("select *from category where `cid`=?;",
                    new BeanHandler<Category>(Category.class), product.getCid());
            product.setCategory(category);

        } else
            return null;

        return product;
    }

    @Override
    public List<Product> findAllProduct() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        List<Product> productList = queryRunner.query("select *from product;",
                new BeanListHandler<Product>(Product.class));

        if (productList != null){
            for (Product p:productList) {
                int cid = p.getCid();
                Category category = queryRunner.query("select *from category where `cid`=?;",
                        new BeanHandler<Category>(Category.class), cid);
                p.setCategory(category);
            }
        } else
            return null;

        return productList;
    }

    @Override
    public int findTotalNumber() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        Long query = (Long)queryRunner.query("select count(*) from product;",
                new ScalarHandler());

        return query.intValue();
    }

    @Override
    public List<Product> findOnePageProduct(int limit, int offset) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        List<Product> productList = queryRunner.query("select *from product limit ? offset ?;",
                new BeanListHandler<Product>(Product.class),limit, offset);
        if (productList != null){
            for (Product p:productList) {
                int cid = p.getCid();
                Category category = queryRunner.query("select *from category where `cid`=?;",
                        new BeanHandler<Category>(Category.class), cid);
                p.setCategory(category);
            }
        } else
            return null;

        return productList;
    }

    @Override
    public Product findProductByPid(String pid) throws SQLException,NumberFormatException {
        if (Integer.parseInt(pid) <= 0)
            return null;

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        Product product = queryRunner.query("select *from product where `pid`=?;", new BeanHandler<Product>(Product.class), pid);
        if (product != null){
            Category category = queryRunner.query("select *from category where `cid`=?;",
                    new BeanHandler<Category>(Category.class), product.getCid());
            product.setCategory(category);

        } else
            return null;

        return product;
    }

    @Override
    public Boolean deleteProductByPid(String pid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        int isSuccess = queryRunner.update("delete from product where `pid`=?;",pid);
        if (isSuccess > 0)
            return true;

        return false;
    }

    @Override
    public List<Product> multiConditionSearchProduct(String pid, int cid, String pname, String minprice, String maxprice) throws SQLException,NumberFormatException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        String sql = "select *from product where 1=1 ";

        ArrayList<Object> paramsList = new ArrayList<>();
        if (Integer.parseInt(pid) > 0){
            sql += "and pid=? ";
            paramsList.add(pid);
        }
        if (cid > 0){
            sql += "and cid=? ";
            paramsList.add(cid);
        }
        if (pname != null && !"".equals(pname)){
            sql += "and pname=? ";
            paramsList.add(pname);
        }
        if (Double.parseDouble(minprice) >= 0){
            double minprice_Double = Double.parseDouble(minprice);
            sql += "and estoreprice>=? ";
            paramsList.add(minprice);
        }
        if (Double.parseDouble(maxprice) >= 0){
            double maxprice_Double = Double.parseDouble(maxprice);
            sql += "and markprice<=? ";
            paramsList.add(maxprice_Double);
        }

        Object[] params = paramsList.toArray();
        List<Product> searchProducts = queryRunner.query(sql +" ;",
                new BeanListHandler<Product>(Product.class),params);

        if (searchProducts != null){
            for (Product product:searchProducts) {
                int cid1 = product.getCid();
                Category category = queryRunner.query("select *from category where `cid`=?;",
                        new BeanHandler<Category>(Category.class), cid1);
                product.setCategory(category);
            }
        } else
            return null;

        return searchProducts;
    }

    @Override
    public List<Product> findTopProducts() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        int topProductsNum = 5;

        List<Product> topProducts = queryRunner.query("select *from product order by estoreprice desc limit ?;",
                new BeanListHandler<Product>(Product.class), topProductsNum);
        return topProducts;
    }

    @Override
    public List<Product> findHotProducts() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        int hotProductsNum = 6;
        //int sNum = 2;
        //订单量+价格 综合考虑

        List<Product> hotProducts = queryRunner.query("select *from product order by RAND() limit ?;",
                new BeanListHandler<Product>(Product.class), hotProductsNum);
        return hotProducts;
    }

    @Override
    public List<Product> findProductsByName(String pname,int limit, int offset) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());

        List<Product> productsByName = queryRunner.query("select *from product where `pname` like ? or `desc` like ? " +
                        "limit ? offset ?;",
                new BeanListHandler<Product>(Product.class), "%"+pname+"%","%"+pname+"%",limit,offset);
        return productsByName;
    }

    @Override
    public List<Product> findProductsByCid(int cid,int limit, int offset) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());

        List<Product> productsByCid = queryRunner.query("select *from product where `cid`=? limit ? offset ?;",
                new BeanListHandler<Product>(Product.class), cid,limit,offset);
        return productsByCid;
    }

    @Override
    public int findTotalNumberByCid(int cid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        Long query = (Long)queryRunner.query("select count(*) from product where `cid` = ?;",
                new ScalarHandler(),cid);

        return query.intValue();
    }

    @Override
    public boolean updateProductPnumByPid(String pid, int snum) throws SQLException {
        if (pid != null && !"".equals(pid)){
            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
            Product product = findProductByPid(pid);
            int pnum = product.getPnum();
            int updatePnum = pnum + snum;
            int update = queryRunner.update("update `product` set `pnum`=? where `pid`=?;", updatePnum, pid);
            if (update > 0){
                return true;
            }
        }

        return false;
    }
}
