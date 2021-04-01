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
    FilterTabView mFtbFilter;
    List<BaseFilterBean> mAreaList = new ArrayList<>();//区域
    List<BaseFilterBean> mAllPriceList = new ArrayList<>();//总价
    List<BaseFilterBean> mSortList = new ArrayList<>();//排序
    List<BaseFilterBean> mAreaSizeList = new ArrayList<>();//平数
    List<BaseFilterBean> mMoreList = new ArrayList<>();//更多
    List<BaseFilterBean> mSinglePriceList = new ArrayList<>();//单价


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
        FilterSelectedEntity ff = new FilterSelectedEntity();
        ff.setTid(0);
        ff.setName("不限");
        ff.setSelected(1);

        FilterSelectedEntity ff1 = new FilterSelectedEntity();
        ff1.setTid(1);
        ff1.setName("价格从低到高");
        ff1.setSelected(0);

        FilterSelectedEntity ff2 = new FilterSelectedEntity();
        ff2.setTid(2);
        ff2.setName("价格从高到低");
        ff2.setSelected(0);

        FilterSelectedEntity ff3 = new FilterSelectedEntity();
        ff3.setTid(3);
        ff3.setName("日价从低到高");
        ff3.setSelected(0);


        FilterSelectedEntity ff4 = new FilterSelectedEntity();
        ff4.setTid(4);
        ff4.setName("日价从高到低");
        ff4.setSelected(0);

        FilterSelectedEntity ff5 = new FilterSelectedEntity();
        ff5.setTid(5);
        ff5.setName("时间从早到晚");
        ff5.setSelected(0);


        FilterSelectedEntity ff6 = new FilterSelectedEntity();
        ff6.setTid(6);
        ff6.setName("时间从晚到早");
        ff6.setSelected(0);
        mSortList.add(ff);
        mSortList.add(ff1);
        mSortList.add(ff2);
        mSortList.add(ff3);
        mSortList.add(ff4);
        mSortList.add(ff5);
        mSortList.add(ff6);


        mAreaSizeList.add(ff);
        mAreaSizeList.add(ff1);
        mAreaSizeList.add(ff2);
        mAreaSizeList.add(ff3);
        mAreaSizeList.add(ff4);
        mAreaSizeList.add(ff5);
        mAreaSizeList.add(ff6);

    }


    /**
     * 设置更多数据
     */
    private void setMoreData() {
        FilterMulSelectEntity filterOneEntity = new FilterMulSelectEntity();
        List<FilterSelectedEntity> list = new ArrayList<>();
        filterOneEntity.setIsCan(1);
        filterOneEntity.setSelecteStatus(1);
        filterOneEntity.setSortdata(list);
        filterOneEntity.setSortname("形态");
        FilterSelectedEntity f1 = new FilterSelectedEntity();
        f1.setSelected(0);
        f1.setName("公寓");
        f1.setSelecteStatus(0);
        f1.setTid(1);
        list.add(f1);

        FilterSelectedEntity f2 = new FilterSelectedEntity();
        f2.setSelected(0);
        f2.setName("电梯大楼");
        f2.setSelecteStatus(0);
        f2.setTid(2);
        list.add(f2);

        FilterSelectedEntity f3 = new FilterSelectedEntity();
        f3.setSelected(0);
        f3.setName("透天");
        f3.setSelecteStatus(0);
        f3.setTid(3);
        list.add(f3);

        FilterSelectedEntity f4 = new FilterSelectedEntity();
        f4.setSelected(0);
        f4.setName("别墅");
        f4.setSelecteStatus(0);
        f4.setTid(4);
        list.add(f4);
//---------------------------------------------


        FilterMulSelectEntity filterOneEntity1 = new FilterMulSelectEntity();
        List<FilterSelectedEntity> list1 = new ArrayList<>();
        filterOneEntity1.setIsCan(1);
        filterOneEntity1.setSelecteStatus(1);
        filterOneEntity1.setSortdata(list1);
        filterOneEntity1.setSortname("屋龄");
        FilterSelectedEntity ff1 = new FilterSelectedEntity();
        ff1.setSelected(0);
        ff1.setName("5年以下");
        ff1.setSelecteStatus(0);
        ff1.setTid(1);
        list1.add(ff1);

        FilterSelectedEntity ff2 = new FilterSelectedEntity();
        ff2.setSelected(0);
        ff2.setName("5-10年");
        ff2.setSelecteStatus(0);
        ff2.setTid(2);
        list1.add(ff2);

        FilterSelectedEntity ff3 = new FilterSelectedEntity();
        ff3.setSelected(0);
        ff3.setName("10-20年");
        ff3.setSelecteStatus(0);
        ff3.setTid(3);
        list1.add(ff3);

        FilterSelectedEntity ff4 = new FilterSelectedEntity();
        ff4.setSelected(0);
        ff4.setName("20-30年");
        ff4.setSelecteStatus(0);
        ff4.setTid(4);
        list1.add(ff4);


//---------------------------------------------


        FilterMulSelectEntity filterOneEntity2 = new FilterMulSelectEntity();
        List<FilterSelectedEntity> list2 = new ArrayList<>();
        filterOneEntity2.setIsCan(1);
        filterOneEntity2.setSelecteStatus(1);
        filterOneEntity2.setSortdata(list2);
        filterOneEntity2.setSortname("楼层");
        FilterSelectedEntity fff1 = new FilterSelectedEntity();
        fff1.setSelected(0);
        fff1.setName("1楼以下");
        fff1.setSelecteStatus(0);
        fff1.setTid(1);
        list2.add(fff1);

        FilterSelectedEntity fff2 = new FilterSelectedEntity();
        fff2.setSelected(0);
        fff2.setName("1-3楼");
        fff2.setSelecteStatus(0);
        fff2.setTid(2);
        list2.add(fff2);

        FilterSelectedEntity fff3 = new FilterSelectedEntity();
        fff3.setSelected(0);
        fff3.setName("4-6楼");
        fff3.setSelecteStatus(0);
        fff3.setTid(3);
        list2.add(fff3);

        FilterSelectedEntity fff4 = new FilterSelectedEntity();
        fff4.setSelected(0);
        fff4.setName("7-9楼");
        fff4.setSelecteStatus(0);
        fff4.setTid(4);
        list2.add(fff4);


        mMoreList.add(filterOneEntity);
        mMoreList.add(filterOneEntity1);
        mMoreList.add(filterOneEntity2);


    }

    /**
     * 设置总价数据
     */
    private void setAllPriceData() {
        FilterSelectedEntity filterSelectedEntity = new FilterSelectedEntity();
        filterSelectedEntity.setTid(0);
        filterSelectedEntity.setName("不限");
        filterSelectedEntity.setSelecteStatus(1);
        FilterSelectedEntity filterSelectedEntity1 = new FilterSelectedEntity();
        filterSelectedEntity1.setTid(1);
        filterSelectedEntity1.setName("100");
        filterSelectedEntity1.setSelecteStatus(0);

        FilterSelectedEntity filterSelectedEntity2 = new FilterSelectedEntity();
        filterSelectedEntity2.setTid(2);
        filterSelectedEntity2.setName("200");
        filterSelectedEntity2.setSelecteStatus(0);

        FilterSelectedEntity filterSelectedEntity3 = new FilterSelectedEntity();
        filterSelectedEntity3.setTid(3);
        filterSelectedEntity3.setName("300");
        filterSelectedEntity3.setSelecteStatus(0);

        FilterSelectedEntity filterSelectedEntity4 = new FilterSelectedEntity();
        filterSelectedEntity4.setTid(4);
        filterSelectedEntity4.setName("400");
        filterSelectedEntity4.setSelecteStatus(0);

        FilterSelectedEntity filterSelectedEntity5 = new FilterSelectedEntity();
        filterSelectedEntity5.setTid(5);
        filterSelectedEntity5.setName("500");
        filterSelectedEntity5.setSelecteStatus(0);

        FilterSelectedEntity filterSelectedEntity6 = new FilterSelectedEntity();
        filterSelectedEntity6.setTid(6);
        filterSelectedEntity6.setName("600");
        filterSelectedEntity6.setSelecteStatus(0);

        mAllPriceList.add(filterSelectedEntity);
        mAllPriceList.add(filterSelectedEntity1);
        mAllPriceList.add(filterSelectedEntity2);
        mAllPriceList.add(filterSelectedEntity3);
        mAllPriceList.add(filterSelectedEntity4);
        mAllPriceList.add(filterSelectedEntity5);
        mAllPriceList.add(filterSelectedEntity6);
    }


    /**
     * 设置单价数据
     */
    private void setSinglePriceData() {
        FilterSelectedEntity filterSelectedEntity = new FilterSelectedEntity();
        filterSelectedEntity.setTid(0);
        filterSelectedEntity.setName("不限");
        filterSelectedEntity.setSelecteStatus(1);


        FilterSelectedEntity filterSelectedEntity1 = new FilterSelectedEntity();
        filterSelectedEntity1.setTid(1);
        filterSelectedEntity1.setName("1000");
        filterSelectedEntity1.setSelecteStatus(0);

        FilterSelectedEntity filterSelectedEntity2 = new FilterSelectedEntity();
        filterSelectedEntity2.setTid(2);
        filterSelectedEntity2.setName("2000");
        filterSelectedEntity2.setSelecteStatus(0);

        FilterSelectedEntity filterSelectedEntity3 = new FilterSelectedEntity();
        filterSelectedEntity3.setTid(3);
        filterSelectedEntity3.setName("3000");
        filterSelectedEntity3.setSelecteStatus(0);

        FilterSelectedEntity filterSelectedEntity4 = new FilterSelectedEntity();
        filterSelectedEntity4.setTid(4);
        filterSelectedEntity4.setName("4000");
        filterSelectedEntity4.setSelecteStatus(0);

        FilterSelectedEntity filterSelectedEntity5 = new FilterSelectedEntity();
        filterSelectedEntity5.setTid(5);
        filterSelectedEntity5.setName("5000");
        filterSelectedEntity5.setSelecteStatus(0);

        FilterSelectedEntity filterSelectedEntity6 = new FilterSelectedEntity();
        filterSelectedEntity6.setTid(6);
        filterSelectedEntity6.setName("6000");
        filterSelectedEntity6.setSelecteStatus(0);

        mSinglePriceList.add(filterSelectedEntity);
        mSinglePriceList.add(filterSelectedEntity1);
        mSinglePriceList.add(filterSelectedEntity2);
        mSinglePriceList.add(filterSelectedEntity3);
        mSinglePriceList.add(filterSelectedEntity4);
        mSinglePriceList.add(filterSelectedEntity5);
        mSinglePriceList.add(filterSelectedEntity6);
    }

    /**
     * 设置区域筛选框里面的数据list
     */
    private void setAreaData() {
        try {
            FilterAreaOneEntity filterOneEntity = new FilterAreaOneEntity();
            filterOneEntity.setArea_id(0);
            filterOneEntity.setName("区域0");
            filterOneEntity.setSelected(1);
            filterOneEntity.setFilterAreaEntityList(loadAreaData());

            FilterAreaOneEntity filterOneEntity2 = new FilterAreaOneEntity();
            filterOneEntity2.setArea_id(1);
            filterOneEntity2.setName("捷运1");
            filterOneEntity2.setSelected(0);
            filterOneEntity2.setFilterAreaEntityList(loadSubWayData());

            FilterAreaOneEntity filterOneEntity3 = new FilterAreaOneEntity();
            filterOneEntity3.setArea_id(2);
            filterOneEntity3.setName("附近2");
            filterOneEntity3.setSelected(0);
            filterOneEntity3.setFilterAreaEntityList(loadNearData());


            mAreaList.add(filterOneEntity);
            mAreaList.add(filterOneEntity2);
            mAreaList.add(filterOneEntity3);
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
        FilterAreaTwoEntity area = new FilterAreaTwoEntity();
        area.setArea_id(0);
        area.setName("1000");
        area.setSelected(0);

        FilterAreaTwoEntity area1 = new FilterAreaTwoEntity();
        area1.setArea_id(1);
        area1.setName("2000");
        area1.setSelected(0);

        FilterAreaTwoEntity area2 = new FilterAreaTwoEntity();
        area2.setArea_id(2);
        area2.setName("3000");
        area2.setSelected(0);

        areaList.add(area);
        areaList.add(area1);
        areaList.add(area2);
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
