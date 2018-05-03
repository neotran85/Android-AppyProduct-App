package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.Address;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductShippingAddressBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import io.realm.RealmResults;

public class AddressAdapter extends SampleAdapter<Address, AddressItemNavigator> {

    private AddressItemViewModel mSelected;
    private DataManager mDataManager;
    private SchedulerProvider mSchedulerProvider;
    private AddressItemNavigator mNavigator;

    public AddressAdapter(DataManager dataManager, SchedulerProvider schedulerProvider) {
        mSelected = null;
        mItems = null;
        mDataManager = dataManager;
        mSchedulerProvider = schedulerProvider;
    }

    public void selectAddress(AddressItemViewModel viewModel) {
        if (mSelected != null) {
            mSelected.checked.set(false);
        }
        viewModel.checked.set(true);
        viewModel.updateDefaultToDatabase();
        mSelected = viewModel;
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

    private AddressItemViewModel createViewModel(Address address, AddressItemNavigator itemNavigator) {
        AddressItemViewModel itemViewModel = new AddressItemViewModel(mDataManager, mSchedulerProvider);
        itemViewModel.name.set(address.recipient_name);
        itemViewModel.phoneNumber.set(address.recipient_phone_number);
        itemViewModel.address.set(address.getAddressText());
        itemViewModel.checked.set(address.is_default == 1);
        itemViewModel.setIdAddress(address.id);
        itemViewModel.setNavigator(itemNavigator);
        if (address.is_default == 1)
            mSelected = itemViewModel;
        return itemViewModel;
    }

    @Override
    public void addItems(RealmResults<Address> results, AddressItemNavigator navigator) {
        mItems = new ArrayList<>();
        mNavigator = navigator;
        if (results != null) {
            for (Address item : results) {
                mItems.add(createViewModel(item, navigator));
            }
        }
    }

    @Override
    protected AddressItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemProductShippingAddressBinding itemViewBinding = ViewItemProductShippingAddressBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AddressItemViewHolder(itemViewBinding, mNavigator);
    }

    public class AddressItemViewHolder extends BaseViewHolder {
        private ViewItemProductShippingAddressBinding mBinding;

        private AddressItemViewHolder(ViewItemProductShippingAddressBinding binding, AddressItemNavigator navigator) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setNavigator(navigator);
        }

        @Override
        public void onBind(int position) {
            AddressItemViewModel viewModel = (AddressItemViewModel) mItems.get(position);
            mBinding.setViewModel(viewModel);
        }
    }
}