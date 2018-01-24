package com.appyhome.appyproduct.mvvm.ui.servicerequest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemRequestBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_NORMAL = 1;
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_LOADING = -1;

    private int mType = RequestFragment.TYPE_REQUEST;

    private ArrayList<RequestItemViewModel> mRequestList;

    public RequestAdapter(ArrayList arrayList, int type) {
        this.mRequestList = arrayList;
        mType = type;
    }

    public void updateData(ArrayList list) {
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
        View view = inflater.inflate(getEmptyLayoutId(parent), parent, false);
        return new RequestItemEmptyViewHolder(view);
    }
    private int getEmptyLayoutId(ViewGroup parent) {
        int layoutId = parent.getId();
        if (mType == RequestFragment.TYPE_REQUEST) {
            layoutId = R.layout.view_item_request_empty;
        }
        if (mType == RequestFragment.TYPE_ORDER) {
            layoutId = R.layout.view_item_order_empty;
        }
        if (mType == RequestFragment.TYPE_CLOSED) {
            layoutId = R.layout.view_item_closed_empty;
        }
        return layoutId;
    }
    @Override
    public int getItemViewType(int position) {
        if (mRequestList == null) {
            return VIEW_TYPE_LOADING; // loading item
        }
        if (mRequestList != null && mRequestList.size() == 0) {
            return VIEW_TYPE_EMPTY; // empty item
        }
        return VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if (mRequestList == null) {
            return 1; // loading item
        }
        if (mRequestList != null && mRequestList.size() == 0) {
            return 1; // empty item
        }
        if (mRequestList != null && mRequestList.size() > 0) {
            return mRequestList.size();
        }
        return 1;
    }

    public class RequestItemEmptyViewHolder extends BaseViewHolder {
        public RequestItemEmptyViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBind(int position) {

        }
    }

    public class RequestItemLoadingViewHolder extends BaseViewHolder {
        public RequestItemLoadingViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBind(int position) {

        }
    }

    public class RequestItemViewHolder extends BaseViewHolder {

        private ViewItemRequestBinding mBinding;

        public RequestItemViewHolder(ViewItemRequestBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (mRequestList != null && mRequestList.size() > 0) {
                final RequestItemViewModel itemViewModel = mRequestList.get(position);
                mBinding.setViewModel(itemViewModel);
                mBinding.executePendingBindings();
                mBinding.tvTitle.setText(itemViewModel.title.get());
                mBinding.tvTimeCreated.setText(itemViewModel.timeCreated.get());
            }
        }

    }
}