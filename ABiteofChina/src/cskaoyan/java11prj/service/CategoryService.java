package cskaoyan.java11prj.service;

import cskaoyan.java11prj.domain.Category;
import cskaoyan.java11prj.util.Page;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/07
 * Time: 下午 7:32
 * Detail requirement:
 * Method:
 */
public interface CategoryService {
    /**
     *@Description: 增加分类
     *@Param: 新增类的信息
     *@return: 增加成功就返回true，否则返回false
     *@Author: yadi.zhang
     *@date: 20181107
     */
    Boolean addCategory(Category category) throws SQLException;

    /**
     *@Description: 删除分类
     *@Param: 类别的id
     *@return: 删除成功就返回true，否则返回false
     *@Author: yadi.zhang
     *@date: 20181107
     */
    Boolean deleteCategory(int cid) throws SQLException;

    /**
     *@Description: 修改分类
     *@Param: 已经修改的类别的信息
     *@return:  修改成功就返回true，否则返回false
     *@Author: yadi.zhang
     *@date: 20181107
     */
    Boolean updateCategory(Category category) throws SQLException;

    /**
     *@Description: 查询分类
     *@Param: 无
     *@return:  修改成功就返回查到的类别列表，否则返回null
     *@Author: yadi.zhang
     *@date: 20181107
     */
    List<Category> findAllCategory() throws SQLException;

    Page<Category> findPageCategory(String num) throws SQLException;
}
