package com.yangbin.popupwindow;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yangbin.adapter.AreaThreeAdapter;
import com.yangbin.adapter.AreaParentAdapter;
import com.yangbin.adapter.AreaTwoAdapter;
import com.yangbin.base.BaseFilterBean;
import com.yangbin.base.BasePopupWindow;
import com.yangbin.bean.FilterResultBean;
import com.yangbin.lib_filtertab.R;
import com.yangbin.listener.OnAdapterRefreshListener;
import com.yangbin.listener.OnFilterToViewListener;
import com.yangbin.view.FilterTabView;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 区域联动筛选Popupwindow
 */
public class AreaSelectPopupWindow extends BasePopupWindow implements OnAdapterRefreshListener, View.OnClickListener {
    private int mTabPostion;

    private RecyclerView rv_parent;
    private RecyclerView rv_one;
    private RecyclerView rv_child;
    private Button mBtReset;
    private Button mBtOk;

    private AreaParentAdapter mParentAdapter;
    private AreaTwoAdapter mTwoParentAdapter;
    private AreaThreeAdapter mChildAdapter;

    /**
     * 初始化数据
     */
    private List<BaseFilterBean> initList;

    /**
     * 初始化数据
     */
    private List<BaseFilterBean> sureList;


    /**
     * 总数据
     */
    private List<BaseFilterBean> mParentList;

    /**
     * 当前点击的一级分类数据
     */
    private BaseFilterBean mCurrentClickParentBean;

    /**
     * 当前点击的二级分类数据
     */
    private BaseFilterBean mCurrentTwoClickParentBean;
    /**
     * 当前点击的一级Position
     */
    private int mCurrentClickParentPosition = 0;

    /**
     * 当前点击的二级Position
     */
    private int mCurrentTwoClickParentPosition;

    /**
     * 当前点击的三级Position
     */
    private int mCurrentThreeClickParentPosition;

    /**
     * 当前点击的一级分类数据
     */
    private BaseFilterBean mSureOneBean;

    /**
     * 当前点击的二级分类数据
     */
    private BaseFilterBean mSureTwoBean;

    /**
     * 当前点击的三级分类数据
     */
    private BaseFilterBean mSureThreeBean;


    /**
     * 当前点击的一级Position
     */
    private int sureOnePosition = 0;

    /**
     * 当前点击的二级Position
     */
    private int sureTwoPosition;

    /**
     * 当前点击的三级Position
     */
    private int sureThreePosition;


    private List<BaseFilterBean> oneList;
    private List<BaseFilterBean> twoList;
    private List<BaseFilterBean> threeList;


    public AreaSelectPopupWindow(Context context, List data, int filterType, int position, OnFilterToViewListener onFilterToViewListener, FilterTabView view) {
        super(context, data, filterType, position, onFilterToViewListener);
        view.setOnAdapterRefreshListener(this);
        mTabPostion = position;
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
    public View initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.popup_area_select, null, false);
        rv_one = rootView.findViewById(R.id.rv_one);
        rv_parent = rootView.findViewById(R.id.rv_parent);
        mBtReset = rootView.findViewById(R.id.bt_reset);
        mBtReset.setOnClickListener(this);
        mBtOk = rootView.findViewById(R.id.bt_ok);
        mBtOk.setOnClickListener(this);
        mParentList = getData();
        initList = new ArrayList<>();
        initList.addAll(deepCopy(mParentList));
        Log.e("=====", "init1" + initList.toString());


        Handler mHandler = new Handler() {
            @Override
            public void dispatchMessage(Message msg) {
                if (msg.what == 1) {
                    int position = (int) msg.obj;
                    if (position != -1) {
                        BaseFilterBean clickParentBean = mParentList.get(position);
                        if (clickParentBean.getChildList() != null && clickParentBean.getChildList().size() > 0) {
                            mTwoParentAdapter.addData(clickParentBean.getChildList());
//                           mTwoParentAdapter.updateItemsData(clickParentBean.getChildList());
                        }
                    }
                } else if (msg.what == 2) {
                    int position = (int) msg.obj;
                    if (position != -1) {
                        List<BaseFilterBean> clickParentBean = mParentList.get(mCurrentClickParentPosition).getChildList();
                        if (clickParentBean.get(position).getChildList() != null && clickParentBean.get(position).getChildList().size() > 0) {
                            mChildAdapter.addData(clickParentBean.get(position).getChildList());
                        }
                    }
                }

            }
        };

        // 获取默认显示的值
        if (mParentList != null && mParentList.size() > 0) {
            int size = mParentList.size();
            for (int i = 0; i < size; i++) {
                BaseFilterBean bean = mParentList.get(i);
                if (bean.getSelecteStatus() == 1 && bean.getId() != -1) {
                    mCurrentClickParentBean = bean;
                    mCurrentClickParentPosition = i;
                    break;
                }
            }
        }

        mParentAdapter = new AreaParentAdapter(getContext(), mParentList, mHandler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_parent.setLayoutManager(linearLayoutManager);
        rv_parent.setAdapter(mParentAdapter);

        mTwoParentAdapter = new AreaTwoAdapter(getContext(), new ArrayList<BaseFilterBean>(), mHandler);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        rv_one.setLayoutManager(linearLayoutManager1);
        rv_one.setAdapter(mTwoParentAdapter);


        rv_child = rootView.findViewById(R.id.rv_child);
        rv_child.setVisibility(View.GONE);

        mChildAdapter = new AreaThreeAdapter(getContext());
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        rv_child.setLayoutManager(linearLayoutManager2);
        rv_child.setAdapter(mChildAdapter);


        View v_outside = rootView.findViewById(R.id.v_outside);
        v_outside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isShowing()) {
                    dismiss();

                    if(oneList!=null) {
                        List<BaseFilterBean> list = new ArrayList<>();
                        list.addAll(deepCopy(oneList));
                        mParentAdapter.addData(list);
                        mTwoParentAdapter.addData(twoList);
                        mChildAdapter.addData(threeList);
                        mParentAdapter.notifyDataSetChanged();
                        mTwoParentAdapter.notifyDataSetChanged();
                        mChildAdapter.notifyDataSetChanged();

                        mCurrentTwoClickParentBean = twoList.get(sureTwoPosition);
                        mCurrentClickParentBean = oneList.get(sureOnePosition);
                        mCurrentClickParentPosition = sureOnePosition;
                        mCurrentTwoClickParentPosition = sureTwoPosition;
                    }else {
                        List<BaseFilterBean> list = new ArrayList<>();
                        list.addAll(deepCopy(initList));
                        mParentAdapter.addData(list);

                        mTwoParentAdapter.addData(  initList.get(mCurrentClickParentPosition).getChildList());
                        BaseFilterBean b = (BaseFilterBean) initList.get(mCurrentClickParentPosition).getChildList().get(0);
                        if(b.getChildList()!=null) {
                            mChildAdapter.addData((List<BaseFilterBean>) b.getChildList());
                        }else {
                            mChildAdapter.addData( new ArrayList<BaseFilterBean>());
                        }
                        mParentAdapter.notifyDataSetChanged();
                        mTwoParentAdapter.notifyDataSetChanged();
                        mChildAdapter.notifyDataSetChanged();

                        mCurrentTwoClickParentBean = (BaseFilterBean) initList.get(mCurrentClickParentPosition).getChildList().get(mCurrentClickParentPosition);
                        mCurrentClickParentBean = initList.get(mCurrentClickParentPosition);
                        mCurrentClickParentPosition = sureOnePosition;
                        mCurrentTwoClickParentPosition = sureTwoPosition;


                    }

                    if (mCurrentTwoClickParentPosition == 0 || mCurrentClickParentPosition == 2) {
                        rv_child.setVisibility(View.GONE);
                    } else {
                        rv_child.setVisibility(View.VISIBLE);
                    }

                }
            }
        });
        return rootView;
    }

    @Override
    public void initSelectData() {
        mParentAdapter.setOnItemClickListener(new AreaParentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                try {
                    mCurrentClickParentBean = mParentList.get(position);
                    mCurrentTwoClickParentBean= mParentList.get(position) ;
                    mCurrentClickParentPosition = position;
                    List<BaseFilterBean> childList = mCurrentClickParentBean.getChildList();
                    if (childList != null && childList.size() > 0) {
                        mTwoParentAdapter.addData(childList);
                    } else {
                        mTwoParentAdapter.cleanData();
                    }

                    rv_child.setVisibility(View.GONE);
                    // -1 即为点击“不限”
                    if (mCurrentClickParentBean.getId() == -1) {
                        FilterResultBean resultBean = new FilterResultBean();
                        resultBean.setPopupType(mCurrentClickParentPosition);
                        resultBean.setPopupIndex(getPosition());
                        getOnFilterToViewListener().onFilterToView(resultBean,mTabPostion);
                        dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        mTwoParentAdapter.setOnItemClickListener(new AreaTwoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                try {
                    if (mCurrentClickParentPosition == 2) {
                        mCurrentTwoClickParentBean = (BaseFilterBean) mParentList.get(mCurrentClickParentPosition).getChildList().get(position);
                        mCurrentTwoClickParentPosition = position;
                        rv_child.setVisibility(View.GONE);
                    } else {


                        if (position == 0&&mCurrentClickParentPosition==0) {
                            rv_child.setVisibility(View.GONE);
                            mCurrentTwoClickParentBean = (BaseFilterBean) mParentList.get(mCurrentClickParentPosition).getChildList().get(position);
                            mCurrentTwoClickParentPosition = position;

                            FilterResultBean resultBean = new FilterResultBean();
                            resultBean.setPopupType(mCurrentClickParentPosition);
                            resultBean.setPopupIndex(getPosition());
                        } else {
                            mCurrentTwoClickParentBean = (BaseFilterBean) mParentList.get(mCurrentClickParentPosition).getChildList().get(position);
                            mCurrentTwoClickParentPosition = position;
                            List<BaseFilterBean> childList = mCurrentTwoClickParentBean.getChildList();
                            rv_child.setVisibility(View.VISIBLE);
                            if (childList != null && childList.size() > 0) {
                                for (int i = 0; i < childList.size(); i++) {
                                    if (i == 0) {
                                        childList.get(0).setSelecteStatus(1);
                                    } else {
                                        childList.get(i).setSelecteStatus(0);
                                    }
                                }
                                mChildAdapter.addData(childList);
                            } else {
//                        mChildAdapter.cleanData();
                            }
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        mChildAdapter.setOnItemClickListener(new AreaThreeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                try {
                    if (mCurrentClickParentBean != null) {
                        mCurrentThreeClickParentPosition = position;
                        // 点击之前需要清除其他一级分类下选择的二级分类
                        int size = mCurrentTwoClickParentBean.getChildList().size();
                        for (int i = 0; i < size; i++) {
                            if (i != mCurrentClickParentPosition) {
                                BaseFilterBean bean = (BaseFilterBean) mCurrentTwoClickParentBean.getChildList().get(i);


                                List<BaseFilterBean> childList = bean.getChildList();


                                if (childList != null && childList.size() > 0) {
                                    int childSize = childList.size();
                                    for (int j = 0; j < childSize; j++) {
                                        BaseFilterBean childBean = childList.get(j);
                                        childBean.setSelecteStatus(0);
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



    }

    @Override
    public void onRefresh(BaseFilterBean selectBean) {
        mCurrentClickParentBean = selectBean;
        mParentAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_ok) {

            if (mCurrentClickParentPosition == 2) {
                FilterResultBean resultBean = new FilterResultBean();
                resultBean.setItemId(mCurrentTwoClickParentBean.getId());
                resultBean.setPopupType(mCurrentClickParentPosition);
                resultBean.setChildId(mCurrentClickParentBean.getId());
                resultBean.setPopupIndex(getPosition());
                resultBean.setName(mCurrentTwoClickParentBean.getItemName());


                mSureOneBean = mCurrentClickParentBean;
                mSureTwoBean = (BaseFilterBean) mCurrentClickParentBean.getChildList().get(mCurrentTwoClickParentPosition);
                mSureThreeBean = (BaseFilterBean) mCurrentClickParentBean.getChildList().get(0);
                sureList = new ArrayList<>();
                sureList.addAll(deepCopy(mParentList));
                sureOnePosition = mCurrentClickParentPosition;
                sureTwoPosition = mCurrentTwoClickParentPosition;
                sureThreePosition = 0;
                oneList = new ArrayList<>();
                oneList.addAll(deepCopy(sureList));


                twoList = new ArrayList<>();
                twoList.addAll(deepCopy(mSureOneBean.getChildList()));

                threeList = new ArrayList<>();
                threeList.addAll(threeList);
                rv_child.setVisibility(View.GONE);


                getOnFilterToViewListener().onFilterToView(resultBean,mTabPostion);
            } else {
                if (mCurrentTwoClickParentPosition == 0 && mCurrentClickParentPosition == 0) {
                    List<FilterResultBean> resultBeansList = new ArrayList<>();
                    FilterResultBean resultBean = new FilterResultBean();
                    resultBean.setItemId(0);
                    resultBean.setPopupType(mCurrentClickParentPosition);
                    resultBean.setChildId(0);
                    resultBean.setPopupIndex(getPosition());
                    resultBean.setName(mCurrentTwoClickParentBean.getItemName()   );
                    resultBeansList.add(resultBean);


                    mSureOneBean = mCurrentClickParentBean;
                    mSureTwoBean = mCurrentClickParentBean;
                    mSureThreeBean = (BaseFilterBean) mCurrentClickParentBean.getChildList().get(0);
                    sureList = new ArrayList<>();
                    sureList.addAll(deepCopy(mParentList));
                    sureOnePosition = mCurrentClickParentPosition;
                    sureTwoPosition = mCurrentTwoClickParentPosition;
                    sureThreePosition = 0;
                    oneList = new ArrayList<>();
                    oneList.addAll(deepCopy(sureList));


                    twoList = new ArrayList<>();
                    twoList.addAll(deepCopy(mSureOneBean.getChildList()));

                    threeList = new ArrayList<>();
                    threeList.addAll(threeList);
                    rv_child.setVisibility(View.GONE);
                    getOnFilterToViewListener().onFilterListToView(resultBeansList,mTabPostion);
                } else {
                    List<BaseFilterBean> childList = mCurrentTwoClickParentBean.getChildList();
                    List<FilterResultBean> resultBeansList = new ArrayList<>();
                    mSureOneBean = mCurrentClickParentBean;
                    mSureTwoBean = mCurrentClickParentBean;
                    mSureThreeBean = (BaseFilterBean) mCurrentClickParentBean.getChildList().get(mCurrentTwoClickParentPosition);
                    sureList = new ArrayList<>();
                    sureList.addAll(deepCopy(mParentList));
                    sureOnePosition = mCurrentClickParentPosition;
                    sureTwoPosition = mCurrentTwoClickParentPosition;
                    sureThreePosition = mCurrentThreeClickParentPosition;
                    oneList = new ArrayList<>();
                    oneList.addAll(deepCopy(sureList));


                    twoList = new ArrayList<>();
                    twoList.addAll(deepCopy(mSureOneBean.getChildList()));

                    threeList = new ArrayList<>();
                    threeList.addAll(deepCopy(mSureThreeBean.getChildList()));


                    for (int i = 0; i < childList.size(); i++) {
                        if (childList.get(i).getSelecteStatus() == 1) {
                            FilterResultBean resultBean = new FilterResultBean();
                            resultBean.setItemId(mCurrentTwoClickParentBean.getId());
                            resultBean.setPopupType(mCurrentClickParentPosition);
                            resultBean.setChildId(childList.get(i).getId());
                            resultBean.setPopupIndex(getPosition());
                            resultBean.setName(childList.get(i).getItemName());
                            resultBeansList.add(resultBean);
                        }
                    }
                    getOnFilterToViewListener().onFilterListToView(resultBeansList,mTabPostion);
                }
            }
            dismiss();
        } else if (id == R.id.bt_reset) {

            List<BaseFilterBean> list = new ArrayList<>();
            list.addAll(deepCopy(initList));
            mParentAdapter.addData(list);

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getSelecteStatus() == 1) {
                    mCurrentClickParentBean = list.get(i);
                    mCurrentClickParentPosition = i;

                }
            }

            for (int y = 0; y < list.get(mCurrentClickParentPosition).getChildList().size(); y++) {
                List<BaseFilterBean> childList = list.get(mCurrentClickParentPosition).getChildList();
                if (childList.get(y).getSelecteStatus() == 1) {
                    mCurrentTwoClickParentBean = (BaseFilterBean) list.get(mCurrentClickParentPosition).getChildList().get(y);
                    mCurrentTwoClickParentPosition = y;
                }
            }


            mTwoParentAdapter.notifyDataSetChanged();
            mChildAdapter.notifyDataSetChanged();
            List<BaseFilterBean> childList = initList.get(0).getChildList();
            if (childList.get(0).getSelecteStatus() == 1) {
                rv_child.setVisibility(View.GONE);
            } else {
                rv_child.setVisibility(View.VISIBLE);
            }

//            dismiss();
        }
    }
}


