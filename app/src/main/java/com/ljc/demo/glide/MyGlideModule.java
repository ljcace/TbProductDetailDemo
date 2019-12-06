package com.ljc.demo.glide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;

/**
 * ${desc}
 * created at 2019/4/19 by ljc
 **/
@com.bumptech.glide.annotation.GlideModule
public class MyGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .build();
        builder
                .setLogLevel(Log.DEBUG)//日志等级
                .setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()))//内存缓存
                .setBitmapPool(new LruBitmapPool(calculator.getBitmapPoolSize()))//Bitmap池
                .setDiskCache(new ExternalCacheDiskCacheFactory(context));//磁盘缓存
    }
}
