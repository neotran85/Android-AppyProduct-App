package com.appyhome.appyproduct.mvvm.utils.helper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
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
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
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

    public static void loadImageAsset(Context context, ImageView imageView, String imagePath) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream ims = assetManager.open(imagePath);
            Drawable d = Drawable.createFromStream(ims, null);
            imageView.setBackground(d);
        } catch (Exception ex) {
            return;
        }
    }

    public static void animateMoving(View view, int sizeInPixels, Point start, Point end, AnimatorListenerAdapter listenerAdapter) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator posX = null;
        ObjectAnimator posY = null;
        ObjectAnimator alpha = null;
        ObjectAnimator scaleX = null;
        ObjectAnimator scaleY = null;
        posX = ObjectAnimator.ofFloat(view, "translationX", start.x, end.x - sizeInPixels / 2);
        posY = ObjectAnimator.ofFloat(view, "translationY", start.y, end.y - sizeInPixels / 2);
        alpha = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
        scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1, 0.3f);
        scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1, 0.3f);
        animatorSet.setDuration(1500);
        animatorSet.playTogether(posX, posY, scaleX, scaleY);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.addListener(listenerAdapter);
        animatorSet.start();
    }
}
