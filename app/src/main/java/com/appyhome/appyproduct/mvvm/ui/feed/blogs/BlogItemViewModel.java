package com.appyhome.appyproduct.mvvm.ui.feed.blogs;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.model.api.BlogResponse;


public class BlogItemViewModel {

    public ObservableField<String> imageUrl;
    public ObservableField<String> title;
    public ObservableField<String> author;
    public ObservableField<String> date;
    public ObservableField<String> content;
    public BlogItemViewModelListener mListener;
    private BlogResponse.Blog mBlog;

    public BlogItemViewModel(BlogResponse.Blog blog, BlogItemViewModelListener listener) {
        this.mBlog = blog;
        this.mListener = listener;
        imageUrl = new ObservableField<>(mBlog.getCoverImgUrl());
        title = new ObservableField<>(mBlog.getTitle());
        author = new ObservableField<>(mBlog.getAuthor());
        date = new ObservableField<>(mBlog.getDate());
        content = new ObservableField<>(mBlog.getDescription());
    }

    public void onItemClick() {
        mListener.onItemClick(mBlog.getBlogUrl());
    }

    public interface BlogItemViewModelListener {
        void onItemClick(String blogUrl);
    }
}
