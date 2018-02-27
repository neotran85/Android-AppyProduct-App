package com.appyhome.appyproduct.mvvm.ui.appyproduct.category;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductCategory;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemCategoryBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewHolder;

import java.util.ArrayList;

import io.realm.RealmResults;

public class CategoryAdapter extends RecyclerView.Adapter<BaseViewHolder> implements View.OnClickListener {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_LOADING = -1;

    private ArrayList<CategoryItemViewModel> mCategories;

    private OnItemClickListener onItemClickListener;

    public CategoryAdapter(ArrayList<CategoryItemViewModel> arrayList) {
        this.mCategories = arrayList;
    }

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public void updateData(RealmResults<ProductCategory> results) {
        mCategories = new ArrayList<>();
        for(ProductCategory item: results) {
            CategoryItemViewModel itemViewModel = new CategoryItemViewModel();
            itemViewModel.title.set(item.name);
            itemViewModel.imageURL.set(item.thumbnail);
            mCategories.add(itemViewModel);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

    private CategoryItemViewHolder getContentHolder(ViewGroup parent) {
        ViewItemCategoryBinding itemViewBinding = ViewItemCategoryBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        itemViewBinding.getRoot().setOnClickListener(this);
        return new CategoryItemViewHolder(itemViewBinding);
    }

    private CategoryItemLoadingViewHolder getLoadingHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View loadingView = inflater.inflate(R.layout.view_item_category_loading, parent, false);
        return new CategoryItemLoadingViewHolder(loadingView);
    }

    private CategoryItemEmptyViewHolder getEmptyHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View view = inflater.inflate(R.layout.view_item_category_empty, parent, false);
        return new CategoryItemEmptyViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (mCategories == null) {
            return VIEW_TYPE_LOADING; // loading item
        }
        if (mCategories.size() == 0) {
            return VIEW_TYPE_EMPTY; // empty item
        }
        return VIEW_TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if (mCategories != null && mCategories.size() > 0) {
            return mCategories.size();
        }
        return 1;
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public class CategoryItemEmptyViewHolder extends BaseViewHolder {
        private CategoryItemEmptyViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBind(int position) {

        }
    }

    public class CategoryItemLoadingViewHolder extends BaseViewHolder {
        private CategoryItemLoadingViewHolder(View view) {
            super(view);
        }
        @Override
        public void onBind(int position) {}
    }

    public class CategoryItemViewHolder extends BaseViewHolder {

        private ViewItemCategoryBinding mBinding;

        private CategoryItemViewHolder(ViewItemCategoryBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
        }

    }
}