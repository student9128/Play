package com.kevin.play.view;

import android.app.ActivityManager;
import android.content.Context;
import android.support.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by Kevin on 2018/12/27<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 * 一定要重写isManifestParsingEnabled并返回false,否则会报错<br/>
 * <b>add description on 2019/1/3<br>
 * <code style="color:#ee6f57">
 * java.lang.RuntimeException: Expected instanceof GlideModule, but found: com.kevin.play.view.GModule@8ad7cba
 * at com.bumptech.glide.module.ManifestParser.parseModule(ManifestParser.java:87)<br/>
 * at com.bumptech.glide.module.ManifestParser.parse(ManifestParser.java:47)<br/>
 * at com.bumptech.glide.Glide.initializeGlide(Glide.java:230)<br/>
 * at com.bumptech.glide.Glide.initializeGlide(Glide.java:221)<br/>
 * at com.bumptech.glide.Glide.checkAndInitializeGlide(Glide.java:182)<br/>
 * at com.bumptech.glide.Glide.get(Glide.java:166)<br/>
 * at com.bumptech.glide.Glide.getRetriever(Glide.java:680)</code>
 */
@GlideModule
public class GModule extends AppGlideModule {
    int diskSize = 1024 * 1024 * 100;
    int memorySize = (int) ((Runtime.getRuntime().maxMemory()) / 8);

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (null != activityManager) {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);
            builder.setDefaultRequestOptions(new RequestOptions().format(memoryInfo.lowMemory ?
                    DecodeFormat.PREFER_RGB_565 : DecodeFormat.DEFAULT));
        }
        builder.setMemoryCache(new LruResourceCache(memorySize));
        builder.setBitmapPool(new LruBitmapPool(memorySize));


    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
