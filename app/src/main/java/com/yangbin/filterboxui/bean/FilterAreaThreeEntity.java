package com.yangbin.filterboxui.bean;

import com.yangbin.base.BaseFilterBean;

import java.util.List;

/**
 * @describe 区域选择子Entity  第三级页面的数据bean
 */
public class FilterAreaThreeEntity extends BaseFilterBean {

    /**
     * 街道ID
     */
    private int street_id;
    /**
     * 街道名称
     */
    private String name;
    /**
     * 选择状态
     */
    private int selected;

    public int getStreet_id() {
        return street_id;
    }

    public void setStreet_id(int street_id) {
        this.street_id = street_id;
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
        return street_id;
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
    public List getChildList() {
        return null;
    }

    @Override
    public String getSortTitle() {
        return null;
    }

    @Override
    public String getSortKey() {
        return null;
    }
}
