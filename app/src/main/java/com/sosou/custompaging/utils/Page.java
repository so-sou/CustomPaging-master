package com.sosou.custompaging.utils;

/**
 * 分页类
 */
public class Page
{
    /**
     * 第一个默认页标
     */
    public static final int FIRST_PAGE = 1;

    /**
     * 当前页标数
     */
    public int pageNo = FIRST_PAGE;

    /**
     * 已加载页数
     */
    public int oldPageNo = FIRST_PAGE;

    /**
     * 分页数
     */
    public int pageSize = 5;

    /**
     * 有下一页
     */
    public boolean hasNext = true;

    /**
     * 是分页加载中
     */
    private boolean isPageLoadding;

    /**
     *
     * [简要描述]:不能加载下一页<br/>
     * [详细描述]:没有下一页<br/>
     *
     */
    public void noNext()
    {
        hasNext = false;
        loadFail();
    }

    /**
     *
     * [简要描述]:下一页<br/>
     * [详细描述]:下一页<br/>
     *
     * @return hasNext
     */
    public boolean hasNext()
    {
        return hasNext;
    }

    /**
     *
     * [简要描述]:是分页加载中<br/>
     * [详细描述]:是分页加载中<br/>
     *
     * @return 是分页加载中
     */
    public boolean isPageLoadding()
    {
        return isPageLoadding;
    }

    /**
     *
     * [简要描述]:是可以加载<br/>
     * [详细描述]:是可以加载<br/>
     *
     * @return 不是分页增加中及没有下一页
     */
    public boolean isCanLoad()
    {
        return !isPageLoadding && hasNext;
    }

    /**
     *
     * [简要描述]:加载成功<br/>
     * [详细描述]:加载成功<br/>
     *
     */
    public void loadSucc()
    {
        isPageLoadding = false;
    }

    /**
     *
     * [简要描述]:下一页<br/>
     * [详细描述]:下一页<br/>
     *
     */
    public void next()
    {
        oldPageNo = pageNo;
        pageNo++;
        isPageLoadding = true;
    }

    /**
     *
     * [简要描述]:加载失败<br/>
     * [详细描述]:加载失败<br/>
     *
     */
    public void loadFail()
    {
        isPageLoadding = false;
        pageNo = oldPageNo;
    }

    /**
     *
     * [简要描述]:是第一个分页<br/>
     * [详细描述]:是第一个分页<br/>
     *
     * @return 分页标数
     */
    public boolean isFirstPage()
    {
        return pageNo == FIRST_PAGE;
    }

    /**
     *
     * [简要描述]:获取的数据<br/>
     * [详细描述]:获取的数据<br/>
     *
     * @return 一共加载的数据
     */
    public int getCursor()
    {
        isPageLoadding = true;
        return (pageNo - 1) * pageSize + 1;
    }

    /**
     *
     * [简要描述]:获取分页数<br/>
     * [详细描述]:获取分页数<br/>
     *
     * @return 分页数
     */
    public int getPageSize()
    {
        return pageSize;
    }

    /**
     *
     * [简要描述]:重置<br/>
     * [详细描述]:重置<br/>
     *
     */
    public void reset()
    {
        pageNo = FIRST_PAGE;
        oldPageNo = FIRST_PAGE;
        hasNext = true;
    }

}
