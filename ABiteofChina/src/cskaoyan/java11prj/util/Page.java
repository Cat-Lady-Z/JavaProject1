package cskaoyan.java11prj.util;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/09
 * Time: 下午 10:19
 * Detail requirement:
 * Method:
 */
public class Page<T> {
    List<T> records;

    /**
     * 总的记录数据
     */
    int totalRecordsNum;

    /**
     * 总页码数
     */
    int totalPageNum;


    /**
     * 当前的页码
     */
    int  currentPageNum;

    /**
     * 下一的页码
     */
    int  nextPageNum;

    /**
     * 前一页的页码
     */
    int  prevPageNum;

    @Override
    public String toString() {
        return "Page{" +
                "records=" + records +
                ", totalRecordsNum=" + totalRecordsNum +
                ", totalPageNum=" + totalPageNum +
                ", currentPageNum=" + currentPageNum +
                ", nextPageNum=" + nextPageNum +
                ", prevPageNum=" + prevPageNum +
                '}';
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public int getTotalRecordsNum() {
        return totalRecordsNum;
    }

    public void setTotalRecordsNum(int totalRecordsNum) {
        this.totalRecordsNum = totalRecordsNum;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getNextPageNum() {
        return nextPageNum;
    }

    public void setNextPageNum(int nextPageNum) {
        this.nextPageNum = nextPageNum;
    }

    public int getPrevPageNum() {
        return prevPageNum;
    }

    public void setPrevPageNum(int prevPageNum) {
        this.prevPageNum = prevPageNum;
    }
}