package com.appyhome.appyproduct.mvvm.ui.appyproduct.tracking.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductOrder;
import com.appyhome.appyproduct.mvvm.databinding.ActivityProductOrderListBinding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import javax.inject.Inject;

import io.realm.RealmResults;

public class ListProductOrderActivity extends BaseActivity<ActivityProductOrderListBinding, ListProductOrderViewModel> implements ListProductOrderNavigator {

    @Inject
    public ListProductOrderViewModel mMainViewModel;

    ActivityProductOrderListBinding mBinder;

    @Inject
    int mLayoutId;

    ListProductOrderAdapter vActiveAdapter;
    ListProductOrderAdapter vHistoryAdapter;
    ListProductOrderAdapter vClosedAdapter;

    private Button mCurrentTab;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ListProductOrderActivity.class);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setNavigator(this);
        mBinder.setViewModel(mMainViewModel);
        mMainViewModel.setNavigator(this);
        vActiveAdapter = new ListProductOrderAdapter();
        vHistoryAdapter = new ListProductOrderAdapter();
        vClosedAdapter = new ListProductOrderAdapter();
        mBinder.activeRecyclerView.setAdapter(vActiveAdapter);
        mBinder.historyRecyclerView.setAdapter(vHistoryAdapter);
        mBinder.closedRecyclerView.setAdapter(vClosedAdapter);
        ViewUtils.setUpRecyclerViewListVertical(mBinder.activeRecyclerView, false);
        ViewUtils.setUpRecyclerViewListVertical(mBinder.historyRecyclerView, false);
        ViewUtils.setUpRecyclerViewListVertical(mBinder.closedRecyclerView, false);
        getViewModel().syncAllProductOrders();
        mCurrentTab = mBinder.btRequest;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public ListProductOrderViewModel getViewModel() {
        return mMainViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void showAlert(String message) {
        if (!isFinishing())
            AlertManager.getInstance(this).showLongToast(message);
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void done_syncAllProductOrders(RealmResults<ProductOrder> orders) {
        vActiveAdapter.newAdapter();
        vHistoryAdapter.newAdapter();
        vClosedAdapter.newAdapter();
        for (ProductOrder order : orders) {
            switch (order.status) {
                case OrderItemViewModel.STATUS_ACTIVE:
                    vActiveAdapter.addItem(order, this);
                    break;
                case OrderItemViewModel.STATUS_HISTORY:
                    vHistoryAdapter.addItem(order, this);
                    break;
                case OrderItemViewModel.STATUS_CLOSED:
                    vClosedAdapter.addItem(order, this);
                    break;
            }
        }
        if (vActiveAdapter.getItemSize() > 0) {
            mBinder.btRequest.setText("Request (" + vActiveAdapter.getItemSize() + ")");
        } else mBinder.btRequest.setText("Request");

        if (vHistoryAdapter.getItemSize() > 0) {
            mBinder.btOrders.setText("Orders (" + vActiveAdapter.getItemSize() + ")");
        } else mBinder.btOrders.setText("Orders");

        if (vClosedAdapter.getItemSize() > 0) {
            mBinder.btClosed.setText("Closed (" + vActiveAdapter.getItemSize() + ")");
        } else mBinder.btClosed.setText("Closed");

        vActiveAdapter.notifyDataSetChanged();
        vHistoryAdapter.notifyDataSetChanged();
        vClosedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSwitchTab(View view) {
        if (view instanceof Button) {
            Button btn = (Button) view;
            btn.setBackgroundResource(R.color.colorAccent);
            btn.setTextColor(ContextCompat.getColor(this, R.color.white));
            if (mCurrentTab != null) {
                mCurrentTab.setTextColor(ContextCompat.getColor(this, R.color.semi_gray));
                mCurrentTab.setBackgroundResource(R.color.white);
            }
            mCurrentTab = btn;
            switch (view.getId()) {
                case R.id.btRequest:
                    mBinder.activeRecyclerView.setVisibility(View.VISIBLE);
                    mBinder.historyRecyclerView.setVisibility(View.GONE);
                    mBinder.closedRecyclerView.setVisibility(View.GONE);
                    break;
                case R.id.btOrders:
                    mBinder.activeRecyclerView.setVisibility(View.GONE);
                    mBinder.historyRecyclerView.setVisibility(View.VISIBLE);
                    mBinder.closedRecyclerView.setVisibility(View.GONE);
                    break;
                case R.id.btClosed:
                    mBinder.activeRecyclerView.setVisibility(View.GONE);
                    mBinder.historyRecyclerView.setVisibility(View.GONE);
                    mBinder.closedRecyclerView.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }
}
