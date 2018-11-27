package com.sosou.custompaging.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sosou.custompaging.custompaging_master.R;

import java.util.List;

public class GetCouponsAdapter extends RecyclerView.Adapter<GetCouponsAdapter.ViewHolder>
{

    /**
     * 列表
     */
    private List<String> mList;


    /**
     * 布局
     */
    private LayoutInflater mInflater;

    /**
     * 上下文对象
     */
    private Context mContext;


    /**
     * 构造方法
     * @param list
     * @param ctx
     */
    public GetCouponsAdapter(Context ctx, List<String> list)
    {
        mList = list;
        mContext = ctx;
        mInflater = LayoutInflater.from(mContext);

    }

    /**
     * 列表数据更新
     * @param list
     */
    public void setData(List<String> list)
    {
        this.mList = list;
        notifyDataSetChanged();
    }


    /**
     * 创建一个新的ViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // 创建View
        View v = mInflater.inflate(R.layout.item_list_live_coupons, parent, false);

        // 创建ViewHolder
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    /**
     * 数据的绑定显示
     * @param holder
     * @param position
     */
    public void onBindViewHolder(ViewHolder holder, int position)
    {

        // 设置控件参数
        String bean = mList.get(position);

        holder.nameTV.setText(bean);
    }

    /**
     * 重写 getItemViewType 方法
     * @param position
     * @return
     */
    public int getItemViewType(int position)
    {
        return position;
    }

    /**
     * 获取列表大小
     * @return
     */
    public int getItemCount()
    {
        return mList.size();
    }

    /**
     * 自定义ViewHolder，持有每个Item的的所有界面元素
     */
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        /**
         * 名字
         */
        private TextView nameTV;

        /**
         * 时间
         */
        private TextView timeTV;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTV = (TextView) itemView.findViewById(R.id.dialog_coupons_name);
            timeTV = (TextView) itemView.findViewById(R.id.dialog_coupons_time);
        }
    }
}
