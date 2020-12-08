package com.niu.lib.plugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author 杜宗宁 zongning.du@niu.com
 * @date 2020/12/8 14:32
 * @description TODO
 */
public abstract class BasePluginActivity extends AppCompatActivity implements IPlugin {

    private static final String TAG = "PluginActivityTAG";

    protected Activity that;

    /**
     * 默认是插件单独测试
     */
    private int from = IPlugin.FROM_INTERNAL;

    @Override
    public void attach(Activity activity) {
        that = activity;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        if (saveInstanceState != null) {
            from = saveInstanceState.getInt(PluginConstant.TAG_FROM);
        }
        if (from == IPlugin.FROM_INTERNAL) {
            super.onCreate(saveInstanceState);
            that = this;
        }
    }

    @Override
    public void onStart() {
        if (from == IPlugin.FROM_INTERNAL) {
            super.onStart();
        } else {
            Log.d(TAG, "宿主启动：onStart()");
        }
    }

    @Override
    public void onResume() {
        if (from == IPlugin.FROM_INTERNAL) {
            super.onResume();
        } else {
            Log.d(TAG, "宿主启动：onResume()");
        }
    }

    @Override
    public void onRestart() {
        if (from == IPlugin.FROM_INTERNAL) {
            super.onRestart();
        } else {
            Log.d(TAG, "宿主启动：onRestart()");
        }
    }

    @Override
    public void onPause() {
        if (from == IPlugin.FROM_INTERNAL) {
            super.onPause();
        } else {
            Log.d(TAG, "宿主启动：onPause()");
        }
    }

    @Override
    public void onStop() {
        if (from == IPlugin.FROM_INTERNAL) {
            super.onStop();
        } else {
            Log.d(TAG, "宿主启动：onStop()");
        }
    }

    @Override
    public void onDestroy() {
        if (from == IPlugin.FROM_INTERNAL) {
            super.onDestroy();
        } else {
            Log.d(TAG, "宿主启动：onDestroy()");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (from == IPlugin.FROM_INTERNAL) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.d(TAG, "宿主启动：onActivityResult()");
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (from == IPlugin.FROM_INTERNAL) {
            super.setContentView(layoutResID);
        } else {
            that.setContentView(layoutResID);
        }
    }

    @Override
    public <T extends View> T findViewById(int id) {
        if (from == FROM_INTERNAL) {
            return super.findViewById(id);
        } else {
            return that.findViewById(id);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        if (from == IPlugin.FROM_INTERNAL) {
            //原intent只能用于插件单独运行时
            super.startActivity(intent);
        } else {
            PluginManager.getInstance().openActivity(that, intent.getComponent().getClassName());
        }
    }
}
