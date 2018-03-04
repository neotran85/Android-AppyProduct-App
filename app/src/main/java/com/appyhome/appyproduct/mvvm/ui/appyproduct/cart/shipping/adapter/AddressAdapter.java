package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Address;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductShippingAddressBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.ShippingAddressViewModel;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;

import java.util.ArrayList;

import io.realm.RealmResults;

public class AddressAdapter extends SampleAdapter {

    private int posDefaultAddress = -1;
    private int posFirstDefaultAddress = 0;

    @Override
    public void onClick(View view) {
        // DO NOTHING
    }

    @Override
    protected int getEmptyItemLayout() {
        return R.layout.view_item_sample_empty;
    }

    private AddressItemViewModel createViewModel(Address address, ShippingAddressViewModel viewModel) {
        AddressItemViewModel itemViewModel = new AddressItemViewModel(viewModel.getDataManager(), viewModel.getSchedulerProvider());
        itemViewModel.name.set(address.customer_name);
        itemViewModel.phoneNumber.set("Phone Number: " + address.phone_number);
        itemViewModel.address.set(address.address);
        itemViewModel.checked.set(address.is_default);
        itemViewModel.setIdAddress(address.id);
        return itemViewModel;
    }

    public AddressAdapter(RealmResults<Address> results, ShippingAddressViewModel viewModel) {
        mItems = new ArrayList<>();
        if (results != null) {
            for (Address item : results) {
                mItems.add(createViewModel(item, viewModel));
            }
        }
    }

    private void updateDefaultAddressItem(int pos) {
        AddressItemViewModel vAddressItemViewModel = (AddressItemViewModel) mItems.get(pos);
        vAddressItemViewModel.updateDefaultToDatabase();
    }
    public void updateDefaultAddressToDatabase() {
        if(posDefaultAddress > 0) {
            updateDefaultAddressItem(posDefaultAddress);
            updateDefaultAddressItem(posFirstDefaultAddress);
        }
    }

    private void updateCheckedDefaultAddress(AddressItemViewModel defaultAddress) {
        for(BaseViewModel item: mItems) {
            AddressItemViewModel vAddressItemViewModel = (AddressItemViewModel) item;
            if(vAddressItemViewModel.checked.get()) {
                vAddressItemViewModel.checked.set(false);
                notifyViewModelChanged(vAddressItemViewModel);
            }
        }
        defaultAddress.checked.set(true);
        posDefaultAddress = mItems.indexOf(defaultAddress);
        notifyViewModelChanged(defaultAddress);
    }

    private void notifyViewModelChanged(AddressItemViewModel item) {
        notifyItemChanged(mItems.indexOf(item));
    }

    @Override
    protected AddressItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductShippingAddressBinding itemViewBinding = ViewItemProductShippingAddressBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AddressItemViewHolder(itemViewBinding);
    }

    public class AddressItemViewHolder extends BaseViewHolder implements View.OnClickListener{

        private ViewItemProductShippingAddressBinding mBinding;

        public ViewItemProductShippingAddressBinding getBinding() {
            return mBinding;
        }

        private AddressItemViewHolder(ViewItemProductShippingAddressBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        @Override
        public void onClick(View view) {
            updateCheckedDefaultAddress(mBinding.getViewModel());
        }

        @Override
        public void onBind(int position) {
            AddressItemViewModel viewModel = (AddressItemViewModel) mItems.get(position);
            if (mBinding != null) {
                mBinding.setViewModel(viewModel);
                mBinding.llItemView.setTag(mBinding.getViewModel());
                mBinding.llItemView.setOnClickListener(AddressAdapter.this);
                mBinding.rbDefault.setOnClickListener(this);
            }
        }
    }
}