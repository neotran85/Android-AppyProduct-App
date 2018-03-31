package com.appyhome.appyproduct.mvvm.ui.appyproduct.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.SearchItem;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductSearchBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductSearchTagBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.search.adapter.SearchAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.search.adapter.SearchItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.search.adapter.SearchItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

import io.realm.RealmResults;

public class SearchActivity extends BaseActivity<ActivityProductSearchBinding, SearchViewModel> implements SearchNavigator, SearchItemNavigator {

    @Inject
    public SearchViewModel mMainViewModel;

    @Inject
    public SearchAdapter mSuggestionsAdapter;

    @Inject
    int mLayoutId;

    ActivityProductSearchBinding mBinder;


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
                boolean isNotEmpty = getKeywords().length() >= 3;
                if (isNotEmpty)
                    getViewModel().getSearchSuggestions();
            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isNotEmpty = getKeywords().length() > 0;
                getViewModel().isClearable.set(isNotEmpty);
            }
        });
        getViewModel().getSearchHisTory();
        mBinder.rvSuggestions.setAdapter(mSuggestionsAdapter);
    }

    @Override
    public void showSuggestions(RealmResults<SearchItem> items) {
        getViewModel().isHistoryVisible.set(false);
        ViewUtils.setUpRecyclerViewList(mBinder.rvSuggestions, true);
        mSuggestionsAdapter.addItems(items, this);
        mSuggestionsAdapter.notifyDataSetChanged();
    }

    @Override
    public void search() {
        String keywords = getKeywords();
        if (keywords != null && keywords.length() > 0) {
            getViewModel().addSearchItems(new SearchItem[]{createKeywordCached(getKeywords())});
            setKeywords("");
        }
    }

    private SearchItem createKeywordCached(String keyword) {
        SearchItem item = new SearchItem();
        item.content = keyword;
        item.cached = true;
        item.user_id = getViewModel().getUserId();
        return item;
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
    public void onItemSuggestionClick(View view) {
        SearchItemViewModel viewModel = (SearchItemViewModel) view.getTag();
        setKeywords(viewModel.keyword.get());
        getViewModel().addSearchItems(new SearchItem[]{createKeywordCached(getKeywords())});
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
