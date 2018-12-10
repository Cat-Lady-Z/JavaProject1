package cskaoyan.java11prj.service.impl;

import cskaoyan.java11prj.dao.impl.AdminDaoImpl;
import cskaoyan.java11prj.domain.Admin;
import cskaoyan.java11prj.service.AdminService;
import cskaoyan.java11prj.util.Page;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/12
 * Time: 上午 11:43
 * Detail requirement:
 * Method:
 */
public class AdminServiceImpl implements AdminService {
    AdminDaoImpl adminDao = new AdminDaoImpl();
    /**
     * 每页显示10条记录
     */
    private static final int PAGE_COUNT = 10;

    @Override
    public Admin login(String username, String password) throws SQLException {
        if (username == null || "".equals(username) || password == null || "".equals(password))
            return null;

        Admin admin= adminDao.findOneAdmin(username, password);
        return admin;
    }

    @Override
    public boolean addAdmin(Admin admin) throws SQLException {
        if (admin == null)
            return false;

        return adminDao.addAdmin(admin);
    }

    @Override
    public Boolean deleteAdmin(int aid) throws SQLException {
        if (aid <= 0)
            return false;

        return adminDao.deleteAdmin(aid);
    }

    @Override
    public Boolean updateAdmin(Admin admin) throws SQLException {
        if (admin == null)
            return false;

        return adminDao.updateAdmin(admin);
    }

    @Override
    public List<Admin> findAllAdmin() throws SQLException {
        return adminDao.findAllAdmin();
    }

    @Override
    public int findTotalNumber() throws SQLException {
        return adminDao.findTotalNumber();
    }

    @Override
    public Page<Admin> findPageAdmin(String num) throws SQLException {
        Page<Admin> page=new Page();

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

        int totalNumber = adminDao.findTotalNumber();

        page.setTotalRecordsNum(totalNumber);
        page.setCurrentPageNum(pageNumber);

        int totalpageNumber = (totalNumber + PAGE_COUNT - 1)/PAGE_COUNT;
        page.setTotalPageNum(totalpageNumber);

        page.setPrevPageNum(pageNumber==1?pageNumber:pageNumber-1);
        page.setNextPageNum(pageNumber==totalpageNumber?totalpageNumber:pageNumber+1);

        int limit = PAGE_COUNT;
        int offset = (pageNumber-1)*PAGE_COUNT;
        List<Admin> records = adminDao.findOnePageAdmin(limit,offset);
        page.setRecords(records);

        return page;
    }

    @Override
    public Admin findAdmin(String username, String password) throws SQLException {
        if (username == null || "".equals(username) || password == null || "".equals(password))
            return null;

        Admin admin= adminDao.findOneAdmin(username, password);
        return admin;
    }
}