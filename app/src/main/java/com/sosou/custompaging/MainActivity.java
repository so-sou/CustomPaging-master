package com.sosou.custompaging;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.sosou.custompaging.adapter.GetCouponsAdapter;
import com.sosou.custompaging.custompaging_master.R;
import com.sosou.custompaging.utils.Page;
import com.sosou.custompaging.weight.HeaderListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements HeaderListView.OnHeaderListViewListener {


    /**
     * 分页
     */
    private Page recordPage = new Page();

    // 明细
    private HeaderListView recyclerView;

    /**
     * 适配器
     */
    private GetCouponsAdapter adapter;

    /**
     * 数据集
     */
    private List<String> mList = new ArrayList<>();

    /**
     * 开始位置，默认0
     */
    private int startNum;

    /**
     * 每页数，默认5
     */
    private int count = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    @Override
    protected void onStart() {
        super.onStart();
        getBackCommentRequest(startNum, count);
    }

    /**
     * 初始化控件
     */
    private void initView() {

        recyclerView = (HeaderListView) findViewById(R.id.record_hlv);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public boolean onNextPage() {

        // 加载更多功能的代码
        if (recordPage.isCanLoad()) {
            recordPage.next();

            // Toast可以改为自己的自定义控件、效果会更好（这里就不啰嗦了）
            Toast.makeText(this, "正在加载中。。。。", Toast.LENGTH_SHORT).show();
            getBackCommentRequest(startNum, count);

//            // 此处是因为后台跟你说会无限取数据只能根据页数判断数据是否加载完处理方法，最悲催的情况
//            if (recordPage.oldPageNo < chargeRecordPages) {
//                getBackCommentRequest(chargeRecordPage.pageNo , count);
//                Toast.makeText(this, "正在加载中。。。。", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "数据已加载完！", Toast.LENGTH_SHORT).show();
//            }
        }
        return true;
    }

    @Override
    public boolean isDataLoadding() {
        return recordPage.isPageLoadding();
    }

    /**
     * [简要描述]:如果第一页数据，未充满屏幕，则继续加载下一页<br/>
     * [详细描述]:如果第一页数据，未充满屏幕，则继续加载下一页<br/>
     */
    private void fillItems() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int recyclerViewHeight = recyclerView.getHeight();
                int itemsH = recyclerView.getItemTotalHeight(mList.size());

                // 如果第一页数据，未充满屏幕，则继续加载下一页
                if (recyclerViewHeight > itemsH) {

                    // 加载下一页
                    onNextPage();
                }
            }
        }, 500);
    }


    /**
     * 模拟请求数据
     */
    private void getBackCommentRequest(int pageNum, int pageSize) {

        List<String> mListTemp = new ArrayList<>();

        if (pageNum <= 20) {
            for (int i = 0; i < pageSize; i++) {
                mListTemp.add("呵呵" + Math.random());
            }
        }
        dataSuccess(mListTemp);
    }

    /**
     * 模拟回调数据
     *
     * @param data
     */
    private void dataSuccess(List<String> data) {

        if (data != null) {

            // 分页是第一页就加载新一页及数据
            if (recordPage.isFirstPage()) {

                mList.addAll(data);

                startNum = mList.size();

//                adapter.setData(mList);

                adapter = new GetCouponsAdapter(this, mList);

                // 绑定分页监听
                recyclerView.setOnHeaderListViewListener(this);

                // 绑定适配器
                recyclerView.setAdapter(adapter);

            } else {

                // 分页不是第一页就加载新一页叠加数据
                List<String> tempData = data;

                // 分页到了最后一页
                if (tempData.size() == 0) {

                    // 如果没数据就回退到当前页
                    recordPage.noNext();
                    Toast.makeText(this, "数据已加载完！", Toast.LENGTH_SHORT).show();
                    return;
                }

                int start = mList.size();
                int length = tempData.size();
                mList.addAll(tempData);
                adapter.notifyItemRangeInserted(start, length);

                startNum = mList.size();
            }

            // 加载成功
            recordPage.loadSucc();

            fillItems();

        } else {

            // 无数据处理
        }
    }
}
