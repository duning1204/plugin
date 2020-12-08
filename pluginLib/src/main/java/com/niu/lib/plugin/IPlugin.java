package com.niu.lib.plugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author 杜宗宁 zongning.du@niu.com
 * @date 2020/12/8 14:10
 * @description TODO
 */
public interface IPlugin {
    /**
     * 插件单独测试时的内部跳转
     */
    int FROM_INTERNAL = 0;
    /**
     * 宿主执行的跳转逻辑
     */
    int FROM_EXTERNAL = 1;

    /**
     * 给插件Activity指定上下文
     *
     * @param activity
     */
    void attach(Activity activity);

    /**
     * 以下全都是Activity生命周期函数,
     * 插件Activity本身 在被用作"插件"的时候不具备生命周期，由宿主里面的代理Activity类代为管理
     */
    void onCreate(Bundle saveInstanceState);

    void onStart();

    void onResume();

    void onRestart();

    void onPause();

    void onStop();

    void onDestroy();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
