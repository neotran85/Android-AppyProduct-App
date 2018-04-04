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
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
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

    private ArrayList<String> mTopicNames;

    private ArrayList<Integer> mTopicIds;

    private HashMap<Integer, String> mCategoryIds;


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
        mCategoryIds = new HashMap<>();
        mTopicNames = new ArrayList<>();
        mTopicIds = new ArrayList<>();

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
            SearchItem item = createKeywordCached(getKeywords(), TextUtils.join(",", mTopicIds),
                    getAllCategoriesForSearch(), TextUtils.join(", ", mTopicNames));
            search(item);
        }
    }

    private void search(SearchItem item) {
        getViewModel().addSearchItems(new SearchItem[]{item});
        Intent intent = ProductListActivity.getStartIntent(this);
        intent.putExtra("keyword", item.content);
        intent.putExtra("categoryIds", item.categories);
        intent.putExtra("topics", item.topic_names);
        startActivity(intent);
    }

    private String getAllCategoriesForSearch() {
        String result = "";
        if (mCategoryIds.keySet() != null && mCategoryIds.keySet().size() > 0) {
            for (Integer id : mCategoryIds.keySet()) {
                result = result + mCategoryIds.get(id) + ",";
            }
        }
        return result;
    }

    private SearchItem createKeywordCached(String keyword, String topicIds, String categories, String names) {
        SearchItem item = new SearchItem();
        item.time_added = System.currentTimeMillis();
        item.content = keyword;
        item.cached = true;
        item.user_id = getViewModel().getUserId();
        item.topics = topicIds;
        item.categories = categories;
        item.topic_names = names;
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
        //SearchItemViewModel viewModel = (SearchItemViewModel) view.getTag();
        //setKeywords(viewModel.keyword.get());
        //getViewModel().addSearchItems(new SearchItem[]{createKeywordCached(getKeywords(), mTopicIds)});
        //search();
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
    public void addProductCategoryIdsByTopic(int idTopic, ArrayList<Integer> ids) {
        if (ids.size() > 0)
            mCategoryIds.put(idTopic, TextUtils.join(",", ids));
    }

    @Override
    public void clickSearchHistoryItem(View view) {
        SearchItem item = (SearchItem) view.getTag();
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
        search(item);
    }

    private String getKeywords() {
        return mBinder.etKeyword.getText().toString();
    }

    private void setKeywords(String keywords) {
        mBinder.etKeyword.setText(keywords);
    }

    @Override
    public void updateUISearchCategory(RealmResults<ProductTopic> items) {
        if (items != null) {
            if (mBinder.flTopics.getChildCount() == 0) {
                mBinder.flTopics.removeAllViews();
                for (ProductTopic item : items) {
                    mBinder.flTopics.addView(createCategory(item));
                }
            }
        }
    }

    private boolean containsTopicId(ArrayList<Integer> ids, int id) {
        if (ids == null || ids.size() == 0) return false;
        for (Integer integer : ids) {
            if (integer == id) {
                return true;
            }
        }
        return false;
    }

    private void setSelectedTopics(ArrayList<Integer> idTopic) {
        int count = mBinder.flTopics.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = mBinder.flTopics.getChildAt(i);
            if (view instanceof TextView) {
                if (view.getTag() instanceof ProductTopic) {
                    ProductTopic item = (ProductTopic) view.getTag();
                    if (containsTopicId(idTopic, item.id)) {
                        setTopicSelected((TextView) view);
                    } else setTopicUnselected((TextView) view);
                }
            }
        }
    }

    private void setTopicUnselected(TextView textView) {
        ProductTopic topic = (ProductTopic) textView.getTag();
        textView.setTextColor(ContextCompat.getColor(this, R.color.semi_gray));
        textView.setBackgroundResource(R.drawable.view_rounded_bg_gray_border);
        mCategoryIds.remove(topic.id);
        int index = indexOfTopicNames(topic.name);
        if (index >= 0) {
            mTopicNames.remove(index);
            mTopicIds.remove(index);
        }
    }

    private int indexOfTopicNames(String name) {
        for (int i = 0; i < mTopicNames.size(); i++) {
            if (mTopicNames.get(i).equals(name))
                return i;
        }
        return -1;
    }

    private void setTopicSelected(TextView textView) {
        ProductTopic topic = (ProductTopic) textView.getTag();
        int whiteColor = ContextCompat.getColor(this, R.color.white);
        textView.setTextColor(whiteColor);
        textView.setBackgroundResource(R.drawable.view_rounded_bg_orange_large_radius);
        getViewModel().getProductCategoryIdsByTopic(topic.id);
        mTopicNames.add(topic.name);
        mTopicIds.add(topic.id);
    }

    @Override
    public void clickSearchCategoryItem(View view) {
        if (view instanceof TextView) {
            int whiteColor = ContextCompat.getColor(this, R.color.white);
            TextView textView = (TextView) view;
            boolean selected = textView.getCurrentTextColor() == whiteColor;
            if (selected) {
                // Unselected it
                setTopicUnselected(textView);
            } else {
                // Selected it
                setTopicSelected(textView);
            }
        }
    }

    @Override
    public void showAlert(String message) {
        AlertManager.getInstance(this).showLongToast(message);
    }

    private View createCategory(ProductTopic item) {
        ViewItemProductSearchCategoryBinding binding = ViewItemProductSearchCategoryBinding.inflate(getLayoutInflater(), null, false);
        binding.setData(item);
        binding.getRoot().setTag(item);
        binding.setNavigator(this);
        return binding.getRoot();
    }

    private View createTag(SearchItem item) {
        ViewItemProductSearchTagBinding binding = ViewItemProductSearchTagBinding.inflate(getLayoutInflater(), null, false);
        binding.setData(item);
        binding.getRoot().setTag(item);
        binding.setNavigator(this);
        return binding.getRoot();
    }
}
