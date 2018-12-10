package cskaoyan.java11prj.service.impl;

import cskaoyan.java11prj.dao.impl.CategoryDaoImpl;
import cskaoyan.java11prj.domain.Category;
import cskaoyan.java11prj.service.CategoryService;
import cskaoyan.java11prj.util.Page;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/07
 * Time: 下午 7:54
 * Detail requirement:
 * Method:
 */
public class CategoryServiceImpl implements CategoryService {
    CategoryDaoImpl categoryDao = new CategoryDaoImpl();
    /**
     * 每页显示10条记录
     */
    private static final int PAGE_COUNT = 10;

    /**
     *@Description: 增加分类
     *@Param: 新增类的信息
     *@return: 增加成功就返回true，否则返回false
     *@Author: yadi.zhang
     *@date: 20181107
     */
    @Override
    public Boolean addCategory(Category category) throws SQLException {
        if ("".equals(category.getCname()) || "null".equals(category.getCname()+"")||category == null)
            return false;

        Boolean isSuccess = categoryDao.addCategory(category);

        return isSuccess;
    }

    @Override
    public Boolean deleteCategory(int cid) throws SQLException {
        if (cid<=0)
            return false;

        Boolean isSuccess = categoryDao.deleteCategory(cid);
        return isSuccess;
    }

    @Override
    public Boolean updateCategory(Category category) throws SQLException {
        if ("".equals(category.getCname()) || "null".equals(category.getCname()+"")|| category.getCid()<=0
                ||category == null)
            return false;

        Boolean isSuccess = categoryDao.updateCategory(category);
        return isSuccess;
    }

    @Override
    public List<Category> findAllCategory() throws SQLException {
        List<Category> categoryList = categoryDao.findAllCategory();
        return categoryList;
    }

    @Override
    public Page<Category> findPageCategory(String num) throws SQLException {
        Page<Category> page = new Page();

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

        int totalNumber = categoryDao.findTotalNumber();

        page.setTotalRecordsNum(totalNumber);
        page.setCurrentPageNum(pageNumber);

        int totalpageNumber = (totalNumber + PAGE_COUNT - 1)/PAGE_COUNT;
        page.setTotalPageNum(totalpageNumber);

        page.setPrevPageNum(pageNumber==1?pageNumber:pageNumber-1);
        page.setNextPageNum(pageNumber==totalpageNumber?totalpageNumber:pageNumber+1);

        int limit = PAGE_COUNT;
        int offset = (pageNumber-1)*PAGE_COUNT;
        List<Category> records = categoryDao.findOnePageCategory(limit,offset);
        page.setRecords(records);

        return page;
    }
}