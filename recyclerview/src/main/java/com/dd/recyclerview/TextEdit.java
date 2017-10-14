package com.dd.recyclerview;

/**
 * Created by limingde1 on 2017/4/10.
 *
 *
 * 给Footer 提供显示各种状态下的显示文案
 */

public  interface TextEdit {
    /**
     * 加载更多状态下的显示文案
     * @return
     */
    public String getLoadingText();

    /**
     * 加载更多完成后显示的文案
     * @return
     */
    public String getLoadingCompleteText();
}
