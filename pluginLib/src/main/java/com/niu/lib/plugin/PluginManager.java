package com.niu.lib.plugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Objects;

import dalvik.system.DexClassLoader;

/**
 * @author 杜宗宁 zongning.du@niu.com
 * @date 2020/12/8 14:15
 * @description TODO
 */
public class PluginManager {
    private DexClassLoader mLoader;
    private Context mContext;
    private PackageInfo mPackageInfo;
    private Resources mResources;
    private boolean debug;


    private PluginManager() {
    }

    private static class Holder {
        private static final PluginManager INSTANCE = new PluginManager();
    }

    public static PluginManager getInstance() {
        return Holder.INSTANCE;
    }

    public void init(Context context, boolean logDebug) {
        mContext = context.getApplicationContext();
        debug = logDebug;
    }

    /**
     * 加载插件apk
     */
    public void loadPluginApk(String apkPath) {
        Objects.requireNonNull(mContext);
        mPackageInfo = mContext.getPackageManager().getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        Objects.requireNonNull(mPackageInfo);
        File outFile = mContext.getDir("odex", Context.MODE_PRIVATE);
        mLoader = new DexClassLoader(apkPath, outFile.getAbsolutePath(), null, mContext.getClassLoader());
        Objects.requireNonNull(mLoader);
        try {
            Class<AssetManager> aClass = AssetManager.class;
            AssetManager assetManager = aClass.newInstance();
            Method method = aClass.getDeclaredMethod("addAssetPath", String.class);
            method.invoke(assetManager, apkPath);
            mResources = new Resources(assetManager, mContext.getResources().getDisplayMetrics(), mContext.getResources().getConfiguration());
            Objects.requireNonNull(mResources);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DexClassLoader getClassLoader() {
        return mLoader;
    }

    public PackageInfo getPackageInfo() {
        return mPackageInfo;
    }

    public Resources getResources() {
        return mResources;
    }

    public void openActivity(Activity that, String className) {
        Intent intent = new Intent(that, ProxyActivity.class);
        intent.putExtra(PluginConstant.CLASS_NAME, className);
        that.startActivity(intent);
    }
}
