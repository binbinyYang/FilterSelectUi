package com.yangbin.adapter;

import android.content.Context;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yangbin.base.BaseFilterBean;
import com.yangbin.lib_filtertab.R;
import com.yangbin.util.SpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域筛选--适配器  三级联动 第3个
 */
public class AreaThreeAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<BaseFilterBean> mList;
    private OnItemClickListener onItemClickListener;

    public AreaThreeAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_area_child, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        try {
            ViewHolder viewHolder = (ViewHolder) holder;
            BaseFilterBean bean = mList.get(position);
            viewHolder.tv_content.setText(bean.getItemName());

            // 是否设置“不限”为选中
            boolean isSelectFirst = true;
            for (int i = 0; i < mList.size(); i++) {
                BaseFilterBean entity = mList.get(i);
                if (entity.getSelecteStatus() == 1) {
                    isSelectFirst = false;
                    break;
                }
            }

            if (isSelectFirst) {
                mList.get(0).setSelecteStatus(1);
            }

            TextPaint textPaint = viewHolder.tv_content.getPaint();
            if (bean.getSelecteStatus() == 0) {
                textPaint.setFakeBoldText(false);
                viewHolder.tv_content.setTextColor(SpUtils.getInstance(mContext).getTextUnSelect());

            } else {
                if (SpUtils.getInstance(mContext).getTextStyle() == 1) {
                    textPaint.setFakeBoldText(true);
                }
                viewHolder.tv_content.setTextColor(SpUtils.getInstance(mContext).getTextSelect());
                viewHolder.tv_content.setBackgroundColor(mContext.getResources().getColor(R.color.white));

            }

            if (1 == mList.get(position).getSelecteStatus()) {
                viewHolder.tv_content.setTextColor(mContext.getResources().getColor(R.color.color_FF6F00));
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < mList.size(); i++) {
                        if (mList.get(position).getSelecteStatus() == 1) {
                            if (i == position && i != 0) {
                                mList.get(position).setSelecteStatus(0);
                            } else if (i == position && i == 0) {
                                mList.get(0).setSelecteStatus(1);
                            }
                        } else if (mList.get(position).getSelecteStatus() == 0) {

                            if (i == position && i != 0) {
                                mList.get(position).setSelecteStatus(1);
                                mList.get(0).setSelecteStatus(0);
                            } else {
                                if (i == position && i == 0) {
                                    mList.get(position).setSelecteStatus(1);
                                    for (int z = 0; z < mList.size(); z++) {
                                        if (z != 0) {
                                            mList.get(z).setSelecteStatus(0);
                                        }
                                    }

                                }
                            }
                        }


                    }
                    notifyDataSetChanged();
                    onItemClickListener.onItemClick(position);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addData(List<BaseFilterBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void cleanData() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_content;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
