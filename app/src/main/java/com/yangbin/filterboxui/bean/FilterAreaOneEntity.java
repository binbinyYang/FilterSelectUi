package com.yangbin.filterboxui.bean;

import com.yangbin.base.BaseFilterBean;

import java.util.List;

/**
 * @describe 区域选择父Entity （三级联动的父entity)第一级页面的数据bean
 */
public class FilterAreaOneEntity extends BaseFilterBean {

    /**
     * 区域名称
     */
    private String name;
    /**
     * 区域对应的ID
     */
    private int area_id;
    /**
     * 选择状态 0 选择 1 选择
     */
    private int selected;
    /**
     * 二级分类数据
     */
    private List<FilterAreaTwoEntity> filterAreaTwoEntityList;




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public List<FilterAreaTwoEntity> getFilterAreaEntityList() {
        return filterAreaTwoEntityList;
    }

    public void setFilterAreaEntityList(List<FilterAreaTwoEntity> filterAreaTwoEntityList) {
        this.filterAreaTwoEntityList = filterAreaTwoEntityList;
    }

    @Override
    public String getItemName() {
        return name;
    }

    @Override
    public int getId() {
        return area_id;
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
        return filterAreaTwoEntityList;
    }

    @Override
    public String getSortKey() {
        return null;
    }
}
