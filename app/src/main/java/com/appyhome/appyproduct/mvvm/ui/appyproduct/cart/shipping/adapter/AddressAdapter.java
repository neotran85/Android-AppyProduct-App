package com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.AppyAddress;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemProductShippingAddressBinding;
import com.appyhome.appyproduct.mvvm.ui.appyproduct.cart.shipping.newaddress.NewAddressActivity;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;
import com.appyhome.appyproduct.mvvm.ui.common.sample.adapter.SampleAdapter;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import io.realm.RealmResults;

public class AddressAdapter extends SampleAdapter<AppyAddress, AddressItemNavigator> implements AddressItemNavigator {

    private AddressItemViewModel mSelected;
    private DataManager mDataManager;
    private SchedulerProvider mSchedulerProvider;
    private AddressItemNavigator mNavigator;
    private Context mContext;

    public AddressAdapter(DataManager dataManager, SchedulerProvider schedulerProvider) {
        mSelected = null;
        mItems = null;
        mDataManager = dataManager;
        mSchedulerProvider = schedulerProvider;
    }

    @Override
    public void updateDatabaseCompleted() {

    }

    @Override
    public void onItemChecked(AddressItemViewModel viewModel) {
        if (viewModel != null) {
            if (mSelected != null) {
                mSelected.checked.set(false);
            }
            viewModel.checked.set(true);
            viewModel.updateDefaultToDatabase();
            mSelected = viewModel;
        }
    }

    @Override
    public void removeAddress(AddressItemViewModel viewModel) {
        if (viewModel != null) {
            viewModel.removeAddress();
            int index = indexOf(viewModel);
            mItems.remove(viewModel);
            notifyItemRemoved(index);
            if (mItems.size() > 0 && viewModel.checked.get()) {
                onItemChecked((AddressItemViewModel) mItems.get(0));
            }
        }
    }

    @Override
    public void editAddress(AddressItemViewModel item) {
        if (mContext != null && item != null) {
            Intent intent = NewAddressActivity.getStartIntent(mContext);
            intent.putExtra("address_id", item.getAddressId());
            mContext.startActivity(intent);
        }
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

    private AddressItemViewModel createViewModel(AppyAddress address, AddressItemNavigator itemNavigator) {
        AddressItemViewModel itemViewModel = new AddressItemViewModel(mDataManager, mSchedulerProvider);
        itemViewModel.name.set(address.recipient_name);
        itemViewModel.phoneNumber.set(address.recipient_phone_number);
        itemViewModel.address.set(address.getAddressText());
        itemViewModel.checked.set(address.is_default == 1);
        if (address.company_name != null && address.company_name.length() > 0)
            itemViewModel.companyName.set("Company: " + address.company_name);
        itemViewModel.setIdAddress(address.id);
        itemViewModel.setNavigator(itemNavigator);
        if (address.is_default == 1)
            mSelected = itemViewModel;
        return itemViewModel;
    }

    @Override
    public void addItems(RealmResults<AppyAddress> results, AddressItemNavigator navigator) {
        mItems = new ArrayList<>();
        mNavigator = navigator;
        if (results != null) {
            for (AppyAddress item : results) {
                mItems.add(createViewModel(item, navigator));
            }
        }
    }

    @Override
    public AddressItemViewHolder getContentHolder(ViewGroup parent) {
        mContext = parent.getContext();
        ViewItemProductShippingAddressBinding itemViewBinding = ViewItemProductShippingAddressBinding
                .inflate(LayoutInflater.from(mContext), parent, false);
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