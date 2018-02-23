package com.appyhome.appyproduct.mvvm.ui.appyservice.servicerequest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemRequestBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<BaseViewHolder> implements View.OnClickListener {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_LOADING = -1;

    private int mType = RequestType.TYPE_REQUEST;

    private ArrayList<RequestItemViewModel> mRequestList;

    private OnItemClickListener onItemClickListener;

    private int[] idEmptyLayout = {R.layout.view_item_request_empty, R.layout.view_item_order_empty,
            R.layout.view_item_closed_empty};
    private int[] mRequestTypes = {RequestType.TYPE_REQUEST, RequestType.TYPE_ORDER, RequestType.TYPE_CLOSED};

    public RequestAdapter(ArrayList<RequestItemViewModel> arrayList, int type) {
        this.mRequestList = arrayList;
        mType = type;
    }

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, mType);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public void updateData(ArrayList<RequestItemViewModel> list) {
        mRequestList = list;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewItemRequestBinding itemViewBinding;
        LayoutInflater inflater;
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

    private BaseViewHolder getDefaultHolder(ViewGroup parent) {
        return new BaseViewHolder(parent) {
            @Override
            public void onBind(int position) {
                // Do nothing
            }
        };
    }

    private RequestItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemRequestBinding itemViewBinding = ViewItemRequestBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        itemViewBinding.getRoot().setOnClickListener(this);
        return new RequestItemViewHolder(itemViewBinding);
    }

    private RequestItemLoadingViewHolder getLoadingHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View loadingView = inflater.inflate(R.layout.view_item_loading, parent, false);
        return new RequestItemLoadingViewHolder(loadingView);
    }

    private RequestItemEmptyViewHolder getEmptyHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View view = inflater.inflate(getEmptyLayoutId(), parent, false);
        return new RequestItemEmptyViewHolder(view);
    }


    private int getEmptyLayoutId() {
        int index = 0;
        for (int i = 0; i < mRequestTypes.length; i++) {
            if (mRequestTypes[i] == mType)
                index = i;
        }
        return idEmptyLayout[index];
    }

    @Override
    public int getItemViewType(int position) {
        if (mRequestList == null) {
            return VIEW_TYPE_LOADING; // loading item
        }
        if (mRequestList.size() == 0) {
            return VIEW_TYPE_EMPTY; // empty item
        }
        return VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if (mRequestList != null && mRequestList.size() > 0) {
            return mRequestList.size();
        }
        return 1;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int type);
    }

    public class RequestItemEmptyViewHolder extends BaseViewHolder {
        private RequestItemEmptyViewHolder(View view) {
            super(view);
        }
        @Override
        public void onBind(int position) {

        }
    }

    public class RequestItemLoadingViewHolder extends BaseViewHolder {
        private RequestItemLoadingViewHolder(View view) {
            super(view);
        }
        @Override
        public void onBind(int position) {

        }
    }

    public class RequestItemViewHolder extends BaseViewHolder {

        private ViewItemRequestBinding mBinding;

        private RequestItemViewHolder(ViewItemRequestBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (mRequestList != null && mRequestList.size() > 0) {
                final RequestItemViewModel itemViewModel = mRequestList.get(position);
                mBinding.setViewModel(itemViewModel);
                mBinding.executePendingBindings();
                mBinding.getRoot().setTag(itemViewModel.getIdNumber());
                mBinding.tvTitle.setText(itemViewModel.title.get());
                String time = "";
                switch (itemViewModel.getType()) {
                    case RequestType.TYPE_REQUEST:
                        mBinding.tvStatus.setText(RequestStatus.AWAITING_SCHEDULE_CONFIRMATION);
                        time = itemViewModel.timeCreated.get();
                        time = time != null ? time : "";
                        time = "Created at: " + time;
                        break;
                    case RequestType.TYPE_CLOSED:
                        mBinding.tvStatus.setText(RequestStatus.CLOSED);
                        time = itemViewModel.timeCreated.get();
                        time = "Closed at: " + time;
                        break;
                    case RequestType.TYPE_ORDER:
                        mBinding.tvStatus.setText(itemViewModel.statusOfOrder.get());
                        time = itemViewModel.dateTime1.get();
                        time = time != null ? time : "";
                        time = "Scheduled for: " + time;
                        break;
                }
                mBinding.tvTimeCreated.setText(time);
            }
        }

    }
}