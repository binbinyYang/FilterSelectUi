package com.yangbin.filterboxui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yangbin.FilterTabConfig;
import com.yangbin.base.BaseFilterBean;
import com.yangbin.bean.FilterInfoBean;
import com.yangbin.bean.FilterResultBean;
import com.yangbin.filterboxui.bean.FilterAreaOneEntity;
import com.yangbin.filterboxui.bean.FilterAreaThreeEntity;
import com.yangbin.filterboxui.bean.FilterAreaTwoEntity;
import com.yangbin.filterboxui.bean.FilterMulSelectEntity;
import com.yangbin.filterboxui.bean.FilterSelectedEntity;
import com.yangbin.listener.OnSelectResultListener;
import com.yangbin.util.StatusBarHelper;
import com.yangbin.view.FilterTabView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 杨彬
 * demo使用类。针对于使用筛选框库
 */
public class MainActivity extends AppCompatActivity implements OnSelectResultListener {
    private FilterTabView mFtbFilter;
    private List<BaseFilterBean> mAreaList = new ArrayList<>();//区域
    private List<BaseFilterBean> mAllPriceList = new ArrayList<>();//总价
    private List<BaseFilterBean> mSortList = new ArrayList<>();//排序
    private List<BaseFilterBean> mAreaSizeList = new ArrayList<>();//平数
    private List<BaseFilterBean> mMoreList = new ArrayList<>();//更多
    private List<BaseFilterBean> mSinglePriceList = new ArrayList<>();//单价


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarHelper.translucent(this);
        setContentView(R.layout.activity_main);
        try {
            initView();
            initData();


            setFilterViewAndData();


            mFtbFilter.setOnSelectResultListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置要初始化的筛选框跟数据
     */
    private void setFilterViewAndData() {
        FilterInfoBean bean_area = new FilterInfoBean("区域", FilterTabConfig.FILTER_TYPE_AREA, mAreaList);
        FilterInfoBean bean_price = new FilterInfoBean("总价", FilterTabConfig.FILTER_TYPE_PRICE_UPRIGHT, mAllPriceList);
        FilterInfoBean bean_sort = new FilterInfoBean("排序", FilterTabConfig.FILTER_TYPE_SINGLE_SELECT_HAVA_PIC, mSortList);
        FilterInfoBean bean_more = new FilterInfoBean("更多", FilterTabConfig.FILTER_TYPE_MUL_SELECT, mMoreList);
        FilterInfoBean bean5 = new FilterInfoBean("坪数", FilterTabConfig.FILTER_TYPE_SINGLE_GIRD, mAreaSizeList);
        FilterInfoBean bean6 = new FilterInfoBean("单价", FilterTabConfig.FILTER_TYPE_PRICE_HORIZONTAL, mSinglePriceList);

        mFtbFilter.addFilterItem(bean_area.getTabName(), bean_area.getFilterData(), bean_area.getPopupType(), 0, false);
        mFtbFilter.addFilterItem(bean_price.getTabName(), bean_price.getFilterData(), bean_price.getPopupType(), 1, false);
        mFtbFilter.addFilterItem(bean_sort.getTabName(), bean_sort.getFilterData(), bean_sort.getPopupType(), 2, true);
        mFtbFilter.addFilterItem(bean_more.getTabName(), bean_more.getFilterData(), bean_more.getPopupType(), 3, false);
        mFtbFilter.addFilterItem(bean5.getTabName(), bean5.getFilterData(), bean5.getPopupType(), 4, false);
        mFtbFilter.addFilterItem(bean6.getTabName(), bean6.getFilterData(), bean6.getPopupType(), 5, false);
    }

    /**
     * 初始化view
     */
    private void initView() {
        mFtbFilter = findViewById(R.id.ftb_filter);
        mFtbFilter.setColorMain(getResources().getColor(R.color.color_FF6F00));
        mFtbFilter.removeViews();
    }

    /**
     * 初始化list有关data
     */
    private void initData() {

        setAreaData();
        setAllPriceData();
        setSinglePriceData();
        setSortData();
        setMoreData();
    }

    /**
     * 设置排序数据
     */
    private void setSortData() {
        FilterSelectedEntity sortBean_a = new FilterSelectedEntity();
        sortBean_a.setTid(0);
        sortBean_a.setName("不限");
        sortBean_a.setSelected(1);

        FilterSelectedEntity sortBean_b = new FilterSelectedEntity();
        sortBean_b.setTid(1);
        sortBean_b.setName("价格从低到高");
        sortBean_b.setSelected(0);

        FilterSelectedEntity sortBean_c = new FilterSelectedEntity();
        sortBean_c.setTid(2);
        sortBean_c.setName("价格从高到低");
        sortBean_c.setSelected(0);

        FilterSelectedEntity sortBean_d = new FilterSelectedEntity();
        sortBean_d.setTid(3);
        sortBean_d.setName("日价从低到高");
        sortBean_d.setSelected(0);


        FilterSelectedEntity sortBean_e = new FilterSelectedEntity();
        sortBean_e.setTid(4);
        sortBean_e.setName("日价从高到低");
        sortBean_e.setSelected(0);

        FilterSelectedEntity sortBean_f = new FilterSelectedEntity();
        sortBean_f.setTid(5);
        sortBean_f.setName("时间从早到晚");
        sortBean_f.setSelected(0);


        FilterSelectedEntity sortBean_g = new FilterSelectedEntity();
        sortBean_g.setTid(6);
        sortBean_g.setName("时间从晚到早");
        sortBean_g.setSelected(0);

        mSortList.add(sortBean_a);
        mSortList.add(sortBean_c);
        mSortList.add(sortBean_d);
        mSortList.add(sortBean_e);
        mSortList.add(sortBean_f);
        mSortList.add(sortBean_g);

        mAreaSizeList.add(sortBean_a);
        mAreaSizeList.add(sortBean_b);
        mAreaSizeList.add(sortBean_c);
        mAreaSizeList.add(sortBean_d);
        mAreaSizeList.add(sortBean_e);
        mAreaSizeList.add(sortBean_f);
        mAreaSizeList.add(sortBean_g);

    }


    /**
     * 设置更多数据
     */
    private void setMoreData() {
        //形态数据集合------------------------------------
        List<FilterSelectedEntity> typeList = new ArrayList<>();
        FilterMulSelectEntity filterOneEntity = new FilterMulSelectEntity();
        filterOneEntity.setIsCan(1);
        filterOneEntity.setSelecteStatus(1);
        filterOneEntity.setSortdata(typeList);
        filterOneEntity.setSortname("形态");

        FilterSelectedEntity f_a = new FilterSelectedEntity();
        f_a.setSelected(0);
        f_a.setName("公寓");
        f_a.setSelecteStatus(0);
        f_a.setTid(1);
        typeList.add(f_a);

        FilterSelectedEntity f_b = new FilterSelectedEntity();
        f_b.setSelected(0);
        f_b.setName("电梯大楼");
        f_b.setSelecteStatus(0);
        f_b.setTid(2);
        typeList.add(f_b);

        FilterSelectedEntity f_c = new FilterSelectedEntity();
        f_c.setSelected(0);
        f_c.setName("透天");
        f_c.setSelecteStatus(0);
        f_c.setTid(3);
        typeList.add(f_c);

        FilterSelectedEntity f_d = new FilterSelectedEntity();
        f_d.setSelected(0);
        f_d.setName("别墅");
        f_d.setSelecteStatus(0);
        f_d.setTid(4);
        typeList.add(f_d);


        //屋龄数据集合------------------------------------
        FilterMulSelectEntity filterOneEntity_a = new FilterMulSelectEntity();
        List<FilterSelectedEntity> houseAgeList = new ArrayList<>();
        filterOneEntity_a.setIsCan(1);
        filterOneEntity_a.setSelecteStatus(1);
        filterOneEntity_a.setSortdata(houseAgeList);
        filterOneEntity_a.setSortname("屋龄");

        FilterSelectedEntity ff_a = new FilterSelectedEntity();
        ff_a.setSelected(0);
        ff_a.setName("5年以下");
        ff_a.setSelecteStatus(0);
        ff_a.setTid(1);
        houseAgeList.add(ff_a);

        FilterSelectedEntity ff_b = new FilterSelectedEntity();
        ff_b.setSelected(0);
        ff_b.setName("5-10年");
        ff_b.setSelecteStatus(0);
        ff_b.setTid(2);
        houseAgeList.add(ff_b);

        FilterSelectedEntity ff_c = new FilterSelectedEntity();
        ff_c.setSelected(0);
        ff_c.setName("10-20年");
        ff_c.setSelecteStatus(0);
        ff_c.setTid(3);
        houseAgeList.add(ff_c);

        FilterSelectedEntity ff_d = new FilterSelectedEntity();
        ff_d.setSelected(0);
        ff_d.setName("20-30年");
        ff_d.setSelecteStatus(0);
        ff_d.setTid(4);
        houseAgeList.add(ff_d);


//---------------------------------------------


        FilterMulSelectEntity filterOneEntity_b = new FilterMulSelectEntity();
        List<FilterSelectedEntity> floorList = new ArrayList<>();
        filterOneEntity_b.setIsCan(1);
        filterOneEntity_b.setSelecteStatus(1);
        filterOneEntity_b.setSortdata(floorList);
        filterOneEntity_b.setSortname("楼层");
        FilterSelectedEntity fff_a = new FilterSelectedEntity();
        fff_a.setSelected(0);
        fff_a.setName("1楼以下");
        fff_a.setSelecteStatus(0);
        fff_a.setTid(1);
        floorList.add(fff_a);

        FilterSelectedEntity fff_c = new FilterSelectedEntity();
        fff_c.setSelected(0);
        fff_c.setName("1-3楼");
        fff_c.setSelecteStatus(0);
        fff_c.setTid(2);
        floorList.add(fff_c);

        FilterSelectedEntity fff_d = new FilterSelectedEntity();
        fff_d.setSelected(0);
        fff_d.setName("4-6楼");
        fff_d.setSelecteStatus(0);
        fff_d.setTid(3);
        floorList.add(fff_d);

        FilterSelectedEntity fff_e = new FilterSelectedEntity();
        fff_e.setSelected(0);
        fff_e.setName("7-9楼");
        fff_e.setSelecteStatus(0);
        fff_e.setTid(4);
        floorList.add(fff_e);


        mMoreList.add(filterOneEntity);
        mMoreList.add(filterOneEntity_a);
        mMoreList.add(filterOneEntity_b);
    }

    /**
     * 设置总价数据
     */
    private void setAllPriceData() {
        FilterSelectedEntity filterSelectedEntity_a = new FilterSelectedEntity();
        filterSelectedEntity_a.setTid(0);
        filterSelectedEntity_a.setName("不限");
        filterSelectedEntity_a.setSelecteStatus(1);

        FilterSelectedEntity filterSelectedEntity_b = new FilterSelectedEntity();
        filterSelectedEntity_b.setTid(1);
        filterSelectedEntity_b.setName("100");
        filterSelectedEntity_b.setSelecteStatus(0);

        FilterSelectedEntity filterSelectedEntity_c = new FilterSelectedEntity();
        filterSelectedEntity_c.setTid(2);
        filterSelectedEntity_c.setName("200");
        filterSelectedEntity_c.setSelecteStatus(0);

        FilterSelectedEntity filterSelectedEntity_d = new FilterSelectedEntity();
        filterSelectedEntity_d.setTid(3);
        filterSelectedEntity_d.setName("300");
        filterSelectedEntity_d.setSelecteStatus(0);

        FilterSelectedEntity filterSelectedEntity_e = new FilterSelectedEntity();
        filterSelectedEntity_e.setTid(4);
        filterSelectedEntity_e.setName("400");
        filterSelectedEntity_e.setSelecteStatus(0);

        FilterSelectedEntity filterSelectedEntity_f = new FilterSelectedEntity();
        filterSelectedEntity_f.setTid(5);
        filterSelectedEntity_f.setName("500");
        filterSelectedEntity_f.setSelecteStatus(0);

        FilterSelectedEntity filterSelectedEntity_g = new FilterSelectedEntity();
        filterSelectedEntity_g.setTid(6);
        filterSelectedEntity_g.setName("600");
        filterSelectedEntity_g.setSelecteStatus(0);

        mAllPriceList.add(filterSelectedEntity_a);
        mAllPriceList.add(filterSelectedEntity_b);
        mAllPriceList.add(filterSelectedEntity_c);
        mAllPriceList.add(filterSelectedEntity_d);
        mAllPriceList.add(filterSelectedEntity_e);
        mAllPriceList.add(filterSelectedEntity_f);
        mAllPriceList.add(filterSelectedEntity_g);
    }


    /**
     * 设置单价数据
     */
    private void setSinglePriceData() {
        FilterSelectedEntity filterSelectedEntity_a = new FilterSelectedEntity();
        filterSelectedEntity_a.setTid(0);
        filterSelectedEntity_a.setName("不限");
        filterSelectedEntity_a.setSelecteStatus(1);


        FilterSelectedEntity filterSelectedEntity_b = new FilterSelectedEntity();
        filterSelectedEntity_b.setTid(1);
        filterSelectedEntity_b.setName("1000");
        filterSelectedEntity_b.setSelecteStatus(0);

        FilterSelectedEntity filterSelectedEntity_c = new FilterSelectedEntity();
        filterSelectedEntity_c.setTid(2);
        filterSelectedEntity_c.setName("2000");
        filterSelectedEntity_c.setSelecteStatus(0);

        FilterSelectedEntity filterSelectedEntity_d = new FilterSelectedEntity();
        filterSelectedEntity_d.setTid(3);
        filterSelectedEntity_d.setName("3000");
        filterSelectedEntity_d.setSelecteStatus(0);

        FilterSelectedEntity filterSelectedEntity_e = new FilterSelectedEntity();
        filterSelectedEntity_e.setTid(4);
        filterSelectedEntity_e.setName("4000");
        filterSelectedEntity_e.setSelecteStatus(0);

        FilterSelectedEntity filterSelectedEntity_f = new FilterSelectedEntity();
        filterSelectedEntity_f.setTid(5);
        filterSelectedEntity_f.setName("5000");
        filterSelectedEntity_f.setSelecteStatus(0);

        FilterSelectedEntity filterSelectedEntity_g = new FilterSelectedEntity();
        filterSelectedEntity_g.setTid(6);
        filterSelectedEntity_g.setName("6000");
        filterSelectedEntity_g.setSelecteStatus(0);

        mSinglePriceList.add(filterSelectedEntity_a);
        mSinglePriceList.add(filterSelectedEntity_b);
        mSinglePriceList.add(filterSelectedEntity_c);
        mSinglePriceList.add(filterSelectedEntity_d);
        mSinglePriceList.add(filterSelectedEntity_e);
        mSinglePriceList.add(filterSelectedEntity_f);
        mSinglePriceList.add(filterSelectedEntity_g);
    }

    /**
     * 设置区域筛选框里面的数据list
     */
    private void setAreaData() {
        try {
            FilterAreaOneEntity filterOneEntity_a = new FilterAreaOneEntity();
            filterOneEntity_a.setArea_id(0);
            filterOneEntity_a.setName("区域0");
            filterOneEntity_a.setSelected(1);
            filterOneEntity_a.setFilterAreaEntityList(loadAreaData());

            FilterAreaOneEntity filterOneEntity_b = new FilterAreaOneEntity();
            filterOneEntity_b.setArea_id(1);
            filterOneEntity_b.setName("捷运1");
            filterOneEntity_b.setSelected(0);
            filterOneEntity_b.setFilterAreaEntityList(loadSubWayData());

            FilterAreaOneEntity filterOneEntity_c = new FilterAreaOneEntity();
            filterOneEntity_c.setArea_id(2);
            filterOneEntity_c.setName("附近2");
            filterOneEntity_c.setSelected(0);
            filterOneEntity_c.setFilterAreaEntityList(loadNearData());


            mAreaList.add(filterOneEntity_a);
            mAreaList.add(filterOneEntity_b);
            mAreaList.add(filterOneEntity_c);
        } catch (Exception e) {

        }
    }

    /**
     * 设置附近数据集合
     *
     * @return
     */
    public List<FilterAreaTwoEntity> loadNearData() {
        List<FilterAreaTwoEntity> areaList = new ArrayList<>();
        FilterAreaTwoEntity area_a = new FilterAreaTwoEntity();
        area_a.setArea_id(0);
        area_a.setName("1000");
        area_a.setSelected(0);

        FilterAreaTwoEntity area_b = new FilterAreaTwoEntity();
        area_b.setArea_id(1);
        area_b.setName("2000");
        area_b.setSelected(0);

        FilterAreaTwoEntity area_c = new FilterAreaTwoEntity();
        area_c.setArea_id(2);
        area_c.setName("3000");
        area_c.setSelected(0);

        areaList.add(area_a);
        areaList.add(area_b);
        areaList.add(area_c);
        return areaList;
    }

    /**
     * 返回数据（单bean）
     *
     * @param resultBean 单个筛选的数据集合
     * @param postion    postion对应是第几个筛选框的数据（比如是区域，还是单价）
     */
    @Override
    public void onSelectResult(FilterResultBean resultBean, int postion) {
        String message = "";
        if (resultBean.getPopupType() == 3) {
            List<FilterResultBean.MulTypeBean> list = resultBean.getSelectList();
            for (int i = 0; i < list.size(); i++) {
                FilterResultBean.MulTypeBean bean = list.get(i);
                if (i == (list.size() - 1)) {
                    message = message + bean.getItemName();
                } else {
                    message = message + bean.getItemName() + ",";
                }
            }
        } else {
            message = resultBean.getItemId() + ":" + resultBean.getName();
        }

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }


    /**
     * 返回数据（List集合bean）
     *
     * @param resultBeans List集合bean，多选
     * @param postion     postion对应是第几个筛选框的数据（比如是区域，还是单价）
     */
    @Override
    public void onSelectResultList(List<FilterResultBean> resultBeans, int postion) {
        String message = "";
        List<FilterResultBean> list = resultBeans;
        for (int i = 0; i < list.size(); i++) {
            FilterResultBean bean = list.get(i);
            if (i == (list.size() - 1)) {
                message = message + bean.getName();
            } else {
                message = message + bean.getName() + ",";
            }
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        Log.e("选中的", "name:" + list.get(0).getName() + "/1-Id:" + list.get(0).getItemId() + "/2-Id:" + list.get(0).getChildId());
        mFtbFilter.resetTab(1, mSinglePriceList, "总价");

    }


    /**
     * 设置区域数据集合
     *
     * @return
     */
    public List<FilterAreaTwoEntity> loadAreaData() {
        List<FilterAreaTwoEntity> areaList = new ArrayList<>();
        FilterAreaTwoEntity area = null;
        FilterAreaThreeEntity district;
        List<FilterAreaThreeEntity> districtList = null;
        area = new FilterAreaTwoEntity();
        area.setArea_id(0);
        area.setName("不限");
        area.setSelected(1);
        areaList.add(area);


        try {
            InputStream inputStream = MainActivity.this.getResources().openRawResource(R.raw.location);
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStream, "utf-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String tagName = parser.getName();
                        if ("region".equals(tagName)) {
                            area = new FilterAreaTwoEntity();


                            String id = parser.getAttributeValue(null, "id");
                            String name = parser.getAttributeValue(null, "name");
                            String latitude = parser.getAttributeValue(null, "lat");
                            String longitude = parser.getAttributeValue(null, "lng");
                            int initID = Integer.parseInt(id);
                            area.setArea_id(initID);
                            area.setName(name);
                            districtList = new ArrayList<>();
                            area.setChildAreas(districtList);

                            district = new FilterAreaThreeEntity();
                            district.setStreet_id(0);
                            district.setName("不限");
                            district.setSelected(0);
                            districtList.add(district);

                        } else if ("section".equals(tagName)) {
                            String id = parser.getAttributeValue(null, "id");
                            String name = parser.nextText();
                            String latitude = parser.getAttributeValue(null, "lat");
                            String longitude = parser.getAttributeValue(null, "lng");
                            district = new FilterAreaThreeEntity();
                            int initID = Integer.parseInt(id);
                            district.setStreet_id(initID);
                            district.setName(name);
                            district.setSelected(0);
                            if (null != districtList && null != district) {
                                districtList.add(district);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("region".equals(parser.getName())) {
                            if (null != areaList && null != area) {
                                areaList.add(area);
                            }
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areaList;

    }


    /**
     * 将3种地区捷运数据合并成一个list
     *
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    public List<FilterAreaTwoEntity> loadSubWayData() throws IOException, XmlPullParserException {
        List<FilterAreaTwoEntity> praser1 = realPraserLine2(MainActivity.this, R.raw.subway_taipei);
        List<FilterAreaTwoEntity> praser2 = realPraserLine2(MainActivity.this, R.raw.subway_gaoxiong);
        List<FilterAreaTwoEntity> praser3 = realPraserLine2(MainActivity.this, R.raw.subway_taoyuan);
        List<FilterAreaTwoEntity> praser4 = realPraserLine2(MainActivity.this, R.raw.subway_xinbei);
        praser1.addAll(praser2);
        praser1.addAll(praser3);
        praser1.addAll(praser4);

        return praser1;

    }

    /**
     * 设置捷运数据集合
     *
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    public List<FilterAreaTwoEntity> realPraserLine2(Context context, int resourceId) throws XmlPullParserException, IOException {
        List<FilterAreaTwoEntity> areaList = new ArrayList<>();

        FilterAreaTwoEntity area = null;
        FilterAreaThreeEntity district;
        List<FilterAreaThreeEntity> districtList = null;
        InputStream inStream = context.getResources().openRawResource(resourceId);
        XmlPullParser pullParser = Xml.newPullParser();
        pullParser.setInput(inStream, "UTF-8");
        int event = pullParser.getEventType();// 觸發第一個事件
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    if ("line".equals(pullParser.getName())) {
                        area = new FilterAreaTwoEntity();
                        String name = pullParser.getAttributeValue(null, "name");
                        String id = pullParser.getAttributeValue(null, "id");
                        String latitude = pullParser.getAttributeValue(null, "lat");
                        String longitude = pullParser.getAttributeValue(null, "lng");
                        String order = pullParser.getAttributeValue(null, "zoom");
                        int initID = Integer.parseInt(id);
                        area.setArea_id(initID);
                        area.setName(name);
                        districtList = new ArrayList<>();
                        area.setChildAreas(districtList);
                        district = new FilterAreaThreeEntity();
                        district.setStreet_id(0);
                        district.setName("不限");
                        district.setSelected(0);
                        districtList.add(district);
                    }
                    if ("station".equals(pullParser.getName())) {
                        String name = pullParser.getAttributeValue(null, "name");

                        String id = pullParser.getAttributeValue(null, "id");
                        String latitude = pullParser.getAttributeValue(null, "lat");
                        String longitude = pullParser.getAttributeValue(null, "lng");
                        String zoom = pullParser.getAttributeValue(null, "zoom");
                        district = new FilterAreaThreeEntity();
                        int initID = Integer.parseInt(id);
                        district.setStreet_id(initID);
                        district.setName(name);
                        if (null != districtList && null != district) {
                            districtList.add(district);
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("line".equals(pullParser.getName())) {
                        if (null != areaList && null != area) {
                            areaList.add(area);
                        }
                    }

                    break;
                default:
                    break;
            }
            event = pullParser.next();
        }
        return areaList;
    }


    /**
     * 集合的复制   作用：防止adapter清空数据的时候 集合数据也被清空
     */
    public static <E> List<E> deepCopy(List<E> src) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            @SuppressWarnings("unchecked")
            List<E> dest = (List<E>) in.readObject();
            return dest;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<E>();
        }
    }

}
