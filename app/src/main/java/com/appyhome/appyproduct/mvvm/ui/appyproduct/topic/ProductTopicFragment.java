package com.appyhome.appyproduct.mvvm.ui.appyproduct.topic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductTopic;
import com.appyhome.appyproduct.mvvm.databinding.FragmentProductTopicBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.category.CategoryActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.topic.adapter.TopicAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.topic.adapter.TopicItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.topic.adapter.TopicItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseFragment;
import com.appyhome.appyproduct.mvvm.utils.helper.CompletedJobListener;

import javax.inject.Inject;

import io.realm.RealmResults;

public class ProductTopicFragment extends BaseFragment<FragmentProductTopicBinding, ProductTopicViewModel> implements ProductTopicNavigator, TopicItemNavigator {

    public static final String TAG = "ProductTopicFragment";
    public static final int DEFAULT_SPAN_COUNT = 4;

    @Inject
    ProductTopicViewModel mViewModel;

    FragmentProductTopicBinding mBinder;

    @Inject
    TopicAdapter mAdapter;

    @Inject
    int mLayoutId;

    private CompletedJobListener mListener;

    /************************* LIFE RECYCLE METHODS ************************/

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /************************* SETUP  ************************/

    private void setUp() {
        mViewModel.setNavigator(this);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mViewModel);
        mBinder.topicsRecyclerView.setAdapter(mAdapter);
        setUpRecyclerViewGrid(mBinder.topicsRecyclerView);
        getViewModel().getAllProductTopics();
    }

    public void setCompletedJobListener(CompletedJobListener listener) {
        mListener = listener;
    }

    private void setUpRecyclerViewGrid(RecyclerView rv) {
        rv.setLayoutManager(new GridLayoutManager(this.getActivity(),
                DEFAULT_SPAN_COUNT, GridLayoutManager.VERTICAL,
                false));
        rv.setItemAnimator(new DefaultItemAnimator());
    }

    /************************* GET METHODS ************************/

    public static ProductTopicFragment newInstance() {
        Bundle args = new Bundle();
        ProductTopicFragment fragment = new ProductTopicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ProductTopicViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    /************************* USER INTERACTION METHODS ************************/

    @Override
    public void onItemClick(TopicItemViewModel viewModel) {
        Intent intent = CategoryActivity.getStartIntent(this.getContext());
        intent.putExtra("id_topic", viewModel.getIdTopic());
        startActivity(intent);
    }

    /************************* NAVIGATOR METHODS ************************/

    @Override
    public void getAllProductTopics_Done(RealmResults<ProductTopic> topics) {
        if (topics != null && topics.size() > 0) {
            mAdapter.addItems(topics, this);
            mAdapter.notifyDataSetChanged();
            if (mListener != null) {
                mListener.onJobCompleted(null);
            }
        }
    }
}
