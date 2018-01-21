package com.appyhome.appyproduct.mvvm.utils.helper;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.appyhome.appyproduct.mvvm.data.model.api.BlogResponse;
import com.appyhome.appyproduct.mvvm.data.model.others.QuestionCardData;
import com.appyhome.appyproduct.mvvm.ui.feed.blogs.BlogAdapter;
import com.appyhome.appyproduct.mvvm.ui.feed.opensource.OpenSourceAdapter;
import com.appyhome.appyproduct.mvvm.ui.feed.opensource.OpenSourceItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.main.MainViewModel;
import com.appyhome.appyproduct.mvvm.ui.main.QuestionCard;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;

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

    @BindingAdapter({"adapter", "action"})
    public static void addQuestionItems(SwipePlaceHolderView mCardsContainerView,
                                        ArrayList<QuestionCardData> mQuestionList,
                                        int mAction) {
        if (mAction == MainViewModel.ACTION_ADD_ALL) {
            if (mQuestionList != null) {
                mCardsContainerView.removeAllViews();
                for (QuestionCardData question : mQuestionList) {
                    if (question != null
                            && question.options != null
                            && question.options.size() == 3) {
                        mCardsContainerView.addView(new QuestionCard(question));
                    }
                }
                ViewAnimationUtils.scaleAnimateView(mCardsContainerView);
            }
        }
    }
}
