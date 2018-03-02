package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCart;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductCartItemBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.list.ProductCartListViewModel;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.ProductListActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

import java.util.ArrayList;

import io.realm.RealmResults;

public class ProductCartAdapter extends SampleAdapter {

    private String imageTestPath = "https://redbean2013.files.wordpress.com/2013/07/38361-paul_smith_iphone_5_case_strip_car.jpg";
    private ProductCartListViewModel mViewModel;
    private boolean isChangedByUser = false;

    public ProductCartAdapter(ProductCartListViewModel viewModel) {
        this.mItems = null;
        mViewModel = viewModel;
    }

    @Override
    public void onClick(View view) {
        Object tag = view.getTag();
        if (tag instanceof ProductCartItemViewModel) {
            ProductCartItemViewModel viewModel = (ProductCartItemViewModel) tag;
            viewModel.getNavigator().showContent(this, view, viewModel.getProductId());
        }
    }

    public void onUpdateDatabase() {
        if(isChangedByUser) {
            if (mItems != null && mItems.size() > 0) {
                for (BaseViewModel item : mItems) {
                    ProductCartItemViewModel cartItem = (ProductCartItemViewModel) item;
                    cartItem.productCartUpdate();
                }
            }
            isChangedByUser = false;
        }
    }

    private ProductCartItemViewModel createViewModel(ProductCart productCart, ProductCartItemNavigator navigator) {
        ProductCartItemViewModel itemViewModel = new ProductCartItemViewModel(mViewModel.getDataManager(),
                mViewModel.getSchedulerProvider());
        itemViewModel.title.set(productCart.product_name);
        //itemViewModel.imageURL.set(productCart.product_avatar);
        itemViewModel.imageURL.set(imageTestPath);
        itemViewModel.setProductCartId(productCart.id);
        itemViewModel.setProductId(productCart.product_id);
        itemViewModel.sellerName.set(productCart.seller_name);
        itemViewModel.amount.set(productCart.amount + "");
        itemViewModel.price.set(productCart.price + "");
        itemViewModel.setNavigator(navigator);
        itemViewModel.checked.set(productCart.checked);
        return itemViewModel;
    }

    public void addItems(RealmResults<ProductCart> results, ProductCartItemNavigator navigator) {
        mItems = new ArrayList<>();
        String sellerName = "";
        if (results != null) {
            for (ProductCart item : results) {
                ProductCartItemViewModel cartItem = createViewModel(item, navigator);
                if(!sellerName.equals(item.seller_name)) {
                    sellerName = item.seller_name;
                    cartItem.isFirstProductOfStore.set(true);
                }else {
                    cartItem.isFirstProductOfStore.set(false);
                }
                mItems.add(cartItem);
            }
        }
    }

    @Override
    protected ProductCartItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductCartItemBinding itemViewBinding = ViewItemProductCartItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductCartItemViewHolder(itemViewBinding);
    }

    public class ProductCartItemViewHolder extends BaseViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        private ViewItemProductCartItemBinding mBinding;

        public ViewItemProductCartItemBinding getBinding() {
            return mBinding;
        }

        private ProductCartItemViewHolder(ViewItemProductCartItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.tvOriginalPrice.setPaintFlags(mBinding.tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            mBinding.btnDecrease.setOnClickListener(this);
            mBinding.btnIncrease.setOnClickListener(this);
            mBinding.cbWillBuy.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View view) {
            int amount = 0;
            ProductCartItemViewModel viewModel = mBinding.getViewModel();
            switch (view.getId()) {
                case R.id.btnDecrease:
                    amount = Integer.valueOf(mBinding.getViewModel().amount.get());
                    if (amount > 0) {
                        amount = amount - 1;
                    } else amount = 0;
                    break;
                case R.id.btnIncrease:
                    amount = Integer.valueOf(mBinding.getViewModel().amount.get()) + 1;
                    break;
            }
            isChangedByUser = true;
            viewModel.amount.set(amount + "");
        }

        @Override
        public void onBind(int position) {
            ProductCartItemViewModel viewModel = (ProductCartItemViewModel) mItems.get(position);
            if (mBinding != null) {
                mBinding.setViewModel(viewModel);
                mBinding.llItemView.setTag(mBinding.getViewModel());
                mBinding.llItemView.setOnClickListener(ProductCartAdapter.this);
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            isChangedByUser = true;
            mBinding.getViewModel().checked.set(isChecked);
        }
    }
}