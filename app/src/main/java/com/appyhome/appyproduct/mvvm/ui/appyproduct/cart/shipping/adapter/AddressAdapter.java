package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Address;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductShippingAddressBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.ShippingAddressViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

import java.util.ArrayList;

import io.realm.RealmResults;

public class AddressAdapter extends SampleAdapter implements AddressItemNavigator {

    private AddressItemViewModel mSelected;

    public AddressAdapter() {
        mSelected = null;
        mItems = null;
    }

    @Override
    public void onClick(View view) {
        // DO NOTHING
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

    private AddressItemViewModel createViewModel(Address address, ShippingAddressViewModel viewModel) {
        AddressItemViewModel itemViewModel = new AddressItemViewModel(viewModel.getDataManager(), viewModel.getSchedulerProvider());
        itemViewModel.name.set(address.customer_name);
        itemViewModel.phoneNumber.set(address.phone_number);
        itemViewModel.address.set(address.address);
        itemViewModel.checked.set(address.is_default);
        itemViewModel.setIdAddress(address.id);
        itemViewModel.setNavigator(this);
        if (address.is_default)
            mSelected = itemViewModel;
        return itemViewModel;
    }

    public void updateAdapter(RealmResults<Address> results, ShippingAddressViewModel viewModel) {
        mItems = new ArrayList<>();
        if (results != null) {
            for (Address item : results) {
                mItems.add(createViewModel(item, viewModel));
            }
        }
    }

    @Override
    protected AddressItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductShippingAddressBinding itemViewBinding = ViewItemProductShippingAddressBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AddressItemViewHolder(itemViewBinding);
    }

    @Override
    public void updateDatabaseCompleted(AddressItemViewModel viewModel) {

    }

    public class AddressItemViewHolder extends BaseViewHolder {

        private ViewItemProductShippingAddressBinding mBinding;

        private AddressItemViewHolder(ViewItemProductShippingAddressBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public ViewItemProductShippingAddressBinding getBinding() {
            return mBinding;
        }

        @Override
        public void onBind(int position) {
            AddressItemViewModel viewModel = (AddressItemViewModel) mItems.get(position);
            if (mBinding != null) {
                mBinding.setViewModel(viewModel);
                mBinding.llItemView.setOnClickListener(v -> {
                    if (mSelected != null)
                        mSelected.checked.set(false);
                    viewModel.checked.set(true);
                    viewModel.updateDefaultToDatabase();
                    mSelected = viewModel;
                });
            }
        }
    }
}