package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Address;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemShippingAddressBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

import java.util.ArrayList;

import io.realm.RealmResults;

public class AddressAdapter extends SampleAdapter {
    @Override
    public void onClick(View view) {
        // DO NOTHING
    }

    @Override
    protected int getEmptyItemLayout() {
        return R.layout.view_item_sample_empty;
    }

    private AddressItemViewModel createViewModel(Address address) {
        AddressItemViewModel itemViewModel = new AddressItemViewModel();
        itemViewModel.name.set(address.customer_name);
        itemViewModel.phoneNumber.set(address.phone_number);
        itemViewModel.address.set(address.address);
        return itemViewModel;
    }

    public AddressAdapter(RealmResults<Address> results) {
        mItems = new ArrayList<>();
        if (results != null) {
            for (Address item : results) {
                mItems.add(createViewModel(item));
            }
        }
    }

    @Override
    protected AddressItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemShippingAddressBinding itemViewBinding = ViewItemShippingAddressBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AddressItemViewHolder(itemViewBinding);
    }

    public class AddressItemViewHolder extends BaseViewHolder {

        private ViewItemShippingAddressBinding mBinding;

        public ViewItemShippingAddressBinding getBinding() {
            return mBinding;
        }

        private AddressItemViewHolder(ViewItemShippingAddressBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            AddressItemViewModel viewModel = (AddressItemViewModel) mItems.get(position);
            if (mBinding != null) {
                mBinding.setViewModel(viewModel);
                mBinding.llItemView.setTag(mBinding.getViewModel());
                mBinding.llItemView.setOnClickListener(AddressAdapter.this);
            }
        }
    }
}