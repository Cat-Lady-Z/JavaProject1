package cskaoyan.java11prj.dao.impl;

import cskaoyan.java11prj.dao.CategoryDao;
import cskaoyan.java11prj.domain.Category;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import cskaoyan.java11prj.util.C3P0Utils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/07
 * Time: 下午 8:09
 * Detail requirement:
 * Method:
 */
public class CategoryDaoImpl implements CategoryDao {
    @Override
    public Boolean addCategory(Category category) throws SQLException {
        //判空，若类名为空字符串
        if ("".equals(category.getCname()) || "null".equals(category.getCname()+"")||category == null)
            return false;

        String cname = category.getCname();
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        int isSuccess = queryRunner.update("insert into Category (cname) values (?);", cname);
        if (isSuccess > 0)
            return true;

        return false;
    }

    @Override
    public Boolean deleteCategory(int cid) throws SQLException {
        if (cid <= 0)
            return false;

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        //先判断cid在表里是否存在
        Category query = queryRunner.query("select *from Category where `cid`=?;", new BeanHandler<Category>(Category.class), cid);
        if (query == null)
            return false;

        int isSuccess = queryRunner.update("delete from Category where cid=?;",cid);
        if (isSuccess > 0)
            return true;

        return false;
    }

    @Override
    public Boolean updateCategory(Category category) throws SQLException {
        //判空，若类名为空字符串
        if ("".equals(category.getCname()) || "null".equals(category.getCname()+"")|| category.getCid()<=0
                ||category == null)
            return false;

        int cid = category.getCid();
        String cname = category.getCname();
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        Category query = queryRunner.query("select *from Category where `cid`=?;", new BeanHandler<Category>(Category.class), cid);
        if (query == null)
            return false;

        int isSuccess = queryRunner.update("update Category set `cname`=? where `cid`=?;", cname,cid);
        if (isSuccess > 0)
            return true;

        return false;
    }

    @Override
    public List<Category> findAllCategory() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        List<Category> categoryList = queryRunner.query("select *from Category;", new BeanListHandler<Category>(Category.class));
        if (categoryList !=null)
            return categoryList;

        return null;
    }

    @Override
    public int findTotalNumber() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        Long query = (Long)queryRunner.query("select count(*) from category;",
                new ScalarHandler());

        return query.intValue();
    }

    @Override
    public List<Category> findOnePageCategory(int limit, int offset) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        List<Category> categoryList = queryRunner.query("select *from category limit ? offset ?;",
                new BeanListHandler<Category>(Category.class),limit, offset);
        if (categoryList == null)
            return null;

        return categoryList;
    }
}