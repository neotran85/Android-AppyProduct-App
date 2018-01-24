package com.appyhome.appyproduct.mvvm.utils.helper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.appyhome.appyproduct.mvvm.R;

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

    public static void setOnClickListener(View mainView, int idResourceButton, View.OnClickListener listener) {
        if (mainView != null) {
            View view = mainView.findViewById(idResourceButton);
            if (view != null)
                view.setOnClickListener(listener);
        }
    }
}
