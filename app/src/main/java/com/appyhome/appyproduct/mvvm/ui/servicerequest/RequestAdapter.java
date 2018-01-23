package com.appyhome.appyproduct.mvvm.ui.servicerequest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.databinding.ViewItemRequestBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_NORMAL = 1;

    private ArrayList<RequestItemViewModel> mRequestList;

    public RequestAdapter(ArrayList arrayList) {
        this.mRequestList = arrayList;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                ViewItemRequestBinding itemViewBinding = ViewItemRequestBinding
                        .inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new RequestItemViewHolder(itemViewBinding);
            default:
                return new BaseViewHolder(parent) {
                    @Override
                    public void onBind(int position) {
                        // Do nothing
                    }
                };
        }
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if (mRequestList != null && mRequestList.size() > 0) {
            return mRequestList.size();
        }
        return 0;
    }

    public class RequestItemViewHolder extends BaseViewHolder {

        private ViewItemRequestBinding mBinding;


        public RequestItemViewHolder(ViewItemRequestBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if(mRequestList != null && mRequestList.size() > 0) {
                final RequestItemViewModel itemViewModel = mRequestList.get(position);
                mBinding.setViewModel(itemViewModel);
                mBinding.executePendingBindings();
                mBinding.tvTitle.setText(itemViewModel.title.get());
                mBinding.tvTimeCreated.setText(itemViewModel.timeCreated.get());
            }
        }

    }
}