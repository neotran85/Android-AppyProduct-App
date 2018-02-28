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
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.ProductListActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

import io.realm.RealmResults;

public class CategoryActivity extends BaseActivity<ActivityProductCategoryBinding, CategoryViewModel> implements CategoryNavigator, CategoryItemNavigator {
    @Inject
    CategoryViewModel mCategoryViewModel;
    ActivityProductCategoryBinding mBinder;
    private CategoryAdapter mCategoryAdapter;
    private CategoryAdapter mSubCategoryAdapter;

    public static final int  ID_DEFAULT_TOPIC = 73;
    public static final int  DEFAULT_SPAN_COUNT = 2;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, CategoryActivity.class);
        return intent;
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
        mCategoryViewModel.setNavigator(this);
        mCategoryAdapter = new CategoryAdapter(null);
        mSubCategoryAdapter = new CategoryAdapter(null);
        setUpRecyclerViewList(mBinder.categoryRecyclerView, mCategoryAdapter);
        setUpRecyclerViewGrid(mBinder.subCategoryRecyclerView, mSubCategoryAdapter);
        int idTopic = getIntent().getIntExtra("id_topic", ID_DEFAULT_TOPIC);
        mCategoryViewModel.getProductCategoryByTopic(idTopic);
        mCategoryViewModel.getProductTopicById(idTopic);
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
        AlertManager.getInstance(this).showLongToast(getString(R.string.error_network_general));
    }

    @Override
    public void showContent(CategoryAdapter adapter, View view, int id) {
        if(adapter == mBinder.categoryRecyclerView.getAdapter()) {
            mCategoryViewModel.getProductSubCategoryByCategory(id);
        }
        if(adapter == mBinder.subCategoryRecyclerView.getAdapter()) {
            Intent intent = ProductListActivity.getStartIntent(this);
            intent.putExtra("id_sub", id);
            startActivity(intent);
        }
    }

    @Override
    public void showErrorServer() {
        showAlert(getString(R.string.login_error_internal_server));
    }

    @Override
    public void showErrorOthers() {
        showAlert(getString(R.string.login_error));
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
    public int getLayoutId() {
        return R.layout.activity_product_category;
    }
}
