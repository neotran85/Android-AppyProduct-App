package com.appyhome.appyproduct.mvvm.ui.feed.blogs;

import com.appyhome.appyproduct.mvvm.data.model.api.BlogResponse;

import java.util.List;


public interface BlogNavigator {

    void updateBlog(List<BlogResponse.Blog> blogList);

    void handleError(Throwable throwable);

}
