package com.yangbin.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yangbin.base.BaseFilterBean;
import com.yangbin.lib_filtertab.R;
import com.yangbin.util.SpUtils;

import java.util.List;


public class AreaParentAdapter extends RecyclerView.Adapter{

    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private List<BaseFilterBean> mList;
    private Handler mHandler;
    /**
     * 当前选中的item position
     */

    public AreaParentAdapter(Context context, List<BaseFilterBean> list, Handler handler) {
        mContext = context;
        mList = list;
        mHandler = handler;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(  ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_area_parent, parent, false));
    }

    @Override
    public void onBindViewHolder(  RecyclerView.ViewHolder holder, final int position) {

        try {
            ViewHolder viewHolder = (ViewHolder) holder;

            if (mList != null) {
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
                    Message msg = new Message();
                    msg.obj = position;
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                }
                if(1== mList.get(position).getSelecteStatus()){
                    viewHolder.tv_content.setTextColor(mContext.getResources().getColor(R.color.color_FF6F00));
                }

                viewHolder.tv_content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BaseFilterBean baseFilterBean = mList.get(position);
                        for (int i = 0; i < mList.size(); i++) {
                            if (i == position) {
                                mList.get(position).setSelecteStatus(1);
                            } else {
                                mList.get(i).setSelecteStatus(0);
                            }
                        }
                        notifyDataSetChanged();
                        onItemClickListener.onItemClick(position);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
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
    public void addData(List<BaseFilterBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }
    public void clearData( ) {
        mList.clear();
        notifyDataSetChanged();
    }
}
