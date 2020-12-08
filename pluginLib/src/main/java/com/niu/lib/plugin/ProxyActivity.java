package com.niu.lib.plugin;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import dalvik.system.DexClassLoader;

/**
 * @author 杜宗宁 zongning.du@niu.com
 * @date 2020/12/8 14:08
 * @description 代理插件类
 */
public class ProxyActivity extends Activity {
    /**
     * 正在要跳转的全类名
     */
    private String realActivityName;

    private IPlugin mPlugin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realActivityName = getIntent().getStringExtra(PluginConstant.CLASS_NAME);
        try {
            Class<?> realActivityClass = PluginManager.getInstance().getClassLoader().loadClass(realActivityName);
            Object o = realActivityClass.newInstance();
            if (o instanceof IPlugin) {
                mPlugin = (IPlugin) o;
                Bundle bundle = new Bundle();
                bundle.putInt(PluginConstant.TAG_FROM, IPlugin.FROM_EXTERNAL);
                mPlugin.attach(this);
                mPlugin.onCreate(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        mPlugin.onStart();
        super.onStart();
    }

    @Override
    protected void onResume() {
        mPlugin.onResume();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        mPlugin.onRestart();
        super.onRestart();
    }

    @Override
    protected void onPause() {
        mPlugin.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mPlugin.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mPlugin.onDestroy();
        super.onDestroy();
    }

    @Override
    public ClassLoader getClassLoader() {
        DexClassLoader classLoader = PluginManager.getInstance().getClassLoader();
        return classLoader != null ? classLoader : super.getClassLoader();
    }

    @Override
    public Resources getResources() {
        Resources resources = PluginManager.getInstance().getResources();
        return resources != null ? resources : super.getResources();
    }
}
