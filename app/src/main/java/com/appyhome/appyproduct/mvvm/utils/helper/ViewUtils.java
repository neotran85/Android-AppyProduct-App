package com.appyhome.appyproduct.mvvm.utils.helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.appyhome.appyproduct.mvvm.R;

import java.io.InputStream;

public final class ViewUtils {

    private ViewUtils() {
        // This class is not publicly instantiable
    }

    public static float pxToDp(float px) {
        float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        return px / (densityDpi / 160f);
    }

    public static int dpToPx(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public static void changeIconDrawableToGray(Context context, Drawable drawable) {
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(ContextCompat
                    .getColor(context, R.color.dark_gray), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public static String getStringByTag(View view) {
        if (view != null && view.getTag() != null) {
            return view.getTag().toString();
        }
        return "";
    }

    public static void setOnClickListener(View mainView, int[] idResourceButtons, View.OnClickListener listener) {
        if (mainView != null) {
            for (int i = 0; i < idResourceButtons.length; i++) {
                View view = mainView.findViewById(idResourceButtons[i]);
                if (view != null)
                    view.setOnClickListener(listener);
            }
        }
    }

    public static void setOnClickListener(View mainView, View.OnClickListener listener, int... idResourceButtons) {
        if (mainView != null) {
            for (int i = 0; i < idResourceButtons.length; i++) {
                View view = mainView.findViewById(idResourceButtons[i]);
                if (view != null)
                    view.setOnClickListener(listener);
            }
        }
    }

    public static void setOnCheckedChangeListener(View mainView, RadioGroup.OnCheckedChangeListener listener, int... idResources) {
        if (mainView != null) {
            for (int i = 0; i < idResources.length; i++) {
                RadioGroup view = mainView.findViewById(idResources[i]);
                if (view != null)
                    view.setOnCheckedChangeListener(listener);
            }
        }
    }

    public static void setOnClickListener(View.OnClickListener listener, View... views) {
        for (int i = 0; i < views.length; i++) {
            if (views[i] != null)
                views[i].setOnClickListener(listener);
        }
    }

    public static void setOnClickListenersOfParentView(ViewGroup view) {

    }

    public static void loadImageAsset(Context context, ImageView imageView, String imagePath) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream ims = assetManager.open(imagePath);
            Drawable d = Drawable.createFromStream(ims, null);
            imageView.setImageDrawable(d);
        } catch (Exception ex) {
            return;
        }
    }
}
