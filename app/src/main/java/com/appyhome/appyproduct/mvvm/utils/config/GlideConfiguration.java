package com.appyhome.appyproduct.mvvm.utils.config;

import android.content.Context;
import android.os.Build;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

@GlideModule
public class GlideConfiguration extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        int diskCacheSizeBytes = 1024 * 1024 * 100; // 100 MB size cache for images

        // Workaround for ANDROID O causes an error
        // "IllegalStateException: Software rendering doesn't support hardware bitmaps"
        RequestOptions options = new RequestOptions();
        boolean isAndroidO = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O);
            DecodeFormat format = isAndroidO ? DecodeFormat.PREFER_ARGB_8888 : DecodeFormat.PREFER_RGB_565;
        options.format(format);
        if (isAndroidO) options.disallowHardwareConfig();

        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskCacheSizeBytes));
        builder.setDefaultRequestOptions(options);
    }
}
