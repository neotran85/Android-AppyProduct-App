package com.appyhome.appyproduct.mvvm.utils.helper;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.model.api.BlogResponse;
import com.appyhome.appyproduct.mvvm.ui.feed.blogs.BlogAdapter;
import com.appyhome.appyproduct.mvvm.ui.feed.opensource.OpenSourceAdapter;
import com.appyhome.appyproduct.mvvm.ui.feed.opensource.OpenSourceItemViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        if (url != null && url.length() > 0) {
            Context context = imageView.getContext();
            Glide.with(context)
                    .load(url)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.no_image)
                    .into(imageView);
        } else {
            imageView.setBackgroundResource(R.mipmap.no_image);
        }
    }

    @BindingAdapter("editModeText")
    public static void setEditModeText(TextView textView, boolean isEditMode) {
        textView.setText(isEditMode ? "Done" : "Edit");
    }

    @BindingAdapter("totalCart")
    public static void setTotalCart(TextView textView, int totalCart) {
        if (totalCart > 0) {
            textView.setText((totalCart <= 99) ? totalCart + "" : "99+");
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setText("");
            textView.setVisibility(View.GONE);
        }
    }


    @BindingAdapter("saveModeText")
    public static void setSaveModeText(TextView textView, boolean isSaveMode) {
        textView.setText(isSaveMode ? "SAVE" : "NEXT");
    }

    @BindingAdapter("imageAssetsUrl")
    public static void setImageAssetUrl(ImageView imageView, String path) {
        if (path != null && path.length() > 0) {
            Context context = imageView.getContext();
            ViewUtils.loadImageAsset(context, imageView, path);
        } else {
            imageView.setBackgroundResource(R.mipmap.no_image);
        }
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

    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, Boolean value) {
        view.setVisibility(value ? View.VISIBLE : View.GONE);
    }
}
