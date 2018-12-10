package cskaoyan.java11prj.service.impl;

import cskaoyan.java11prj.dao.impl.ProductDaoImpl;
import cskaoyan.java11prj.domain.Product;
import cskaoyan.java11prj.domain.Shoppingitem;
import cskaoyan.java11prj.service.ProductService;
import cskaoyan.java11prj.util.Page;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/09
 * Time: 下午 4:17
 * Detail requirement:
 * Method:
 */
public class ProductServiceImpl implements ProductService {
    ProductDaoImpl productDao = new ProductDaoImpl();

    /**
     * 每页显示10条记录
     */
    private static final int PAGE_COUNT = 9;
    @Override
    public Boolean addProduct(Product product) throws SQLException {
        if (product == null)
            return false;
        return productDao.addProduct(product);
    }

    @Override
    public Boolean deleteProduct(Product product) throws SQLException {
        if (product == null)
            return false;
        return productDao.deleteProduct(product);
    }

    @Override
    public Boolean updateProduct(Product product) throws SQLException {
        if (product == null)
            return false;
        return productDao.updateProduct(product);
    }

    @Override
    public Product findProduct(String name) throws SQLException {
        if ("".equals(name) || name == null)
            return null;

        return productDao.findProduct(name);
    }

    @Override
    public List<Product> findAllProduct(String num) throws SQLException {
        return null;
    }

    @Override
    public Page<Product> findPageProduct(String num) throws SQLException {
        Page<Product> page=new Page();

        int pageNumber = -1;

        try {
            pageNumber = Integer.parseInt(num);
        }catch (NumberFormatException e){
            System.out.println("页面字符串转换成int发生错误！");
            e.printStackTrace();
            return  null;
        }

        //参数校验
        if (pageNumber<=0)
            return null;

        int totalNumber = productDao.findTotalNumber();

        page.setTotalRecordsNum(totalNumber);
        page.setCurrentPageNum(pageNumber);

        int totalpageNumber = (totalNumber + PAGE_COUNT - 1)/PAGE_COUNT;
        page.setTotalPageNum(totalpageNumber);

        page.setPrevPageNum(pageNumber==1?pageNumber:pageNumber-1);
        page.setNextPageNum(pageNumber==totalpageNumber?totalpageNumber:pageNumber+1);

        int limit = PAGE_COUNT;
        int offset = (pageNumber-1)*PAGE_COUNT;
        List<Product> records = productDao.findOnePageProduct(limit,offset);
        page.setRecords(records);

        return page;
    }

    @Override
    public Product findProductByPid(String pid) throws NumberFormatException, SQLException {
        if ("".equals(pid) || pid == null || Integer.parseInt(pid) <= 0)
            return null;

        Product product= productDao.findProductByPid(pid);
        return product;
    }

    @Override
    public Boolean deleteProductByPid(String pid) throws NumberFormatException, SQLException {
        if (pid == null || "".equals(pid) || Integer.parseInt(pid) <= 0)
            return false;

        return productDao.deleteProductByPid(pid);
    }

    @Override
    public Page<Product> multiConditionSearchProduct(String pid, String cid, String pname, String minprice, String maxprice) throws SQLException,NumberFormatException {
        Page<Product> page = new Page<>();
        if (pid == null || cid == null || pname == null || minprice == null || maxprice == null )
            return null;

        int cidInt = Integer.parseInt(cid);
        List<Product> records = productDao.multiConditionSearchProduct(pid,cidInt,pname,minprice,maxprice);
        page.setRecords(records);

        int totalNumber = records.size();
        page.setTotalRecordsNum(totalNumber);
        int pageNumber = 1;
        page.setCurrentPageNum(pageNumber);

        int totalpageNumber = (totalNumber + PAGE_COUNT - 1)/PAGE_COUNT;
        page.setTotalPageNum(totalpageNumber);

        page.setPrevPageNum(pageNumber==1?pageNumber:pageNumber-1);
        page.setNextPageNum(pageNumber==totalpageNumber?totalpageNumber:pageNumber+1);

        return page;
    }

    @Override
    public List<Product> findTopProducts() throws SQLException {
        return productDao.findTopProducts();
    }

    @Override
    public List<Product> findHotProducts() throws SQLException {
        return productDao.findHotProducts();
    }

    @Override
    public Page<Product> findProductsByName(String pname,String num) throws SQLException,NumberFormatException {
        if (pname==null || "".equals(pname) || num==null || "".equals(num))
            return null;
        Page<Product> page = new Page();
        int numInt = Integer.parseInt(num);
        if (numInt <=0)
            return null;

        int pageNumber = numInt;  //默认返回首页
        int limit = PAGE_COUNT;
        int offset = (pageNumber-1)*PAGE_COUNT;
        List<Product> records = productDao.findProductsByName(pname,limit,offset);
        page.setRecords(records);

        int totalNumber = records.size();

        page.setTotalRecordsNum(totalNumber);
        page.setCurrentPageNum(pageNumber);

        int totalpageNumber = (totalNumber + PAGE_COUNT - 1)/PAGE_COUNT;
        page.setTotalPageNum(totalpageNumber);

        page.setPrevPageNum(pageNumber==1?pageNumber:pageNumber-1);
        page.setNextPageNum(pageNumber==totalpageNumber?totalpageNumber:pageNumber+1);

        return page;
    }

    @Override
    public Page<Product> findProductByCid(String cid,String num) throws SQLException,NumberFormatException {
        if (cid==null || "".equals(cid) || num==null || "".equals(num))
            return null;
        int cidInt = Integer.parseInt(cid);
        int numInt = Integer.parseInt(num);
        if (cidInt <=0 ||numInt<=0)
            return null;
        Page<Product> page = new Page();

        int pageNumber = numInt;  //默认返回首页
        int limit = PAGE_COUNT;
        int offset = (pageNumber-1)*PAGE_COUNT;

        int totalNumber = productDao.findTotalNumberByCid(cidInt);

        page.setTotalRecordsNum(totalNumber);
        page.setCurrentPageNum(pageNumber);

        int totalpageNumber = (totalNumber + PAGE_COUNT - 1)/PAGE_COUNT;
        page.setTotalPageNum(totalpageNumber);

        page.setPrevPageNum(pageNumber==1?pageNumber:pageNumber-1);
        page.setNextPageNum(pageNumber==totalpageNumber?totalpageNumber:pageNumber+1);

        List<Product> records = productDao.findProductsByCid(cidInt,limit,offset);
        page.setRecords(records);

        return page;
    }

    @Override
    public boolean reducePnumByPid(List<Shoppingitem> shoppingitems) throws SQLException {
        boolean result = false;
        //参数校验
        if (shoppingitems != null){
            for (Shoppingitem s:shoppingitems) {
                String pid = s.getPid();
                int snum = s.getSnum();
                if (pid == null || "".equals(pid) || snum < 0){
                    return false;
                }
            }
            for (Shoppingitem s:shoppingitems) {
                String pid = s.getPid();
                int snum = -s.getSnum();
                result = productDao.updateProductPnumByPid(pid, snum);
            }
        }

        return result;
    }
}