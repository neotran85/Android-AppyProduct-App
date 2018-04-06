package com.appyhome.appyproduct.mvvm.ui.appyproduct.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductTopic;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.SearchItem;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductSearchBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductSearchCategoryBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductSearchTagBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.ProductListActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.search.adapter.SearchAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.search.adapter.SearchItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.search.adapter.SearchItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import java.util.ArrayList;
import java.util.HashMap;

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

    private HashMap<ProductTopic, String> mTopics;

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
                //boolean isNotEmpty = getKeywords().length() >= 3;
                //if (isNotEmpty)
                //getViewModel().getSearchSuggestions();
            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isNotEmpty = getKeywords().length() > 0;
                getViewModel().isClearable.set(isNotEmpty);
            }
        });
        getViewModel().getSearchHisTory();
        getViewModel().getAllProductTopics();
        mBinder.rvSuggestions.setAdapter(mSuggestionsAdapter);

        mBinder.etKeyword.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && keyCode == KeyEvent.KEYCODE_ENTER) {
                search();
                return true;
            }
            return false;
        });
    }

    @Override
    public void showSuggestions(RealmResults<SearchItem> items) {
        ViewUtils.setUpRecyclerViewList(mBinder.rvSuggestions, true);
        mSuggestionsAdapter.addItems(items, this);
        mSuggestionsAdapter.notifyDataSetChanged();
    }

    @Override
    public void search() {
        String keywords = getKeywords();
        if (keywords != null && keywords.length() > 0) {
            ArrayList<ProductTopic> selectedTopics = getSelectedTopics();
            ArrayList<Integer> topicsIds = new ArrayList<>();
            ArrayList<String> topicsNames = new ArrayList<>();
            for (ProductTopic topic : selectedTopics) {
                topicsIds.add(topic.id);
                topicsNames.add(topic.name);
            }
            String getSelectedTopicIds = TextUtils.join(",", topicsIds);
            String getSelectedTopicNames = TextUtils.join(", ", topicsNames);

            SearchItem item = createKeywordCached(getKeywords(), getSelectedTopicIds);

            getViewModel().addSearchItems(new SearchItem[]{item});
            Intent intent = ProductListActivity.getStartIntent(this);
            intent.putExtra("keyword", getKeywords());
            intent.putExtra("categoryIds", getAllCategoriesForSearch(selectedTopics));
            intent.putExtra("topics", getSelectedTopicNames);
            startActivity(intent);
        }
    }

    private ArrayList<ProductTopic> getSelectedTopics() {
        ArrayList<ProductTopic> topics = new ArrayList<>();
        int count = mBinder.flTopics.getChildCount();
        int whiteColor = ContextCompat.getColor(this, R.color.white);
        for (int i = 0; i < count; i++) {
            View view = mBinder.flTopics.getChildAt(i);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                if (textView.getCurrentTextColor() == whiteColor) {
                    if (view.getTag() instanceof ProductTopic) {
                        ProductTopic item = (ProductTopic) view.getTag();
                        topics.add(item);
                    }
                }
            }
        }
        return topics;
    }

    private String getAllCategoriesForSearch(ArrayList<ProductTopic> selectedTopics) {
        String result = "";
        if (selectedTopics != null) {
            for (ProductTopic topic : mTopics.keySet()) {
                if (selectedTopics.contains(topic)) {
                    String category = mTopics.get(topic);
                    result = result + category + ",";
                }
            }
        }
        return result;
    }

    private SearchItem createKeywordCached(String keyword, String topicIds) {
        SearchItem item = new SearchItem();
        item.time_added = System.currentTimeMillis();
        item.content = keyword;
        item.cached = true;
        item.user_id = getViewModel().getUserId();
        item.topics = topicIds;
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
    public void onItemSuggestionClick(SearchItemViewModel viewModel) {
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
        if (items != null) {
            mBinder.flHistorySearch.removeAllViews();
            for (SearchItem item : items) {
                mBinder.flHistorySearch.addView(createTag(item));
            }
        } else {
            mBinder.flHistorySearch.removeAllViews();
        }
    }

    @Override
    public void clearHistory() {
        getViewModel().clearSearchHistory();
    }

    @Override
    public void onSearchHistoryClick(SearchItem item) {
        setKeywords(item.content);
        ArrayList<Integer> ids = new ArrayList<>();
        if (item.topics.length() > 0) {
            String[] array = TextUtils.split(item.topics, ",");
            if (array != null && array.length > 0) {
                for (int i = 0; i < array.length; i++) {
                    ids.add(Integer.valueOf(array[i]));
                }
            }
        }
        setSelectedTopics(ids);
        search();
    }

    private String getKeywords() {
        return mBinder.etKeyword.getText().toString();
    }

    private void setKeywords(String keywords) {
        if (keywords != null) {
            mBinder.etKeyword.setText(keywords);
            mBinder.etKeyword.setSelection(keywords.length());
        }
    }

    @Override
    public void updateUISearchCategory(HashMap<ProductTopic, String> items) {
        mTopics = items;
        if (items != null) {
            if (mBinder.flTopics.getChildCount() == 0) {
                mBinder.flTopics.removeAllViews();
                for (ProductTopic item : items.keySet()) {
                    mBinder.flTopics.addView(createProductTopics(item));
                }
            }
        }
    }

    private void setSelectedTopics(ArrayList<Integer> idTopic) {
        int count = mBinder.flTopics.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = mBinder.flTopics.getChildAt(i);
            if (view instanceof TextView) {
                if (view.getTag() instanceof ProductTopic) {
                    ProductTopic item = (ProductTopic) view.getTag();
                    setTopicSelected((TextView) view, DataUtils.contains(idTopic, item.id));
                }
            }
        }
    }

    private void setTopicSelected(TextView textView, boolean isSelected) {
        textView.setTextColor(ContextCompat.getColor(this, isSelected ? R.color.white
                : R.color.semi_gray));
        textView.setBackgroundResource(isSelected ? R.drawable.view_rounded_bg_orange_large_radius
                : R.drawable.view_rounded_bg_gray_border);
    }

    @Override
    public void onProductTopicClick(View view, ProductTopic topics) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            int whiteColor = ContextCompat.getColor(this, R.color.white);
            setTopicSelected(textView, !(textView.getCurrentTextColor() == whiteColor));
        }
    }

    @Override
    public void showAlert(String message) {
        AlertManager.getInstance(this).showLongToast(message);
    }

    private View createProductTopics(ProductTopic item) {
        ViewItemProductSearchCategoryBinding binding = ViewItemProductSearchCategoryBinding.inflate(getLayoutInflater(), null, false);
        binding.setData(item);
        binding.setNavigator(this);
        binding.getRoot().setTag(item);
        return binding.getRoot();
    }

    private View createTag(SearchItem item) {
        ViewItemProductSearchTagBinding binding = ViewItemProductSearchTagBinding.inflate(getLayoutInflater(), null, false);
        binding.setData(item);
        binding.setNavigator(this);
        return binding.getRoot();
    }
}
