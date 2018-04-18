package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.table;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemTableRowBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemTableRowWideBinding;
import com.appyhome.appyproduct.mvvm.ui.common.component.LinearListView;
import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Nullable;

public class TableLayout extends LinearListView<TabletItem> {

    public TableLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public View getItemView(int position) {
        if (position != getItemCount() - 1) {
            TabletItem data = getItem(position);
            ViewItemTableRowBinding binding = ViewItemTableRowBinding.inflate(LayoutInflater.from(getContext()), this, false);
            binding.setData(data);
            //int max = Math.max(binding.contentText.getLineCount(), binding.titleText.getLineCount());
            //binding.contentText.setLines(max);
            //binding.titleText.setLines(max);
            return binding.getRoot();
        } else {
            TabletItem data = getItem(position);
            ViewItemTableRowWideBinding binding = ViewItemTableRowWideBinding.inflate(LayoutInflater.from(getContext()), this, false);
            binding.setData(data);
            return binding.getRoot();
        }
    }

    public void loadData(ProductVariant variant) {
        removeAllViews();

        ArrayList<TabletItem> tableAdapter = new ArrayList<>();
        tableAdapter.add(new TabletItem("Product's name", variant.product_name, 10));
        tableAdapter.add(new TabletItem("Price", "RM " + variant.price, 1));
        tableAdapter.add(new TabletItem("Manufacturer", variant.getManufacturer(), 1));
        HashMap<String, String> parseDes = variant.parseDescription();
        if (parseDes != null) {
            for (String key : parseDes.keySet()) {
                tableAdapter.add(new TabletItem(key, parseDes.get(key), 10));
            }
        }
        tableAdapter.add(new TabletItem("Type / Color / Size", variant.variant_name, 2));
        tableAdapter.add(new TabletItem("Dimension (cm)", variant.getSize(), 1));
        tableAdapter.add(new TabletItem("Weight", DataUtils.roundNumber(variant.weight, 2) + " Kg", 1));
        tableAdapter.add(new TabletItem("Stock's quantity", variant.quantity + " (" + variant.getAvailable() + ")", 1));
        String warranty = variant.getWarranty();
        if (warranty.length() > 0)
            tableAdapter.add(new TabletItem("Warranty", warranty, 10));
        tableAdapter.add(new TabletItem("Shipping From", variant.getShippingFrom(), 1));
        tableAdapter.add(new TabletItem("Est. Delivery Date", variant.getDeliveryEstimation(), 10));
        tableAdapter.add(new TabletItem("Payment Methods", "VISA, Master Card, Mol Pay", 1));
        tableAdapter.add(new TabletItem("Seller Name", variant.seller_name, 1));
        tableAdapter.add(new TabletItem("After Sales Service", "Please contact seller", 1));
        tableAdapter.add(new TabletItem("Return / Exchange", getContext().getString(R.string.return_notice), 10));
        tableAdapter.add(new TabletItem("Available Date", "Since " + variant.date_available, 1));
        tableAdapter.add(new TabletItem("Updated Date", variant.updated_at, 1));
        tableAdapter.add(new TabletItem("Description", variant.getDescription(), 100));
        setAdapter(tableAdapter);
    }
}
