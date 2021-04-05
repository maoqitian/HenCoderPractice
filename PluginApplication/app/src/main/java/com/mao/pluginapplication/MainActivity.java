package com.mao.pluginapplication;

import androidx.appcompat.app.AppCompatActivity;
import dalvik.system.DexClassLoader;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

import android.os.Bundle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.xml.transform.Source;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //正经插件化 只是 方便项目扩展模块动态变化部署
        
        //通过 DexClassLoader 来加载插件中的 dex 文件

        //创建缓存 apk 文件
        File pluginApk = new File(getCacheDir() + "/plugin1.apk");

        //使用 okio 复制文件到 缓存文件中
        try (BufferedSource bufferSource = Okio.buffer(Okio.source(getAssets().open("plugin1-debug.apk")));
             BufferedSink bufferedSink = Okio.buffer(Okio.sink(pluginApk))){
            bufferedSink.writeAll(bufferSource);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //DexClassLoader
        DexClassLoader dexClassLoader = new DexClassLoader(pluginApk.getPath(), getCacheDir().getPath(), null, null);

        try {
            //不对反射的类有任何引用 使用 class.forName , 使用  DexClassLoader.loadClass 替换
            //拿到对应的类
            Class reflectionUtils = dexClassLoader.loadClass("com.mao.plugin1.utils.ReflectionUtils");
            //获取构造方法
            Constructor declaredConstructor = reflectionUtils.getDeclaredConstructors()[0];
            //解除调用私有限制
            declaredConstructor.setAccessible(true);
            //构造方法调用
            Object constructor = declaredConstructor.newInstance();
            //获取需要调用的方法
            Method testReflection = reflectionUtils.getDeclaredMethod("testReflection");
            //解除方法调用私有限制
            testReflection.setAccessible(true);
            //方法调用
            testReflection.invoke(constructor);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}