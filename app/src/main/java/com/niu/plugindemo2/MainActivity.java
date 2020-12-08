package com.niu.plugindemo2;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.niu.lib.plugin.PluginManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PluginManager.getInstance().init(this, BuildConfig.DEBUG);
        findViewById(R.id.load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = "pluginapp-debug.apk";
                String path = AssetUtil.copyAssetToCache(MainActivity.this, fileName);
                PluginManager.getInstance().loadPluginApk(path);
                Toast.makeText(MainActivity.this, "加载完成", Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PluginManager.getInstance().openActivity(MainActivity.this, PluginManager.getInstance().getPackageInfo().activities[0].name);
            }
        });
    }
}