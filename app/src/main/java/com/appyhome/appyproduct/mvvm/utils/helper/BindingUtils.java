package com.appyhome.appyproduct.mvvm.utils.helper;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.appyhome.appyproduct.mvvm.data.model.api.BlogResponse;
import com.appyhome.appyproduct.mvvm.ui.feed.blogs.BlogAdapter;
import com.appyhome.appyproduct.mvvm.ui.feed.opensource.OpenSourceAdapter;
import com.appyhome.appyproduct.mvvm.ui.feed.opensource.OpenSourceItemViewModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(url).into(imageView);
    }

    @BindingAdapter("imageAssetsUrl")
    public static void setImageAssetUrl(ImageView imageView, String path) {
        Context context = imageView.getContext();
        ViewUtils.loadImageAsset(context, imageView, path);
    }

    @BindingAdapter({"adapter"})
    public static void addOpenSourceItems(RecyclerView recyclerView,
                                          ArrayList<OpenSourceItemViewModel> openSourceItems) {
        OpenSourceAdapter adapter = (OpenSourceAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(openSourceItems);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addBlogItems(RecyclerView recyclerView,
                                    ArrayList<BlogResponse.Blog> blogs) {
        BlogAdapter adapter = (BlogAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(blogs);
        }
    }
}
