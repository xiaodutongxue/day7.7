package com.ujiuye.bean;

import java.util.List;

/**
 * 分页工具类
 * @param <T> 页面中存放数据类型
 */
public class Page<T> {
    // 每页显示数据的条数,默认5条
    private Integer initSize = 5;
    // 总共有多少条数据
    private Integer countNum;
    //总共有多少页
    private Integer countpage;
    // 当前页.默认设置为第一页
    private Integer currentPage = 1;
    // 上一页
    private Integer prePage;
    // 下一页
    private Integer nextpage;
    // 页面数据的封装
    private List<T>  pageList;
    // 页面对象的初始化
    public Page(Integer initSize, Integer countNum, Integer currentPage, List<T> pageList) {
        this.initSize = initSize;
        this.countNum = countNum;
        this.currentPage = currentPage;
        this.pageList = pageList;
        // 计算总页数
        int num =  this.countNum / this.initSize;
        //如果总页数正好是每页显示条数的倍数,则为总页数为商 否则 商+1
        this.countpage = this.countNum % this.initSize == 0 ? num : num + 1;
        // 上一页.如果当前页大于1则前一页为当前页减1,否则就为1
        this.prePage =  this.currentPage > 1 ? this.currentPage -1 : 1;
        // 如果当前页小于总页码,下一页为当前页加 1,否则为总页码
        this.nextpage = this.currentPage < this.countpage ? this.currentPage + 1: this.countpage;
    }
    // 页面对象的初始化
    public Page(Integer countNum, Integer currentPage, List<T> pageList) {
        this.countNum = countNum;
        this.currentPage = currentPage;
        this.pageList = pageList;
        // 计算总页数
        int num =  this.countNum / this.initSize;
        //如果总页数正好是每页显示条数的倍数,则为总页数为商 否则 商+1
        this.countpage = this.countNum % this.initSize == 0 ? num : num + 1;
        // 上一页.如果当前页大于1则前一页为当前页减1,否则就为1
        this.prePage =  this.currentPage > 1 ? this.currentPage -1 : 1;
        // 如果当前页小于总页码,下一页为当前页加 1,否则为总页码
        this.nextpage = this.currentPage < this.countpage ? this.currentPage + 1: this.countpage;
    }

    public Integer getInitSize() {
        return initSize;
    }

    public void setInitSize(Integer initSize) {
        this.initSize = initSize;
    }

    public Integer getCountNum() {
        return countNum;
    }

    public void setCountNum(Integer countNum) {
        this.countNum = countNum;
    }

    public Integer getCountpage() {
        return countpage;
    }

    public void setCountpage(Integer countpage) {
        this.countpage = countpage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPrePage() {
        return prePage;
    }

    public void setPrePage(Integer prePage) {
        this.prePage = prePage;
    }

    public Integer getNextpage() {
        return nextpage;
    }

    public void setNextpage(Integer nextpage) {
        this.nextpage = nextpage;
    }

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }

    @Override
    public String toString() {
        return "Page{" +
                "initSize=" + initSize +
                ", countNum=" + countNum +
                ", countpage=" + countpage +
                ", currentPage=" + currentPage +
                ", prePage=" + prePage +
                ", nextpage=" + nextpage +
                ", pageList=" + pageList +
                '}';
    }
}
