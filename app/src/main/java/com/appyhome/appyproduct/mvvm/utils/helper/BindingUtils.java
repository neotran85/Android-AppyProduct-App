package com.appyhome.appyproduct.mvvm.utils.helper;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.model.api.BlogResponse;
import com.appyhome.appyproduct.mvvm.ui.common.component.LinearListView;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;
import com.appyhome.appyproduct.mvvm.ui.feed.blogs.BlogAdapter;
import com.appyhome.appyproduct.mvvm.ui.feed.opensource.OpenSourceAdapter;
import com.appyhome.appyproduct.mvvm.ui.feed.opensource.OpenSourceItemViewModel;
import com.appyhome.appyproduct.mvvm.utils.config.GlideApp;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        if (url != null && url.length() > 0) {
            Context context = imageView.getContext();
            GlideApp.with(context)
                    .load(url)
                    .placeholder(R.mipmap.no_image)
                    .error(R.mipmap.no_image)
                    .fitCenter()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        } else {
            imageView.setBackgroundResource(R.mipmap.no_image);
        }
    }

    @BindingAdapter("imageUrlForVariant")
    public static void setImageUrlForVariant(ImageView imageView, String url) {
        if (url != null && url.length() > 0) {
            Context context = imageView.getContext();
            GlideApp.with(context)
                    .load(url)
                    .placeholder(R.mipmap.variant_no_thumb)
                    .error(R.mipmap.variant_no_thumb)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        } else {
            imageView.setBackgroundResource(R.mipmap.variant_no_thumb);
        }
    }


    @BindingAdapter("adapter")
    public static void setAdapter(LinearListView view, ArrayList data) {
        view.setAdapter(data);
    }

    @BindingAdapter("layout_height")
    public static void setLayoutHeight(ImageView view, float height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = Math.round(height);
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("layout_width")
    public static void setLayoutWidth(ImageView view, float width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = Math.round(width);
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("clickable")
    public static void setClickable(View view, boolean clickable) {
        view.setClickable(clickable);
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

    @BindingAdapter("adapter")
    public static void setAdapter(RecyclerView rc, SampleAdapter adapter) {
        rc.setAdapter(adapter);
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

    @BindingAdapter("isFavorite")
    public static void setIsFavorite(ImageView imageView, boolean isFavorite) {
        if (isFavorite) {
            imageView.setBackgroundResource(R.mipmap.icon_favorite);
        } else {
            imageView.setBackgroundResource(R.mipmap.icon_unfavorite);
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
