package com.appyhome.appyproduct.mvvm.ui.notification;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.databinding.ViewItemNotificationBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_NORMAL = 1;

    private List<NotificationItemViewModel> mNotificationList;

    public NotificationAdapter(ArrayList arrayList) {
        this.mNotificationList = arrayList;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                ViewItemNotificationBinding itemViewBinding = ViewItemNotificationBinding
                        .inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new NotificationItemViewHolder(itemViewBinding);
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
        if (mNotificationList != null && mNotificationList.size() > 0) {
            return mNotificationList.size();
        }
        return 0;
    }

    public class NotificationItemViewHolder extends BaseViewHolder{

        private ViewItemNotificationBinding mBinding;


        public NotificationItemViewHolder(ViewItemNotificationBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final NotificationItemViewModel mItemViewModel = mNotificationList.get(position);
            mBinding.setViewModel(mItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();
        }

    }
}