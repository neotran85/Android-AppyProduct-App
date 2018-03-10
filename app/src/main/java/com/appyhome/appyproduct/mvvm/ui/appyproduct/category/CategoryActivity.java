package com.appyhome.appyproduct.mvvm.ui.appyproduct.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCategory;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductSub;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductCategoryBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.ProductCartListActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter.CategoryAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter.CategoryItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.ProductListActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.common.component.cart.SearchToolbarViewHolder;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

import io.realm.RealmResults;

public class CategoryActivity extends BaseActivity<ActivityProductCategoryBinding, CategoryViewModel> implements CategoryNavigator, CategoryItemNavigator {
    public static final int ID_DEFAULT_TOPIC = 73;
    public static final int DEFAULT_SPAN_COUNT = 2;
    @Inject
    CategoryViewModel mCategoryViewModel;
    @Inject
    CategoryAdapter mCategoryAdapter;
    @Inject
    CategoryAdapter mSubCategoryAdapter;
    ActivityProductCategoryBinding mBinder;
    @Inject
    int mLayoutId;
    private SearchToolbarViewHolder mSearchToolbarViewHolder;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, CategoryActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    public void openProductCart() {
        Intent intent = ProductCartListActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void showAlert(String message) {
        AlertManager.getInstance(this).showLongToast(message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mCategoryViewModel);
        mBinder.setNavigator(this);
        mCategoryViewModel.setNavigator(this);
        setUpRecyclerViewList(mBinder.categoryRecyclerView, mCategoryAdapter);
        setUpRecyclerViewGrid(mBinder.subCategoryRecyclerView, mSubCategoryAdapter);
        int idTopic = getIntent().getIntExtra("id_topic", ID_DEFAULT_TOPIC);
        mCategoryViewModel.getProductCategoriesByTopic(idTopic);
        mCategoryViewModel.getProductTopicById(idTopic);
        mSearchToolbarViewHolder = new SearchToolbarViewHolder(this, mBinder.toolbar);
    }

    private void setUpRecyclerViewGrid(RecyclerView rv, CategoryAdapter adapter) {
        rv.setLayoutManager(new GridLayoutManager(this,
                DEFAULT_SPAN_COUNT, GridLayoutManager.VERTICAL,
                false));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
    }

    private void setUpRecyclerViewList(RecyclerView rv, CategoryAdapter adapter) {
        rv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
    }

    @Override
    public void handleErrorService(Throwable throwable) {
        AlertManager.getInstance(this).showLongToast(getString(R.string.error_unknown));
    }

    @Override
    public void showContent(CategoryAdapter adapter, View view, int id) {
        if (adapter.isCategory()) {
            mCategoryViewModel.getProductSubCategoryByCategory(id);
        }
        if (adapter.isSubCategory()) {
            Intent intent = ProductListActivity.getStartIntent(this);
            intent.putExtra("id_sub", id);
            startActivity(intent);
        }
    }

    @Override
    public CategoryViewModel getViewModel() {
        return mCategoryViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void showSubCategories(RealmResults<ProductSub> result) {
        mSubCategoryAdapter.addItems(result, CategoryAdapter.TYPE_SUB_CATEGORY, this);
        mSubCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCategories(RealmResults<ProductCategory> result) {
        mCategoryAdapter.addItems(result, CategoryAdapter.TYPE_CATEGORY, this);
        mCategoryAdapter.notifyDataSetChanged();
        mCategoryAdapter.clickTheFirstItem();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSearchToolbarViewHolder.onBind(0);
    }

}
