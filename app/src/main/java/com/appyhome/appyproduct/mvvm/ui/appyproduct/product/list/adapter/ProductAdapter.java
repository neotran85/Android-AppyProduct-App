package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Product;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;

import java.util.ArrayList;

import io.realm.RealmResults;

public class ProductAdapter extends RecyclerView.Adapter<BaseViewHolder> implements View.OnClickListener {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_LOADING = -1;
    private ArrayList<ProductItemViewModel> mItems;
    private String imageTestPath = "https://redbean2013.files.wordpress.com/2013/07/38361-paul_smith_iphone_5_case_strip_car.jpg";
    public ProductAdapter(ArrayList<ProductItemViewModel> arrayList) {
        this.mItems = arrayList;
    }

    @Override
    public void onClick(View view) {

    }

    public void addItems(Product[] results, ProductItemNavigator navigator) {
        mItems = new ArrayList<>();
        if (results != null) {
            for (Product item : results) {
                mItems.add(getViewModel(item, navigator));
            }
        }
    }

    private ProductItemViewModel getViewModel(Product product, ProductItemNavigator navigator) {
        ProductItemViewModel itemViewModel = new ProductItemViewModel();
        itemViewModel.title.set(product.product_name);
        itemViewModel.imageURL.set(imageTestPath);
        itemViewModel.setIdCategory(product.category_id);
        itemViewModel.setIdProduct(product.id);
        itemViewModel.setNavigator(navigator);
        return itemViewModel;
    }

    public void addItems(RealmResults<Product> results, ProductItemNavigator navigator) {
        mItems = new ArrayList<>();
        if (results != null) {
            for (Product item : results) {
                mItems.add(getViewModel(item, navigator));
            }
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return getContentHolder(parent);
            case VIEW_TYPE_EMPTY:
                return getEmptyHolder(parent);
            case VIEW_TYPE_LOADING:
                return getLoadingHolder(parent);
            default:
                return getDefaultHolder(parent);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        holder.onBind(position);
    }

    private BaseViewHolder getDefaultHolder(ViewGroup parent) {
        return new BaseViewHolder(parent) {
            @Override
            public void onBind(int position) {
                // Do nothing
            }
        };
    }

    private ProductItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductBinding itemViewBinding = ViewItemProductBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductItemViewHolder(itemViewBinding);
    }

    private ProductItemLoadingViewHolder getLoadingHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View loadingView = inflater.inflate(R.layout.view_item_product_loading, parent, false);
        return new ProductItemLoadingViewHolder(loadingView);
    }

    private ProductItemEmptyViewHolder getEmptyHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View view = inflater.inflate(R.layout.view_item_product_empty, parent, false);
        return new ProductItemEmptyViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems == null) {
            return VIEW_TYPE_LOADING; // loading item
        }
        if (mItems.size() == 0) {
            return VIEW_TYPE_EMPTY; // empty item
        }
        return VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if (mItems != null && mItems.size() > 0) {
            return mItems.size();
        }
        return 1;
    }

    public class ProductItemEmptyViewHolder extends BaseViewHolder {
        private ProductItemEmptyViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBind(int position) {
        }
    }

    public class ProductItemLoadingViewHolder extends BaseViewHolder {
        private ProductItemLoadingViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBind(int position) {
        }
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

        @Override
        public void onBind(int position) {
            ProductItemViewModel viewModel = mItems.get(position);
            if (mBinding != null) {
                mBinding.setViewModel(viewModel);
                mBinding.llItemView.setTag(mBinding.getViewModel());
                mBinding.llItemView.setOnClickListener(ProductAdapter.this);
            }
        }
    }
}