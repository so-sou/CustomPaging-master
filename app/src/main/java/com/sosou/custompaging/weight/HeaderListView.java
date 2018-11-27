package com.sosou.custompaging.weight;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * [简要描述]:自定义RecyclerView<br/>
 * [详细描述]:用于拦截触摸事件<br/>
 * 
 */
public class HeaderListView extends RecyclerView
{

    /**
     * 相对位置
     */
    public static final float RELATIVE_TO_SELF_HALF = 0.5f;

    /**
     * 用于判断上下滑动
     */
    public static final int OFFSET = 10;

    /**
     * 标题栏
     */
    private View titleBar;

    /**
     * 标题文字
     */
    private TextView titleTv;

    /**
     * 副标题文字
     */
    private TextView subTitleTv;

    /**
     * 动画器
     */
    private AnimationSet animator;

    /**
     * 标题栏正常高度
     */
    private int tHeight;

    /**
     * 标题栏展开高度
     */
    private int tExpendHeight;

    /**
     * 正常高度于展开高度的差
     */
    private int tOffset;

    /**
     * 布局对象
     */
    private LinearLayoutManager llm;

    /**
     * 监听器
     */
    private OnHeaderListViewListener listener;

    /**
     * 有滑动头部
     */
    private boolean hasHeader;

    /**
     * 动画拦截器,用于控制动画进度
     */
    private Interpolator myInterpolator = new Interpolator()
    {
        @Override
        public float getInterpolation(float rate)
        {
            int offset = titleBar.getHeight() - tHeight;

            // 高度为0时直接返回1f
            if (0 == offset)
            {
                return 1f;
            }
            return (tOffset - offset) * 1f / tOffset;
        }
    };

    /**
     * [简要描述]:构造函数<br/>
     * [详细描述]:构造函数<br/>
     *
     * @param context 上下文对象
     * @param attrs 属性表
     * @param defStyle 样式
     */
    public HeaderListView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * [简要描述]:构造函数<br/>
     * [详细描述]:构造函数<br/>
     *
     * @param context 上下文对象
     * @param attrs 属性表
     */
    public HeaderListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    /**
     * [简要描述]:设置头部参数<br/>
     * [详细描述]:设置头部参数<br/>
     *
     * @param tb 标题栏
     * @param tv 标题文本
     * @param stv 副标题文本
     * @param th 正常高度
     * @param te 展开高度
     */
    public void setHeader(View tb, TextView tv, TextView stv, int th, int te)
    {
        this.titleBar = tb;
        this.titleTv = tv;
        this.subTitleTv = stv;
        this.tHeight = th;
        this.tExpendHeight = te;
        this.tOffset = te - th;
        this.setPadding(0, tOffset, 0, 0);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.getLayoutParams();
        lp.topMargin = th;
        this.setLayoutParams(lp);
        unExpendAnimation();
        this.hasHeader = true;
    }

    /**
     * [简要描述]:设置监听器<br/>
     * [详细描述]:设置监听器<br/>
     *
     * @param l 监听器
     */
    public void setOnHeaderListViewListener(OnHeaderListViewListener l)
    {
        this.listener = l;
    }

    /**
     * [简要描述]:初始化<br/>
     * [详细描述]:初始化<br/>
     *
     * @param ctx 上下文对象
     */
    private void init(Context ctx)
    {
        llm = new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false);
        setLayoutManager(llm);
        this.setOnScrollListener(new MyOnScrollListener());
    }

    /**
     * [简要描述]:获取当前子项的总高度<br/>
     * [详细描述]:获取当前子项的总高度<br/>
     *
     * @param count 数值
     * @return 总高度
     */
    public int getItemTotalHeight(int count)
    {
        if (count == 0)
        {
            return 0;
        }

        View view = this.getChildAt(0);

        // 布局为空则返回零
        if (view == null)
        {
            return 0;
        }
        return view.getHeight() * count;
    }

    private void unExpendAnimation()
    {

//        // 缩小后的标题文字尺寸
//        int smailSize = getContext().getResources().getDimensionPixelSize(R.dimen.font_size_title);
//
//        // 放大后的标题文字尺寸
//        int bigSize = getContext().getResources().getDimensionPixelSize(R.dimen.wallet_expend_title_size);
//
//        // 标题内边距
//        int titlePadding = getContext().getResources().getDimensionPixelSize(R.dimen.space_large);
//
//        // 缩小后文字宽度
//        float smallWidth = DisplayUtil.getTextWidth(titleTv.getText().toString(), smailSize, false);
//
//        // 放大后文字宽度
//        float bigWidth = DisplayUtil.getTextWidth(titleTv.getText().toString(), bigSize, false);
//
//        // 放大后文字高度
//        int bigHeight = DisplayUtil.getTextHeight(titleTv.getText().toString(), bigSize, false);
//
//        // 缩小比例
//        float scale = smallWidth / bigWidth;
//
//        // 屏幕宽度
//        int screenWidth = DisplayUtil.getScreenWidth(getContext());
//
//        animator = new AnimationSet(true);
//        animator.setInterpolator(myInterpolator);
//
//        animator.setFillEnabled(true);
//        animator.setFillAfter(true);
//
//        ScaleAnimation scaleAnima = new ScaleAnimation(1f, scale, 1f, scale, Animation.RELATIVE_TO_SELF,
//                RELATIVE_TO_SELF_HALF, Animation.RELATIVE_TO_SELF, RELATIVE_TO_SELF_HALF);
//        animator.addAnimation(scaleAnima);
//
//        TranslateAnimation translateAnima = new TranslateAnimation(0, (screenWidth - bigWidth) / 2 - titlePadding, 0,
//                -tHeight + (tHeight - bigHeight) / 2);
//        animator.addAnimation(translateAnima);
//        titleTv.startAnimation(animator);
//
//        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
//        alphaAnimation.setInterpolator(myInterpolator);
//        alphaAnimation.setFillEnabled(true);
//        alphaAnimation.setFillAfter(true);
//        subTitleTv.startAnimation(alphaAnimation);
    }

    /**
     * [简要描述]:修改头部高度<br/>
     * [详细描述]:修改头部高度<br/>
     *
     * @param hOffset 高度
     */
    private boolean changeTitleHeight(int hOffset)
    {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) titleBar.getLayoutParams();
        int height = tExpendHeight - hOffset;
        height = Math.max(height, tHeight);
        lp.height = height;
        titleBar.setLayoutParams(lp);

        return height == tHeight;
    }

    /**
     * [简要描述]:滚动处理器<br/>
     * [详细描述]:滚动处理器<br/>
     *
     */
    class MyOnScrollListener extends RecyclerView.OnScrollListener
    {

        /**
         * 表示头部隐藏了
         */
        private boolean isHeadHide;

        /**
         * 滚动条位置
         */
        private int scrollY;

        /**
         * [简要描述]:重置滚动状态<br/>
         * [详细描述]:重置滚动状态<br/>
         *
         * @author TangCheng tWX326901
         */
        public void reset()
        {
            scrollY = 0;
        }

        /**
         * [简要描述]:滚动事件<br/>
         * [详细描述]:滚动事件<br/>
         *
         * @param recyclerView 列表控件
         * @param newState 滚动状态
         * @exception
         * @see RecyclerView.OnScrollListener#onScrollStateChanged(RecyclerView,
         *      int)
         */
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState)
        {
            switch (newState)
            {
                // 停止滚动
                case RecyclerView.SCROLL_STATE_IDLE:
                    break;
                // 滑动
                case RecyclerView.SCROLL_STATE_DRAGGING:
                    break;
                case RecyclerView.SCROLL_STATE_SETTLING:
                    break;
            }
            super.onScrollStateChanged(recyclerView, newState);
        }

        /**
         * [简要描述]:滚动事件<br/>
         * [详细描述]:监听滚动事件<br/>
         *
         * @param recyclerView 布局
         * @param dx 布局
         * @param dy 开始位置
         * @exception
         * @see RecyclerView.OnScrollListener#onScrolled(RecyclerView,
         *      int, int)
         */
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy)
        {
            super.onScrolled(recyclerView, dx, dy);

            int childCount = recyclerView.getChildCount();
            if (childCount == 0)
            {
                return;
            }

            // 检查是否需要加载下一页
            if (listener != null && !listener.isDataLoadding())
            {
                localLoadCheck();
            }

            // 未设置头部，则不处理下面逻辑
            if (!hasHeader)
            {
                return;
            }

            scrollY += dy;

            // 如果目前处于第一项,则使头部跟随第一项移动
            if (scrollY <= tOffset)
            {
                isHeadHide = changeTitleHeight(scrollY);
            }

            // 如果头部不是隐藏的
            else if (!isHeadHide)
            {
                isHeadHide = changeTitleHeight(scrollY);
            }

        }

        /**
         * 局部加载检查 如果当前滚动到最后一项了,则加载下一页数据
         */
        private void localLoadCheck()
        {
            // 取得当前最后一个可视的item
            int lastVisiblePostion = llm.findLastVisibleItemPosition();

            // 如果最后可视item就是列表的最后一项,则加载下一页数据
            if (lastVisiblePostion == listener.getItemCount() - 1)
            {
                listener.onNextPage();
            }
        }
    }

    /**
     * [简要描述]:监听器<br/>
     * [详细描述]:监听器<br/>
     *
     */
    public interface OnHeaderListViewListener
    {
        /**
         * [简要描述]:获取子项数量<br/>
         * [详细描述]:获取子项数量<br/>
         *
         * @return 子项数量
         */
        int getItemCount();

        /**
         * [简要描述]:加载下一页<br/>
         * [详细描述]:加载下一页<br/>
         *
         * @return 是否有下一页
         */
        boolean onNextPage();

        /**
         * [简要描述]:获取数据加载状态<br/>
         * [详细描述]:获取数据加载状态<br/>
         *
         * @return true正在加载,false加载完毕
         */
        boolean isDataLoadding();
    }
}