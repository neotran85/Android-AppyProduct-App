package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.ProductListViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

import java.util.ArrayList;

import io.realm.RealmResults;

public class ProductAdapter extends SampleAdapter {

    private String imageTestPath = "https://redbean2013.files.wordpress.com/2013/07/38361-paul_smith_iphone_5_case_strip_car.jpg";

    public ProductAdapter() {
        this.mItems = null;
    }

    @Override
    public void onClick(View view) {}

    public void addItems(Product[] results, ProductItemNavigator navigator, ArrayList<Integer> favoritesId) {
        mItems = new ArrayList<>();
        if (results != null) {
            for (Product item : results) {
                boolean isFavorite = checkIfFavorite(item.id, favoritesId);
                mItems.add(createViewModel(item, navigator, isFavorite));
            }
        }
    }

    private boolean checkIfFavorite(int id, ArrayList<Integer> listId) {
        if(listId == null || listId.size() <= 0) return false;
        for(Integer item: listId) {
            if(item.equals(id))
                return true;
        }
        return false;
    }

    @Override
    public void recycle() {
        mItems.clear();
        mItems = null;
    }

    @Override
    protected int getEmptyItemLayout() {
        return R.layout.view_item_sample_empty;
    }

    private ProductItemViewModel createViewModel(Product product, ProductItemNavigator navigator, boolean isFavorited) {
        BaseViewModel viewModel = navigator.getMainViewModel();
        ProductItemViewModel itemViewModel = new ProductItemViewModel(viewModel.getDataManager(), viewModel.getSchedulerProvider());
        itemViewModel.title.set(product.product_name);
        itemViewModel.imageURL.set(product.avatar_name);
        itemViewModel.setIdProduct(product.id);
        itemViewModel.price.set("RM " + product.price);
        itemViewModel.setNavigator(navigator);
        itemViewModel.isFavorite.set(isFavorited);
        itemViewModel.rate.set(product.rate);
        itemViewModel.rateCount.set(product.rate_count + "");
        itemViewModel.favoriteCount.set(product.favorite_count + "");
        return itemViewModel;
    }

    public void addItems(RealmResults<Product> results, ProductItemNavigator navigator, ArrayList<Integer> favoritesId) {
        mItems = new ArrayList<>();
        if (results != null) {
            for (Product item : results) {
                boolean isFavorite = checkIfFavorite(item.id, favoritesId);
                mItems.add(createViewModel(item, navigator, isFavorite));
            }
        }
    }

    public void addItems(RealmResults<Product> results, ProductItemNavigator navigator, boolean isAllFavorited) {
        mItems = new ArrayList<>();
        if (results != null) {
            for (Product item : results) {
                mItems.add(createViewModel(item, navigator, isAllFavorited));
            }
        }
    }

    @Override
    protected ProductItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductBinding itemViewBinding = ViewItemProductBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductItemViewHolder(itemViewBinding);
    }

    public class ProductItemViewHolder extends BaseViewHolder {

        private ViewItemProductBinding mBinding;

        public ViewItemProductBinding getBinding() {
            return mBinding;
        }

        private ProductItemViewHolder(ViewItemProductBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.tvOriginalPrice.setPaintFlags(mBinding.tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        private View.OnClickListener getListener(ProductItemViewModel viewModel) {
            return v -> {
                switch (v.getId()) {
                    case R.id.ibAddFavorite:
                        viewModel.updateProductFavorite(mItems.indexOf(viewModel));
                        break;
                    case R.id.llItemView:
                        viewModel.addProductToCart();
                        break;
                }
            };
        }

        @Override
        public void onBind(int position) {
            ProductItemViewModel viewModel = (ProductItemViewModel) mItems.get(position);
            if (mBinding != null) {
                mBinding.setViewModel(viewModel);
                View.OnClickListener listener = getListener(mBinding.getViewModel());
                mBinding.ibAddFavorite.setOnClickListener(listener);
                mBinding.llItemView.setOnClickListener(listener);
            }
        }
    }
}