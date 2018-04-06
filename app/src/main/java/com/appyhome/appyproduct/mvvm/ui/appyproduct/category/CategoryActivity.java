package com.appyhome.appyproduct.mvvm.ui.appyproduct.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appyhome.appyproduct.mvvm.AppConstants;
import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCategory;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductSub;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductCategoryBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.ProductCartListActivity;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter.CategoryAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter.CategoryItemNavigator;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter.CategoryItemViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.category.adapter.SubCategoryAdapter;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.ProductListActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.common.component.cart.SearchToolbarViewHolder;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
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
    SubCategoryAdapter mSubCategoryAdapter;

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
    public void onItemClick(CategoryItemViewModel viewModel) {
        if (viewModel.isSub) {
            mSubCategoryAdapter.clickViewModel(viewModel);
            Intent intent = ProductListActivity.getStartIntent(this);
            intent.putExtra("id_sub", viewModel.getIdCategory());
            startActivity(intent);
        } else {
            showSelectedCategory(viewModel);
        }
    }

    private void showSelectedCategory(CategoryItemViewModel viewModel) {
        mCategoryAdapter.clickViewModel(viewModel);
        getViewModel().getProductSubCategoryByCategory(viewModel.getIdCategory());
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
        mCategoryViewModel.getProductTopicById(idTopic);
        mSearchToolbarViewHolder = new SearchToolbarViewHolder(this, mBinder.toolbar, true, true, "");
    }

    private void setUpRecyclerViewGrid(RecyclerView rv, SampleAdapter adapter) {
        int subColumns = calculateSubColumns();
        rv.setLayoutManager(new GridLayoutManager(this,
                subColumns, GridLayoutManager.VERTICAL,
                false));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
    }

    private void setUpRecyclerViewList(RecyclerView rv, SampleAdapter adapter) {
        ViewUtils.setUpRecyclerViewList(rv, false);
        rv.setAdapter(adapter);
    }

    @Override
    public void handleErrorService(Throwable throwable) {
        AlertManager.getInstance(this).showLongToast(getString(R.string.error_unknown));
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
        mSubCategoryAdapter.addItems(result, this);
        mSubCategoryAdapter.notifyDataSetChanged();
        mSearchToolbarViewHolder.onBind(0);
    }


    @Override
    public void showCategories(RealmResults<ProductCategory> result) {
        mCategoryAdapter.addItems(result, this);
        mCategoryAdapter.notifyDataSetChanged();
        if (result != null && result.size() > 0) {
            showSelectedCategory((CategoryItemViewModel) mCategoryAdapter.getItem(0));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mSearchToolbarViewHolder.onBind(0);
    }

    private int calculateSubColumns() {
        int widthScreen = AppConstants.SCREEN_WIDTH;
        int widthLeftMenu = getResources().getDimensionPixelSize(R.dimen.category_left_menu);
        int padding = getResources().getDimensionPixelSize(R.dimen.sub_padding);
        int space = widthScreen - widthLeftMenu - 2 * padding;
        int widthItem = getResources().getDimensionPixelSize(R.dimen.menu_sub_categories_width) + 2 * padding;
        int value = Math.round(space / widthItem);
        return value;
    }
}
