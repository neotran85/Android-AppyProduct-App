package com.appyhome.appyproduct.mvvm.utils.helper;

import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class AppAnimator {
    public static void animateMoving(int duration, View view, int sizeInPixels, Point start, Point end, AnimatorListenerAdapter listenerAdapter) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator posX = null;
        ObjectAnimator posY = null;
        ObjectAnimator scaleX = null;
        ObjectAnimator scaleY = null;
        posX = ObjectAnimator.ofFloat(view, "translationX", start.x, end.x - sizeInPixels / 2);
        posY = ObjectAnimator.ofFloat(view, "translationY", start.y, end.y - sizeInPixels / 2);
        scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1, 0.3f);
        scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1, 0.3f);
        animatorSet.setDuration(duration);
        animatorSet.playTogether(posX, posY, scaleX, scaleY);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.addListener(listenerAdapter);
        animatorSet.start();
    }

    public static void fadeIn(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator alpha = null;
        alpha = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
        animatorSet.setDuration(500);
        animatorSet.playTogether(alpha);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();
    }

    public static void slideFromRight(View view, int width) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator alpha = null;
        ObjectAnimator posX = null;
        posX = ObjectAnimator.ofFloat(view, "translationX", width, 0);
        alpha = ObjectAnimator.ofFloat(view, "alpha", 0.4f, 1);
        animatorSet.setDuration(500);
        animatorSet.playTogether(alpha, posX);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();
    }

    public static void dropdown(View view, int height) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator alpha = null;
        ObjectAnimator posY = null;
        posY = ObjectAnimator.ofFloat(view, "translationY", -height, 0);
        alpha = ObjectAnimator.ofFloat(view, "alpha", 0.4f, 1);
        animatorSet.setDuration(500);
        animatorSet.playTogether(alpha, posY);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();
    }
}
