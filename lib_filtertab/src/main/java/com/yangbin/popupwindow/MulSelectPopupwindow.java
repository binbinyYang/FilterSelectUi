package com.yangbin.popupwindow;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yangbin.adapter.PopupMulAdapter;
import com.yangbin.base.BaseFilterBean;
import com.yangbin.base.BasePopupWindow;
import com.yangbin.bean.FilterResultBean;
import com.yangbin.lib_filtertab.R;
import com.yangbin.listener.OnFilterToViewListener;
import com.yangbin.util.SpUtils;
import com.yangbin.util.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 多分类筛选的多选样式
 */
public class MulSelectPopupwindow extends BasePopupWindow implements View.OnClickListener {
    private List<BaseFilterBean> mList;
    private int mTabPostion;
    private RecyclerView rv_content;
    private Button btn_reset;
    private Button btn_confirm;
    private LinearLayout ll_bottom;
    private LinearLayout ll_bottom_2;
    private PopupMulAdapter mAdapter;
    private List<FilterResultBean.MulTypeBean> mSelectList;
    private TextView tv_clean;
    private TextView tv_confirm;
    private View v_divide;
    private View v_outside;


    public MulSelectPopupwindow(Context context, List data, int filterType, int position, OnFilterToViewListener onFilterToViewListener) {
        super(context, data, filterType, position, onFilterToViewListener);
        mTabPostion = position;
    }

    @Override
    public View initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.popup_mul_select, null, false);
        rv_content = rootView.findViewById(R.id.rv_content);
        btn_reset = rootView.findViewById(R.id.btn_reset);
        btn_confirm = rootView.findViewById(R.id.btn_confirm);
        ll_bottom_2 = rootView.findViewById(R.id.ll_bottom_2);
        ll_bottom = rootView.findViewById(R.id.ll_bottom);
        tv_clean = rootView.findViewById(R.id.tv_clean);
        tv_confirm = rootView.findViewById(R.id.tv_confirm);
        v_divide = rootView.findViewById(R.id.v_divide);
        v_outside = rootView.findViewById(R.id.v_outside);
        mAdapter = new PopupMulAdapter(getContext(), getData());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_content.setLayoutManager(linearLayoutManager);
        rv_content.setAdapter(mAdapter);
        mSelectList = new ArrayList<>();

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rv_content.getLayoutParams();
        LinearLayout.LayoutParams bottomParams = (LinearLayout.LayoutParams) ll_bottom.getLayoutParams();
        LinearLayout.LayoutParams bottomParams2 = (LinearLayout.LayoutParams) ll_bottom_2.getLayoutParams();
        if (SpUtils.getInstance(mContext).getTextStyle() == 1) {
            ll_bottom_2.setVisibility(View.VISIBLE);
            ll_bottom.setVisibility(View.GONE);
            v_divide.setVisibility(View.GONE);
//            layoutParams.bottomMargin = Utils.dp2px(mContext, 48);
            rv_content.setLayoutParams(layoutParams);

            bottomParams.height = Utils.dp2px(mContext, 48);
            ll_bottom_2.setLayoutParams(bottomParams);

        } else {
            ll_bottom_2.setVisibility(View.GONE);
            ll_bottom.setVisibility(View.VISIBLE);
            v_divide.setVisibility(View.VISIBLE);
//            layoutParams.bottomMargin = Utils.dp2px(mContext, 69);
            rv_content.setLayoutParams(layoutParams);

            bottomParams2.height = Utils.dp2px(mContext, 69);
            ll_bottom.setLayoutParams(bottomParams2);
        }

        tv_confirm.setBackgroundColor(SpUtils.getInstance(mContext).getColorMain());
        tv_clean.setBackgroundColor(mContext.getResources().getColor(R.color.color_f5f5f6));

        v_outside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowing()) {
                    dismiss();
                    mAdapter.addData(mList);

                }
            }
        });
        return rootView;
    }

    @Override
    public void initSelectData() {
        GradientDrawable resetDrawable = new GradientDrawable();
        resetDrawable.setStroke(1, SpUtils.getInstance(mContext).getColorMain());
        resetDrawable.setCornerRadius(8);
        btn_reset.setBackgroundDrawable(resetDrawable);
        btn_reset.setTextColor(SpUtils.getInstance(mContext).getColorMain());
        // 重置
        btn_reset.setOnClickListener(this);
        tv_clean.setOnClickListener(this);

        // 确定
        btn_confirm.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_reset
                || v.getId() == R.id.tv_clean) {
            doReset();

        } else if (v.getId() == R.id.btn_confirm
                || v.getId() == R.id.tv_confirm) {
            doConfirm();
        }
    }

    private void doReset() {
        try {
            mSelectList.clear();
            List<BaseFilterBean> list = getData();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                BaseFilterBean parentBean = list.get(i);
                List<BaseFilterBean> childList = parentBean.getChildList();
                if (childList != null && childList.size() > 0) {
                    int childSize = childList.size();
                    for (int j = 0; j < childSize; j++) {
                        BaseFilterBean childBean = childList.get(j);
                        if (childBean.getSelecteStatus() == 1) {
                            childBean.setSelecteStatus(0);
                        }
                    }
                }
            }

            mAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doConfirm() {
        try {
            FilterResultBean resultBean = new FilterResultBean();
            resultBean.setPopupIndex(getPosition());
            resultBean.setPopupType(getFilterType());

            mSelectList.clear();
            List<BaseFilterBean> list = getData();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                BaseFilterBean parentBean = list.get(i);
                List<BaseFilterBean> childList = parentBean.getChildList();
                if (childList != null && childList.size() > 0) {
                    int childSize = childList.size();
                    for (int j = 0; j < childSize; j++) {
                        BaseFilterBean childBean = childList.get(j);
                        if (childBean.getSelecteStatus() == 1 && childBean.getId() != -1) {
                            FilterResultBean.MulTypeBean bean = new FilterResultBean.MulTypeBean();
                            bean.setItemId(childBean.getId());
                            bean.setTypeKey(parentBean.getSortKey());
                            bean.setItemName(childBean.getItemName());

                            mSelectList.add(bean);
                        }
                    }
                }
            }
            resultBean.setSelectList(mSelectList);
            mList = new ArrayList<>();
            mList.addAll(deepCopy(getData()));
            getOnFilterToViewListener().onFilterToView(resultBean,mTabPostion);
            dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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


    @Override
    public void refreshData() {
        mAdapter.notifyDataSetChanged();
    }
}
