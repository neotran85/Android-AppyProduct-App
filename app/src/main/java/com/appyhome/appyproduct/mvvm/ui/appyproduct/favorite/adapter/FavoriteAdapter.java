package com.appyhome.appyproduct.mvvm.ui.appyproduct.favorite.adapter;

import com.appyhome.appyproduct.mvvm.ui.appyproduct.product.list.adapter.ProductAdapter;

public class FavoriteAdapter extends ProductAdapter{

    public void removedFavorite(int position, boolean isFavorite) {
        if(!isFavorite) {
            if (mItems != null && mItems.size() > 0) {
                mItems.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    public int getFavoriteCount() {
        return mItems.size();
    }

}
