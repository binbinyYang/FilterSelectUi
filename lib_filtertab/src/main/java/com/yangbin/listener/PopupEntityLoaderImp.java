package com.yangbin.listener;

import android.content.Context;
import android.widget.PopupWindow;


import com.yangbin.FilterTabConfig;
import com.yangbin.popupwindow.AreaSelectPopupWindow;
import com.yangbin.popupwindow.GridSelectPopupWindow;
import com.yangbin.popupwindow.MulSelectPopupwindow;
import com.yangbin.popupwindow.PriceHorizontalSelectPopupWindow;
import com.yangbin.popupwindow.PriceUprightSelectPopupWindow;
import com.yangbin.popupwindow.SingleSelectPopupWindow;
import com.yangbin.view.FilterTabView;
import com.yangbin.view.IPopupLoader;

import java.util.List;


public class PopupEntityLoaderImp implements IPopupLoader {




    @Override
    public PopupWindow getPopupEntity(Context context, List data, int filterType, int position, OnFilterToViewListener onFilterToViewListener, FilterTabView view) {

        PopupWindow popupWindow = null;

        switch (filterType) {
            case FilterTabConfig.FILTER_TYPE_AREA:
                popupWindow = new AreaSelectPopupWindow(context,data,filterType,position, onFilterToViewListener, view);
                break;
            case FilterTabConfig.FILTER_TYPE_PRICE_UPRIGHT:
                popupWindow = new PriceUprightSelectPopupWindow(context, data, filterType,position, onFilterToViewListener);
                break;
            case FilterTabConfig.FILTER_TYPE_SINGLE_SELECT:
                popupWindow = new SingleSelectPopupWindow(context,data,filterType,position, onFilterToViewListener);
                break;
            case FilterTabConfig.FILTER_TYPE_MUL_SELECT:
                popupWindow = new MulSelectPopupwindow(context,data,filterType,position, onFilterToViewListener);
                break;
            case FilterTabConfig.FILTER_TYPE_SINGLE_GIRD:
                popupWindow = new GridSelectPopupWindow(context,data,filterType,position, onFilterToViewListener);
                break;

            case FilterTabConfig.FILTER_TYPE_SINGLE_SELECT_HAVA_PIC:
                popupWindow = new SingleSelectPopupWindow(context,data,filterType,position, onFilterToViewListener);
                break;

            case FilterTabConfig.FILTER_TYPE_PRICE_HORIZONTAL:
                popupWindow = new PriceHorizontalSelectPopupWindow(context, data, filterType,position, onFilterToViewListener);
                break;
            default:
                break;
        }

        return popupWindow;
    }
}
