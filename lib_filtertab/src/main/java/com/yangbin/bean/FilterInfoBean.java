package com.yangbin.bean;


import com.yangbin.base.BaseFilterBean;

import java.util.List;


public class FilterInfoBean {

    /**
     * tab名称
     */
    private String tabName;

    /**
     * 对应的Popupwindow类型
     */
    private int popupType;

    /**
     * 对应的Popupwindow数据
     */
    private List<BaseFilterBean> filterData;

    /**
     * 对应的Popupwindow数据
     */
    private List<BaseFilterBean> filterData2;

    /**
     * 对应的Popupwindow数据
     */
    private List<BaseFilterBean> filterData3;



    public FilterInfoBean(String tabName, int popupType, List filterData) {
        this.tabName = tabName;
        this.popupType = popupType;
        this.filterData = filterData;
    }


    public FilterInfoBean(String tabName, int popupType, List filterData,List filterData2,List filterData3) {
        this.tabName = tabName;
        this.popupType = popupType;
        this.filterData = filterData;
        this.filterData2 = filterData2;
        this.filterData3 = filterData3;

    }


    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public int getPopupType() {
        return popupType;
    }

    public void setPopupType(int popupType) {
        this.popupType = popupType;
    }

    public List getFilterData() {
        return filterData;
    }

    public void setFilterData(List filterData) {
        this.filterData = filterData;
    }



}
