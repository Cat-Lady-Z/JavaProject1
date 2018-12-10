package test;

import cskaoyan.java11prj.dao.impl.CategoryDaoImpl;
import cskaoyan.java11prj.domain.Category;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/07
 * Time: 下午 9:08
 * Detail requirement:
 * Method:
 */
public class TestCategoryDaoImpl {
    @Test
    public void testAdd(){
        CategoryDaoImpl categoryDao = new CategoryDaoImpl();
        //Category yuecai = new Category(1, "粤菜");
        //Category yuecai = new Category(-1, "粤菜");   -- 判重的功能暂未实现
        //Category yuecai = new Category(1, "");
        Category yuecai = new Category();

        System.out.println("id=" + yuecai.getCid());
        Boolean isSuccess = null;
        try {
            isSuccess = categoryDao.addCategory(yuecai);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Assert.assertFalse(isSuccess);
    }

    @Test
    public void testUpdate(){
        CategoryDaoImpl categoryDao = new CategoryDaoImpl();
        //Category yuecai = new Category(3, "粤菜");
        //Category yuecai = new Category(-1, "粤菜");  // -- 判重的功能暂未实现
        //Category yuecai = new Category(1, "");
        //Category yuecai = new Category();
        Category yuecai = new Category(2, "粤菜");

        Boolean isSuccess = null;
        try {
            isSuccess = categoryDao.updateCategory(yuecai);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(isSuccess);
    }

    @Test
    public void testDelete(){
        CategoryDaoImpl categoryDao = new CategoryDaoImpl();
        //int id = 0;
        //int id = -2;
        int id = 2;

        Boolean isSuccess = null;
        try {
            isSuccess = categoryDao.deleteCategory(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(isSuccess);
    }
    @Test
    public void testFindAll(){
        CategoryDaoImpl categoryDao = new CategoryDaoImpl();

        List<Category> allCategory = null;
        try {
            allCategory = categoryDao.findAllCategory();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(allCategory);
    }
}