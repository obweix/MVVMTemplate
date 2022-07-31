package com.example.mvvmapplication;

import android.app.Application;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * todo：绕过AMS检查，以此启动一个未在AndroidManifest.xml中注册的活动
 */
public class HookApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        try {
            hookAmsAction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在执行AMS之前，替换AndroidManifest里面配置的Activity
     */
    private void hookAmsAction() throws Exception{

        // 动态代理
        Class<?> mIActivityManagerClass = Class.forName("android.app.IActivityManager");

        // 获取ActivityManager对象，在动态代理的invoke中使用
        // 执行static public IActivityManager getDefault(),就能拿到IActivityManager
        Class mActivityManagerNativeClass2 = Class.forName("android.app.ActivityManagerNative");
        final Object mIActivityManager = mActivityManagerNativeClass2.getMethod("getDefault").invoke(null);

        Object mIActivityManagerProxy = Proxy.newProxyInstance(
                HookApplication.class.getClassLoader(),

                new Class[]{mIActivityManagerClass},

                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if("startActivity".equals(method.getName())){
                            // hook 注入的逻辑

                        }
                        Log.d("hook", "invoke: 拦截到了");
                        return method.invoke(mIActivityManager,args);
                    }
                }

        );

        // gDefault对象
        Class mActivityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
        Field gDefaultField = mActivityManagerNativeClass.getDeclaredField("gDefault");
        gDefaultField.setAccessible(true);
        Object gDefault = gDefaultField.get(null);


        // 替换点
        Class mSingletonClass = Class.forName("android.util.Singleton");
        Field mInstanceField = mSingletonClass.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);
        // 替换
        mInstanceField.set(gDefault,mIActivityManagerProxy); // 需要获取gDefault对象


    }
}
