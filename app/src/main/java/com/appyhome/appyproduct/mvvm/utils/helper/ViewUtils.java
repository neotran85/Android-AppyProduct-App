package com.appyhome.appyproduct.mvvm.utils.helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
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

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

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

    public static void requestFocus(EditText editText) {
        if (editText != null && editText.getParent() != null) {
            editText.getParent().requestChildFocus(editText, editText);
        }
    }

    public static void setUpRecyclerViewList(RecyclerView rv, boolean dividerVisible) {
        Context context = rv.getContext();
        rv.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());
        if (dividerVisible) {
            if (rv.getItemDecorationCount() == 0) {
                DividerItemDecoration line = new DividerItemDecoration(context, LinearLayoutManager.VERTICAL);
                rv.addItemDecoration(line);
            }
        }
    }

}
