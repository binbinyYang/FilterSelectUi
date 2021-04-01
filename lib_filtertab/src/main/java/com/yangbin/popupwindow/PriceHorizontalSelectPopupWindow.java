
package com.yangbin.popupwindow;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yangbin.adapter.ItemSelectAdapter2;
import com.yangbin.base.BaseFilterBean;
import com.yangbin.base.BasePopupWindow;
import com.yangbin.bean.FilterResultBean;
import com.yangbin.lib_filtertab.R;
import com.yangbin.listener.OnFilterToViewListener;
import com.yangbin.util.SpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 横着的可以输入价格的单选样式
 */
public class PriceHorizontalSelectPopupWindow extends BasePopupWindow {
    private int mTabPostion;
    private RecyclerView rv_content;
    private ItemSelectAdapter2 mAdapter;
    private TextView tv_bottom;
    private List<FilterResultBean> mSelectList;
    EditText et_min_price;
    EditText et_max_price;

    public PriceHorizontalSelectPopupWindow(Context context, List<BaseFilterBean> data, int filterType, int position, OnFilterToViewListener onFilterToViewListener) {
        super(context, data, filterType, position, onFilterToViewListener);
        mTabPostion = position;
    }

    @Override
    public View initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.popup_price_horizontal_select, null, false);
        rv_content = rootView.findViewById(R.id.rv_content);
        tv_bottom = rootView.findViewById(R.id.btn_price_confirm);
        et_min_price = rootView.findViewById(R.id.et_min_price);
        et_max_price = rootView.findViewById(R.id.et_max_price);
        boolean isCanMulSelect = getData().get(0).isCanMulSelect();
        mAdapter = new ItemSelectAdapter2(getContext(), getData(), true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rv_content.setLayoutManager(gridLayoutManager);
        rv_content.setAdapter(mAdapter);
        mSelectList = new ArrayList<>();

        tv_bottom.setBackgroundColor(SpUtils.getInstance(mContext).getColorMain());

//        tv_bottom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    // 最小金额
//                    String minPrice = et_min_price.getText().toString().trim();
//                    // 最大金额
//                    String maxPrice = et_max_price.getText().toString().trim();
//                    String message = null;
//
//                    if (TextUtils.isEmpty(minPrice) && TextUtils.isEmpty(maxPrice)) {
//                        message = mContext.getResources().getString(R.string.all_empty);
//                        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
//                        return;
//                    }
//
//                    if (TextUtils.isEmpty(minPrice)) {
//                        message = mContext.getResources().getString(R.string.min_empty);
//                        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
//                        return;
//                    }
//
//                    if (TextUtils.isEmpty(maxPrice)) {
//                        message = mContext.getResources().getString(R.string.max_empty);
//                        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
//                        return;
//                    }
//
//                    int min = TextUtils.isEmpty(minPrice) ? 0 : Integer.valueOf(minPrice);
//                    int max = TextUtils.isEmpty(maxPrice) ? 0 : Integer.valueOf(maxPrice);
//
//                    if (min > max) {
//                        message = mContext.getResources().getString(R.string.min_max);
//                        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
//                        return;
//                    }
//
//                    String name = minPrice + "-" + maxPrice;
//
//                    FilterResultBean resultBean = new FilterResultBean();
//                    resultBean.setPopupType(getFilterType());
//                    resultBean.setPopupIndex(getPosition());
//                    resultBean.setItemId(-2);
//                    resultBean.setName(name);
//                    // -2 是用来区分手动输入价格
//                    getOnFilterToViewListener().onFilterToView(resultBean, mTabPostion);
//
//                    // 重置list信息
//                    List<BaseFilterBean> list = getData();
//                    for (int i = 0; i < list.size(); i++) {
//                        BaseFilterBean bean = list.get(i);
//                        if (i == 0) {
//                            bean.setSelecteStatus(1);
//                        } else {
//                            bean.setSelecteStatus(0);
//                        }
//                    }
////                    adapter.notifyDataSetChanged();
////
////                    int filterHeight = mAnchor.getHeight();
////
////
////                    RelativeLayout.LayoutParams layoutParams
////                            = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
////                    layoutParams.topMargin = recyclerHeight;
////                    ll_input.setLayoutParams(layoutParams);
////                    int h = mScreenHeight - filterHeight - toolbarHeight - statusBarHeight;
////                    update(mAnchor, WindowManager.LayoutParams.MATCH_PARENT, h);
//                    dismiss();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });

     et_max_price.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

         }

         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {

         }

         @Override
         public void afterTextChanged(Editable s) {

             if(s.toString().length()>0){
                 mAdapter.resetAll(getData());
             }
         }
     });



        et_min_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().length()>0){
                    mAdapter.resetAll(getData());
                }
            }
        });

     mAdapter.setOnItemClickListener(new  ItemSelectAdapter2.OnItemClickListener() {
         @Override
         public void onItemClick(int position) {
              et_min_price.setText("");
              et_max_price.setText("");
         }
     });
        View v_outside = rootView.findViewById(R.id.v_outside);
        v_outside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowing()) {
                    dismiss();
                }
            }
        });
        return rootView;
    }

    @Override
    public void initSelectData() {
        tv_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String minPrice = et_min_price.getText().toString().trim();
                    // 最大金额
                    String maxPrice = et_max_price.getText().toString().trim();
                    String message = null;
                    int min = TextUtils.isEmpty(minPrice) ? 0 : Integer.valueOf(minPrice);
                    int max = TextUtils.isEmpty(maxPrice) ? 0 : Integer.valueOf(maxPrice);
                    if (!(min>0) || !(max>0)) {
                        mSelectList.clear();
                        List<BaseFilterBean> list = getData();
                        if (list != null && list.size() > 0) {
                            for (int i = 0; i < list.size(); i++) {
                                BaseFilterBean bean = list.get(i);
                                if (bean.getSelecteStatus() == 1) {
                                    int itemId = bean.getId();
                                    String itemName = bean.getItemName();
                                    FilterResultBean resultBean = new FilterResultBean();
                                    resultBean.setPopupIndex(getPosition());
                                    resultBean.setPopupType(getFilterType());
                                    resultBean.setItemId(itemId);
                                    resultBean.setName(itemName);
                                    mSelectList.add(resultBean);
                                }
                            }
                        }
                        getOnFilterToViewListener().onFilterListToView(mSelectList, mTabPostion);
                    }else  if (min>0 || max>0) {
                        if (min > max) {
                            message = mContext.getResources().getString(R.string.min_max);
                            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                            return;
                        }
                        String name = minPrice + "-" + maxPrice;
                        mSelectList.clear();
                        FilterResultBean resultBean = new FilterResultBean();
                        resultBean.setPopupType(getFilterType());
                        resultBean.setPopupIndex(getPosition());
                        resultBean.setItemId(99);
                        resultBean.setName(name);
                        // -2 是用来区分手动输入价格
                        mSelectList.add(resultBean);
                        getOnFilterToViewListener().onFilterListToView(mSelectList, mTabPostion);

                    }
                    dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void refreshData() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }
}
