package com.yangbin.filterboxui.bean;


import com.yangbin.base.BaseFilterBean;

import java.util.List;

/**
 * @describe 单条选择Entity
 */
public class FilterSelectedEntity extends BaseFilterBean {

    /**
     * 选项ID
     */
    private int tid;
    /**
     * 选项名称
     */
    private String name;
    /**
     * 选择状态
     */
    private int selected;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    @Override
    public String getItemName() {
        return name;
    }

    @Override
    public int getId() {
        return tid;
    }

    @Override
    public int getSelecteStatus() {
        return selected;
    }

    @Override
    public void setSelecteStatus(int status) {
        this.selected = status;
    }

    @Override
    public String getSortTitle() {
        return null;
    }

    @Override
    public List getChildList() {
        return null;
    }

    @Override
    public String getSortKey() {
        return null;
    }
}
