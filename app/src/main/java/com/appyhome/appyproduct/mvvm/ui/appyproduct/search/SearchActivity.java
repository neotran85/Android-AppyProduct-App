package com.appyhome.appyproduct.mvvm.ui.appyproduct.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.SearchItem;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductSearchBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductSearchTagBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.search.adapter.SearchItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

import io.realm.RealmResults;

public class SearchActivity extends BaseActivity<ActivityProductSearchBinding, SearchViewModel> implements SearchNavigator, SearchItemNavigator {

    @Inject
    public SearchViewModel mMainViewModel;
    ActivityProductSearchBinding mBinder;
    @Inject
    int mLayoutId;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        return intent;
    }

    @Override
    public void goBack() {
        finish();
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setNavigator(this);
        mBinder.setViewModel(mMainViewModel);
        mMainViewModel.setNavigator(this);
        mBinder.etKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getViewModel().isClearable.set(mBinder.etKeyword.getText().toString().length() > 0);
            }
        });
        getViewModel().getSearchHisTory();
    }

    @Override
    public void search() {
        String keywords = getKeywords();
        if (keywords != null && keywords.length() > 0) {
            SearchItem item = new SearchItem();
            item.content = getKeywords();
            item.cached = true;
            item.user_id = getViewModel().getUserId();
            getViewModel().addSearchItems(new SearchItem[]{item});
            setKeywords("");
        }
    }

    @Override
    public void clearKeywords() {
        setKeywords("");
    }

    @Override
    public void doAfterSearchItemsAdded() {
        getViewModel().getSearchHisTory();
    }

    @Override
    public void onItemClick(View view) {
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public SearchViewModel getViewModel() {
        return mMainViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void updateUISearchHistory(RealmResults<SearchItem> items) {
        getViewModel().isHistoryVisible.set(items != null && items.size() > 0);
        if (items != null) {
            mBinder.flHistorySearch.removeAllViews();
            for (SearchItem item : items) {
                mBinder.flHistorySearch.addView(createTag(item));
            }
        }
    }

    @Override
    public void clearHistory() {
        getViewModel().clearSearchHistory();
    }

    @Override
    public void clickSearchHistoryItem(View view) {
        SearchItem item = (SearchItem) view.getTag();
        setKeywords(item.content);
    }

    private String getKeywords() {
        return mBinder.etKeyword.getText().toString();
    }

    private void setKeywords(String keywords) {
        mBinder.etKeyword.setText(keywords);
    }

    @Override
    public void showAlert(String message) {
        AlertManager.getInstance(this).showLongToast(message);
    }

    private View createTag(SearchItem item) {
        ViewItemProductSearchTagBinding binding = ViewItemProductSearchTagBinding.inflate(getLayoutInflater(), null, false);
        binding.tvTag.setText(item.content);
        binding.getRoot().setTag(item);
        binding.setNavigator(this);
        return binding.getRoot();
    }
}
