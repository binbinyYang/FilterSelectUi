![]( https://img.shields.io/badge/PRs-welcome-green.svg)    ![]( https://img.shields.io/badge/JitPack-1.0.0-green.svg)
# FilterSelectUi
房产类APP条件筛选框，包含区域（单选，多选），竖着排列（单选，多选），横向排列（单选，多选）
-------  

要将Git项目放入您的构建中：

 步骤1.将JitPack存储库添加到您的构建文件中
----

```java
 allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
 步骤2.添加依赖项 Tag= ![]( https://img.shields.io/badge/JitPack-1.0.0-green.svg)
----
```java
dependencies {
	        implementation 'com.github.binbinyYang:FilterSelectUi:Tag'
	}
```


![演示图1](https://github.com/binbinyYang/FilterSelectUi/blob/master/1.gif) ![演示图1](https://github.com/binbinyYang/FilterSelectUi/blob/master/2.gif)
![演示图1](https://github.com/binbinyYang/FilterSelectUi/blob/master/3.gif) ![演示图1](https://github.com/binbinyYang/FilterSelectUi/blob/master/4.gif)
![演示图1](https://github.com/binbinyYang/FilterSelectUi/blob/master/5.gif)
 

| 功能       | 区分    |  
| --------   | -----:   | 
| 支持区域二级联动选择/三级联动选择        |FilterTabConfig.FILTER_TYPE_AREA     | 
| 支持单行List样式选择		| FilterTabConfig.FILTER_TYPE_SINGLE_SELECT     | 
| 支持带EditText的单行选择（竖着）	        | FilterTabConfig.FILTER_TYPE_PRICE_UPRIGHT |   
| 支持带EditText的单行选择（横着）	        | FilterTabConfig.FILTER_TYPE_PRICE_HORIZONTAL |   
| 支持多类型选择		        | FilterTabConfig.FILTER_TYPE_MUL_SELECT |   
| 支持Grid样式多选			        | FilterTabConfig.FILTER_TYPE_SINGLE_GIRD |   
| 支持筛选框title是图片的样式选择        | FilterTabConfig.FILTER_TYPE_SINGLE_SELECT_HAVA_PIC|



XML部分
-------  
```java
  <com.yangbin.view.FilterTabView
        android:id="@+id/ftb_filter"
        android:layout_width="match_parent"
        android:layout_height="@dimen/tool_bar"
        android:background="@color/white"
        app:btn_solid_unselect_color="#F5F5F5"
        app:btn_corner_radius="24dp"
        app:btn_stroke_select_color="@color/color_ff8800"
        app:btn_stroke_unselect_color="#F5F5F5"
        app:btn_text_select_color="@color/color_ff8800"
        app:btn_text_unselect_color="@color/color_222222"
        app:tab_text_style="0"
        app:column_num="3"
        />
```

代码部分
-------  
```java
           FilterInfoBean bean_area = new FilterInfoBean("区域", FilterTabConfig.FILTER_TYPE_AREA, mAreaList);
            FilterInfoBean bean_price = new FilterInfoBean("总价", FilterTabConfig.FILTER_TYPE_PRICE_UPRIGHT, mAllPriceList);
            FilterInfoBean bean_sort = new FilterInfoBean("排序", FilterTabConfig.FILTER_TYPE_SINGLE_SELECT_HAVA_PIC, mSortList);
            FilterInfoBean bean_more = new FilterInfoBean("更多", FilterTabConfig.FILTER_TYPE_MUL_SELECT, mMoreList);
            FilterInfoBean bean5 = new FilterInfoBean("坪数", FilterTabConfig.FILTER_TYPE_SINGLE_GIRD, mSortList);
            FilterInfoBean bean6 = new FilterInfoBean("单价", FilterTabConfig.FILTER_TYPE_PRICE_HORIZONTAL, mSinglePriceList);

            ftb_filter.addFilterItem(bean_area.getTabName(), bean_area.getFilterData(), bean_area.getPopupType(), 0,false);
            ftb_filter.addFilterItem(bean_price.getTabName(), bean_price.getFilterData(), bean_price.getPopupType(), 1,false);
            ftb_filter.addFilterItem(bean_sort.getTabName(), bean_sort.getFilterData(), bean_sort.getPopupType(), 2,true);
            ftb_filter.addFilterItem(bean_more.getTabName(), bean_more.getFilterData(), bean_more.getPopupType(), 3,false);
            ftb_filter.addFilterItem(bean5.getTabName(), bean5.getFilterData(), bean5.getPopupType(), 4,false);
            ftb_filter.addFilterItem(bean6.getTabName(), bean6.getFilterData(), bean6.getPopupType(), 5,false);
```


javabean 要继承 BaseFilterBean，重写里面的方法
-------  
```java
         public class FilterAreaEntity extends BaseFilterBean {
    ....
}
```

需要用到的Activity或者fragment里面实现OnSelectResultListener这个接口-------  
```java
    ftb_filter.setOnSelectResultListener(new OnSelectResultListener() {
    @Override
    public void onSelectResult(FilterResultBean resultBean) {
        // 接受点击的返回值
    }
});
```
 
 
控件调用之前最好初始化一下，避免加载失败重新加载数据是出现重复的问题。
```java
 ftb_filter.removeViews();
  
```
  OnSelectFilterNameListener 实现这个接口可以拿到选择后对应的Tab名称
```java
 ftb_filter.setOnSelectFilterNameListener(new OnSelectFilterNameListener() {
    @Override
    public void onSelectFilterName(String name, int popupindex) {
        // name：tab名称  popupindex：对应的popup的位置
    }
});
```
 
 



|自定义属性      | 说明    |  
| --------   | -----:   | 
| tab_arrow_select_color        |筛选Tab选择的图片    | 
| tab_arrow_unselect_color		| 筛选Tab选择的图片  | 
| tab_text_style        | 选中状态是否为粗体 0为否 1为是 默认为0|   
| color_main	        | 主题色 |   
| btn_stroke_select_color		        | 选中button边框颜色 |   
| btn_stroke_unselect_color			        | 未选中button边框颜色 |   
| btn_solid_select_color        | 选中button填充颜色|
| btn_solid_unselect_color        | 未选中button填充颜色|
| btn_corner_radius        | button圆角大小|
| btn_text_select_color        | 选中字体颜色|
| btn_text_unselect_color        | 未选中字体颜色|
| column_num        | grid样式下的列数|

 
 
 
 
